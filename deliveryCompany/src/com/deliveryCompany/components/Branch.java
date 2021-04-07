package com.deliveryCompany.components;

import java.util.ArrayList;

/**
 * Describes a local branch. Maintains a list of packages stored at the branch or intended for collection
 * from the sender's address to this branch, and a list of vehicles that collect the packages from the 
 * sending customers and deliver the packages to the receiving customers.
 * @version 1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see  	Node
 */

public class Branch implements Node {
	/**
	 * Represents the following branch number
	 */
	private static int nextId = -1; 
	/**
	 * Represents the branch number
	 */
	private final int branchId;
	/**
	 * Represents the branch Name.
	 */
	private String branchName;
	/**
	 * A collection of packages that are in the branch and packages that must be
	 *  collected are shipped by this branch.
	 */
	private ArrayList <Package> listPackages;
	/**
	 * A collection of vehicles belonging to this branch
	 */
	private ArrayList <Truck> listTrucks;
	
	/**
	 * difficult contractor Calculates the serial number of the branch and creates the name of the branch,
	 *  initializing the two remaining fields to empty collections.
	 */
	public Branch() {
		branchId = nextId++;
		branchName = getSimpleName() + " " + branchId;
		listTrucks = new ArrayList <Truck>();
		listPackages = new ArrayList<Package>();
		System.out.println("Creating " + toString());
	}
	
	/**
	 * regular contractor Who gets a branch name, calculates the serial number of the branch,
	 *  the two remaining fields are initialized to empty collections.
	 * @param branchName the specified name to give the newly created branch
	 */
	public Branch(String branchName) {
		branchId = nextId++;
		this.branchName = branchName;
		listTrucks = new ArrayList <Truck>();
		listPackages = new ArrayList<Package>();
		System.out.println("Creating " + toString());
	}
	
	/**
	 * The function checks for each package that is in the branch,
		if it is in the waiting status for collection from a customer, an attempt is made to collect - if there is
		a vehicle available, he goes out to collect the package. The calculation of the travel time and the
		value obtained is updated in the vehicle in the timeLeft field and the condition of the vehicle changes
		to "not available". Same goes for any package waiting for distribution, if there is a vehicle available,
		it is sent to deliver the package.
	 */
	public void work() {
		for (Truck truck : listTrucks)
			truck.work();
		ArrayList<Package> tempPacks = new ArrayList<Package>();
		for (Package pack : listPackages) {
			if (pack.getStatus() == Status.CREATION) {
				Truck t = checkMovePackage(pack, Status.COLLECTION, pack.getSenderAddress());
				if (t != null)
					collectPackage(pack);
			}
			else if (pack.getStatus() == Status.DELIVERY) {
				if (null != checkMovePackage(pack, Status.DISTRIBUTION, pack.getDestinationAddress())) {
					tempPacks.add(pack);
				}
			}
		}
		listPackages.removeAll(tempPacks);
	}
	
	/**
	 * The function represents the Route time of a vehicle
	 * @param address - Parameter in the calculation of Route time
	 * @return the Route time
	 */
	public int calcRouteTime(Address address) {
		return (address.getStreet() / 10) % 10 + 1;
	}

	/**
	 * A method that handles the collection / receipt of a package
	 */
	@Override
	public void collectPackage(Package p) {
		System.out.printf("Van %d is collecting package %d, time to arrive: %d\n",((Truck)p.getLastTrack().getNode()).getTruckID(),
				p.getPackageID(), ((Truck)(p.getLastTrack().getNode())).getTimeLeft());
	}

	/**
	 * A method that handles the delivery of the package to the next person in the transfer chain.
	 */
	@Override
	public void deliverPackage(Package p) {
		checkMovePackage(p, Status.DISTRIBUTION, p.getDestinationAddress());
	}
	
	
	/**
	 * The function checks the trucks in the list, if the truck is free, changing the status of the package,
		adding to the transfer history, changing the availability of the truck, adding the package to the truck
		and updating the time left
	 * @param p - The package we want to add
	 * @param status - The status we want to change for the package
	 * @param address - The address that will help us calculate the time left
	 * @return The truck was available
	 */
	public Truck checkMovePackage(Package p, Status status, Address address) {
		for (Truck truck : listTrucks)
			if (truck.isAvailable()) {
				p.setStatus(status);
				p.addTracking(truck, status);
				truck.setAvailable(false);
				truck.addPackage(p);
				truck.setTimeLeft(calcRouteTime(address));
				return truck;
			}
		return null;
	}
	

