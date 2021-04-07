package com.deliveryCompany.components;

public interface Node {
	/**
	 * @param p
	 */
	void collectPackage(Package p);
	/**
	 * @param p
	 */
	void deliverPackage(Package p);
	/**
	 * 
	 */
	void work();
}
