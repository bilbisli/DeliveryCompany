package components;

/**
 * Class represents packages with varying weights over one kilogram.
 * @version 1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see  	Package
 */

public class StandardPackage extends Package {
	/**
	 * weight of the package
	 */
	private final double weight;
	
	/**
	 * Contractor that receives as priority arguments, sender and recipient addresses, package weight.
	 * @param priority - the priority of the package
	 * @param senderAddress - the sender address
	 * @param destinationAddress - the destination address
	 * @param weight - weight of the package
	 */
	public StandardPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight) {
		super(priority, senderAddress, destinationAddress);
		this.weight = weight;
		System.out.println("Creating " + toString());
	}
	
	/**
	 * The function returns the weight of the package
	 * @return the weight of the package
	 */
	public double getWeight() {
		return weight;
	}


	/**
	 *The function checks whether the two StandardPackage objects are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardPackage other = (StandardPackage) obj;
		if (Double.doubleToLongBits(weight) != Double.doubleToLongBits(other.weight))
			return false;
		return true;
	}

	/**
	 * The function returns the string representation of the weight.
	 */
	@Override
	protected String packCharacteristics() {
		return "weight=" + weight;
	}

	/**
	 * A function that returns the class name
	 * @return the name of the class
	 */
	public String getSimpleName() {
		return "StandardPackage";
	}
	
	
}
