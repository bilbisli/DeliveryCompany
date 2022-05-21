package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Describes a local branch. Maintains a list of packages stored at the branch
 * or intended for collection from the sender's address to this branch, and a
 * list of vehicles that collect the packages from the sending customers and
 * deliver the packages to the receiving customers.
 * 
 * @version 3.00 12 July 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Node
 */
public class Branch extends Observable implements Node, Cloneable {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the costumers id counter
	 */
	private static int counter = 0;

	/**
	 * Represents the branch number (id)
	 */
	/**
	 * the id of the branch
	 */
	private int branchId;

	/**
	 * Represents the branch Name
	 */
	private String branchName;

	/**
	 * used for the list of packages
	 */
	protected ArrayList<Package> unsafeListPackages = new ArrayList<Package>();

	/**
	 * the list of packages
	 */
	protected List<Package> listPackages = unsafeListPackages; // Collections.synchronizedList(unsafeListPackages);

	/**
	 * A collection of vehicles belonging to this branch
	 */
	protected ArrayList<Truck> listTrucks = new ArrayList<Truck>();

	/**
	 * the location of the line to hub in the post system panel
	 */
	private Point hubPoint;

	/**
	 * the location of the branch in the post system panel
	 */
	private Point branchPoint;

	/**
	 * a flag that indicates the branch's process should be suspended
	 */
	protected boolean threadSuspend = false;

	/**
	 * a flag that indicates the branch's process should be stopped
	 */
	protected boolean threadStop = false;

	/**
	 * Default constructor calculates the serial number of the branch and creates
	 * the name of the branch, initializing the two remaining fields to empty
	 * collections.
	 */
	public Branch() {
		this("Branch " + counter);
	}

	/**
	 * Regular constructor that gets a branch name as string
	 * 
	 * @param branchName the specified name to give the newly created branch
	 */
	public Branch(String branchName) {
		this.branchId = counter++;
		this.branchName = branchName;
		System.out.println("\nCreating " + this);
	}

	/**
	 * Regular constructor that gets a branch name as string, a list of packages and
	 * a list of trucks for the newly created branch
	 * 
	 * @param branchName the specified name to give the newly created branch
	 * @param plist      the list of packages for the new branch
	 * @param tlist      the list of trucks for the new branch
	 */
	public Branch(String branchName, Package[] plist, Truck[] tlist) {
		this.branchId = counter++;
		this.branchName = branchName;
		addPackages(plist);
		addTrucks(tlist);
	}

	/**
	 * Get method for packages field
	 * 
	 * @return packages the list of packages in the branch
	 */
	public synchronized List<Package> getPackages() {
		return this.listPackages;
	}

	/**
	 * Method for printing a branch, it's packages and it's trucks
	 */
	public void printBranch() {
		System.out.println("\nBranch name: " + branchName);
		System.out.println("Packages list:");
		for (Package pack : listPackages)
			System.out.println(pack);
		System.out.println("Trucks list:");
		for (Truck trk : listTrucks)
			System.out.println(trk);
	}

	/**
	 * Method for adding a package to the package list
	 * 
	 * @param pack the package to add to the barnch's package list
	 */
	public synchronized void addPackage(Package pack) {
		listPackages.add(pack);
	}

	/**
	 * Get method for the branche's list of trucks
	 * @return the branche's list of trucks
	 */
	public ArrayList<Truck> getTrucks() {
		return this.listTrucks;
	}

	/**
	 * Method for adding a truck to the truck list
	 * 
	 * @param trk the truck to add to the barnch's truck list
	 */
	public void addTruck(Truck trk) {
		listTrucks.add(trk);
	}

	/**
	 * Get method for the location of the line to hub in the post system panel
	 * @return the location of the line to hub in the post system panel
	 */
	public Point getHubPoint() {
		return hubPoint;
	}

	/**
	 * Get method for the location of the branch in the post system panel
	 * @return the location of the branch in the post system panel
	 */
	public Point getBranchPoint() {
		return branchPoint;
	}

	/**
	 * Method for adding packages to the package list
	 * 
	 * @param plist the packages to add to the barnch's package list
	 */
	public synchronized void addPackages(Package[] plist) {
		for (Package pack : plist)
			listPackages.add(pack);
	}

	/**
	 * Method for adding trucks to the truck list
	 * 
	 * @param tlist the trucks to add to the barnch's truck list
	 */
	public void addTrucks(Truck[] tlist) {
		for (Truck trk : tlist)
			listTrucks.add(trk);
	}

	/**
	 * Get method for branch id
	 * 
	 * @return the branch's id
	 */
	public int getBranchId() {
		return branchId;
	}

	/**
	 * Get method for branch name
	 * 
	 * @return the branch's name
	 */
	public String getName() {
		return branchName;
	}

