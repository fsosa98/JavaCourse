package hr.fer.zemris.java.hw07.observer2;

/**
 * Class ChangeCounter count how many time subject has changed.
 * 
 * @author Filip
 */
public class ChangeCounter implements IntegerStorageObserver {

	private int changed = 0;

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		changed++;
		System.out.println("Number of value changes since tracking: " + changed);
	}

}
