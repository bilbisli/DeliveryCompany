package components;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * A general type represents packages.
 * 
 * @version 2.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see SmallPackage
 * @see StandardPackage
 * @see NonStandardPackage
 */

public abstract class Package implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Represents the following Package number
	 */
	private static int countID = 1000;

	/**
	 * Represents the Package ID number
	 */
	final private int packageID;

	/**
	 * The sender's costumer ID
	 */
	final public int senderId;

	/**
	 * Represents the priority of the package
	 */
	private Priority priority;

	/**
	 * Represents the status of the package
	 */
	private Status status;

	/**
	 * Represents the sender Address
	 */
	private Address senderAddress;

	/**
	 * Represents the destination Address
	 */
	private Address destinationAddress;

	/**
	 * Represents collection of records with transfer history
	 */
	private ArrayList<Tracking> tracking = new ArrayList<Tracking>();

	/**
	 * the branch of the package
	 */
	private Branch branch = null;

	/**
	 * the sender point location of the package
	 */
	private Point sendPoint;

	/**
	 * the destination point location of the package
	 */
	private Point destPoint;

	/**
	 * the in point location of the package
	 */
	private Point bInPoint;

	/**
	 * the out point location of the package
	 */
	private Point bOutPoint;

	/**
	 * Contractor who accepts as arguments arguments, addresses the sender and
	 * receives a package.
	 * 
	 * @param priority          - the priority of the package
	 * @param costumer          - the costumer address
	 * @param destinationAdress - the destination address
	 */
	public Package(Priority priority, Address destinationAdress, Costumer costumer) {
		packageID = countID++;
		this.senderId = costumer.getSerialNumber();
		this.priority = priority;
		this.status = Status.CREATION;
		this.senderAddress = costumer.getAddress();
		this.destinationAddress = destinationAdress;
		tracking.add(new Tracking(MainOffice.getClock(), costumer, status, packageID));
	}

	/**
	 * Set method for the package's branch
	 * @param branch the branch to set
	 */
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	/**
	 * Get method for the package's branch
	 * @return the wanted branch
	 */
	public Branch getBranch() {
		return this.branch;
	}

	/**
	 * The function returns the package priority
	 * 
	 * @return the priority of the package
	 */
	public Priority getPriority() {
		return priority;
	}

	/**
	 * The function sets the value of the priority
	 * 
	 * @param priority - The Priority which we want to update the entry
	 */
	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	/**
	 * The function returns the package's status
	 * 
	 * @return the status of the package
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * The function sets the value of the status
	 * 
	 * @param status - The status which we want to update the entry
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

	/**
	 * The function returns the package ID
	 * 
	 * @return the package ID
	 */
	public int getPackageID() {
		return packageID;
	}

	/**
	 * The function returns the sender address of the package
	 * 
	 * @return the sender address of the package
	 */
	public Address getSenderAddress() {
		return senderAddress;
	}

	/**
	 * The function sets the sender address
	 * 
	 * @param senderAddress - The sender address which we want to update the entry
	 */
	public void setSenderAddress(Address senderAddress) {
		this.senderAddress = senderAddress;
	}

	/**
	 * The function returns the destination address of the package
	 * 
	 * @return the destination address of the package
	 */
	public Address getDestinationAddress() {
		return destinationAddress;
	}

	/**
	 * The function sets the destination address
	 * 
	 * @param destinationAdress - The Destination address which we want to update
	 *                          the entry
	 */
	public void setDestinationAddress(Address destinationAdress) {
		this.destinationAddress = destinationAdress;
	}

	/**
	 * Receives an object of type Node and a Status object, creates and adds an
	 * object of the Tracking class to the tracking collection in the class
	 * 
	 * @param node   - vehicle or branch
	 * @param status - the status of the vehicle or the branch
	 */
	public void addTracking(Node node, Status status) {
		tracking.add(new Tracking(MainOffice.getClock(), node, status, senderId, packageID));
	}

	/**
	 * Receives an object of type Tracking, creates and adds an new tracking listing
	 * for the package
	 * 
	 * @param t - a tracking of package
	 */
	public void addTracking(Tracking t) {
		tracking.add(t);
	}

	/**
	 * The function returns the tracking of the package
	 * 
	 * @return the tracking of the package
	 */
	public ArrayList<Tracking> getTracking() {
		return tracking;
	}

	/**
	 * Get method for the last tracking listing of the package
	 * @return the last tracking listing of the package
	 */
	public Tracking getLastTracking() {
		return getTracking().get(getTracking().size() - 1);
	}

	/**
	 * Prints the records stored in the tracking collection
	 */
	public void printTracking() {
		for (Tracking t : tracking)
			System.out.println(t);
	}

	/**
	 * The function returns the string representation of the object.
	 */
	@Override
	public String toString() {
		return "packageID=" + packageID + ", priority=" + priority + ", status=" + status + ", startTime="
				+ ", senderAddress=" + senderAddress + ", destinationAddress=" + destinationAddress;
	}

	/**
	 * Get method for the sender's point location
	 * @return the sender's point location
	 */
	public Point getSendPoint() {
		return sendPoint;
	}

	/**
	 * Get method for the destination point location
	 * @return the destination point location
	 */
	public Point getDestPoint() {
		return destPoint;
	}

	/**
	 * Get method for the out point location
	 * @return the out point location
	 */
	public Point getBInPoint() {
		return bInPoint;
	}

	/**
	 * Get method for the in point location
	 * @return the in point location
	 */
	public Point getBOutPoint() {
		return bOutPoint;
	}

	/**
	 * Paint method for displaying a package in and out on the visualization window
	 * @param g the visualization panel graphics (where to display the package's locations)
	 * @param x the x axis location to display the package's location
	 * @param offset the offset variant of spacing
	 */
	public void paintComponent(Graphics g, int x, int offset) {
		if (status == Status.CREATION || (branch == null && status == Status.COLLECTION))
			g.setColor(new Color(204, 0, 0));
		else
			g.setColor(new Color(255, 180, 180));
		g.fillOval(x, 20, 30, 30);

		if (status == Status.DELIVERED)
			g.setColor(new Color(204, 0, 0));
		else
			g.setColor(new Color(255, 180, 180));
		g.fillOval(x, 583, 30, 30);

		if (branch != null) {
			g.setColor(Color.BLUE);
			g.drawLine(x + 15, 50, 40, 100 + offset * this.senderAddress.getZip());
			sendPoint = new Point(x + 15, 50);
			bInPoint = new Point(40, 100 + offset * this.senderAddress.getZip());
			g.drawLine(x + 15, 583, 40, 130 + offset * this.destinationAddress.getZip());
			destPoint = new Point(x + 15, 583);
			bOutPoint = new Point(40, 130 + offset * this.destinationAddress.getZip());

		} else {
			g.setColor(Color.RED);
			g.drawLine(x + 15, 50, x + 15, 583);
			g.drawLine(x + 15, 50, 1140, 216);
			sendPoint = new Point(x + 15, 50);
			destPoint = new Point(x + 15, 583);

		}
	}

}
