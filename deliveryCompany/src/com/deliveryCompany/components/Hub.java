package com.deliveryCompany.components;

import java.util.ArrayList;

public class Hub extends Branch {
	private ArrayList<Branch> branches;
	
	public Hub() {
		super("HUB");
		branches = new ArrayList<Branch>();
	}
	
	public void work() {
		for (Truck truck : getListTrucks()) {
			if (truck instanceof StandardTruck && truck.isAvailable()) {
				
			}
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((branches == null) ? 0 : branches.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hub other = (Hub) obj;
		if (branches == null) {
			if (other.branches != null)
				return false;
		} else if (!branches.equals(other.branches))
			return false;
		return true;
	}

	
}


