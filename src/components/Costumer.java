package components;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a costumer of the delivery company
 * 
 * @version 3.00 12 July 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Package
 * @see Address
 */
public class Costumer extends Observable implements Node {

	/**
	 * serial version UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * the packages each costumer will have at the end of the program
	 */
	public static final int COSTUMER_PACKAGE_NUMBER = 5;
	/**
	 * the costumers serial number counter
	 */
	private static int serial = 1;
	/**
	 * the serial number of the costumer
	 */
	private int serialNumber;
	/**
	 * the address of the costumer
	 */
	private Address address;
	/**
	 * a flag that indicates the costumer's process should be suspended
	 */
	protected boolean threadSuspend = false;
	/**
	 * a flag that indicates the costumer's process should be stopped
	 */
	protected boolean threadStop = false;
	/**
	 * a flag that indicates the costumer has finished working (producing packages)
	 */
	private boolean finished = false;

	/**
	 * class regular constructor
	 */
	public Costumer() {
		super();
		this.serialNumber = serial++;
		Random r = new Random();
		this.address = new Address(r.nextInt(MainOffice.getInstance().getHub().getBranches().size()),
				r.nextInt(999999) + 100000);
	}

	/**
	 * class parameter constructor
	 * 
	 * @param address the address of the new costumer
	 */
	public Costumer(Address address) {
		super();
		this.serialNumber = serial++;
		this.address = address;
	}

	/**
	 * run method for the costumer's thread
	 */
	@Override
	public void run() {
		for (int i = 0; i < COSTUMER_PACKAGE_NUMBER; ++i) {
			if (threadStop)
				return;
			try {
				Thread.sleep(((new Random()).nextInt(4) + 2) * 1000);
			} catch (InterruptedException e1) {
				if (!threadStop)
					e1.printStackTrace();
			}
			synchronized (this) {
				if (threadStop)
					return;
				while (threadSuspend) {
					try {
						wait();
					} catch (InterruptedException e) {
						if (!threadStop)
							e.printStackTrace();
					}
				}
			}
			addPackage();
		}

		while (!isAllDelivered()) {
			if (threadStop)
				return;
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e1) {
				if (!threadStop)
					e1.printStackTrace();
			}
			synchronized (this) {
				if (threadStop)
					return;
				try {
					while (threadSuspend)
						wait();
				} catch (InterruptedException e) {
					if (!threadStop)
						e.printStackTrace();
				}
			}
		}
		setFinished(true);
	}

	/**
	 * method for adding a new package to the system
	 */
	private synchronized void addPackage() {
		MainOffice.getInstance().addPackage(this);
	}

	/**
	 * method that checks if all of the costumer's packages were delivered
	 * 
	 * @return true if all the costumer's packages were delivered
	 */
	private boolean isAllDelivered() {
		return totalDelivered() == Costumer.COSTUMER_PACKAGE_NUMBER;
	}

	/**
	 * get method for the costumer's serial number
	 * 
	 * @return the costumer's serial number
	 */
	public int getSerialNumber() {
		return serialNumber;
	}

	/**
	 * set method for the costumer's serial number
	 * 
	 * @param serialNumber the costumer's serial number
	 */
	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	/**
	 * get method for the costumer's address
	 * 
	 * @return the address of the costumer
	 */
	public Address getAddress() {
		return address;
	}

	/**
	 * set method for the costumer's address
	 * 
	 * @param address the costumer's address
	 */
	public void setAddress(Address address) {
		this.address = address;
	}

	/**
	 * set method for indicating the costumer's process should be suspended
	 */
	public void setSuspend() {
		threadSuspend = true;
	}

	/**
	 * set method for indicating the costumer's process should be resumed
	 */
	public void setResume() {
		threadSuspend = false;
		synchronized (this) {
			notify();
		}
	}

	/**
	 * set method for indicating the costumer's process should be stopped
	 */
	public void setStop() {
		setFinished(true);
		threadStop = true;
	}

	/**
	 * method that counts (in the text file) the costumer's packages that were
	 * delivered
	 * 
	 * @return the number of the costumer's packages that were delivered
	 */
	public int totalDelivered() {
		MainOffice.getInstance().getLock().readLock().lock();
		int deliveredNumber = 0;
		FileInputStream trackingIn = null;
		String line;

		try {
			trackingIn = new FileInputStream(MainOffice.TRACKING_FILE);
			Scanner scan = new Scanner(trackingIn);
			while (scan.hasNextLine()) {
				line = scan.nextLine();
				if (line.endsWith("" + this.serialNumber) && line.contains(Status.DELIVERED.toString())) {
					++deliveredNumber;
				}
			}
			scan.close();
			trackingIn.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		MainOffice.getInstance().getLock().readLock().unlock();
		return deliveredNumber;
	}

	/**
	 * 
	 */
	@Override
	public void collectPackage(Package p) {
	}

	/**
	 * 
	 */
	@Override
	public void deliverPackage(Package p) {
	}

	/**
	 * 
	 */
	@Override
	public void work() {
	}

	/**
	 * method for getting the name of the costumer and his serial number
	 */
	@Override
	public String getName() {
		return "Costumer " + getSerialNumber();
	}

	/**
	 * method resets the serial number counter (used for system restoration)
	 */
	public static void resetSerial() {
		serial = 1;
	}

	/**
	 * method for checking if the costumer's finished flag has been raised
	 * 
	 * @return the state of the costumer (finished \ not finished)
	 */
	public boolean isFinished() {
		return finished;
	}

	/**
	 * method for setting the costumer's finished flag
	 * 
	 * @param finished the flag that indicates the costumer has finished working
	 */
	public void setFinished(boolean finished) {
		this.finished = finished;
	}

}
