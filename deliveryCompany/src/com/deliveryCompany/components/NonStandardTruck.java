package com.deliveryCompany.components;

import java.util.Random;

public class NonStandardTruck extends Truck {
	/**
	 * 
	 */
	private int width;
	/**
	 * 
	 */
	private int length;
	/**
	 * 
	 */
	private int height;
	
	/**
	 * 
	 */
	public NonStandardTruck () {
		super();
		Random rand = new Random();
		width = 1 + rand.nextInt(500);
		length = 1 + rand.nextInt(1000);
		height = 1 + rand.nextInt(400);

		System.out.println("Creating " + toString());
		

	}
	
	/**
	 * @param licensePlate
	 * @param truckModel
	 * @param length
	 * @param width
	 * @param height
	 */
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.length = length;
		this.width = width;
		this.height = height;
		System.out.println("Creating " + toString());
	}

	/**
	 *
	 */
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			Package temp = getLastPack();
			if(getTimeLeft() == 0) {
				if(temp.getStatus() == Status.COLLECTION) {
					collectPackage(temp);
				}
				else if(temp.getStatus() == Status.DISTRIBUTION) {
					deliverPackage(temp);
					
				}
			}
		}
	}

	/**
	 *
	 */
	@Override
	public void collectPackage(Package p) {
		p.setStatus(Status.DISTRIBUTION);
		p.addTracking(this, Status.DISTRIBUTION);
		setTimeLeft(calcTime());
		System.out.printf("NonStandardTruck %d has collected package %d\n", getTruckID(), p.getPackageID());
		System.out.printf("NonStandardTruck %d is delivering package %d, time left: %d \n", getTruckID(), 
				p.getPackageID(), getTimeLeft());
	}

	/**
	 *
	 */
	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.DELIVERED);
		p.addTracking(this, Status.DELIVERED);
		System.out.printf("NonStandardTruck %d has delivered package %d to the destination\n", getTruckID(), p.getPackageID());
		if(p instanceof SmallPackage ) {
			if(((SmallPackage) p).isAcknowledge()) {
				System.out.printf("NonStandardTruck %d has delivered package %d to the destination", getTruckID(), p.getPackageID());
			}
		}	
		removePackage(p);
		this.setAvailable(true);
	}
	
	/**
	 * @return
	 */
	public int calcTime() {
		return ((Math.abs(getLastPack().getSenderAddress().getStreet()
				- getLastPack().getDestinationAddress().getStreet()) / 10) % 10) + 1;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return "NonStandardTruck " + super.toString();
	}

	/**
	 *
	 */
	public String getSimpleName() {
		return "NonStandardTruck";
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
		NonStandardTruck other = (NonStandardTruck) obj;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return
	 */
	public int getLength() {
		return length;
	}

	/**
	 * @param length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * @return
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 *
	 */
	protected String truckCharacteristics() {
		return ", length=" + getLength() + ", width=" + getWidth() + ", height=" + getHeight() ;
	}
	
	

}
