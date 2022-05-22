package components;

import java.io.Serializable;

/**
 * Represents the location of a package, can refer to branches and trucks (all
 * points where the package can be in the various stages of its transfer).
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Branch , Truck
 */
public interface Node extends Serializable {

	/**
	 * A method that handles the collection / receipt of a package by the
	 * implementing department.
	 * 
	 * @param p - the package that we want to collect
	 */
	public void collectPackage(Package p);

	/**
	 * A method that handles the delivery of the package to the next person in the
	 * transfer chain.
	 * 
	 * @param p - the package that we want to deliver
	 */
	public void deliverPackage(Package p);

	/**
	 * A method that performs a work unit.
	 */
	public void work();

	/**
	 * A method that returns the name of the node (branch or track).
	 * 
	 * @return the name of the node
	 */
	public String getName();
}
