package components;

/**
 * This class represents an originator that carries the current state, and
 * creates states to save in the memory (memento)
 * 
 * @version 3.00 12 June 2021
 * @author Israel Avihail
 * @author Ofir Golan
 * @see Memento
 * @see CareTaker
 * @see MainOffice
 */
public class Originator {
	/**
	 * The current state in the originator
	 */
	private MainOffice state;

	/**
	 * Set method for the state of the originator
	 * 
	 * @param state the state of the originator
	 */
	public void setState(MainOffice state) {
		this.state = state;
	}

	/**
	 * Get method for the state of the originator
	 * 
	 * @return the state within the originator
	 */
	public MainOffice getState() {
		return state;
	}

	/**
	 * Method for saving the current state within the originator to a memento
	 * 
	 * @return the state of the originator (memento)
	 */
	public Memento saveStateToMemento() {
		return new Memento(state);
	}

	/**
	 * Set method for setting the current state within the originator to the state
	 * within a given memento (unpacking)
	 * 
	 * @param memento the memento to get the state that will be set for the originator
	 */
	public void getStateFromMemento(Memento memento) {
		state = memento.getState();
	}
}
