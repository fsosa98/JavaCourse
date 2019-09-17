package hr.fer.zemris.java.hw07.observer2;

/**
 * Class ObserverExample is demo class.
 * 
 * @author Filip
 */
public class ObserverExample {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {

		IntegerStorage istorage = new IntegerStorage(20);

		IntegerStorageObserver observer = new SquareValue();
		istorage.addObserver(new ChangeCounter());
		istorage.addObserver(new DoubleValue(5));
		istorage.addObserver(observer);
		
		istorage.setValue(5);
		istorage.setValue(2);
		istorage.setValue(25);

		
		istorage.setValue(13);
		istorage.setValue(22);
		istorage.setValue(15);


	}

}
