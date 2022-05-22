package components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.Random;

/**
 * Vehicle for transporting packages from the sorting center to the branches and
 * back. All vehicles of this type are in the sorting center.
 * 
 * @version 2.00 11 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Truck
 */
public class StandardTruck extends Truck {

	private static final long serialVersionUID = 1L;

	/**
	 * Maximum weight that a vehicle can carry.
	 */
	private int maxWeight;

	/**
	 * Target branch / sorting hub
	 */
	private Branch destination = null;

	/**
	 * the source (origin) of the truck path in the current journey
	 */
	private Branch source = null;

	/**
	 * A default Contractor that produces an object with a license plate number and
	 * a vehicle model at random.
	 */
	public StandardTruck() {
		super();
		maxWeight = ((new Random()).nextInt(2) + 2) * 100;
		System.out.println("Creating " + this);

	}

	/**
	 * Contractor that accepts as arguments: license plate number, vehicle model and
	 * maximum weight
	 * 
	 * @param licensePlate - license plate of the standard truck
	 * @param truckModel   - Model of the StandardTruck
	 * @param maxWeight    - Maximum weight that a vehicle can carry
	 */
	public StandardTruck(String licensePlate, String truckModel, int maxWeight) {
		super(licensePlate, truckModel);
		this.maxWeight = maxWeight;
	}

	/**
	 * The function returns the destination of the standard truck .
	 * 
	 * @return the destination of the standard truck
	 */
	public Branch getDestination() {
		return destination;
	}

	/**
	 * The function sets the value of the destination of the the standard truck.
	 * 
	 * @param destination - the destination of the the standard truck.
	 */
	public void setDestination(Branch destination) {
		this.destination = destination;
	}

	/**
	 * The function returns the maximum weight that the standard truck can carry.
	 * 
	 * @return the weight of the package
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * The function sets the value of the maximum weight that the standard truck can
	 * carry.
	 * 
	 * @param maxWeight - the max weight that we want to entry
	 */
	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	/**
	 * The function returns the string representation of the standard truck.
	 */
	@Override
	public String toString() {
		return "StandartTruck [" + super.toString() + ",maxWeight=" + maxWeight + "]";
	}

	/**
	 * Method that deals with unloading packages upon arrival to the destination
	 * @param dest the destination branch to unload the packages
	 */
	public void unload(Branch dest) {
		Status status;
		synchronized (dest) {
			if (dest == MainOffice.getInstance().getHub())
				status = Status.HUB_STORAGE;
			else
				status = Status.DELIVERY;

			for (Package p : getPackages()) {
				p.setStatus(status);
				dest.addPackage(p);
				p.addTracking(dest, status);
			}
			getPackages().removeAll(getPackages());
			System.out.println("StandardTruck " + getTruckID() + " unloaded packages at " + destination.getName());
		}
	}

