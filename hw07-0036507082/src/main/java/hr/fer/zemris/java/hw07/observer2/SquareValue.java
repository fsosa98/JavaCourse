package hr.fer.zemris.java.hw07.observer2;

/**
 * Class DoubleValue prints square value of subject.
 * 
 * @author Filip
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorage) {
		System.out.println(
				"Provided new value: " + istorage.getNewValue() + ", square is " + Math.pow(istorage.getNewValue(), 2));
	}

}
