package com.deliveryCompany.components;

public class StandardPackage extends Package {
	private double weight;
	
	StandardPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight) {
		super(priority, senderAddress, destinationAddress);
		this.weight = weight;
	}

	@Override
	protected String packCharacteristics() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
