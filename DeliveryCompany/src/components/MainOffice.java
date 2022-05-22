package components;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.swing.JPanel;

import program.Main;
import program.PostSystemPanel;

/**
 * This class represents a main office that creates the branches of the delivery
 * company, the trucks for the branches and the packages and makes the whole
 * system run according to a clock
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Branch
 * @see Hub
 * @see Package
 * @see Truck
 */
/**
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Branch
 * @see Package
 * @see StandardTruck
 * @see NonStandardTruck
 */
public class MainOffice implements Observer {

	/**
	 * The number of costumers of the delivery company
	 */
	public static final int COSTUMERS_NUMBER = 10;
	/**
	 * The name of the file to save the tracking listing of the packages of the system
	 */
	public static final String TRACKING_FILE = "tracking.txt";

	/**
	 * Represents the clock for units of work
	 */
	private static int clock = 0;

	/**
	 * Represents the hub branch of the delivery company
	 */
	private volatile Hub hub;

	/**
	 * Represents the list of all packages created in the simulation
	 */
	private volatile ArrayList<Package> packages = new ArrayList<Package>();

	/**
	 * Represents the list of all costumers created in the simulation
	 */
	private volatile ArrayList<Costumer> costumers = new ArrayList<Costumer>();

	/**
	 * Represents the visualization panel of the system
	 */
	private volatile JPanel panel;

	/**
	 * a flag that indicates the system's process should be suspended
	 */
	private volatile boolean threadSuspend = false;

	/**
	 * a flag that indicates the system's process should be stopped
	 */
	protected volatile boolean threadStop = false;

	/**
	 * the main office singleton
	 */
	private volatile static MainOffice mainOffice = null;

	/**
	 * the lock for reading and writing tracking listings to the text file
	 */
	private volatile ReadWriteLock lock = new ReentrantReadWriteLock(true);

	/**
	 * counter for numbering the lines in the trackingisting text file
	 */
	private volatile int writingNumber = 1;

	/**
	 * saves the initial parameters sent when the main office was created (used for system restoration)
	 */
	private volatile HashMap<String, Integer> parameters = new HashMap<String, Integer>();

