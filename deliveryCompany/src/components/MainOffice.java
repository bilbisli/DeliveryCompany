package components;

import java.util.ArrayList;
import java.util.Random;

/**
 * This class represents a main office that creates the branches of the delivery company, the trucks for the branches and the packages 
 * and makes the whole system run according to a clock
 * @version	1.00 7 Apr 2021
 * @author	Israel Avihail 308298363
 * @author	Ofir Golan 315585323
 * @see		Branch
 * @see		Package
 * @see		Truck
 */
public class MainOffice {
	/**
	 * Represents the time of the clock from the creation of the delivery company
	 */
	private static int clock = 0;
	/**
	 * Represents the hub of the delivery company
	 */
	private static Hub hub;
	/**
	 * The list of packages in the delivery company
	 */
	private ArrayList<Package> packages;
	
	/**
	 * Class constructor that creates all the branches and their trucks based on specified number of branches and number of trucks for each branch
	 * @param branches the number of branches to create
	 * @param trucksForBranch the number of trucks each branch will have (a hub has a single non standard truck not included in that number)
	 */
	public MainOffice(int branches, int trucksForBranch) {
		hub = new Hub();
		packages = new ArrayList<Package>();
		hub.addTrucks(trucksForBranch);
		hub.addTruck(new NonStandardTruck());
		for (int i = 0; i < branches; ++i) {
			Branch b = new Branch();
			b.addTrucks(trucksForBranch);
			hub.addBranch(b);
		}
	}
	
	/**
	 * This method makes the clock "tick" a specified number of times (activates a tick of the clock within a loop). 
	 * after all the ticks are done a report of the all the packages tracking will be displayed
	 * @param playTime the specified number of times the clock will tick
	 */
	public void play(int playTime) {
		for (int i = 0; i < playTime; ++i) {
			tick();
		}
		System.out.println("\n========================== STOP ==========================\n\n\n");
		printReport();
	}
	
	/**
	 * This method displays the report of all the packages tracking information
	 */
	public void printReport() {
		for (Package p : packages)
			p.printTracking();
	}
	
	/**
	 * This method constructs a string representation of the clock
	 * @return a string representation of the clock
	 */
	public String clockString() {
		return String.format("%02d:%02d", clock / 100, clock % 100);
	}
	
	/**
	 * This method activates a tick of the clock - displays the current time, advances the clock, creates packages at constant intervals and activates
	 * the work of the hub (with in turn will activate it's trucks and the branches work method)
	 */
	public void tick() {
		System.out.println(clockString());
		if (clock % 5 == 0)
			addPackage();
		hub.work();
		++clock;
	}
	
	/**
	 * This method adds a package (type and characteristics are random but standard packages are twice more likely to be created)
	 */
	public void addPackage() {
		Random rand = new Random();
		Package p;
		Priority priority = Priority.values()[rand.nextInt(3)];
		int zipSender = rand.nextInt(hub.getBranches().size()), zipDestination = rand.nextInt(hub.getBranches().size());
		Address sender = new Address(zipSender, 100000 + rand.nextInt(1000000));
		Address destination = new Address(zipDestination, 100000 + rand.nextInt(1000000));
		
		switch (rand.nextInt(4)) {
		case 0:
			boolean acknowledge = rand.nextBoolean();
			p = new SmallPackage(priority, sender, destination, acknowledge);
			hub.getBranch(zipSender).addPackage(p);
			break;
		case 1:
			int width = 1 + rand.nextInt(500), length = 1 + rand.nextInt(1000), height = 1 + rand.nextInt(400);
			p = new NonStandardPackage(priority, sender, destination, width, length, height);
			hub.addPackage(p);
			break;
		default:
			double weight = 1 + rand.nextInt(9) + rand.nextDouble();
			p = new StandardPackage(priority, sender, destination, weight);
			hub.getBranch(zipSender).addPackage(p);
		}
		packages.add(p);
	}
	
	/**
	 * This static get method of the hub is for accessing the hub all over the delivery company system
	 * @return hub the hub of the delivery company
	 */
	public static Hub getHub() {
		return hub;
	}
	
	/**
	 * Set method for hub field according to a specified parameter
	 * @param hub the specified parameter to set the delivery company's hub
	 */
	public void setHub(Hub hub) {
		MainOffice.hub = hub;
	}
	
	/**
	 * Get method for packages field
	 * @return the list of packages in the delivery company
	 */
	public ArrayList<Package> getPackages() {
		return packages;
	}
	
	/**
	 * Set method for the list of packages that are in the delivery company
	 * @param packages the specified list of packages to be set for the delivery company
	 */
	public void setPackages(ArrayList<Package> packages) {
		this.packages = packages;
	}
	
	/**
	 * Get method for the clock field
	 * @return the current clock of the system
	 */
	public static int getClock() {
		return clock;
	}
	
	/**
	 * set method for the clock field
	 * @param clock the specified clock to set the system's clock
	 */
	public static void setClock(int clock) {
		MainOffice.clock = clock;
	}
	
	/**
	 * This method is for accessing a specific branch by a specified index (id) threwout the system
	 * @param index the index (id) of the branch to get
	 * @return the branch with the specified id (index)
	 */
	public static Branch getBranch(int index) {
		return hub.getBranch(index);
	}
}
