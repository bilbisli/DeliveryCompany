package components;

import java.awt.Color;
import java.awt.Graphics;

/**
 * This class represents a non standard truck that collects and delivers non
 * standard packages (one at a time)
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Package
 * @see Truck
 */
public class Van extends Truck {

	private static final long serialVersionUID = 1L;

	/**
	 * Class constructor that creates a van object
	 */
	public Van() {
		super();
		System.out.println("Creating " + this);
	}

	/**
	 * Class constructor that creates a van based on specified characteristics
	 * 
	 * @param licensePlate the license plate of the van to be set
	 * @param truckModel   the model of the van to be set
	 */
	public Van(String licensePlate, String truckModel) {
		super(licensePlate, truckModel);
	}

	/**
	 * Method returns a string representation of the van with it's characteristics
	 */
	@Override
	public String toString() {
		return "Van [" + super.toString() + "]";
	}

	/**
	 * This method handles the delivery of package from the van to a costumer, the
	 * appropriate status of the package is set and a tracking listing is added
	 */
	@Override
	public synchronized void deliverPackage(Package p) {
		this.getPackages().add(p);
		setAvailable(false);
		int time = (p.getDestinationAddress().street % 10 + 1) * 10;
		this.setTimeLeft(time);
		this.initTime = time;
		p.setStatus(Status.DISTRIBUTION);
		Tracking t = new Tracking(MainOffice.getClock(), this, p.getStatus(), p.getLastTracking().senderSerialNumber,
				p.getPackageID());
		p.addTracking(t);
		notifyObservers(t);

		System.out.println("Van " + this.getTruckID() + " is delivering package " + p.getPackageID() + ", time left: "
				+ this.getTimeLeft());
	}

	/**
	 * Run method for starting the van's thread process
	 */
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			synchronized (this) {
				while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (threadStop)
					return;
			}
			Branch branch = null;
			if (!this.isAvailable()) {
				this.setTimeLeft(this.getTimeLeft() - 1);
				if (this.getTimeLeft() == 0) {
					for (Package p : this.getPackages()) {
						if (p.getStatus() == Status.COLLECTION) {
							branch = MainOffice.getInstance().getHub().getBranches().get(p.getSenderAddress().zip);
							synchronized (branch) {
								p.setStatus(Status.BRANCH_STORAGE);
								System.out.println("Van " + this.getTruckID() + " has collected package "
										+ p.getPackageID() + " and arrived back to branch " + branch.getBranchId());
								branch.addPackage(p);
								p.getBranch().notify();
							}
						} else {
							p.setStatus(Status.DELIVERED);
							branch = MainOffice.getInstance().getHub().getBranches().get(p.getDestinationAddress().zip);
							synchronized (branch) {
								branch.listPackages.remove(p);
								branch.notify();
								branch = null;
								System.out.println("Van " + this.getTruckID() + " has delivered package "
										+ p.getPackageID() + " to the destination");
								if (p instanceof SmallPackage && ((SmallPackage) p).isAcknowledge()) {
									System.out.println("Acknowledge sent for package " + p.getPackageID());
								}
							}
						}
						Tracking t = new Tracking(MainOffice.getClock(), branch, p.getStatus(),
								p.getLastTracking().senderSerialNumber, p.getPackageID());
						p.addTracking(t);
						notifyObservers(t);
					}
					this.getPackages().removeAll(getPackages());
					this.setAvailable(true);
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

	@Override
	public void paintComponent(Graphics g) {
		int size = getPackages().size();
		if (isAvailable() || size == 0)
			return;

		Package p = this.getPackages().get(size == 0 ? 0 : size - 1);
		Point start = null;
		Point end = null;
		if (p.getStatus() == Status.COLLECTION) {
			start = p.getSendPoint();
			end = p.getBInPoint();
		} else if (p.getStatus() == Status.DISTRIBUTION) {
			start = p.getBOutPoint();
			end = p.getDestPoint();
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

			g.setColor(Color.BLUE);
			g.fillRect(dX + x1 - 8, dY + y1 - 8, 16, 16);
			g.setColor(Color.BLACK);
			g.fillOval(dX + x1 - 12, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1, dY + y1, 10, 10);
			g.fillOval(dX + x1, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1 - 12, dY + y1, 10, 10);
		}

	}

}
