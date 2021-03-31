package com.deliveryCompany.deliveries;

import java.util.ArrayList;

public class Package {
	private int packadID;
	private Priority priority;
	private Status status;
	private Address senderAddress;
	private Address destinationAddress;
	private ArrayList<Tracking>  tracking;
	
	Package (Priority priority, Address senderAddress, Address destinationAddress) {
		this.priority = priority;
		this.senderAddress = senderAddress;
		this.destinationAddress = destinationAddress;
	}
	
	void addTracking (Node node, Status status) {
		
	}
	void printTracking() {
		
	}
}
