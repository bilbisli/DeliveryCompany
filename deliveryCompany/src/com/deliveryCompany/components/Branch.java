package com.deliveryCompany.components;
import java.util.ArrayList;

public class Branch {
	private int branchId;
	private String branchName;
	private ArrayList <Truck>   listTrucks;
	private ArrayList <Package> listPackages;
	
	public Branch(){}
	
	public Branch(String branchName){
		this.branchName = branchName;
		
	}
	
	void work() {}
	
}
