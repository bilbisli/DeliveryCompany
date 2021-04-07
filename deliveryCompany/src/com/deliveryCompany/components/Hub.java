package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a hub branch - a branch that acts as a sorting center where all packages from local branches are sent to 
 * after initial collection. After sorting, packages are dispatched from the hub to the matching local branches of the destination address
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
 * @see		Branch
 * @see		Package
 * @see		StandardTruck
 * @see		NonStandardTruck
 */
public class Hub extends Branch {
	/**
	 * The list of all the branches that belong to this hub (that send and receive packages to and from the hub)
	 */
	private ArrayList<Branch> branches;
	/**
	 * Represents the current branch which standard trucks will be sent to
	 */
	private int currentBranch;
	
	/**
	 * Class constructor initiates the list of branches and the current branch to send a standard truck to
	 */
	public Hub() {
		super("HUB");
		branches = new ArrayList<Branch>();
		currentBranch = 0;
	}
	
	/**
	 * This method represents work of the hub - manages every truck under the hub (by activating it's work method), loads it's trucks with packages and
	 * sends them to the appropriate branch: if a standard truck is available, all packages of the current branch are loaded on it and the truck is sent
	 * to dispatch the packages. If a non standard package is to be collected and delivered and the a non standard truck is available, it is then sent 
	 * to collect it
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
	 * This method generates the next branch id send a standard truck to
	 * @return the id of the next branch to send a truck to
	 */
	public int nextBranch() {
		if (currentBranch + 1 >= branches.size())
			currentBranch = 0;
		return currentBranch++;
	}

	/**
	 * Equals method for comparing two hub instances
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
	 * This method is for getting a branch in a specified index within the list of branches
	 * @param 	index the index of the branch within the list of branches
	 * @return	the branch in the specified index
	 */
	public Branch getBranch(int index) {
		return branches.get(index);
	}

	/**
	 * Get method for the list of branches
	 * @return the list of branches that belong to the hub
	 */
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	/**
	 * Set method for the list of branches that belong to the hub
	 * @param branches the specified branch list to set for the hub
	 */
	public void setBranches(ArrayList<Branch> branches) {
		this.branches = branches;
	}

	/**
	 * This method is for adding a new default branch to the hub's branch list
	 */
	public void addBranch() {
		branches.add(new Branch());
	}
	
	/**
	 * This method is for adding a new branch with a specified name to the hub branch list
	 * @param branchName the specified branch name for the branch that will be added to the hub
	 */
	public void addBranch(String branchName) {
		branches.add(new Branch(branchName));
	}
	
	/**
	 * This method is for adding a new specified branch to the hub branch list
	 * @param branch the specified branch to add to the hub
	 */
	public void addBranch(Branch branch) {
		branches.add(branch);
	}
	
	/**
	 * This method is for adding a default standard truck to the hub
	 */
	public void addTruck() {
		super.addTruck(new StandardTruck());
	}
	
	/**
	 * This method is for adding a specified number of default trucks to the hub
	 * @param amount the amount of trucks to create for the hub
	 */
	public void addTrucks(int amount) {
		for (int i = 0; i < amount; ++i)
			addTruck(new StandardTruck());
	}
}


