package com.deliveryCompany.components;

public class SmallPackage extends Package {
	private boolean acknowledge;

	SmallPackage(Priority priority, Address senderAddress, Address destinationAddress, boolean acknowledge) {
		super(priority, senderAddress, destinationAddress);
		this.acknowledge = acknowledge;
	}
	
	

}
