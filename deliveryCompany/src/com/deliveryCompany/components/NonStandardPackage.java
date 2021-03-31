package com.deliveryCompany.components;

public class NonStandardPackage extends Package {
	int width;
	int length;
	int height;
	
	NonStandardPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height) {
		super(priority, senderAddress, destinationAddress);
		this.width = width;
		this.length = length;
		this.height = height;
	}
}
