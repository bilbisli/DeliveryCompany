package components;

import java.io.Serializable;

/**
 * This class represents Tracking listing of a package
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 * @see Node
 */
public class Tracking implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Represents the time when the listing was made
	 */
	public final int time;

	/**
	 * Represents where the package was when the listing was made (truck or branch)
	 */
	public final Node node;

	/**
	 * Represents the status of the package when the listing was made
	 */
	public final Status status;

	/**
	 * Represents the sender serial number
	 */
	public final int senderSerialNumber;

	/**
	 * Represents the ID of the package
	 */
	public final int packageID;

	/**
	 * Class constructor with specified parameters
	 * 
	 * @param time      the time to set the time when the listing was made
	 * @param costumer  the costumer that created the package
	 *                  listing was made
	 * @param status    the status to set the status of the package when the listing
	 *                  was made
	 * @param packageID the ID of the package
	 */
	public Tracking(int time, Costumer costumer, Status status, int packageID) {
		super();
		this.time = time;
		this.node = costumer;
		this.status = status;
		this.senderSerialNumber = costumer.getSerialNumber();
		this.packageID = packageID;
	}

	/**
	 * Class constructor with specified parameters
	 * 
	 * @param time               the time to set the time when the listing was made
	 * @param node               the node to set the Node where the package was when
	 *                           the listing was made
	 * @param status             the status to set the status of the package when
	 *                           the listing was made
	 * @param packageID          the ID of the package
	 * @param senderSerialNumber the sender serial number
	 */
	public Tracking(int time, Node node, Status status, int senderSerialNumber, int packageID) {
		super();
		this.time = time;
		this.node = node;
		this.status = status;
		this.senderSerialNumber = senderSerialNumber;
		this.packageID = packageID;
	}

	/**
	 * This method builds and displays a string representation of a tracking listing
	 */
	@Override
	public String toString() {
		String name = (node == null ? "Costumer destination" : node.getName());
		return "package id: " + packageID + ", time: " + MainOffice.clockString(time) + ", node: " + name + ", status: "
				+ status + ", costumer SN: " + senderSerialNumber;
	}

}
