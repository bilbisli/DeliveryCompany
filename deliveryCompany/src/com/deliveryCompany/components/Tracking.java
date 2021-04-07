package com.deliveryCompany.components;

public class Tracking {

	/**
	 * 
	 */
	private final int time;
	/**
	 * 
	 */
	private Node node;
	/**
	 * 
	 */
	private final Status status;
	
	/**
	 * @param time
	 * @param node
	 * @param status
	 */
	public Tracking(int time, Node node, Status status) {
		this.time = time;
		this.node = node;
		this.status = status;
	}

	/**
	 * @return
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @return
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @return
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 *
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
