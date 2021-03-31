package com.deliveryCompany.components;

public class StandardTruck extends Truck {
	private int maxWeight;
	private Branch destination;
	
	public StandardTruck () {}
	
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;	
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
