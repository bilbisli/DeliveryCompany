package com.deliveryCompany.components;

import java.util.ArrayList;
import java.util.Random;

public class MainOffice {
	private static int clock = 0;
	private static Hub hub;
	private ArrayList<Package> packages;
	
	public MainOffice(int branches, int trucksForBranch) {
		hub = new Hub();
		packages = new ArrayList<Package>();
		hub.addTrucks(trucksForBranch);
		hub.addTruck(new NonStandardTruck());
		for (int i = 0; i < branches; ++i) {
			Branch b = new Branch();
			b.addTrucks(trucksForBranch);
			hub.addBranch(b);
		}
	}
	
	public void play(int playTime) {
		for (int i = 0; i < playTime; ++i) {
			tick();
		}
		System.out.println("\n========================== STOP ==========================\n\n\n");
		printReport();
	}
	
	public void printReport() {
		for (Package p : packages)
			p.printTracking();
	}
	
	public String clockString() {
		return String.format("%02d:%02d", clock / 100, clock % 100);
	}
	
	public void tick() {
		System.out.println(clockString());
		++clock;
		if (clock % 5 == 1)
			addPackage();
		hub.work();
	}
	
	public void addPackage() {
		Random rand = new Random();
		Package p;
		Priority priority = Priority.values()[rand.nextInt(3)];
		int zipSender = rand.nextInt(hub.getBranches().size()), zipDestination = rand.nextInt(hub.getBranches().size());
		Address sender = new Address(zipSender, 1000000 + rand.nextInt(10000000));
		Address destination = new Address(zipDestination, 1000000 + rand.nextInt(10000000));
		
		switch (rand.nextInt(4)) {
		case 0:
			boolean acknowledge = rand.nextBoolean();
			p = new SmallPackage(priority, sender, destination, acknowledge);
			hub.getBranch(zipSender).addPackage(p);
			break;
		case 1:
			int width = rand.nextInt(500), length = rand.nextInt(1000), height = rand.nextInt(400);
			p = new NonStandardPackage(priority, sender, destination, width, length, height);
			hub.addPackage(p);
			break;
		default:
			double weight = 1 + rand.nextInt(9) + rand.nextDouble();
			p = new StandardPackage(priority, sender, destination, weight);
			hub.getBranch(zipSender).addPackage(p);
		}
		packages.add(p);
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
	
	public static Branch getBranch(int index) {
		return hub.getBranch(index);
	}
}
