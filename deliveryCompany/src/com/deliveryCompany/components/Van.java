package com.deliveryCompany.components;

public class Van extends Truck {
	public Van() {}
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
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
