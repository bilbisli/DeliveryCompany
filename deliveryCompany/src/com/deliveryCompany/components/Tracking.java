package com.deliveryCompany.components;

/**
 * This class represents Tracking listing of a package
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see		Package
 * @see		Node
 */
public class Tracking {

	/**
	 * Represents the time when the listing was made
	 */
	private final int time;
	/**
	 * Represents where the package was when the listing was made (truck or branch)
	 */
	private Node node;
	/**
	 * Represents the status of the package when the listing was made
	 */
	private final Status status;
	
	/**
	 * Class constructor with specified parameters
	 * @param time the time to set the time when the listing was made
	 * @param node the node to set the Node where the package was when the listing was made
	 * @param status the status to set the status of the package when the listing was made
	 */
	public Tracking(int time, Node node, Status status) {
		this.time = time;
		this.node = node;
		this.status = status;
	}

	/**
	 * Get method for the time field
	 * @return the time when the listing was made
	 */
	public int getTime() {
		return time;
	}

	/**
	 * Get method for the node field
	 * @return the node that the package was at when the listing was made
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * Get method for the status field
	 * @return the status the package was when the listing was made
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * This method builds and displays a string representation of a tracking listing
	 */
	@Override
	public String toString() {
		String nodeString = "Costumer";
		if(node instanceof Truck)
			nodeString = ((Truck) node).getSimpleName() + " " + ((Truck) node).getTruckID();
		else if (node instanceof Branch)
			nodeString = ((Branch) node).getBranchName();
		return time + ": " + nodeString + ", status=" + status;
	}
}
