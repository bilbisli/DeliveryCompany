package com.deliveryCompany.components;

import java.util.ArrayList;

public class MainOffice {
	static private int clock;
	private Hub hub;
	private ArrayList<Package> packages;
	
	public MainOffice(int branches, int trucksForBranch) {

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
	public Hub getHub() {
		return hub;
	}
	public void setHub(Hub hub) {
		this.hub = hub;
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
