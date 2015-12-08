package com.enode.android.sectionindexer.headerview;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.enode.android.sectionindexer.headerview.CustomListView.PinnedHeaderPullToRefreshSwipeMenuListView;
import com.enode.android.sectionindexer.headerview.SectionIndexer.SideBladeView;
import com.enode.android.sectionindexer.headerview.adapter.PinnedHeaderSectionAdapter;
import com.enode.android.sectionindexer.headerview.models.Countries;
import com.enode.android.sectionindexer.headerview.parsers.CountriesParser;

public class MainActivity extends Activity
{
	private static final String ENG_FORMAT = "[A-Za-z0-9_\\-]+";
	private com.enode.android.sectionindexer.headerview.CustomListView.PinnedHeaderPullToRefreshSwipeMenuListView mListView;
	private com.enode.android.sectionindexer.headerview.SectionIndexer.SideBladeView mLetter;
	private com.enode.android.sectionindexer.headerview.adapter.PinnedHeaderSectionAdapter mAdapter;

	private List<String> mSections;
	private Map<String, List<com.enode.android.sectionindexer.headerview.models.Countries>> mMap;
	private List<Integer> mPositions;
	private Map<String, Integer> mIndexer;
	private List<String> list11;
	private ArrayList<com.enode.android.sectionindexer.headerview.models.Countries> countriesList;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);
		initData();
		initView();
	}

	public String loadJSONFromAsset()
	{
		String json = null;
		try
		{
			InputStream is = getAssets().open("country.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
			return null;
		}
		return json;
	}

	private void initData()
	{
		String lang = Locale.getDefault().getCountry().toLowerCase();
		// lang ="cn";
		countriesList = new ArrayList<Countries>();
		String json = loadJSONFromAsset();
		countriesList = new CountriesParser().parseData(json);

		// extract initials
		String initial;

		for (int i = 0; i < countriesList.size(); i++)
		{
			initial = countriesList.get(i).getCountryEngName().substring(0, 1);
			countriesList.get(i).setInitial(initial);
		}

		Collections.sort(countriesList, new Comparator<Countries>()
		{
			@Override
			public int compare(Countries lhs, Countries rhs)
			{
				return lhs.getCountryEngName().compareTo(rhs.getCountryEngName());
			}
		});

		list11 = new ArrayList<String>();
		for (int i = 0; i < countriesList.size(); i++)
		{
			String nameToShow;

			nameToShow = countriesList.get(i).getCountryEngName() + " ( +" + countriesList.get(i).getCountryPrefixCode() + ")";

			list11.add(nameToShow);

		}

		mSections = new ArrayList<String>(); // section list
		mMap = new HashMap<String, List<Countries>>(); // section name ->
		mPositions = new ArrayList<Integer>();
		mIndexer = new HashMap<String, Integer>();

		for (int i = 0; i < countriesList.size(); i++)
		{

			String firstName = countriesList.get(i).getInitial();
			if (firstName.matches(ENG_FORMAT))
			{
				if (mSections.contains(firstName))
				{
					mMap.get(firstName).add(countriesList.get(i));
				}
				else
				{
					mSections.add(firstName);
					List<Countries> list = new ArrayList<Countries>();
					list.add(countriesList.get(i));
					mMap.put(firstName, list);
				}
			}
			else
			{

				if (mSections.contains("#"))
				{
					mMap.get("#").add(countriesList.get(i));
				}
				else
				{
					mSections.add("#");
					List<Countries> list = new ArrayList<Countries>();
					list.add(countriesList.get(i));
					mMap.put("#", list);
				}
			}
		}

		Collections.sort(mSections);

		int position = 0;
		for (int i = 0; i < mSections.size(); i++)
		{
			mIndexer.put(mSections.get(i), position);
			mPositions.add(position);
			position += mMap.get(mSections.get(i)).size();
		}
	}

	private void initView()
	{
		// TODO Auto-generated method stub
		mListView = (PinnedHeaderPullToRefreshSwipeMenuListView) findViewById(R.id.friends_display);
		mLetter = (SideBladeView) findViewById(R.id.friends_myletterlistview);
		mLetter.setOnItemClickListener(new SideBladeView.OnItemClickListener()
		{

			@Override
			public void onItemClick(String s)
			{
				if (mIndexer.get(s) != null)
				{
					mListView.setSelection(mIndexer.get(s));
				}
			}
		});
		mAdapter = new PinnedHeaderSectionAdapter(this, list11, mSections, mPositions);
		mListView.setAdapter(mAdapter);
		mListView.setOnScrollListener(mAdapter);
		mListView.setPinnedHeaderView(LayoutInflater.from(this).inflate(R.layout.listview_item, mListView, false));
	}

}
