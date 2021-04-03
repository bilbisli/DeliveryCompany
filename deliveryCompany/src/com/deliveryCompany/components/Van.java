package com.deliveryCompany.components;

public class Van extends Truck {
	public Van() {
		super();
		System.out.println("Creating " + toString());
	}
	
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
		System.out.println("Creating " + toString());
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
		p.addTracking(MainOffice.getBranch(p.getSenderAddress().getZip()), Status.BRANCH_STORAGE);
		System.out.printf("Van %d has collected package %d and arrived back to branch %d\n", getTruckID(), p.getPackageID(),
				p.getSenderAddress().getZip());
		removePackages();
		setAvailable(true);
	}
	
	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.DELIVERED);
		p.addTracking(null, Status.DELIVERED);
		System.out.printf("Van %d has delivered package %d to the destination\n", getTruckID(), p.getPackageID());
		if(p instanceof SmallPackage ) {
			if(((SmallPackage) p).isAcknowledge()) {
				System.out.printf("Van %d has delivered package %d to the destination\n", getTruckID(), p.getPackageID());
			}
		}
		this.setAvailable(true);
		removePackage(p);
	}
	
	@Override
	public String toString() {
		return "Van " + super.toString();
	}
	
	public String getSimpleName() {
		return "Van";
	}
}
