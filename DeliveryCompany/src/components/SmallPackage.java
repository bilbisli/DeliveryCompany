package components;

/**
 * This class represents a small package
 * 
 * @version 2.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Package
 */
public class SmallPackage extends Package {

	private static final long serialVersionUID = 1L;

	/**
	 * Boolean field to determine if a special notification should be displayed when
	 * the small package arrives to it's destination
	 */
	private boolean acknowledge;

	/**
	 * Class constructor that creates a small package object based on specified
	 * parameters
	 * 
	 * @param priority          the priority of the package's shipment
	 * @param costumer			the costumer that created the package
	 * @param destinationAdress the address of the recipient of the package
	 * @param acknowledge       the indication if a special notification will be
	 *                          sent when the package arrives to it's destination
	 */
	public SmallPackage(Priority priority, Costumer costumer, Address destinationAdress, boolean acknowledge) {
		super(priority, destinationAdress, costumer);
		this.acknowledge = acknowledge;
		System.out.println("Creating " + this);

	}

	/**
	 * This method checks if a special notification needs to be sent when the
	 * package arrives to it's destination
	 * 
	 * @return true or false based on acknowledge
	 */
	public boolean isAcknowledge() {
		return acknowledge;
	}

	/**
	 * Set method for acknowledge field
	 * 
	 * @param acknowledge the specified parameter to set acknowledge to
	 */
	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	/**
	 * This method returns the special characteristics of a small package
	 * represented as a string
	 */
	@Override
	public String toString() {
		return "SmallPackage [" + super.toString() + ", acknowledge=" + acknowledge + "]";
	}

}
