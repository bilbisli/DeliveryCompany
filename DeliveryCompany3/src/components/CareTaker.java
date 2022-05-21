package components;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bilbisli care taker for storing system states (memento's)
 */
public class CareTaker {
	/**
	 * memento list
	 */
	private List<Memento> mementoList = new ArrayList<Memento>();

	/**
	 * method for adding a memento
	 * 
	 * @param state the state (memento) to save
	 */
	public void add(Memento state) {
		mementoList.add(state);
	}

	/**
	 * method for getting a saved state (memento)
	 * 
	 * @param index the index of the state to retrieve
	 * @return the wanted state
	 */
	public Memento get(int index) {
		return mementoList.get(index);
	}
}
