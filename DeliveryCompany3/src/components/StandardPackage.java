package components;

/**
 * Class represents packages with varying weights over one kilogram.
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 */
public class StandardPackage extends Package {

	private static final long serialVersionUID = 1L;

	/**
	 * weight of the package
	 */
	private double weight;

	/**
	 * Contractor that receives as priority arguments, sender and recipient
	 * addresses, package weight.
	 * 
	 * @param priority          - the priority of the package
	 * @param costumer			- the costumer that created the package
	 * @param destinationAdress - the destination address
	 * @param weight            - weight of the package
	 */
	public StandardPackage(Priority priority, Costumer costumer, Address destinationAdress, double weight) {
		super(priority, destinationAdress, costumer);
		this.weight = weight;
		System.out.println("Creating " + this);
	}

	/**
	 * The function returns the weight of the package
	 * 
	 * @return the weight of the package
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 * The function sets the weight of the package
	 * 
	 * @param weight - The weight which we want to update at the standard package
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}

	/**
	 * The function returns the string representation of the weight.
	 */
	@Override
	public String toString() {
		return "StandardPackage [" + super.toString() + ", weight=" + weight + "]";
	}
}
