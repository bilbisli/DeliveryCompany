package com.deliveryCompany.components;

import java.util.ArrayList;

public class Van extends Truck {
	public Van() {
		super();
	}
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
	}
	
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			Package temp = getLastPack();
			if(getTimeLeft() == 0) {
				if(temp.getStatus() == Status.COLLECTION) {
					temp.setStatus(Status.BRANCH_STORAGE);
					temp.addTracking(this, Status.BRANCH_STORAGE);
					System.out.println("");// ya mett
				}
			}
		}
		
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
