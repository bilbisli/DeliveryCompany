package com.deliveryCompany.components;

/**
 * A general type represents packages.
 * @version 1.00 7 Apr 2021
 * @author  Ofir
 * @see  	SmallPackage, StandardPackage, NonStandardPackage
 */

import java.util.ArrayList;

public abstract class Package {
	/**
	 * Represents the following Package number
	 */
	private static int nextId = 1000;
	/**
	 * Represents the Package ID number
	 */
	private final int packageID;
	/**
	 * Represents the priority of the package
	 */
	private Priority priority;
	/**
	 * Represents the status of the package
	 */
	private Status status;
	/**
	 * Represents the sender Address
	 */
	private final Address senderAddress;
	/**
	 * Represents the destination Address
	 */
	private final Address destinationAddress;
	/**
	 * Represents collection of records with transfer history
	 */
	private ArrayList<Tracking> tracking;
	
	/**
	 * Contractor who accepts as arguments arguments, addresses the sender and receives a package.
	 * @param priority - the priority of the package
	 * @param senderAddress - the sender address
	 * @param destinationAddress - the destination address
	 */
	public Package (Priority priority, Address senderAddress, Address destinationAddress) {
		this.packageID = nextId++;
		this.priority = priority;
		this.senderAddress = senderAddress;
		this.destinationAddress = destinationAddress;
		tracking = new ArrayList<Tracking>();
		addTracking(null, Status.CREATION);
		status = Status.CREATION;
	}
	
	/**
	 * Receives an object of type Node and a Status object, creates and adds an object
	   of the Tracking class to the tracking collection in the class
	 * @param node - vehicle or branch
	 * @param status - the status of the vehicle or the branch
	 */
	public void addTracking (Node node, Status status) {
		tracking.add(new Tracking(MainOffice.getClock(), node, status));
	}
	
	/**
	 * Prints the records stored in the tracking collection
	 */
	public void printTracking() {
		System.out.println("TRACKING " + toString());
		for (Tracking track : tracking)
			System.out.println(track);
	}
	
	/**
	 * The function returns the string representation of the object.
	 */
	@Override
	public String toString() {
		return String.format(getSimpleName() + " [packageID=%d, priority=%s, status=%s, startTime=%s, senderAddress=%s, destinationAddress=%s, %s]",
				packageID, priority, status, tracking.get(0).getTime(), senderAddress.addressString(), 
				destinationAddress.addressString(), packCharacteristics());
	}
	
	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "Package";
	}
	
	
	/**
	 *The function checks whether the two Package objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Package other = (Package) obj;
		if (destinationAddress == null) {
			if (other.destinationAddress != null)
				return false;
		} else if (!destinationAddress.equals(other.destinationAddress))
			return false;
		if (packageID != other.packageID)
			return false;
		if (priority != other.priority)
			return false;
		if (senderAddress == null) {
			if (other.senderAddress != null)
				return false;
		} else if (!senderAddress.equals(other.senderAddress))
			return false;
		if (status != other.status)
			return false;
		if (tracking == null) {
			if (other.tracking != null)
				return false;
		} else if (!tracking.equals(other.tracking))
			return false;
		return true;
	}
	
	/**
	 * The function returns the following package number
	 * @return the following package number
	 */
	public static int getNextId() {
		return nextId;
	}
	
	/**
	 * The function returns the package ID
	 * @return the package ID
	 */
	public int getPackageID() {
		return packageID;
	}
	
	/**
	 * The function returns the package priority
	 * @return the priority of the package
	 */
	public Priority getPriority() {
		return priority;
	}
	
	/**
	 * The function sets the value of the priority
	 * @param Priority - The Priority which we want to update the entry
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	/**
	 * The function returns the package's status
	 * @return the status of the package
	 */
	public Status getStatus() {
		return status;
	}
	
	/**
	 * The function sets the value of the status
	 * @param Priority - The status which we want to update the entry
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	
	/**
	 * The function returns the tracking of the package
	 * @return the tracking of the package
	 */
	public ArrayList<Tracking> getTracking() {
		return tracking;
	}
	
	/**
	 * The function sets the value of the tracking
	 * @param tracking - The tracking which we want to update the list
	 */
	public void setTracking(ArrayList<Tracking> tracking) {
		this.tracking = tracking;
	}
	
	/**
	 * The function returns the sender address of the package
	 * @return the sender address of the package
	 */
	public Address getSenderAddress() {
		return senderAddress;
	}
	
	/**
	 * The function returns the destination address of the package
	 * @return the destination address of the package
	 */
	public Address getDestinationAddress() {
		return destinationAddress;
	}
	
	/**
	 * The function returns the last track of the package
	 * @return the last track of the package
	 */
	public Tracking getLastTrack() {
		return tracking.get(tracking.size() - 1);
	}
	
	/**
	 * The function returns the string representation of the object.
	 * @return the string representation of the object
	 */
	protected abstract String packCharacteristics();
}
