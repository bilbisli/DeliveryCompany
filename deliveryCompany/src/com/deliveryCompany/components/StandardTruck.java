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
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardTruck other = (StandardTruck) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (maxWeight != other.maxWeight)
			return false;
		return true;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	public Branch getDestination() {
		return destination;
	}

	public void setDestination(Branch destination) {
		this.destination = destination;
	}

	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
		destination = null;
	}
	
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			if(getTimeLeft() == 0) {
				for(Package pack : getPackages()) {
					if(pack.getStatus() == Status.COLLECTION) {
						System.out.printf("StandardTruck %d has collected package %d and arrived back to branch %d", getTruckID(), p.getPackageID(),
								pack.getDestinationAddress().getZip());
						collectPackage(pack);
					}
					else{
						deliverPackage(pack);
					}
				}
				
			}
			setAvailable(true);
		}

	}
	

	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.HUB_TRANSPORT);
		p.addTracking(this, Status.HUB_TRANSPORT);
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
	public void deliverPackage(Package p) {
		System.out.printf("StandardTruck %d has delivered package %d to the destination", getTruckID(), p.getPackageID());
		p.addTracking(destination, Status.DELIVERY);
		p.setStatus(Status.DELIVERY);
		destination.addPackages(p);
		removePackage(p);
	}

	@Override
	public String toString() {
		return "StandardTruck [maxWeight=" + maxWeight + ", destination=" + destination + super.toString();
	}
	
}
