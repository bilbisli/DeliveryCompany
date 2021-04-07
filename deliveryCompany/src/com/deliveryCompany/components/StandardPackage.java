package com.deliveryCompany.components;

public class StandardPackage extends Package {
	/**
	 * 
	 */
	private final double weight;
	
	/**
	 * @param priority
	 * @param senderAddress
	 * @param destinationAddress
	 * @param weight
	 */
	public StandardPackage(Priority priority, Address senderAddress, Address destinationAddress, double weight) {
		super(priority, senderAddress, destinationAddress);
		this.weight = weight;
		System.out.println("Creating " + toString());
	}
	
	/**
	 * @return
	 */
	public double getWeight() {
		return weight;
	}

	/**
	 *
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(weight);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	/**
	 *
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
	 *
	 */
	@Override
	protected String packCharacteristics() {
		return "weight=" + weight;
	}

	/**
	 *
	 */
	public String getSimpleName() {
		return "StandardPackage";
	}
	
	
}
