package com.enode.android.sectionindexer.headerview.models;

import java.io.Serializable;

/**
 * Created by abdulrehman on 10/24/15.
 */
public class Countries implements Serializable
{
	private String countryNumber;
	private String countryprefixName;
	private String countryPrefixCode;
	private String countryEngName;
	private String countryChineseName;
	private String initial;

	public String getCountryNumber()
	{
		return countryNumber;
	}

	public void setCountryNumber(String countryNumber)
	{
		this.countryNumber = countryNumber;
	}

	public String getCountryprefixName()
	{
		return countryprefixName;
	}

	public void setCountryprefixName(String countryprefixName)
	{
		this.countryprefixName = countryprefixName;
	}

	public String getCountryEngName()
	{
		return countryEngName;
	}

	public void setCountryEngName(String countryEngName)
	{
		this.countryEngName = countryEngName;
	}

	public String getCountryChineseName()
	{
		return countryChineseName;
	}

	public void setCountryChineseName(String countryChineseName)
	{
		this.countryChineseName = countryChineseName;
	}

	public String getCountryPrefixCode()
	{
		return countryPrefixCode;
	}

	public void setCountryPrefixCode(String countryPrefixCode)
	{
		this.countryPrefixCode = countryPrefixCode;
	}

	public String getInitial() {
		return initial;
	}

	public void setInitial(String initial) {
		this.initial = initial;
	}
}