	/**
	 * the thread pool of the costumers
	 */
	private volatile ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);

	/**
	 * the care taker that saves all the past states of the system
	 */
	private static CareTaker carer = new CareTaker();

	/**
	 * the originator that manages all the states of the system
	 */
	private static Originator originator = new Originator();

	/**
	 * Class constructor
	 * @param branches the branches number of the system
	 * @param trucksForBranch the trucks number for each branch of the system
	 * @param panel the visualization panel of the system
	 * @param maxPack the packages number of the system
	 */
	private MainOffice(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		this.panel = panel;
		parameters.put("branches", branches);
		parameters.put("trucks", trucksForBranch);
		parameters.put("packages", maxPack);
		addHub(trucksForBranch);
		addBranches(branches, trucksForBranch);
		System.out.println("\n\n========================== START ==========================");
	}

	/**
	 * Class singleton get instance mechanism
	 * @param branches the branches number of the system
	 * @param trucksForBranch the trucks number for each branch of the system
	 * @param panel the visualization panel of the system
	 * @param maxPack the packages number of the system
	 * @return the main office singleton
	 */
	public static MainOffice getInstance(int branches, int trucksForBranch, JPanel panel, int maxPack) {
		if (mainOffice == null)
			synchronized (MainOffice.class) {
				if (mainOffice == null) {
					mainOffice = new MainOffice(branches, trucksForBranch, panel,
							COSTUMERS_NUMBER * Costumer.COSTUMER_PACKAGE_NUMBER);
					resetCounters();
					getOriginator().setState(new MainOffice(branches, trucksForBranch, panel,
							COSTUMERS_NUMBER * Costumer.COSTUMER_PACKAGE_NUMBER));
					carer.add(getOriginator().saveStateToMemento());
				}
			}
		return mainOffice;
	}

	/**
	 * Class singleton get instance mechanism (no parameters)
	 * @return the main office singleton
	 */
	public static MainOffice getInstance() {
		if (mainOffice != null)
			return mainOffice;
		return getInstance(5, 5, new PostSystemPanel(new Main()), 11);
	}

	/**
	 * This static get method of the hub is for accessing the hub all over the
	 * delivery company system
	 * 
	 * @return hub the hub of the delivery company
	 */
	public Hub getHub() {
		return hub;
	}

	/**
	 * Get method for the clock field
	 * 
	 * @return clock the current clock of the system
	 */
	public static int getClock() {
		return clock;
	}

	/**
	 * Run method for starting the main office thread process
	 */
	@Override
	public void run() {
		resetFile();
		addCostumers();
		hub.registerObserver(MainOffice.getInstance());
		Thread hubThrad = new Thread(hub);
		hubThrad.start();

		for (Truck t : hub.listTrucks) {
			t.registerObserver(MainOffice.getInstance());
			Thread trackThread = new Thread(t);
			trackThread.start();
		}
		for (Branch b : hub.getBranches()) {
			b.registerObserver(MainOffice.getInstance());
			Thread branch = new Thread(b);
			for (Truck t : b.listTrucks) {
				t.registerObserver(MainOffice.getInstance());
				Thread truckThread = new Thread(t);
				truckThread.start();
			}
			branch.start();
		}
		for (Costumer c : costumers) {
			c.registerObserver(MainOffice.getInstance());
			pool.execute(c);
		}
		while (true) {
			synchronized (this) {
				if (threadStop || !packages.isEmpty() && isAllCostumersFinished()) {
					stopSystem();
					return;
				}
				while (threadSuspend)
					try {
						wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				if (threadStop || !packages.isEmpty() && isAllCostumersFinished()) {
					stopSystem();
					return;
				}
			}
			tick();
		}

	}

	/**
	 * method checks if all the costumers finished working
	 * @return true if all the costumers finished working
	 */
	private boolean isAllCostumersFinished() {
		for (Costumer c : costumers) {
			if (!c.isFinished())
				return false;
		}
		return true;
	}

	/**
	 * This method displays the report of all the packages tracking information
	 */
	public void printReport() {
		for (Package p : packages) {
			System.out.println("\nTRACKING " + p);
			for (Tracking t : p.getTracking())
				System.out.println(t);
		}
	}

	/**
	 * This method constructs a string (time) representation of the clock
	 * @param clock the clock to constructs it's string representation
	 * @return a string representation of the clock
	 */
	public static String clockString(int clock) {
		String s = "";
		int minutes = clock / 60;
		int seconds = clock % 60;
		s += (minutes < 10) ? "0" + minutes : minutes;
		s += ":";
		s += (seconds < 10) ? "0" + seconds : seconds;
		return s;
	}
	
	/**
	 * Method returns a string (time) representation of the current tick
	 * @return a string (time) representation of the current tick
	 */
	public String clockString() {
		return clockString(clock);
	}

	/**
	 * Method activates a tick of the system's clock and displays the visualization
	 */
	public void tick() {
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(clockString());
		++clock;
		panel.repaint();
	}

	/**
	 * Method for sending a branch to work
	 * @param b the branch to be sent to work
	 */
	public void branchWork(Branch b) {
		for (Truck t : b.listTrucks) {
			t.work();
		}
		b.work();
	}

	/**
	 * Method that deals with creating the delivery company hub and it's trucks
	 * (including it's non standard truck)
	 * 
	 * @param trucksForBranch the number of standard trucks to add to the hub
	 */
	public void addHub(int trucksForBranch) {
		hub = new Hub();
		for (int i = 0; i < trucksForBranch; i++) {
			hub.addTruck(new StandardTruck());
		}
		hub.addTruck(new NonStandardTruck());
	}

	/**
	 * Method that deals with creating the delivery company branches and their
	 * trucks
	 * 
	 * @param branches - the total number of branches of the delivery company
	 * @param trucks   - the total number of trucks for each branch of the delivery
	 *                 company
	 */
	public void addBranches(int branches, int trucks) {
		// creates the branches and their threads
		for (int i = 0; i < branches; i++) {
			Branch branch = new Branch();
			for (int j = 0; j < trucks; j++) {
				branch.addTruck(new Van());
			}
			hub.add_branch(branch);
		}
	}

	private void addCostumers() {
		for (int i = 0; i < COSTUMERS_NUMBER; ++i) {
			Costumer costumer = new Costumer();
			costumers.add(costumer);
		}
	}

	/**
	 * Get method for packages field
	 * 
	 * @return packages the list of packages in the delivery company
	 */
	public ArrayList<Package> getPackages() {
		return this.packages;
	}

	/**
	 * This method adds a package (type and characteristics are random but standard
	 * packages are twice more likely to be created)
	 * @param costumer the costumer that added the package
	 */
	public synchronized void addPackage(Costumer costumer) {
		Random r = new Random();
		Package p;
		Branch br;
		Priority priority = Priority.values()[r.nextInt(3)];
		Address dest = new Address(r.nextInt(hub.getBranches().size()), r.nextInt(999999) + 100000);

		switch (r.nextInt(3)) {
		case 0:
			p = new SmallPackage(priority, costumer, dest, r.nextBoolean());
			br = hub.getBranches().get(costumer.getAddress().zip);
			br.addPackage(p);
			p.setBranch(br);
			break;
		case 1:
			p = new StandardPackage(priority, costumer, dest, r.nextFloat() + (r.nextInt(9) + 1));
			br = hub.getBranches().get(costumer.getAddress().zip);
			br.addPackage(p);
			p.setBranch(br);
			break;
		case 2:
			p = new NonStandardPackage(priority, costumer, dest, r.nextInt(1000), r.nextInt(500), r.nextInt(400));
			br = hub;
			hub.addPackage(p);
			break;
		default:
			p = null;
			return;
		}
		this.packages.add(p);
		this.reportPackageArrival(p);
		synchronized (br) {
			br.notify();
		}

	}

	/**
	 * set method for indicating the sysetm's process should be suspended
	 */
	public synchronized void setSuspend() {
		threadSuspend = true;
		for (Truck t : hub.listTrucks) {
			t.setSuspend();
		}
		for (Branch b : hub.getBranches()) {
			for (Truck t : b.listTrucks) {
				t.setSuspend();
			}
			b.setSuspend();
		}
		hub.setSuspend();
		for (Costumer c : costumers) {
			c.setSuspend();
		}
	}

	/**
	 * set method for indicating the system's process should be resumed 
	 */
	public synchronized void setResume() {
		threadSuspend = false;
		notify();
		hub.setResume();
		for (Truck t : hub.listTrucks) {
			t.setResume();
		}
		for (Branch b : hub.getBranches()) {
			b.setResume();
			for (Truck t : b.listTrucks) {
				t.setResume();
			}
		}
		for (Costumer c : costumers) {
			c.setResume();
		}
	}

	/**
	 * set method for indicating the system's process should be stopped
	 */
	public synchronized void stopSystem() {
		threadStop = true;
		for (Truck t : hub.listTrucks) {
			t.setStop();
		}
		for (Branch b : hub.getBranches()) {
			for (Truck t : b.listTrucks) {
				t.setStop();
			}
			b.setStop();
		}
		hub.setStop();
		for (Costumer c : costumers) {
			c.setStop();
		}
		pool.shutdownNow();
	}

	/**
	 * Get method for the reading and writing lock
	 * @return the reading and writing lock
	 */
	public ReadWriteLock getLock() {
		return lock;
	}

	/**
	 * Set method for the reading and writing lock
	 * @param lock the reading and writing lock
	 */
	public void setLock(ReadWriteLock lock) {
		this.lock = lock;
	}

	/**
	 * Method that adds a tracking listing to the text file
	 * @param tracking a tracking listing to the text file
	 */
	public void reportPackageArrival(Tracking tracking) {
		lock.writeLock().lock();
		try {
			PrintWriter trackingOut = new PrintWriter(new FileWriter(TRACKING_FILE, true));
			trackingOut.println(writingNumber++ + ". " + tracking);
			trackingOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lock.writeLock().unlock();
	}

	/**
	 * Method that adds a tracking listing to the text file with a package parameter
	 * @param p a package parameter to add it's last tracking listing
	 */
	private void reportPackageArrival(Package p) {
		reportPackageArrival(p.getLastTracking());
	}

	/**
	 * Method that adds a tracking listing to the text file with an object parameter
	 * @param arg the object parameter to decide if it's aPackage or a Tracking listing and act accordingly
	 */
	private void reportPackageArrival(Object arg) {
		try {
			reportPackageArrival((Tracking) arg);
		} catch (ClassCastException e) {
			try {
				reportPackageArrival((Package) arg);
			} catch (ClassCastException er) {
				er.printStackTrace();
			}
		}
	}

	/**
	 * Method for updating the main office that a package listing was triggered (observer mechanism)
	 */
	@Override
	public void update(Object arg) {
		reportPackageArrival(arg);
	}

	/**
	 * Method for the branch cloning option
	 * @param b the branch to clone
	 */
	public void cloneBranchFromPrototype(Branch b) {
		getHub().add_branch(b.clone());
	}

	/**
	 * Method for the branch cloning option (index parameter)
	 * @param i the index of the branch to clone
	 */
	public void cloneFromPrototype(int i) {
		cloneBranchFromPrototype(getHub().getBranches().get(i));
	}

	/**
	 * Get method for the care taker of the system (list of mementos)
	 * @return the care taker of the system
	 */
	public static CareTaker getCarer() {
		return carer;
	}

	/**
	 * Set method for the care taker of the system (list of mementos)
	 * @param carer the care taker of the system
	 */
	public static void setCarer(CareTaker carer) {
		MainOffice.carer = carer;
	}

	/**
	 * Get method for the originator of the system (mementos management)
	 * @return the originator of the system
	 */
	public static Originator getOriginator() {
		return originator;
	}

	/**
	 * Set method for the originator of the system (mementos management)
	 * @param originator the originator of the system
	 */
	public static void setOriginator(Originator originator) {
		MainOffice.originator = originator;
	}

	/**
	 * method resets the counters of the delivery system (used for system restoration)
	 */
	public static void resetCounters() {
		Branch.resetCount();
		Truck.resetCounter();
		Costumer.resetSerial();
		clock = 0;
	}

	/**
	 * Method used to restore the system to it's initial state (memento DP)
	 */
	public void restore() {
		getOriginator().setState(mainOffice);
		carer.add(getOriginator().saveStateToMemento());
		MainOffice.getOriginator().getStateFromMemento(MainOffice.getCarer().get(0));
		MainOffice temp = MainOffice.getOriginator().getState();
		MainOffice.resetCounters();
		mainOffice = new MainOffice(temp.parameters.get("branches"), temp.parameters.get("trucks"), temp.panel,
				temp.parameters.get("packages"));
	}

	/**
	 * Method for resetting the tracking listing text file between system restorations
	 */
	public void resetFile() {
		lock.writeLock().lock();
		try {
			PrintWriter trackingOut = new PrintWriter(new FileWriter(TRACKING_FILE));
			trackingOut.print("");
			trackingOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		lock.writeLock().unlock();
	}
}
