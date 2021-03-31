package com.deliveryCompany.components;

public class NonStandardPackage extends Package {
	private int width;
	private int length;
	private int height;
	
	NonStandardPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height) {
		super(priority, senderAddress, destinationAddress);
		this.width = width;
		this.length = length;
		this.height = height;
	}

	@Override
	protected String packCharacteristics() {
		// TODO Auto-generated method stub
		return null;
	}
}
