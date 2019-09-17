package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class PrimDemo is demo class for prime.
 * 
 * @author Filip
 */
public class PrimDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		initGUI();
	}

	/**
	 * Helper method which initialize all graphical components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		setLayout(new BorderLayout());

		PrimListModel model = new PrimListModel();

		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);

		JPanel central = new JPanel(new GridLayout(1, 0));
		central.add(new JScrollPane(list1));
		central.add(new JScrollPane(list2));
		cp.add(central, BorderLayout.CENTER);

		JButton btnNext = new JButton("sljedeći");
		btnNext.addActionListener((m) -> {
			model.next();
		});
		cp.add(btnNext, BorderLayout.PAGE_END);

	}

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new PrimDemo().setVisible(true);
		});
	}

}
