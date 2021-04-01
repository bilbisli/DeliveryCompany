package com.deliveryCompany.components;

import java.util.Random;

public class NonStandardTruck extends Truck {
	private int width;
	private int length;
	private int height;
	
	public NonStandardTruck () {
		super();
		Random rand = new Random();
		width = (rand.nextInt(500));
		length = (rand.nextInt(1000));
		height = (rand.nextInt(400));
	}
	
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.length = length;
		this.width = width;
		this.height = height;
		
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
		System.out.printf("NonStandardTruck %d has collected package %d and arrived back to branch %d", getTruckID(), p.getPackageID(),
				p.getDestinationAddress().getZip());
		this.setAvailable(true);
		
	}

	@Override
	public String toString() {
		return "NonStandardTruck [width=" + width + ", length=" + length + ", height=" + height + ", getWidth()="
				+ getWidth() + ", getLength()=" + getLength() + ", getHeight()=" + getHeight() + ", getnextId()="
				+ getnextId() + ", getTruckID()=" + getTruckID() + ", getLicensePlate()=" + getLicensePlate()
				+ ", getTruckModel()=" + getTruckModel() + ", isAvailable()=" + isAvailable() + ", getTimeLeft()="
				+ getTimeLeft() + ", getPackages()=" + getPackages() + ", getLastPack()=" + getLastPack()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + ", truckCharacteristics()="
				+ truckCharacteristics() + ", generateLicensePlate()=" + generateLicensePlate() + ", getClass()="
				+ getClass() + "]";
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
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
		removePackage(p);
		
	}

}
