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
					collectPackage(temp);
				}
				if(temp.getStatus() == Status.DISTRIBUTION) {
					deliverPackage(temp);
				}
			}
		}
	}
	
	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.BRANCH_STORAGE);
		p.addTracking(this, Status.BRANCH_STORAGE);
		System.out.printf("Van %d has collected package %d and arrived back to branch %d", getTruckID(), p.getPackageID(),
				p.getDestinationAddress().getZip());
		this.setAvailable(true);
	}
	
	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.DELIVERED);
		System.out.printf("Van %d has delivered package %d to the destination", getTruckID(), p.getPackageID());
		if(p instanceof SmallPackage ) {
			if(((SmallPackage) p).isAcknowledge()) {
				System.out.printf("Van %d has delivered package %d to the destination", getTruckID(), p.getPackageID());
			}
		}
		this.setAvailable(true);
		removePackage(p);
	}
	
	@Override
	public String toString() {
		return "Van " + super.toString();
	}
}
