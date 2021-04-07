package com.deliveryCompany.components;

public class Address {
	/**
	 * 
	 */
	private final int zip;
	/**
	 * 
	 */
	private final int street;
	
	/**
	 * @return
	 */
	public int getZip() {
		return zip;
	}
	/**
	 * @return
	 */
	public int getStreet() {
		return street;
	}
	/**
	 * @param zip
	 * @param street
	 */
	public Address (int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	/**
	 * @return
	 */
	public String addressString() {
		return zip + "-" + street;
	}
}
