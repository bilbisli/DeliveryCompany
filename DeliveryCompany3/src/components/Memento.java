package components;

/**
 * This class represents a state of the system to be saved in memory (memento)
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail 308298363
 * @author Ofir Golan 315585323
 * @see MainOffice
 * @see Originator
 * @see CareTaker
 */
public class Memento {
	/**
	 * the state of the system
	 */
	private MainOffice state;

	/**
	 * class constructor
	 * 
	 * @param state the state to save
	 */
	public Memento(MainOffice state) {
		this.state = state;
	}

	/**
	 * get method for the saved state of the system
	 * 
	 * @return the saved state f the system
	 */
	public MainOffice getState() {
		return state;
	}
}
