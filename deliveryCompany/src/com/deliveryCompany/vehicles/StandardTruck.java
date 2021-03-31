package com.deliveryCompany.vehicles;

import com.deliveryCompany.offices.*;

public class StandardTruck extends Truck {
	private int maxWeight;
	private Branch destination;
	
	public StandardTruck () {}
	
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;	
	}
	
	void work() {}
}
