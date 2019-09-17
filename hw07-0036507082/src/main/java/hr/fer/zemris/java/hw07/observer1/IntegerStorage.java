package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class IntegerStorage represents basic storage of integer values.
 * 
 * @author Filip
 */
public class IntegerStorage {

	private int value;
	private List<IntegerStorageObserver> observers; // use ArrayList here!!!

	/**
	 * Constructor with given parameters.
	 * 
	 * @param initialValue
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
		this.observers = new ArrayList<IntegerStorageObserver>();
	}

	/**
	 * This method adds given observer.
	 * 
	 * @param observer
	 */
	public void addObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if (!observers.contains(observer)) {
			observers.add(observer);
		}
	}

	/**
	 * This method removes given observer.
	 * 
	 * @param observer
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		Objects.requireNonNull(observer);
		if (observers.contains(observer)) {
			observers.remove(observer);
		}
	}

	/**
	 * This method removes all observers.
	 * 
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for value.
	 * 
	 * @return
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for value.
	 * 
	 * @param value
	 */
	public void setValue(int value) {
		// Only if new value is different than the current value:
		if (this.value != value) {
			// Update current value
			this.value = value;
			// Notify all registered observers
			if (observers != null) {
				for (IntegerStorageObserver observer : observers) {
					observer.valueChanged(this);
				}
			}
		}
	}
}
