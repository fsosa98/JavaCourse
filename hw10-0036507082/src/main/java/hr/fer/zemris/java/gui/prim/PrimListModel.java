package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Class PrimListModel is implementation of list model.
 * 
 * Users can get size, get elements on given index, get next prime number.
 * 
 * @author Filip
 */
public class PrimListModel implements ListModel<Integer> {

	private List<Integer> elements = new ArrayList<>();
	private List<ListDataListener> listeners = new ArrayList<>();
	private int prime;

	/**
	 * Default constructor.
	 */
	public PrimListModel() {
		prime = 1;
		elements.add(1);
	}

	@Override
	public int getSize() {
		return elements.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return elements.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

	/**
	 * This method returns next prime number.
	 */
	public void next() {
		int n = prime;
		while (true) {
			n++;
			boolean isPrime = true;
			for (int i = 2; i < n; i++) {
				if (n % i == 0) {
					isPrime = false;
					break;
				}
			}
			if (isPrime) {
				prime = n;
				elements.add(prime);
				break;
			}
		}
		int pos = elements.size();
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, pos, pos);
		for (ListDataListener l : listeners) {
			l.intervalAdded(event);
		}
	}

}
