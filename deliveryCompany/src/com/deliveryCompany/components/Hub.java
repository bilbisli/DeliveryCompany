package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

public class Hub extends Branch {
	private ArrayList<Branch> branches;
	private int currentBranch;
	
	public Hub() {
		super("HUB");
		branches = new ArrayList<Branch>();
		currentBranch = 0;
	}
	
	public void work() {
		for (Branch branch : branches)
			branch.work();
		for (Truck truck : getListTrucks()) {
			truck.work();
			if (truck.isAvailable()) {
				if (truck instanceof StandardTruck) {
					StandardTruck t = ((StandardTruck)truck);
					t.setDestination(getBranch(nextBranch()));
					int currentWeight = 0;
					for (Package pack : getListPackages()) {
						if (pack.getDestinationAddress().getZip() == t.getDestination().getBranchId()) {
							if(!t.checkCapacityAdd(pack, currentWeight, Status.BRANCH_TRANSPORT))
								break;
						}
					}
					Random rand = new Random();
					t.setTimeLeft(rand.nextInt(10));
				}
				else if (truck instanceof NonStandardTruck) {
					NonStandardTruck t = (NonStandardTruck)truck;
					for (Package pack : getListPackages()) {
						if (pack instanceof NonStandardPackage) {
							NonStandardPackage p = (NonStandardPackage)pack;
							if (p.getStatus() == Status.CREATION && p.getHeight() <= t.getHeight() 
									&& p.getLength() <= t.getLength() && p.getWidth() <= t.getWidth()) {
								p.setStatus(Status.COLLECTION);
								p.addTracking(t, Status.COLLECTION);
								t.addPackage(p);
							}
						}
					}
				}
			}
		}
	}
	
	public int nextBranch() {
		if (currentBranch + 1 >= branches.size())
			currentBranch = 0;
		return currentBranch++;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hub other = (Hub) obj;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		return true;
	}
	
	public Branch getBranch(int index) {
		return branches.get(index);
	}

	public ArrayList<Branch> getBranches() {
		return branches;
	}

	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	public void addBranch() {
		branches.add(new Branch());
	}
	
	public void addBranch(String branchName) {
		branches.add(new Branch(branchName));
	}
	
	public void addBranch(Branch branch) {
		branches.add(branch);
	}
	
	public void addTruck() {
		super.addTruck(new StandardTruck());
	}
	
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new StandardTruck());
	}
}


