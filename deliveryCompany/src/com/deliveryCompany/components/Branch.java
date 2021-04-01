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
		
	}

	@Override
	public void collectPackage(Package p) {
		// TODO Auto-generated method stub
		//	p.addTracking(, Status.DELIVERED);
		
	}

	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		
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

	public String getSimpleName() {
		return "Branch";
	}
	
}
