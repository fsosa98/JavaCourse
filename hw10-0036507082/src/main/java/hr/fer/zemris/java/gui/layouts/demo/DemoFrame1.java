package hr.fer.zemris.java.gui.layouts.demo;

import java.awt.Color;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class DemoFrame1 is demo class for CalcLayout.
 * 
 * @author Filip
 */
public class DemoFrame1 extends JFrame {

	private static final long serialVersionUID = 1L;

	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Default constructor.
	 */
//	public DemoFrame1() {
//		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
//		setSize(500, 500);
//		initGUI();
//	}

	/**
	 * Helper method which initialize all graphical components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
	}

	/**
	 * Helper method for label.
	 * 
	 * @param text
	 * @return
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new DemoFrame1().setVisible(true);
		});
	}

}
