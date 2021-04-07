package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

/**
 * Vehicle for transporting packages from the sorting center to the branches and back.
 * All vehicles of this type are in the sorting center.
 * @version 1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see  	Truck
 */

public class StandardTruck extends Truck {
	/**
	 * Maximum weight that a vehicle can carry.
	 */
	private int maxWeight;
	/**
	 * Target branch / sorting hab 
	 */
	private Branch destination;
	
	/**
	 * A default Contractor that produces an object with a license plate number and a vehicle model at random.
	 */
	public StandardTruck () {
		super();
		maxWeight = 70;
		destination = MainOffice.getHub();
		System.out.println("Creating " + toString());
		
	}

	/**
	 * Contractor that accepts as arguments: license plate number, vehicle model and maximum weight.
	 * @param licensePlate - license plate of the standard truck
	 * @param truckModel - Model of the StandardTruck 
	 * @param maxWeight - Maximum weight that a vehicle can carry.
	 */
	public StandardTruck(String licensePlate,String truckModel,int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
		destination = null;
		System.out.println("Creating " + toString());
	}
	
	/**
	 * The function checks whether the two StandardTruck objects are equal
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
	 * The function returns the maximum weight that the standard truck can carry.
	 * @return the weight of the package
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * The function sets the value of the maximum weight that the standard truck can carry.
	 * @param maxWeight - the max weight that we want to entry
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * The function returns the destination of the standard truck .
	 * @return the destination of the standard truck
	 */
	public Branch getDestination() {
		return destination;
	}

	/**
	 * The function sets the value of the destination of the the standard truck.
	 * @param destination - the destination of the the standard truck.
	 */
	public void setDestination(Branch destination) {
		this.destination = destination;
	}
	
	/**
	 *A vehicle found during a trip reduces the time left to end the trip by 1, if after the reduction the time
	  value is equal to zero, then the trip is over, the vehicle moves all the packages inside to the point it
	  has reached, while updating the status and history of packages. If the trip ended in a sorting center,
	  then the vehicle goes into "free" mode. Otherwise, the trip ended at the local branch - the vehicle loads
	  the packages from this branch and takes them to the sorting center, the status and history of packages is
	  updated. In addition, a new and updated travel time is drawn in the relevant field.
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
						if (checkCapacityAdd(pack, currentWeight, Status.HUB_TRANSPORT))
							tempPackages.add(pack);
						if (currentWeight >= getMaxWeight())
							break;
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
	 * A method that handles the collection / receipt of a package
	 */
	@Override
	public void collectPackage(Package p) {
		p.addTracking(this, p.getStatus());
		addPackage(p);
	}

	/**
	 * A method that handles the delivery of the package to the next person in the transfer chain.
	 */
	@Override
	public void deliverPackage(Package p) {
		p.addTracking(destination, p.getStatus());
		destination.addPackage(p);
	}

	/**
	 * The function checks whether the dimensions actually match the desired package dimensions
	 * @param p - the package that we want to check
	 * @param currentWeight - current Weight of the package
	 * @param status - the package status
	 * @return true if the package was loaded on the truck else false
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
	 *The function returns the string representation of the standard truck.
	 */
	@Override
	public String toString() {
		return "StandardTruck " + super.toString();
	}
	
	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "StandardTruck";
	}
	
	/**
	 * The function returns the string representation of the max weight.
	 */
	protected String truckCharacteristics() {
		return ", maxWeight=" + getMaxWeight();
	}
	
}
