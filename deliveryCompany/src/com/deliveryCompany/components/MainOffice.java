package com.deliveryCompany.components;

import java.util.ArrayList;

public class MainOffice {
	private static int clock;
	private static Hub hub;
	private ArrayList<Package> packages;
	
	public MainOffice(int branches, int trucksForBranch) {
		hub = new Hub();
		packages = new ArrayList<Package>();
		hub.addTrucks(trucksForBranch);
		hub.addTruck(new NonStandardTruck());
		for (int i = 0; i < branches; ++i) {
			hub.addBranch();
			hub.getBranch(i).addTrucks(trucksForBranch);
		}
		
	}
	public void play(int playTime) {
		
	}
	public void printReport() {
		
	}
	public String clockString() {
		return "";
	}
	public void tick() {
		
	}
	public void addPackage() {
		
	}
	public static Hub getHub() {
		return hub;
	}
	public void setHub(Hub hub) {
		MainOffice.hub = hub;
	}
	public ArrayList<Package> getPackages() {
		return packages;
	}
	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	public static int getClock() {
		return clock;
	}
	public static void setClock(int clock) {
		MainOffice.clock = clock;
	}
}
