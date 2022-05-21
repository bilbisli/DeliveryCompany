package components;

import java.io.Serializable;

/**
 * This class represents an address of a costumer (sender \ recipient)
 * 
 * @version 2.00 11 juni 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Node
 */
public class Address implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Represents the zip of the address (the number of branch associated with this
	 * address)
	 */
	public final int zip;

	/**
	 * Represents the street of the costumer (as a 6 digit number)
	 */
	public final int street;

	/**
	 * Class constructor initiates the zip and the and the street number per
	 * specified zip and address
	 * 
	 * @param zip    the specified zip of the address
	 * @param street the specified street number of the address
	 */
	public Address(int zip, int street) {
		this.zip = zip;
		this.street = street;
	}

	/**
	 * Get method for zip field
	 * 
	 * @return the zip of the address
	 */
	public int getZip() {
		return zip;
	}

	/**
	 * Get method for street field
	 * 
	 * @return the street number of the address
	 */
	public int getStreet() {
		return street;
	}

	/**
	 * The function returns the string representation of the zip and the street.
	 */
	@Override
	public String toString() {
		return zip + "-" + street;
	}

}
