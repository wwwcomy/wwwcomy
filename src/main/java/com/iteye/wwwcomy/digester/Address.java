package com.iteye.wwwcomy.digester;

public class Address {
	private String type;
	private String name;
	private String addr;
	private String city;
	private String country;
	private String countryShort;

	public String toString() {
		return "TYPE:	" + type + "\n	NAME: " + name + "\n	ADDRESS:	" + addr
				+ "\n	CITY:	" + city + "\n	COUNTRY:	" + country + "\n	Short	"
				+ countryShort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setCountryShort(String countryShort) {
		this.countryShort = countryShort;
	}

	public String getCountryShort() {
		return countryShort;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}
