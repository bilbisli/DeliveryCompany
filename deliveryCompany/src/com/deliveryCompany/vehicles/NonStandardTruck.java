package com.deliveryCompany.vehicles;

public class NonStandardTruck extends Truck {
	private int width;
	private int length;
	private int height;
	
	public NonStandardTruck () {}
	
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.length = length;
		this.width = width;
		this.height = height;
		
	}
	void work() {}

}
