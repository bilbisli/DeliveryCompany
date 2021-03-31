package com.deliveryCompany.components;

public class Address {
	private final int zip;
	private final int street;
	
	public int getZip() {
		return zip;
	}
	public int getStreet() {
		return street;
	}
	public Address (int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	public String addressString() {
		return zip + "-" + street;
	}
}
