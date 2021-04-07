package com.deliveryCompany.components;
import java.util.Random;

/**
 * This class represents a non standard truck that collects and delivers non standard packages (one at a time)
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
 * @see		Package
 * @see		Truck
 */
public class NonStandardTruck extends Truck {
	/**
	 * The trunk width of the truck
	 */
	private int width;
	/**
	 * The trunk length of the truck
	 */
	private int length;
	/**
	 * The trunk height of the truck
	 */
	private int height;
	
	/**
	 * Class constructor that creates a non standard truck with randomly generated characteristics
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
	 * Class constructor that creates a non standard truck based on specified characteristics
	 * @param licensePlate the license plate of the truck to be set
	 * @param truckModel the model of the truck to be set
	 * @param length the length of the truck to be set
	 * @param width the width of the truck to be set
	 * @param height the height of the truck to be set
	 */
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.length = length;
		this.width = width;
		this.height = height;
		System.out.println("Creating " + toString());
	}

	/**
	 * This method represents work of a non standard truck - if the truck is on it's way the time until it gets to the destination is reduced, 
	 * if the truck has reached it's destination the truck collects\delivers the package according to the appropriate status of the package
	 */
	public void work() {
		if(!isAvailable()) {
			setTimeLeft(getTimeLeft()-1);
			if(getTimeLeft() == 0) {
				Package temp = getLastPack();
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
	 * This method handles the collection of a non standard package from a costumer by the truck, the appropriate status of the package is set, 
	 * a tracking listing is added and the delivery time is calculated
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
	 * This method handles the delivery of a non standard package from the truck to a costumer, the appropriate status of the package is set and a 
	 * tracking listing is added
	 */
	@Override
	public void deliverPackage(Package p) {
		p.setStatus(Status.DELIVERED);
		p.addTracking(this, Status.DELIVERED);
		System.out.printf("NonStandardTruck %d has delivered package %d to the destination\n", getTruckID(), p.getPackageID());
		removePackage(p);
		this.setAvailable(true);
	}
	
	/**
	 * This method calculates the time that the delivery will take based on the following formula: 
	 * ((sender street number - destination street number) / 10) % 10 + 1
	 * @return the time calculated for the delivery
	 */
	public int calcTime() {
		return ((Math.abs(getLastPack().getSenderAddress().getStreet()
				- getLastPack().getDestinationAddress().getStreet()) / 10) % 10) + 1;
	}

	/**
	 * This method constructs a string representation of a non standard truck
	 */
	@Override
	public String toString() {
		return "NonStandardTruck " + super.toString();
	}

	/**
	 * This method returns the name of the class
	 * @return a string representation of the name of the class
	 */
	public String getSimpleName() {
		return "NonStandardTruck";
	}

	/**
	 * Equals method for comparing two non standard truck instances
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
	 * Get method for width field
	 * @return the width of the truck
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set method for the width field
	 * @param width the specified width for setting the truck's width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Get method for length field
	 * @return the length of the truck
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Set method for the length field
	 * @param length the specified length for setting the truck's length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Get method for height field
	 * @return the height of the truck
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set method for the height field
	 * @param height the specified height for setting the truck's height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	/**
	 * This method builds a string representation of the non standard truck unique characteristics and returns it
	 */
	protected String truckCharacteristics() {
		return ", length=" + getLength() + ", width=" + getWidth() + ", height=" + getHeight() ;
	}
}
