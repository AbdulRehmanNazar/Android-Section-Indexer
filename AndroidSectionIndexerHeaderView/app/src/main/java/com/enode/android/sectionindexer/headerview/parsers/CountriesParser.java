package com.enode.android.sectionindexer.headerview.parsers;


import com.enode.android.sectionindexer.headerview.models.Countries;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

public class CountriesParser
{
	ArrayList<Countries> countriesList;

	public ArrayList<Countries> parseData(String inputString)
	{

		try
		{
			JSONObject jsonData = new JSONObject(inputString);

			JSONArray resultJson = jsonData.getJSONArray("list");

			countriesList = new ArrayList<Countries>();
			for (int i = 0; i < resultJson.length(); i++)
			{
				JSONObject catagoryJSON = resultJson.getJSONObject(i);
				Countries country = new Countries();

				country.setCountryNumber(catagoryJSON.getString("i"));
				country.setCountryprefixName(catagoryJSON.getString("ab"));
				country.setCountryPrefixCode(catagoryJSON.optString("prex"));

				JSONObject countryNameJsonObj = catagoryJSON.getJSONObject("name");

				country.setCountryEngName(countryNameJsonObj.optString("en"));
				country.setCountryChineseName(countryNameJsonObj.optString("cn"));

				countriesList.add(country);

			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return countriesList;
	}
}