	/**
	 * The function returns the string representation of the object.
	 */
	@Override
	public String toString() {
		return getSimpleName() + " " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size() + 
				", trucks: "+ listTrucks.size();
	}

	/**
	 * The function checks whether the two Branch objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Branch other = (Branch) obj;
		if (branchId != other.branchId)
			return false;
		if (branchName == null) {
			if (other.branchName != null)
				return false;
		} else if (!branchName.equals(other.branchName))
			return false;
		if (listPackages == null) {
			if (other.listPackages != null)
				return false;
		} else if (!listPackages.equals(other.listPackages))
			return false;
		if (listTrucks == null) {
			if (other.listTrucks != null)
				return false;
		} else if (!listTrucks.equals(other.listTrucks))
			return false;
		return true;
	}

	/**
	 * The function returns the following branch number
	 * @return the following branch number
	 */
	public static int getNextId() {
		return nextId;
	}

	/**
	 * The function return the Branch Name
	 * @return branch Name
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * The function sets the value of the branch Name
	 * @param branchName - the name that we want to entry
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * The function return the ListTrucks
	 * @return the List Trucks
	 */
	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}

	/**
	 * The function sets the value of the setListTrucks
	 * @param listTrucks - The list with which we want to update the entry
	 */
	public void setListTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;
	}

	/**
	 * The function return the ListPackages
	 * @return  the List Packages
	 */
	public ArrayList<Package> getListPackages() {
		return listPackages;
	}

	/**
	 * The function sets the value of the ListPackages
	 * @param listPackages - The list with which we want to update the entry
	 */
	public void setListPackages(ArrayList<Package> listPackages) {
		this.listPackages = listPackages;
	}

	/**
	 * The function returns the branch number
	 * @return the branch number
	 */
	public int getBranchId() {
		return branchId;
	}
	
	/**
	 * A function adds package to the packages array By index
	 * @param index - Where do we want to add the package
	 * @param p - The package we want to add
	 */
	public void addPackage(int index, Package p) {
		listPackages.add(index, p);
	}
	
	/**
	 * A function adds package to the packages list
	 * @param p - The package we want to add to the packages list
	 */
	public void addPackage(Package p) {
		listPackages.add(p);
	}
	
	/**
	 * A function adds the number of packages to the packages list
	 * @param packs - The packages we want to add to the packages list
	 */
	public void addPackages(Object packs) {
		listPackages.addAll((ArrayList<Package>) packs);
	}
	
	/**
	 * A function adds the number of packages to the packages list
	 * @param index - Where do we want to add the packages
	 * @param packs - The packages we want to add to the packages list
	 */
	public void addPackages(int index, Object packs) {
		listPackages.addAll(index, (ArrayList<Package>) packs);
	}
	
	/**
	 * The function receives a package and removes it according to a specific index from the list of packages
	 * @param index - The location of the package we want to remove
	 */
	public void removePackage(int index) {
		listPackages.remove(index);
	}
	
	/**
	 * The function receives a package and removes it from the list of packages
	 * @param p - The package we want to delete from the package list
	 */
	public void removePackage(Package p) {
		listPackages.remove(p);
	}
	
	/**
	 * The function adds a truck to the truck list
	 * @param index - Where do we want to add the truck
	 * @param t - The truck we want to add
	 */
	public void addTruck(int index, Truck t) {
		listTrucks.add(index, t);
	}
	
	/**
	 * The function adds a truck to the truck list
	 * @param t - truck that we want to remove
	 */
	public void addTruck(Truck t) {
		listTrucks.add(t);
	}
	
	/**
	 * The function adds a number of trucks according to the quantity it receives in the truck list.
	 * @param amount - The amount of trucks you want to add
	 * 			 
	 */
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new Van());
	}
	
	/**
	 * A function that receives a truck and removes it from the list of trucks according to a specific index.
	 * @param index - A specific index where we want to remove trucks
	 */
	public void removeTruck(int index) {
		listTrucks.remove(index);
	}
	
	/**
	 * A function that receives a truck and removes it from the list of trucks.
	 * @param t - truck that we want to remove
	 */
	public void removeTruck(Truck t) {
		listTrucks.remove(t);
	}

	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "Branch";
	}
}
