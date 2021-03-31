package com.deliveryCompany.offices;
import com.deliveryCompany.vehicles.*;

import java.util.ArrayList;

import com.deliveryCompany.deliveries.*;

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
