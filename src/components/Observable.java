package components;

import java.util.Vector;

/**
 * This class represents an observable object (used by observers)
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see Observer
 */
abstract class Observable implements Runnable {
	/**
	 * the observers list of this observable
	 */
	Vector<Observer> observers = new Vector<Observer>();

	/**
	 * method for adding (registering) an observer to this observable
	 * 
	 * @param observer the observer to register to this observable
	 */
	public void registerObserver(Observer observer) {
		observers.add(observer);
	}

	/**
	 * method for removing (unregistering) an observer from this observable
	 * 
	 * @param observer the observer to unregister from this observable
	 */
	public void unregisterObserver(Observer observer) {
		observers.remove(observer);
	}

	/**
	 * method for notifying the observers that an event in this observable has
	 * occurred
	 * 
	 * @param arg the message to pass to the observers
	 */
	protected void notifyObservers(Object arg) {
		if (observers != null && !observers.isEmpty())
			for (Observer observer : observers) {
				observer.update(arg);
			}
	}

	/**
	 * get method for the observers of this observable
	 * 
	 * @return the observers list of this observable
	 */
	protected Vector<Observer> getObservers() {
		return observers;
	}

	/**
	 * run method as part of making all observables runnable too
	 */
	@Override
	public abstract void run();
}
