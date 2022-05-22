package components;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a hub branch - a branch that acts as a sorting center
 * where all packages from local branches are sent to after initial collection.
 * After sorting, packages are dispatched from the hub to the matching local
 * branches of the destination address
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Branch
 * @see Package
 * @see StandardTruck
 * @see NonStandardTruck
 */
public class Hub extends Branch {

	private static final long serialVersionUID = 1L;

	/**
	 * The list of all the branches that belong to this hub (that send and receive
	 * packages to and from the hub)
	 */
	private ArrayList<Branch> branches = new ArrayList<Branch>();

	/**
	 * Represents the current index of branch which standard trucks will be sent to
	 */
	private int currentIndex = 0;

	/**
	 * Class default constructor
	 */
	public Hub() {
		super("HUB");
	}

	/**
	 * Get method for the list of branches
	 * 
	 * @return the list of branches that belong to the hub
	 */
	public ArrayList<Branch> getBranches() {
		return branches;
	}

	/**
	 * This method is for adding a new branch with a specified name to the hub
	 * branch list and updating current location
	 * 
	 * @param branch the specified branch name for the branch that will be added to
	 *               the hub and to updating his location
	 */
	public void add_branch(Branch branch) {
		branches.add(branch);
	}

	/**
	 * Method that deals with sending a standard truck to collect \ deliver packages
	 * 
	 * @param t - the standard truck to send
	 */
	public synchronized void sendTruck(StandardTruck t) {
		synchronized (t) {
			t.notify();
		}
		t.setAvailable(false);
		// sets the current destination location of the standard truck to be at the path
		// towards the destination branch position
		t.setDestination(branches.get(currentIndex));
		// loads the packages
		t.load(this, t.getDestination(), Status.BRANCH_TRANSPORT);
		t.setTimeLeft(((new Random()).nextInt(10) + 1) * 10);
		// sets the current location of the standard truck to be at the path towards the
		// destination branch position
		t.initTime = t.getTimeLeft();
		System.out.println(t.getName() + " is on it's way to " + t.getDestination().getName() + ", time to arrive: "
				+ t.getTimeLeft());
		currentIndex = (currentIndex + 1) % branches.size();
	}

	/**
	 * Method for dealing with the shipment of a non standard package
	 * 
	 * @param t - the non standard truck to ship the package
	 */
	public synchronized void shipNonStandard(NonStandardTruck t) {
		for (Package p : listPackages) {
			if (p instanceof NonStandardPackage) {
				/*
				 * if (((NonStandardPackage) p).getHeight() <= t.getHeight() &&
				 * ((NonStandardPackage) p).getLength()<=t.getLength() && ((NonStandardPackage)
				 * p).getWidth()<=t.getWidth()){
				 */
				synchronized (t) {
					t.notify();
				}
				t.collectPackage(p);
				listPackages.remove(p);
				return;
				// }
			}
		}
	}

	@Override
	public void work() {

	}

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				if (threadStop)
					break;
				while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
			for (Truck t : listTrucks) {
				if (t.isAvailable()) {
					if (t instanceof NonStandardTruck) {
						shipNonStandard((NonStandardTruck) t);
					} else {
						sendTruck((StandardTruck) t);
					}
				}
			}
			synchronized (this) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
