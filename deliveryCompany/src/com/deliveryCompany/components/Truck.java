package com.deliveryCompany.components;

import java.util.ArrayList;

public class Truck {
	private int truckID;
	private String licensePlate;
	private String truckModel;
	private boolean available;
	private int timeLeft;
	private ArrayList<Package> packages;
	
	public Truck() {}
	
	public Truck(String licensePlate, String truckModel) {
		this.licensePlate = licensePlate;
		this.truckModel = truckModel;
	}
}
