package components;

/**
 * This class represents an observer object
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Observable
 */
public interface Observer extends Runnable {
	/**
	 * method for updating this observer that an event in an object that it observes
	 * occurred
	 * 
	 * @param arg the message from the object that this observer observers
	 */
	public void update(Object arg);

	/**
	 * run method as part of making all observers runnable too
	 */
	public void run();
}
