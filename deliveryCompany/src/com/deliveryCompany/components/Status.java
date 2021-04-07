package com.deliveryCompany.components;

/**
 * This enum is for setting the status of a certain package
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail
 * @see		Package
 */
public enum Status {
	CREATION,
	COLLECTION,
	BRANCH_STORAGE,
	HUB_TRANSPORT,
	HUB_STORAGE,
	BRANCH_TRANSPORT,
	DELIVERY,
	DISTRIBUTION,
	DELIVERED
}
