package com.deliveryCompany.components;
import java.util.ArrayList;

public class Branch implements Node{
	private int branchId;
	private String branchName;
	private ArrayList <Truck>   listTrucks;
	private ArrayList <Package> listPackages;
	
	public Branch(){}
	
	public Branch(String branchName){
		this.branchName = branchName;
		
	}
	
	public void work() {
		
	}

	@Override
	public void collectPackage(Package p) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deliverPackage(Package p) {
		// TODO Auto-generated method stub
		
	}
	
}
