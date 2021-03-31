package com.deliveryCompany.components;

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

	public void work() {
		
	}

	@Override
	public void collectPackage(Package p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		
	}

}
