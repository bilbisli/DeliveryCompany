package com.deliveryCompany.components;

/**
 * Represents the non-standard size packages.
 * @version 1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see  	Package
 */

public class NonStandardPackage extends Package {
	/**
	 * width of the package
	 */
	private final int width;
	/**
	 * length of the package
	 */
	private final int length;
	/**
	 * height of the package
	 */
	private final int height;
	
	/**
	 * contractor that accepts priority arguments, sender and recipient addresses, and package dimensions.
	 * @param priority - Represents the priority of the shipment
	 * @param senderAddress - Represents the sender's address
	 * @param destinationAddress - Represents the address to which the package is sent
	 * @param width - Represents the width of the package
	 * @param length - Represents the length of the package
	 * @param height - Represents the height of the package
	 */
	public NonStandardPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height) {
		super(priority, senderAddress, destinationAddress);
		this.width = width;
		this.length = length;
		this.height = height;
		System.out.println("Creating " + toString());
	}

	/**
	 * The function returns the width of the package
	 * @return the width of the package
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * The function returns the length of the package
	 * @return the length of the package
	 */
	public int getLength() {
		return length;
	}

	/**
	 * The function returns the height of the package
	 * @return the height of the package
	 */
	public int getHeight() {
		return height;
	}

	/**
	 *The function checks whether the two NonStandardPackage objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		NonStandardPackage other = (NonStandardPackage) obj;
		if (height != other.height)
			return false;
		if (length != other.length)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

	/**
	 * The function returns the string representation of the dimensions.
	 */
	@Override
	protected String packCharacteristics() {
		return "width=" + width + ", length=" + length + ", height=" + height;
	}
	
	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "NonStandardPackage";
	}
}
