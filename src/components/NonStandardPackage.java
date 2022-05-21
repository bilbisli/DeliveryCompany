package components;

/**
 * Represents the non-standard size packages.
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 */
public class NonStandardPackage extends Package {

	private static final long serialVersionUID = 1L;

	/**
	 * The width, length, height of the package.
	 */
	private int width, length, height;

	/**
	 * contractor that accepts priority arguments, sender and recipient addresses,
	 * and package dimensions.
	 * 
	 * @param priority          - Represents the priority of the shipment
	 * @param costumer  		- The costumer that created the package
	 * @param destinationAdress - Represents the address to which the package is
	 *                          sent
	 * @param width             - Represents the width of the package
	 * @param length            - Represents the length of the package
	 * @param height            - Represents the height of the package
	 */
	public NonStandardPackage(Priority priority, Costumer costumer, Address destinationAdress, int width, int length,
			int height) {
		super(priority, destinationAdress, costumer);
		this.width = width;
		this.length = length;
		this.height = height;
		System.out.println("Creating " + this);
	}

	/**
	 * The function returns the width of the package
	 * 
	 * @return the width of the package
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * The function sets the width of the package
	 * 
	 * @param width - The width which we want to update at the non standard package
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * The function returns the length of the package
	 * 
	 * @return the length of the package
	 */
	public int getLength() {
		return length;
	}

	/**
	 * The function sets the length of the package
	 * 
	 * @param length - The length which we want to update at the non standard
	 *               package
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * The function returns the height of the package
	 * 
	 * @return the height of the package
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * The function sets the height of the package
	 * 
	 * @param height - The height which we want to update at the non standard
	 *               package
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * The function returns the string representation of the dimensions of non
	 * standard package.
	 */
	@Override
	public String toString() {
		return "NonStandardPackage [" + super.toString() + ", width=" + width + ", length=" + length + ", height="
				+ height + "]";
	}

}
