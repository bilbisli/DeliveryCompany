package com.deliveryCompany.components;

public class NonStandardPackage extends Package {
	private final int width;
	private final int length;
	private final int height;
	
	public NonStandardPackage(Priority priority, Address senderAddress, Address destinationAddress, int width, int length, int height) {
		super(priority, senderAddress, destinationAddress);
		this.width = width;
		this.length = length;
		this.height = height;
		System.out.println("Creating " + toString());
	}

	public int getWidth() {
		return width;
	}

	public int getLength() {
		return length;
	}

	public int getHeight() {
		return height;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + height;
		result = prime * result + length;
		result = prime * result + width;
		return result;
	}

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

	@Override
	protected String packCharacteristics() {
		return "width=" + width + ", length=" + length + ", height=" + height;
	}
	
	public String getSimpleName() {
		return "NonStandardPackage";
	}
}
