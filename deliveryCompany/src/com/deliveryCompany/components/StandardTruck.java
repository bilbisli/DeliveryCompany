package com.deliveryCompany.components;

import java.util.Random;

public class StandardTruck extends Truck {
	private int maxWeight;
	private Branch destination;
	
	public StandardTruck () {
		super();
		maxWeight = 70;
		destination = null;
		
	}
	
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
		destination = null;
	}
	
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			Package temp = getLastPack();
			if(getTimeLeft() == 0) {
				if(temp.getStatus() == Status.COLLECTION) {
					collectPackage(temp);
				}
				else{
					deliverPackage(temp);
				}
			}
		}
	}
	

	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.BRANCH_STORAGE);
		p.addTracking(this, Status.BRANCH_STORAGE);
		System.out.printf("StandardTruck %d has collected package %d and arrived back to branch %d", getTruckID(), p.getPackageID(),
				p.getDestinationAddress().getZip());
		this.setAvailable(true);		
	}

	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.HUB_TRANSPORT);
		p.addTracking(this, Status.HUB_TRANSPORT);
		System.out.printf("StandardTruck %d has delivered package %d to the destination", getTruckID(), p.getPackageID());
		Random rand = new Random();
		this.setTimeLeft(rand.nextInt(7));
		System.out.printf("StandardTruck %d is on it's way to the HUB, time to arrive: %d", getTruckID(),
				p.getDestinationAddress().getZip());
		int sum = 0;
		for(Package pack : getPackages()) {
			if(pack instanceof SmallPackage ) {
				sum += 1;
			}
			else if(pack instanceof StandardPackage ) {
				sum += ((StandardPackage)pack).getWeight();
			}
			if(sum <= maxWeight) {
				this.addPackage(pack);
			}
		} 
		
	}

	@Override
	public String toString() {
		return "StandardTruck [maxWeight=" + maxWeight + ", destination=" + destination + super.toString();
	}
}