	/**
	 * This method returns the string representation of the object
	 */
	@Override
	public String toString() {
		return "Branch " + branchId + ", branch name:" + branchName + ", packages: " + listPackages.size()
				+ ", trucks: " + listTrucks.size();
	}

	/**
	 * A method that handles the collection of a package from a sender's address to
	 * the branch by a van
	 */
	@Override
	public synchronized void collectPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized (v) {
					v.notify();
				}
				v.collectPackage(p);
				return;
			}
		}
	}

	/**
	 * A method that handles the delivery of the package to the it's destination
	 * address
	 */
	@Override
	public synchronized void deliverPackage(Package p) {
		for (Truck v : listTrucks) {
			if (v.isAvailable()) {
				synchronized (v) {
					v.notify();
				}
				v.deliverPackage(p);
				return;
			}
		}
	}

	/**
	 * The function checks for each package that is in the branch, if it is in the
	 * waiting status for collection from a customer, an attempt is made to collect
	 * - if there is a vehicle available, he goes out to collect the package. The
	 * calculation of the travel time and the value obtained is updated in the
	 * vehicle in the timeLeft field and the condition of the vehicle changes to
	 * "not available". Same goes for any package waiting for distribution, if there
	 * is a vehicle available, it is sent to deliver the package.
	 */
	@Override
	public void work() {
		/*
		 * for (Package p: listPackages) { if (p.getStatus()==Status.CREATION) {
		 * collectPackage(p); } if (p.getStatus()==Status.DELIVERY) { deliverPackage(p);
		 * } }
		 */
	}

	/**
	 * Method for checking if the branch has any packages
	 * @return true if the branch has any packages
	 */
	private boolean arePackagesInBranch() {
		for (Package p : listPackages) {
			if (p.getStatus() == Status.BRANCH_STORAGE)
				return true;
		}
		return false;
	}

	/**
	 * Paint method for displaying a branch on the visualization window
	 * @param g the visualization panel graphics (where to display the branch)
	 * @param y the y axis location to display the branch
	 * @param y2 the end point for the line from branch to hub
	 */
	public void paintComponent(Graphics g, int y, int y2) {
		if (arePackagesInBranch())
			g.setColor(new Color(0, 0, 153));
		else
			g.setColor(new Color(51, 204, 255));
		g.fillRect(20, y, 40, 30);

		g.setColor(new Color(0, 102, 0));
		g.drawLine(60, y + 15, 1120, y2);
		branchPoint = new Point(60, y + 15);
		hubPoint = new Point(1120, y2);
	}

	/**
	 * Run method for starting the branch's thread process
	 */

	@Override
	public void run() {
		while (true) {
			synchronized (this) {
				if (threadStop)
					return;
				while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (threadStop)
					return;
				for (int i = 0; i < listPackages.size(); ++i) {
					Package p = listPackages.get(i);
					if (p.getStatus() == Status.CREATION) {
						collectPackage(p);
					}
					if (p.getStatus() == Status.DELIVERY) {
						deliverPackage(p);
					}
				}
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * set method for indicating the branch's process should be suspended
	 */
	public synchronized void setSuspend() {
		threadSuspend = true;
	}

	/**
	 * set method for indicating the branch's process should be resumed
	 */
	public synchronized void setResume() {
		threadSuspend = false;
		notify();
	}

	/**
	 * set method for indicating the branch's process should be stopped
	 */
	public synchronized void setStop() {
		threadStop = true;
	}

	/**
	 * clone method for deep cloning a branch
	 */
	public Branch clone() {
		Branch clone = null;
		try {
			clone = (Branch) super.clone();
			clone.setBranchId();
			clone.setBranchName();
			clone.setPackages(new ArrayList<Package>());
			clone.setTrucks(new ArrayList<Truck>());
			for (int i = 0; i < getTrucks().size(); ++i) {
				clone.addTruck(getTrucks().get(i).clone());
			}
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	/**
	 * Get method for the branch package list
	 * @param listPackages the branch package list
	 */
	private void setPackages(ArrayList<Package> listPackages) {
		this.listPackages = listPackages;

	}

	/**
	 * Set method for the branch truck list
	 * @param the branch truck list
	 */
	private void setTrucks(ArrayList<Truck> listTrucks) {
		this.listTrucks = listTrucks;

	}

	/**
	 * Set method for the branch name
	 */
	private void setBranchName() {
		this.branchName = "Branch " + this.branchId;

	}

	/**
	 * Set method for the branch id
	 */
	private void setBranchId() {
		this.branchId = Branch.counter++;
	}

	/**
	 * Method resets the branches id counter (used for system restoration)
	 */
	public static void resetCount() {
		Branch.counter = 0;
	}

}
