package com.deliveryCompany.components;

/**
 * This class represents a non standard truck that collects and delivers non standard packages (one at a time)
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
 * @see		Package
 * @see		Truck
 */
public class Van extends Truck {
	/**
	 * Class constructor that creates a van object
	 */
	public Van() {
		super();
		System.out.println("Creating " + toString());
	}
	
	/**
	 * Class constructor that creates a van based on specified characteristics
	 * @param licensePlate the license plate of the van to be set
	 * @param truckModel the model of the van to be set
	 */
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
		System.out.println("Creating " + toString());
	}
	
	/**
	 * This method represents work van - if the van is on it's way the time until it gets to the destination is reduced, 
	 * if the van has reached it's destination the van collects\delivers the package according to the appropriate status of the package
	 */
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
	
	/**
	 * This method handles the completion of collection of a package from a costumer by the van, the appropriate status of the package is set, 
	 * a tracking listing is added and the packaged is removed from the van (it is at the branch now)
	 */
	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.BRANCH_STORAGE);
		p.addTracking(MainOffice.getBranch(p.getSenderAddress().getZip()), Status.BRANCH_STORAGE);
		System.out.printf("Van %d has collected package %d and arrived back to branch %d\n", getTruckID(), p.getPackageID(),
				p.getSenderAddress().getZip());
		removePackages();
		setAvailable(true);
	}
	
	/**
	 * This method handles the delivery of a non standard package from the van to a costumer, the appropriate status of the package is set and a 
	 * tracking listing is added
	 */
	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.DELIVERED);
		p.addTracking(null, Status.DELIVERED);
		System.out.printf("Van %d has delivered package %d to the destination\n", getTruckID(), p.getPackageID());
		if(p instanceof SmallPackage ) {
			if(((SmallPackage) p).isAcknowledge()) {
				System.out.printf("Small package %d with notification arrived to it's destination.\n", p.getPackageID());
			}
		}
		this.setAvailable(true);
		removePackage(p);
	}
	
	/**
	 *
	 */
	@Override
	public String toString() {
		return "Van " + super.toString();
	}
	
	/**
	 * This method constructs a string representation of a van
	 */
	public String getSimpleName() {
		return "Van";
	}
}
