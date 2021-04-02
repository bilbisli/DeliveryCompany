package com.deliveryCompany.components;

import java.util.ArrayList;

public class Branch implements Node {
	private static int nextId = -1; 
	private final int branchId;
	private String branchName;
	private ArrayList <Package> listPackages;
	private ArrayList <Truck> listTrucks;
	
	public Branch() {
		branchId = nextId++;
		branchName = getSimpleName() + " " + branchId;
		listTrucks = new ArrayList <Truck>();
		listPackages = new ArrayList<Package>();
	}
	
	public Branch(String branchName) {
		branchId = nextId++;
		this.branchName = branchName;
	}
	
	public void work() {
		for (Truck truck : listTrucks)
			truck.work();
		for (Package pack : listPackages) {
			switch (pack.getStatus()) {
			case BRANCH_STORAGE:
				checkAddTrack(pack);
				break;
			case CREATION:
				collectPackage(pack);
				break;
			case DELIVERY:
				deliverPackage(pack);
				break;
			default:
				break;
			}
		}
	}
	
	public int calcRouteTime(Address address) {
		return (address.getStreet() / 10) % 10 + 1;
	}

	@Override
	public void collectPackage(Package p) {
			checkMovePackage(p, Status.DISTRIBUTION, p.getSenderAddress(), false);
	}

	@Override
	public void deliverPackage(Package p) {
			checkMovePackage(p, Status.DISTRIBUTION, p.getDestinationAddress(), true);
	}
	
	public void checkAddTrack(Package pack) {
		if (!pack.getLastTrack().getNode().equals(this))
			pack.addTracking(this, Status.BRANCH_STORAGE);
	}
	
	public void checkMovePackage(Package p, Status status, Address address, boolean remove) {
		for (Truck truck : listTrucks)
			if (truck.isAvailable())
			{
				p.setStatus(status);
				p.addTracking(truck, status);
				truck.setAvailable(false);
				truck.addPackage(p);
				truck.setTimeLeft(calcRouteTime(address));
				if (remove)
					removePackage(p);
			}
	}
	
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

	@Override
	public String toString() {
		return getSimpleName() + " " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size() + 
				", trucks: "+ listTrucks.size();
	}

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

	public static int getNextId() {
		return nextId;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public ArrayList<Truck> getListTrucks() {
		return listTrucks;
	}

	public void setListTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;
	}

	public ArrayList<Package> getListPackages() {
		return listPackages;
	}

	public void setListPackages(ArrayList<Package> listPackages) {
		this.listPackages = listPackages;
	}

	public int getBranchId() {
		return branchId;
	}
	
	public void addPackage(int index, Package p) {
		listPackages.add(index, p);
	}
	
	public void addPackage(Package p) {
		listPackages.add(p);
	}
	
	public void addPackages(Object packs) {
		listPackages.addAll((ArrayList<Package>) packs);
	}
	
	public void addPackages(int index, Object packs) {
		listPackages.addAll(index, (ArrayList<Package>) packs);
	}
	
	public void removePackage(int index) {
		listPackages.remove(index);
	}
	
	public void removePackage(Package p) {
		listPackages.remove(p);
	}
	
	public void addTruck(int index, Truck t) {
		listTrucks.add(index, t);
	}
	
	public void addTruck(Truck t) {
		listTrucks.add(t);
	}
	
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new Van());
	}
	
	public void removeTruck(int index) {
		listTrucks.remove(index);
	}
	
	public void removeTruck(Truck t) {
		listTrucks.remove(t);
	}

	public String getSimpleName() {
		return "Branch";
	}
}
