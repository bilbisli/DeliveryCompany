package components;


/**
 * This class represents an address of a costumer (sender \ recipient)
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see		Node
 */
public class Address {
	/**
	 * Represents the zip of the address (the number of branch associated with this address)
	 */
	private final int zip;
	/**
	 * Represents the street of the costumer (as a 6 digit number)
	 */
	private final int street;
	/**
	 * Class constructor	initiates the zip and the and the street number per specified zip and address 
	 * @param zip			the specified zip of the address
	 * @param street		the specified street number of the address
	 */
	public Address (int zip, int street) {
		this.zip = zip;
		this.street = street;
	}
	/**
	 * Get method for zip field
	 * @return the zip of the address
	 */
	public int getZip() {
		return zip;
	}
	/**
	 * Get method for street field
	 * @return the street number of the address
	 */
	public int getStreet() {
		return street;
	}
	
	/**
	 * This method is for creating a representation of the address as "{zip} - {street numbet}" (i.e: 1-123456)
	 * @return the string representation of the address
	 */
	public String addressString() {
		return zip + "-" + street;
	}
}
