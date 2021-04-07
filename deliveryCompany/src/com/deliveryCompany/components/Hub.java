package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

public class Hub extends Branch {
	/**
	 * 
	 */
	private ArrayList<Branch> branches;
	/**
	 * 
	 */
	private int currentBranch;
	
	/**
	 * 
	 */
	public Hub() {
		super("HUB");
		branches = new ArrayList<Branch>();
		currentBranch = 0;
	}
	
	/**
	 *
	 */
	public void work() {
		for (Truck truck : getListTrucks()) {
			truck.work();
			if (truck.isAvailable()) {
				if (truck instanceof StandardTruck) {
					StandardTruck t = ((StandardTruck)truck);
					t.setDestination(getBranch(nextBranch()));
					int currentWeight = 0;
					ArrayList<Package> tempPackages = new ArrayList<Package>();
					for (Package pack : getListPackages()) {
						if (pack.getDestinationAddress().getZip() == t.getDestination().getBranchId()) {
							if(!t.checkCapacityAdd(pack, currentWeight, Status.BRANCH_TRANSPORT))
								break;
							else tempPackages.add(pack);
						}
					}
					getListPackages().removeAll(tempPackages);
					Random rand = new Random();
					t.setTimeLeft(1 + rand.nextInt(9));
					t.getTimeLeft();
					t.setAvailable(false);
					if (truck.getTimeLeft() != 0)
						System.out.printf("StandardTruck %d is on it's way to %s, time to arrive: %d\n", 
								((StandardTruck)truck).getTruckID(), 
								((StandardTruck)truck).getDestination().getBranchName(), truck.getTimeLeft());
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
								t.setAvailable(false);
								t.setTimeLeft(t.calcTime());
								removePackage(p);
								System.out.printf("NonStandartTruck %d is collecting package %d, time left: %d\n",t.getTruckID(), p.getPackageID(),
										t.getTimeLeft());
								break;
							}
						}
					}
				}
			}
		}
		for (Branch branch : branches)
			branch.work();
	}
	
	/**
	 * @return
	 */
	public int nextBranch() {
		if (currentBranch + 1 >= branches.size())
			currentBranch = 0;
		return currentBranch++;
	}

	/**
	 *
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		return result;
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
		Hub other = (Hub) obj;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		return true;
	}
	
	/**
	 * @param index
	 * @return
	 */
	public Branch getBranch(int index) {
		return branches.get(index);
	}

	/**
	 * @return
	 */
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	/**
	 * @param branches
	 */
	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	/**
	 * 
	 */
	public void addBranch() {
		branches.add(new Branch());
	}
	
	/**
	 * @param branchName
	 */
	public void addBranch(String branchName) {
		branches.add(new Branch(branchName));
	}
	
	/**
	 * @param branch
	 */
	public void addBranch(Branch branch) {
		branches.add(branch);
	}
	
	/**
	 * 
	 */
	public void addTruck() {
		super.addTruck(new StandardTruck());
	}
	
	/**
	 *
	 */
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new StandardTruck());
	}
}


