package com.deliveryCompany.components;

public class SmallPackage extends Package {
	private boolean acknowledge;

	SmallPackage(Priority priority, Address senderAddress, Address destinationAddress, boolean acknowledge) {
		super(priority, senderAddress, destinationAddress);
		this.acknowledge = acknowledge;
	}
	
	public boolean isAcknowledge() {
		return acknowledge;
	}

	public void setAcknowledge(boolean acknowledge) {
		this.acknowledge = acknowledge;
	}

	@Override
	protected String packCharacteristics() {
		return "acknowledge="  + acknowledge;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (acknowledge ? 1231 : 1237);
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
		SmallPackage other = (SmallPackage) obj;
		if (acknowledge != other.acknowledge)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SmallPackage [acknowledge=" + acknowledge + "]";
	}
	
	@Override
	public String getSimpleName() {
		return "SmallPackage";
	}
}
