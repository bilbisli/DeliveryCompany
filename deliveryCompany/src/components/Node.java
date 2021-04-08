package components;
/**
 * Represents the location of a package, can refer to branches and trucks (all points where the package can be
 * in the various stages of its transfer).
 * @version 1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see  	Branch , Truck
 */

public interface Node {
	/**
	 * A method that handles the collection / receipt of a package by the implementing department.
	 * @param p - the package that we want to collect
	 */
	void collectPackage(Package p);
	/**
	 * A method that handles the delivery of the package to the next person in the transfer chain.
	 * @param p - the package that we want to deliver
	 */
	void deliverPackage(Package p);
	/**
	 * A method that performs a work unit.
	 */
	void work();
}