	/**
	 * Method that deals with loading packages upon leaving a branch \ hub
	 * 
	 * @param sender - the branch that sends the standard truck
	 * @param dest   - the branch that the standard truck is sent to
	 * @param status - the status to update the packages to
	 */
	public void load(Branch sender, Branch dest, Status status) {
		double totalWeight = 0;
		synchronized (sender) {
			for (int i = 0; i < sender.listPackages.size(); i++) {
				Package p = sender.listPackages.get(i);
				if (p.getStatus() == Status.BRANCH_STORAGE || (p.getStatus() == Status.HUB_STORAGE && MainOffice
						.getInstance().getHub().getBranches().get(p.getDestinationAddress().zip) == dest)) {
					if (p instanceof SmallPackage && totalWeight + 1 <= maxWeight
							|| totalWeight + ((StandardPackage) p).getWeight() <= maxWeight) {
						getPackages().add(p);
						sender.listPackages.remove(p);
						i--;
						p.setStatus(status);
						p.addTracking(this, status);
					}
				}
			}
			System.out.println(this.getName() + " loaded packages at " + sender.getName());
		}
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
			if (!isAvailable()) {
				setTimeLeft(getTimeLeft() - 1);
				if (getTimeLeft() == 0) {
					System.out.println("StandardTruck " + getTruckID() + " arrived to " + destination.getName());
					unload(destination);
					synchronized (destination) {
						destination.notify();
					}
					if (destination == MainOffice.getInstance().getHub()) {
						setAvailable(true);
						synchronized (MainOffice.getInstance().getHub()) {
							MainOffice.getInstance().getHub().notify();
						}
					}

					else {
						load(destination, MainOffice.getInstance().getHub(), Status.HUB_TRANSPORT);
						setTimeLeft(((new Random()).nextInt(6) + 1) * 10);
						this.initTime = this.getTimeLeft();
						source = destination;
						destination = MainOffice.getInstance().getHub();
						System.out.println(
								this.getName() + " is on it's way to the HUB, time to arrive: " + getTimeLeft());
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

//	@Override
//	public void run() {
//		while(true) {
//			try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		    synchronized(this) {
//				if(threadStop)
//		    		break;	
//                while (threadSuspend)
//					try {
//						wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//		    }
//			if (!isAvailable()) {
//				setTimeLeft(getTimeLeft()-1);
//				if (getTimeLeft()==0) {
//					System.out.println("StandardTruck "+ getTruckID()+ " arrived to " + destination.getName());
//					unload(destination);
//					if (destination==MainOffice.getInstance().getHub()) {
//						setAvailable(true);
//					}
//						
//					else {
//						load(destination, MainOffice.getInstance().getHub(), Status.HUB_TRANSPORT);
//						setTimeLeft(((new Random()).nextInt(6)+1)*10);
//						this.initTime = this.getTimeLeft();
//						source = destination;
//						destination=MainOffice.getInstance().getHub();
//						System.out.println(this.getName() + " is on it's way to the HUB, time to arrive: "+ getTimeLeft());
//					}			
//				}
//			}
//			else
//				synchronized(this) {
//					try {
//						wait();
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//		}
//	}

	public void work() {

	}

	@Override
	public void paintComponent(Graphics g) {
		Point start = null;
		Point end = null;
		Color col = new Color(102, 255, 102);
		if (this.getPackages() == null || destination == null)
			return;

		if (this.getPackages().size() == 0) {
			if (destination != MainOffice.getInstance().getHub()) {
				end = this.destination.getBranchPoint();
				start = this.destination.getHubPoint();
			} else {
				start = this.source.getBranchPoint();
				end = this.source.getHubPoint();
			}
		} else {
			Package p = this.getPackages().get(getPackages().size() - 1);
			col = new Color(0, 102, 0);
			if (p.getStatus() == Status.HUB_TRANSPORT) {
				start = this.source.getBranchPoint();
				end = this.source.getHubPoint();
			} else if (p.getStatus() == Status.BRANCH_TRANSPORT) {
				end = this.destination.getBranchPoint();
				start = this.destination.getHubPoint();
			}
		}

		if (start != null) {
			int x2 = start.getX();
			int y2 = start.getY();
			int x1 = end.getX();
			int y1 = end.getY();

			double ratio = (double) this.getTimeLeft() / this.initTime;
			// System.out.println(x2+" "+x1+" "+ratio);
//			double length = Math.sqrt((x2-x1)*(x2-x1)+(y2-y1)*(y2-y1));
			int dX = (int) (ratio * (x2 - x1));
			int dY = (int) (ratio * (y2 - y1));

			g.setColor(col);
			g.fillRect(dX + x1 - 8, dY + y1 - 8, 16, 16);
			g.setColor(Color.BLACK);
			g.setFont(new Font("Courier", Font.BOLD, 13));
			if (this.getPackages().size() > 0)
				g.drawString("" + this.getPackages().size(), dX + x1 - 3, dY + y1 - 8 - 5);
			g.fillOval(dX + x1 - 12, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1, dY + y1, 10, 10);
			g.fillOval(dX + x1, dY + y1 - 12, 10, 10);
			g.fillOval(dX + x1 - 12, dY + y1, 10, 10);
		}

	}

}
