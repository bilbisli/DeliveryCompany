package com.deliveryCompany.components;

import java.util.ArrayList;

public abstract class Package {
	private static int nextId = 1000;
	private final int packageID;
	private Priority priority;
	private Status status;
	private final Address senderAddress;
	private final Address destinationAddress;
	private ArrayList<Tracking> tracking;
	
	public Package (Priority priority, Address senderAddress, Address destinationAddress) {
		this.packageID = nextId++;
		this.priority = priority;
		this.senderAddress = senderAddress;
		this.destinationAddress = destinationAddress;
		tracking = new ArrayList<Tracking>();
		addTracking(null, Status.CREATION);
		status = Status.CREATION;
	}
	
	public void addTracking (Node node, Status status) {
		tracking.add(new Tracking(MainOffice.getClock(), node, status));
	}
	
	public void printTracking() {
		System.out.println("TRACKING " + toString());
		for (Tracking track : tracking)
			System.out.println(track);
	}
	
	@Override
	public String toString() {
		return String.format(getSimpleName() + " [packageID=%d, priority=%s, status=%s, startTime=%s, senderAddress=%s, destinationAddress=%s, %s]",
				packageID, priority, status, tracking.get(0).getTime(), senderAddress.addressString(), 
				destinationAddress.addressString(), packCharacteristics());
	}
	
	public String getSimpleName() {
		return "Package";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destinationAddress == null) ? 0 : destinationAddress.hashCode());
		result = prime * result + packageID;
		result = prime * result + ((priority == null) ? 0 : priority.hashCode());
		result = prime * result + ((senderAddress == null) ? 0 : senderAddress.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((tracking == null) ? 0 : tracking.hashCode());
		return result;
	}
	
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
	
	public static int getNextId() {
		return nextId;
	}
	
	public int getPackageID() {
		return packageID;
	}
	
	public Priority getPriority() {
		return priority;
	}
	
	public void setPriority(Priority priority) {
		this.priority = priority;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public ArrayList<Tracking> getTracking() {
		return tracking;
	}
	
	public void setTracking(ArrayList<Tracking> tracking) {
		this.tracking = tracking;
	}
	
	public Address getSenderAddress() {
		return senderAddress;
	}
	
	public Address getDestinationAddress() {
		return destinationAddress;
	}
	
	public Tracking getLastTrack() {
		return tracking.get(tracking.size() - 1);
	}
	
	protected abstract String packCharacteristics();
}
