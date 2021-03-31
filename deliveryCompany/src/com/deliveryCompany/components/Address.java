package com.deliveryCompany.components;

public class Address {
	private int zip;
	private int street;
	
	public Address (int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	public String addressString() {
		return zip + "-" + street;
	}
}
