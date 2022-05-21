package components;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents the vehicles (trucks) that transport packages between
 * the hub and the other branches
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 */
public abstract class Truck extends Observable implements Node, Cloneable {

	private static final long serialVersionUID = 1L;

	/**
	 * Represents the truck number (id) to set for the next truck that will be
	 * created
	 */
	private static int countID = 2000;

	/**
	 * Vehicle serial number (truck id)
	 */
	private int truckID;

	/**
	 * Truck's ID number
	 */
	private String licensePlate;

	/**
	 * Model of the truck
	 */
	private String truckModel;

	/**
	 * Vehicle availability
	 */
	private boolean available = true;

	/**
	 * Time left until the end of the current transport
	 */
	private int timeLeft = 0;

	/**
	 * List of packages for transportation that are in the vehicle
	 */
	private ArrayList<Package> packages = new ArrayList<Package>();

	/**
	 * the initial time
	 */
	protected int initTime;

	/**
	 * a flag that indicates the branch's process should be suspended
	 */
	protected boolean threadSuspend = false;

	/**
	 * a flag that indicates the branch's process should be stopped
	 */
	protected boolean threadStop = false;

	/**
	 * Random default constructor that produces an truck with a license plate and
	 * model of a vehicle randomly
	 */
	public Truck() {
		truckID = countID++;
		Random r = new Random();
		licensePlate = generateLicendPlate();
		truckModel = "M" + r.nextInt(5);
	}

	/**
	 * Regular constructor that receives as arguments a number plate and model of
	 * the vehicle and creates a truck accordingly
	 * 
	 * @param licensePlate - Vehicle ID number
	 * @param truckModel   - Model of vehicle
	 */
	public Truck(String licensePlate, String truckModel) {
		truckID = countID++;
		this.licensePlate = licensePlate;
		this.truckModel = truckModel;
	}

	/**
	 * Method for generating a license plate number
	 * @return the generated license plate number
	 */
	public static String generateLicendPlate() {
		Random r = new Random();
		return (r.nextInt(900) + 100) + "-" + (r.nextInt(90) + 10) + "-" + (r.nextInt(900) + 100);
	}

	/**
	 * Method that returns the Packages of the truck
	 * 
	 * @return the Packages of the truck
	 */
	public ArrayList<Package> getPackages() {
		return packages;
	}

	/**
	 * The method returns the time left until the end of the transport
	 * 
	 * @return timeLeft the time left
	 */
	public int getTimeLeft() {
		return timeLeft;
	}

	/**
	 * The method sets the value of the time left
	 * 
	 * @param timeLeft - The time left which we want to update the entry
	 */
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	/**
	 * The method returns the string representation of the truck
	 */
	@Override
	public String toString() {
		return "truckID=" + truckID + ", licensePlate=" + licensePlate + ", truckModel=" + truckModel + ", available= "
				+ available;
	}

	/**
	 * Method that deals with the collection of a package
	 * 
	 * @param p - the package that needs to be collected
	 */
	@Override
	public synchronized void collectPackage(Package p) {
		setAvailable(false);
		int time = (p.getSenderAddress().street % 10 + 1) * 10;
		this.setTimeLeft(time);
		this.initTime = time;
		this.packages.add(p);
		p.setStatus(Status.COLLECTION);
		Tracking t = new Tracking(MainOffice.getClock(), this, p.getStatus(), p.getLastTracking().senderSerialNumber,
				p.getPackageID());
		p.addTracking(t);
		notifyObservers(t);
		System.out.println(
				getName() + " is collecting package " + p.getPackageID() + ", time to arrive: " + getTimeLeft());
	}

	@Override
	public synchronized void deliverPackage(Package p) {
	}

	/**
	 * The function returns whether the vehicle is available or not
	 * 
	 * @return the availability of the truck
	 */
	public boolean isAvailable() {
		return available;
	}

	/**
	 * Get method for truck id number
	 * 
	 * @return the truck id number
	 */
	public int getTruckID() {
		return truckID;
	}

	/**
	 * Set method for truck id number to the next available id
	 */
	public void setTruckID() {
		this.truckID = Truck.countID++;
	}

	/**
	 * The function sets the value of the available
	 * 
	 * @param available - The availability which we want set for the truck
	 */
	public void setAvailable(boolean available) {
		this.available = available;
	}

	/**
	 * Get method for the name of the truck
	 */
	public String getName() {
		return this.getClass().getSimpleName() + " " + truckID;
	}

	/**
	 * set method for indicating the truck's process should be suspended
	 */
	public synchronized void setSuspend() {
		threadSuspend = true;
	}

	/**
	 * set method for indicating the truck's process should be resumed
	 */
	public synchronized void setResume() {
		threadSuspend = false;
		notify();
	}

	/**
	 * Paint method for displaying a branch on the visualization window
	 * @param g the visualization panel graphics (where to display the truck)
	 */
	public abstract void paintComponent(Graphics g);

	/**
	 * set method for indicating the truck's process should be stopped 
	 */
	public synchronized void setStop() {
		threadStop = true;
	}

	/**
	 * clone method for deep cloning a truck
	 */
	public Truck clone() {
		Truck clone = null;
		try {
			clone = (Truck) super.clone();
			clone.setPackages(new ArrayList<Package>());
			clone.setTruckID();
			clone.setLicensePlate();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return clone;
	}

	private void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}

	private void setLicensePlate() {
		this.licensePlate = Truck.generateLicendPlate();
	}

	/**
	 * Method for reseting the truck id counter (used for the occasion that a
	 * simulation is recreated)
	 */
	public static void resetCounter() {
		Truck.countID = 2000;
	}

}
