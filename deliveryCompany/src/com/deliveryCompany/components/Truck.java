package com.deliveryCompany.components;
import java.util.Random;
import java.util.ArrayList;

/**
 * Department representing the vehicles for transporting packages.
 * @version 1.00 7 Apr 2021
 * @author  Ofir
 * @see  	Package
 */

public abstract class Truck implements Node {
	/**
	 * Represents the following truck number
	 */
	static private int nextId = 2000; 
	/**
	 * Vehicle serial number
	 */
	private int truckID;
	/**
	 * Vehicle ID number
	 */
	private final String licensePlate;
	/**
	 * Model of vehicle
	 */
	private final String truckModel;
	/**
	 * Vehicle availability
	 */
	private boolean available;
	/**
	 * Time left until the end of the transport
	 */
	private int timeLeft;
	/**
	 * List of packages for transportation that are in the vehicle
	 */
	private ArrayList<Package> packages;
	
	/**
	 * A random default Contractor that produces an object with a license plate and model of a vehicle at random.
	 */
	public Truck() {
		truckID = nextId++;	
		Random rand = new Random();
		truckModel = "M" + rand.nextInt(5);
		available = true;
		packages = new ArrayList<Package>();
		timeLeft = 0;
		licensePlate = generateLicensePlate();
	}
	
	/**
	 * A Contractor who receives as arguments a number plate and model of the vehicle and produces an object.
	 * @param licensePlate - Vehicle ID number
	 * @param truckModel - Model of vehicle
	 */
	public Truck(String licensePlate, String truckModel) {
		this.licensePlate = licensePlate;
		this.truckModel = truckModel;
		available = true;
		packages = new ArrayList<Package>();
		truckID = nextId++;	
		timeLeft = 0;
	}
	
	
	/**
	 * The function returns the next Id of the truck
	 * @return the next Id of the truck
	 */
	public int getnextId() {
		return nextId;
	}
	
	/**
	 * The function returns the Vehicle serial number 
	 * @return the Vehicle serial number 
	 */
	public int getTruckID() {
		return truckID;
	}

	/**
	 * The function sets the value of the truck ID
	 * @param TruckID - The truck ID  which we want to update the entry
	 */
	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}

	/**
	 * The function returns the license plate of the truck 
	 * @return the vehicle serial number 
	 */
	public String getLicensePlate() {
		return licensePlate;
	}


	/**
	 * The function returns the truck model of the truck 
	 * @return the truck model number
	 */
	public String getTruckModel() {
		return truckModel;
	}

	/**
	 * The function returns whether the vehicle is available or not
	 * @return the available of the truck
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * The function sets the value of the available
	 * @param available - The available which we want to update the entry
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * The function returns the time left until the end of the transport
	 * @return the time left
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * The function sets the value of the time left
	 * @param TimeLeft - The time left which we want to update the entry
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * the function returns the Packages of the truck
	 * @return the Packages of the truck
	 */
	public ArrayList<Package> getPackages() {
		return packages;
	}

	/**
	 * The function sets the value of the packages
	 * @param packages - The packages which we want to update the entry
	 */
	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	
	/**
	 * the function returns the Last Package
	 * @return the Last Package
	 */
	public Package getLastPack() {
		return packages.get(packages.size() - 1);
	}
	
	/**
	 * Add a package to the list of packages by index
	 * @param index - the index that we want to add the package
	 * @param p - the package that we want to add to the list
	 */
	public void addPackage(int index, Package p) {
		packages.add(index, p);
	}
	
	/**
	 * Add a package to the list of packages
	 * @param p - the package that we want to add to the list
	 */
	public void addPackage(Package p) {
		packages.add(p);
	}
	
	/**
	 * Adding multiple packages to an list
	 * @param packs - the packs that we want to add to the list
	 */
	public void addPackages(Object packs) {
		packages.addAll((ArrayList<Package>) packs);
	}
	
	/**
	 * Adding multiple packages to an array by index
	 * @param index - the index that we want to add the packages from the list
	 * @param packs - the packs that we want to add to the list
	 */
	public void addPackages(int index, Object packs) {
		packages.addAll(index, (ArrayList<Package>) packs);
	}
	
	/**
	 * The function removes the packet sent from the list by index
	 * @param index - the index that we want to remove the package from the list
	 */
	public void removePackage(int index) {
		packages.remove(index);
	}
	
	/**
	 * The function removes the packet sent from the list
	 * @param p - the package that we want to remove
	 */
	public void removePackage(Package p) {
		packages.remove(p);
	}
	
	/**
	 * removes all packages from the list
	 */
	public void removePackages() {
		packages.clear();
	}


	/**
	 *The function checks whether the two Truck objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Truck other = (Truck) obj;
		if (available != other.available)
			return false;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		if (timeLeft != other.timeLeft)
			return false;
		if (truckID != other.truckID)
			return false;
		if (truckModel == null) {
			if (other.truckModel != null)
				return false;
		} else if (!truckModel.equals(other.truckModel))
			return false;
		return true;
	}

	/**
	 *The function returns the string representation of the object.
	 */
	@Override
	public String toString() {
		return "[truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel 
				+ ", available=" + available + truckCharacteristics() + "]";
		
	}
	
	/**
	 * @return empty string
	 */
	protected String truckCharacteristics() {
		return "";
	}

	/**
	 * the function calculates and return the License Plate of the truck
	 * @return the License Plate of the truck
	 */
	public String generateLicensePlate() {
		int n = 10000000 + new Random().nextInt(100000000);
		return  n / 100000 + "-" + (n % 100000)/1000 + "-" + n % 1000 ;
	}

	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "Truck";
	}	
}

