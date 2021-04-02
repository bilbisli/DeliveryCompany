package com.deliveryCompany.components;

import java.util.Random;

public class StandardTruck extends Truck {
	private int maxWeight;
	private Branch destination;
	
	public StandardTruck () {
		super();
		maxWeight = 70;
		destination = MainOffice.getHub();
		System.out.println("Creating " + toString());
		
	}

	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
		destination = null;
		System.out.println("Creating " + toString());
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
	
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			if(getTimeLeft() == 0) {
				System.out.printf("StandardTruck %d loaded packages at %s\n", getTruckID(), destination.getBranchName());
				for(Package pack : getPackages()) {
					if(pack.getStatus() == Status.BRANCH_TRANSPORT)
						pack.setStatus(Status.DELIVERY);
					else if(pack.getStatus() == Status.HUB_TRANSPORT)
						pack.setStatus(Status.HUB_STORAGE);
					deliverPackage(pack);
				}
				getPackages().clear();
				if (destination instanceof Hub)
					setAvailable(true);
				else
				{
					int currentWeight = 0;
					for (Package pack : destination.getListPackages()) {
						if (pack.getStatus() == Status.BRANCH_STORAGE) {
//							destination.checkAddTrack(pack);
							if(!checkCapacityAdd(pack, currentWeight, Status.HUB_TRANSPORT))
								break;
						}
					}
					Random rand = new Random();
					setTimeLeft(rand.nextInt(7));
					setDestination(MainOffice.getHub());
				}
				System.out.printf("StandardTruck %d is on it's way to the %s, time to arrive: %d\n", getTruckID(),
						destination.getBranchName(), getTimeLeft());
			}
		}

	}

	@Override
	public void collectPackage(Package p) {
		p.addTracking(this, p.getStatus());
		addPackage(p);
	}

	@Override
	public void deliverPackage(Package p) {
		System.out.printf("StandardTruck %d arrived to Branch %d\n", getTruckID(), destination.getBranchId());
		p.addTracking(destination, p.getStatus());
		destination.addPackage(p);
	}

	public boolean checkCapacityAdd(Package p, int currentWeight, Status status) {
		double temp;
		
		if (p instanceof SmallPackage && (1 + currentWeight <= getMaxWeight())) 
				temp = 1;
		else if (p instanceof StandardPackage && (((StandardPackage)p).getWeight() + currentWeight) <= getMaxWeight())
			temp = ((StandardPackage)p).getWeight();
		else return false;
		currentWeight += temp;
		p.setStatus(status);
		collectPackage(p);
		return true;
	}

	@Override
	public String toString() {
		return "StandardTruck " + super.toString();
	}
	
	protected String truckCharacteristics() {
		return ", maxWeight=" + getMaxWeight();
	}
	
}
