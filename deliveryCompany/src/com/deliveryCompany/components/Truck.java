package com.deliveryCompany.components;
import java.util.Random;
import java.util.ArrayList;

public abstract class Truck implements Node {
	static private int nextId = 2000; 
	private int truckID;
	private final String licensePlate;
	private final String truckModel;
	private boolean available;
	private int timeLeft;
	private ArrayList<Package> packages;
	
	public Truck() {
		truckID = nextId++;	
		Random rand = new Random();
		truckModel = "M" + rand.nextInt(5);
		available = true;
		packages = new ArrayList<Package>();
		timeLeft = 0;
		licensePlate = generateLicensePlate();
	}
	
	public Truck(String licensePlate, String truckModel) {
		this.licensePlate = licensePlate;
		this.truckModel = truckModel;
		available = true;
		packages = new ArrayList<Package>();
		truckID = nextId++;	
		timeLeft = 0;
	}
	
	public int getnextId() {
		return nextId;
	}
	
	public int getTruckID() {
		return truckID;
	}

	public void setTruckID(int truckID) {
		this.truckID = truckID;
	}

	public String getLicensePlate() {
		return licensePlate;
	}


	public String getTruckModel() {
		return truckModel;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	public ArrayList<Package> getPackages() {
		return packages;
	}

	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	
	public Package getLastPack() {
		return packages.get(packages.size() - 1);
	}
	
	public void addPackage(int index, Package p) {
		packages.add(index, p);
	}
	
	public void addPackage(Package p) {
		packages.add(p);
	}
	
	public void removePackage(int index) {
		packages.remove(index);
	}
	
	public void removePackage(Package p) {
		packages.remove(p);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (available ? 1231 : 1237);
		result = prime * result + ((licensePlate == null) ? 0 : licensePlate.hashCode());
		result = prime * result + ((packages == null) ? 0 : packages.hashCode());
		result = prime * result + timeLeft;
		result = prime * result + truckID;
		result = prime * result + ((truckModel == null) ? 0 : truckModel.hashCode());
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
		Truck other = (Truck) obj;
		if (available != other.available)
			return false;
		if (licensePlate == null) {
			if (other.licensePlate != null)
				return false;
		} else if (!licensePlate.equals(other.licensePlate))
			return false;
		if (packages == null) {
			if (other.packages != null)
				return false;
		} else if (!packages.equals(other.packages))
			return false;
		if (timeLeft != other.timeLeft)
			return false;
		if (truckID != other.truckID)
			return false;
		if (truckModel == null) {
			if (other.truckModel != null)
				return false;
		} else if (!truckModel.equals(other.truckModel))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel 
				+ ", available=" + available + truckCharacteristics() + "]";
		
	}
	
	protected String truckCharacteristics() {
		return "";
	}

	public String generateLicensePlate() {
		int n = 10000000 + new Random().nextInt(100000000);
		int temp[] = new int[8];
		int i = 0;
		while(n>0) {
			temp[i] = n%10;
			i++;
			n = n/10;
		}
		return temp[0] + "" + temp[1] + "" + temp[2] + "-" + temp[3] + "" + temp[4] + "-" + temp[5] + "" + temp[6] + "" + temp[7];
		
	}

		
}

