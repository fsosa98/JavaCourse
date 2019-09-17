package hr.fer.zemris.java.hw07.observer2;

/**
 * Class DoubleValue prints n times current value of subject.
 * 
 * @author Filip
 */
public class DoubleValue implements IntegerStorageObserver {

	private int n;
	private int changed = 0;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param n
	 */
	public DoubleValue(int n) {
		this.n = n;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		if (n > changed) {
			System.out.println("Double value: " + 2 * istorage.getNewValue());
			changed++;
		} else {
			istorage.getIstorage().removeObserver(this);
		}
	}

}
