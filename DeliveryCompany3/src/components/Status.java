package components;

/**
 * This enum is for setting the status of a certain package
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 */
public enum Status {
	/**
	 * creation status
	 */
	CREATION,
	/**
	 * collection status
	 */
	COLLECTION,
	/**
	 * branch storage status
	 */
	BRANCH_STORAGE,
	/**
	 * hub transport status
	 */
	HUB_TRANSPORT,
	/**
	 * hub storage status
	 */
	HUB_STORAGE,
	/**
	 * branch transport status
	 */
	BRANCH_TRANSPORT,
	/**
	 * delivery status
	 */
	DELIVERY,
	/**
	 * distribution status
	 */
	DISTRIBUTION,
	/**
	 * delivered status
	 */
	DELIVERED

}
