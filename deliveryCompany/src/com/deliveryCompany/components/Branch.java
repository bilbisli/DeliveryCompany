package com.deliveryCompany.components;

import java.util.ArrayList;

public class Branch implements Node {
	/**
	 * 
	 */
	private static int nextId = -1; 
	/**
	 * 
	 */
	private final int branchId;
	/**
	 * 
	 */
	private String branchName;
	/**
	 * 
	 */
	private ArrayList <Package> listPackages;
	/**
	 * 
	 */
	private ArrayList <Truck> listTrucks;
	
	/**
	 * 
	 */
	public Branch() {
		branchId = nextId++;
		branchName = getSimpleName() + " " + branchId;
		listTrucks = new ArrayList <Truck>();
		listPackages = new ArrayList<Package>();
		System.out.println("Creating " + toString());
	}
	
	/**
	 * @param branchName
	 */
	public Branch(String branchName) {
		branchId = nextId++;
		this.branchName = branchName;
		listTrucks = new ArrayList <Truck>();
		listPackages = new ArrayList<Package>();
		System.out.println("Creating " + toString());
	}
	
	/**
	 *
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
	 * @param address
	 * @return
	 */
	public int calcRouteTime(Address address) {
		return (address.getStreet() / 10) % 10 + 1;
	}

	/**
	 *
	 */
	@Override
	public void collectPackage(Package p) {
		System.out.printf("Van %d is collecting package %d, time to arrive: %d\n",((Truck)p.getLastTrack().getNode()).getTruckID(),
				p.getPackageID(), ((Truck)(p.getLastTrack().getNode())).getTimeLeft());
	}

	/**
	 *
	 */
	@Override
	public void deliverPackage(Package p) {
		checkMovePackage(p, Status.DISTRIBUTION, p.getDestinationAddress());
	}
	
	/**
	 * @param pack
	 */
	public void checkAddTrack(Package pack) {
		if (!pack.getLastTrack().getNode().equals(this))
			pack.addTracking(this, Status.BRANCH_STORAGE);
	}
	
	/**
	 * @param p
	 * @param status
	 * @param address
	 * @return
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
	 *
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + branchId;
		result = prime * result + ((branchName == null) ? 0 : branchName.hashCode());
		result = prime * result + ((listPackages == null) ? 0 : listPackages.hashCode());
		result = prime * result + ((listTrucks == null) ? 0 : listTrucks.hashCode());
		return result;
	}

	/**
	 *
	 */
	@Override
	public String toString() {
		return getSimpleName() + " " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size() + 
				", trucks: "+ listTrucks.size();
	}

	/**
	 *
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
	 * @return
	 */
	public static int getNextId() {
		return nextId;
	}

	/**
	 * @return
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return
	 */
	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}

	/**
	 * @param listTrucks
	 */
	public void setListTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;
	}

	/**
	 * @return
	 */
	public ArrayList<Package> getListPackages() {
		return listPackages;
	}

	/**
	 * @param listPackages
	 */
	public void setListPackages(ArrayList<Package> listPackages) {
		this.listPackages = listPackages;
	}

	/**
	 * @return
	 */
	public int getBranchId() {
		return branchId;
	}
	
	/**
	 * @param index
	 * @param p
	 */
	public void addPackage(int index, Package p) {
		listPackages.add(index, p);
	}
	
	/**
	 * @param p
	 */
	public void addPackage(Package p) {
		listPackages.add(p);
	}
	
	/**
	 * @param packs
	 */
	public void addPackages(Object packs) {
		listPackages.addAll((ArrayList<Package>) packs);
	}
	
	/**
	 * @param index
	 * @param packs
	 */
	public void addPackages(int index, Object packs) {
		listPackages.addAll(index, (ArrayList<Package>) packs);
	}
	
	/**
	 * @param index
	 */
	public void removePackage(int index) {
		listPackages.remove(index);
	}
	
	/**
	 * @param p
	 */
	public void removePackage(Package p) {
		listPackages.remove(p);
	}
	
	/**
	 * @param index
	 * @param t
	 */
	public void addTruck(int index, Truck t) {
		listTrucks.add(index, t);
	}
	
	/**
	 * @param t
	 */
	public void addTruck(Truck t) {
		listTrucks.add(t);
	}
	
	/**
	 * @param amount
	 */
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new Van());
	}
	
	/**
	 * @param index
	 */
	public void removeTruck(int index) {
		listTrucks.remove(index);
	}
	
	/**
	 * @param t
	 */
	public void removeTruck(Truck t) {
		listTrucks.remove(t);
	}

	/**
	 * @return
	 */
	public String getSimpleName() {
		return "Branch";
	}
}
