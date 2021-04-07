package com.deliveryCompany.components;


/**
 * This class represents a small package
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
 * @see		Package
 */
public class SmallPackage extends Package {
	/**
	 * Boolean field to determine if a special notification should be displayed when the small package arrives to it's destination
	 */
	private boolean acknowledge;

	/**
	 * Class constructor that creates a small package object based on specified parameters
	 * @param priority the priority of the package's shipment
	 * @param senderAddress the address of the sender of the package
	 * @param destinationAddress the address of the recipient of the package
	 * @param acknowledge the indication if a special notification will be sent when the package arrives to it's destination
	 */
	public SmallPackage(Priority priority, Address senderAddress, Address destinationAddress, boolean acknowledge) {
		super(priority, senderAddress, destinationAddress);
		this.acknowledge = acknowledge;
		System.out.println("Creating " + toString());
	}
	
	/**
	 * This method checks if a special notification needs to be sent when the package arrives to it's destination
	 * @return true or false based on acknowledge
	 */
	public boolean isAcknowledge() {
		return acknowledge;
	}

	/**
	 * Set method for acknowledge field
	 * @param acknowledge the specified parameter to set acknowledge to
	 */
	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	/**
	 * This method returns the special characteristics of a small package represented as a string
	 */
	@Override
	protected String packCharacteristics() {
		return "acknowledge="  + acknowledge;
	}

	/**
	 * Equals method for comparing two small package instances
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SmallPackage other = (SmallPackage) obj;
		if (acknowledge != other.acknowledge)
			return false;
		return true;
	}

	/**
	 * This method returns the name of the class
	 * @return a string representation of the name of the class
	 */
	public String getSimpleName() {
		return "SmallPackage";
	}
}
