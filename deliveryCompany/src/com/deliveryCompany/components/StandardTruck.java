package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

public class StandardTruck extends Truck {
	/**
	 * 
	 */
	private int maxWeight;
	/**
	 * 
	 */
	private Branch destination;
	
	/**
	 * 
	 */
	public StandardTruck () {
		super();
		maxWeight = 70;
		destination = MainOffice.getHub();
		System.out.println("Creating " + toString());
		
	}

	/**
	 * @param licensePlate
	 * @param truckModel
	 * @param maxWeight
	 */
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
		destination = null;
		System.out.println("Creating " + toString());
	}
	
	/**
	 *
	 */
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

	/**
	 * @return
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * @param maxWeight
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * @return
	 */
	public Branch getDestination() {
		return destination;
	}

	/**
	 * @param destination
	 */
	public void setDestination(Branch destination) {
		this.destination = destination;
	}
	
	/**
	 *
	 */
	public void work() {
		if (!isAvailable())
			setTimeLeft(getTimeLeft()-1);
		if(!isAvailable() && getTimeLeft() == 0) {
			System.out.printf("StandardTruck %d arrived to %s\n", getTruckID(), destination.getBranchName());
			// System.out.printf("StandardTruck %d loaded packages at %s\n", getTruckID(), destination.getBranchName());
			for(Package pack : getPackages()) {
				if(pack.getStatus() == Status.BRANCH_TRANSPORT) {
					pack.setStatus(Status.DELIVERY);
					deliverPackage(pack);
				}
				else if(pack.getStatus() == Status.HUB_TRANSPORT) {
					pack.setStatus(Status.HUB_STORAGE);
					deliverPackage(pack);
				}
			}
			getPackages().clear();
			System.out.printf("StandardTruck %d unloaded packages at %s\n", getTruckID(), destination.getBranchName());
			if (destination instanceof Hub) {
				setAvailable(true);
			}
			else
			{
				int currentWeight = 0;
				ArrayList<Package> tempPackages = new ArrayList<Package>();
				for (Package pack : destination.getListPackages()) {
					if(pack.getStatus() == Status.BRANCH_STORAGE) {
						if (!checkCapacityAdd(pack, currentWeight, Status.HUB_TRANSPORT))
							break;
						else tempPackages.add(pack);
					}
				}
				destination.getListPackages().removeAll(tempPackages);
				Random rand = new Random();
				setTimeLeft(1 + rand.nextInt(6));
				System.out.printf("StandardTruck %d loaded packages at %s\n", getTruckID(), destination.getBranchName());
				setDestination(MainOffice.getHub());
				System.out.printf("StandardTruck %d is on it's way to the %s, time to arrive: %d\n", getTruckID(),
						destination.getBranchName(), getTimeLeft());
			}
		}
	}

	/**
	 *
	 */
	@Override
	public void collectPackage(Package p) {
		p.addTracking(this, p.getStatus());
		addPackage(p);
	}

	/**
	 *
	 */
	@Override
	public void deliverPackage(Package p) {
		p.addTracking(destination, p.getStatus());
		destination.addPackage(p);
	}

	/**
	 * @param p
	 * @param currentWeight
	 * @param status
	 * @return
	 */
	public boolean checkCapacityAdd(Package p, int currentWeight, Status status) {
		double temp;
		if (p instanceof SmallPackage && (1 + currentWeight <= getMaxWeight())) 
				temp = 1;
		else if (p instanceof StandardPackage && (((StandardPackage)p).getWeight() + currentWeight) <= getMaxWeight())
			temp = ((StandardPackage)p).getWeight();
		else if (p instanceof NonStandardPackage) return true;
		else return false;
		currentWeight += temp;
		p.setStatus(status);
		collectPackage(p);
		return true;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return "StandardTruck " + super.toString();
	}
	
	/**
	 *
	 */
	public String getSimpleName() {
		return "StandardTruck";
	}
	
	/**
	 *
	 */
	protected String truckCharacteristics() {
		return ", maxWeight=" + getMaxWeight();
	}
	
}
