/**
 * 
 */
package components;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

/**
 * This class represents a non standard truck that collects and delivers non
 * standard packages (one at a time)
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Package
 * @see Truck
 */
public class NonStandardTruck extends Truck {

	private static final long serialVersionUID = 1L;

	/**
	 * The truck width of the truck
	 */
	/**
	 * The truck length of the truck
	 */
	/**
	 * The truck height of the truck
	 */
	private int width, length, height;

	/**
	 * Class constructor that creates a non standard truck with randomly generated
	 * characteristics
	 */
	public NonStandardTruck() {
		super();
		Random r = new Random();
		width = (r.nextInt(3) + 2) * 100;
		length = (r.nextInt(6) + 10) * 100;
		height = (r.nextInt(2) + 3) * 100;
		System.out.println("Creating " + this);
	}

	/**
	 * Class constructor that creates a non standard truck based on specified
	 * characteristics
	 * 
	 * @param licensePlate the license plate of the truck to be set
	 * @param truckModel   the model of the truck to be set
	 * @param length       the length of the truck to be set
	 * @param width        the width of the truck to be set
	 * @param height       the height of the truck to be set
	 */
	public NonStandardTruck(String licensePlate, String truckModel, int length, int width, int height) {
		super(licensePlate, truckModel);
		this.width = width;
		this.length = length;
		this.height = height;
	}

	/**
	 * Get method for width field
	 * 
	 * @return the width of the truck
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Set method for the width field
	 * 
	 * @param width the specified width for setting the truck's width
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Get method for length field
	 * 
	 * @return the length of the truck
	 */
	public int getLength() {
		return length;
	}

	/**
	 * Set method for the length field
	 * 
	 * @param length the specified length for setting the truck's length
	 */
	public void setLength(int length) {
		this.length = length;
	}

	/**
	 * Get method for height field
	 * 
	 * @return the height of the truck
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Set method for the height field
	 * 
	 * @param height the specified height for setting the truck's height
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (this) {
				if (threadStop)
					break;
				while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
			if (!this.isAvailable()) {
				Package p = this.getPackages().get(0);
				this.setTimeLeft(this.getTimeLeft() - 1);
				if (this.getTimeLeft() == 0) {
					if (p.getStatus() == Status.COLLECTION) {
						System.out.println(
								"NonStandartTruck " + this.getTruckID() + " has collected package " + p.getPackageID());
						deliverPackage(p);
					}

					else {
						System.out.println("NonStandartTruck " + this.getTruckID() + " has delivered package "
								+ p.getPackageID() + " to the destination");
						this.getPackages().remove(p);
						p.setStatus(Status.DELIVERED);
						Tracking t = new Tracking(MainOffice.getClock(), null, p.getStatus(),
								p.getLastTracking().senderSerialNumber, p.getPackageID());
						p.addTracking(t);
						notifyObservers(t);
						setAvailable(true);
						if (!((MainOffice.getInstance().getPackages()).isEmpty())) {
							synchronized (MainOffice.getInstance().getHub()) {
								MainOffice.getInstance().getHub().notify();
							}
						}
					}
				}
			} else
				synchronized (this) {
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
		}
	}

	@Override
	public void work() {
	}

	/**
	 * Method for dealing with the delivery of a non standard package
	 */
	@Override
	public synchronized void deliverPackage(Package p) {
		int time = (Math.abs(p.getDestinationAddress().street - p.getDestinationAddress().street) % 10 + 1) * 10;
		this.setTimeLeft(time);
		this.initTime = time;
		p.setStatus(Status.DISTRIBUTION);
		Tracking t = new Tracking(MainOffice.getClock(), this, p.getStatus(), p.getLastTracking().senderSerialNumber,
				p.getPackageID());
		p.addTracking(t);
		notifyObservers(t);
		System.out.println("NonStandartTruck " + this.getTruckID() + " is delivering package " + p.getPackageID()
				+ ", time left: " + this.getTimeLeft());
	}

	/**
	 * To string method of a non standard truck to display it's characteristics
	 */
	@Override
	public String toString() {
		return "NonStandardTruck [" + super.toString() + ", length=" + length + ", width=" + width + ", height="
				+ height + "]";
	}

	@Override
	public void paintComponent(Graphics g) {
		int size = getPackages().size();
		if (isAvailable() || size == 0)
			return;
		Package p = this.getPackages().get(size == 0 ? 0 : size - 1);
		Point start = null;
		Point end = null;
		Color col = null;
		if (p.getStatus() == Status.COLLECTION) {
			start = new Point(1140, 216);
			end = p.getSendPoint();
			col = new Color(255, 180, 180);
		} else if (p.getStatus() == Status.DISTRIBUTION) {
			start = p.getSendPoint();
			end = p.getDestPoint();
			col = Color.RED;
		}

		if (start != null) {
			int x2 = start.getX();
			int y2 = start.getY();
			int x1 = end.getX();
			int y1 = end.getY();

			double ratio = (double) this.getTimeLeft() / this.initTime;
//			double length = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			int dX = (int) (ratio * (x2 - x1));
			int dY = (int) (ratio * (y2 - y1));

			g.setColor(col);
			g.fillRect(dX + x1 - 8, dY + y1 - 8, 16, 16);
			g.setColor(Color.BLACK);
			g.fillOval(dX + x1 - 12, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1, dY + y1, 10, 10);
			g.fillOval(dX + x1, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1 - 12, dY + y1, 10, 10);
		}
	}

}
