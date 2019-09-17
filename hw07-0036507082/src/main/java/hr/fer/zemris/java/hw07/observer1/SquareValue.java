package hr.fer.zemris.java.hw07.observer1;

/**
 * Class DoubleValue prints square value of subject.
 * 
 * @author Filip
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		System.out.println(
				"Provided new value: " + istorage.getValue() + ", square is " + Math.pow(istorage.getValue(), 2));
	}

}
