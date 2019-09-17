package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.Line;

/**
 * Class LineEditor is line editor class extends GeometricalObjectEditor.
 * 
 * @author Filip
 */
public class LineEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	private Line line;
	private JTextField startX;
	private JTextField startY;
	private JTextField endX;
	private JTextField endY;
	private JTextField colorR;
	private JTextField colorG;
	private JTextField colorB;
	private int aX;
	private int aY;
	private int bX;
	private int bY;
	private int r;
	private int g;
	private int b;

	/**
	 * Constructor
	 * 
	 * @param line
	 */
	public LineEditor(Line line) {
		this.line = line;

		JPanel north = new JPanel();
		JPanel south = new JPanel();
		north.setLayout(new GridLayout(0, 4));

		north.add(new JLabel(" Start point x "));
		startX = new JTextField("" + line.getA().x);
		north.add(startX);
		north.add(new JLabel(" Start point y "));
		startY = new JTextField("" + line.getA().y);
		north.add(startY);

		north.add(new JLabel(" End point x "));
		endX = new JTextField("" + line.getB().x);
		north.add(endX);
		north.add(new JLabel(" End point y "));
		endY = new JTextField("" + line.getB().y);
		north.add(endY);

		this.add(north, BorderLayout.NORTH);

		south.setLayout(new GridLayout(0, 6));

		south.add(new JLabel(" Color red "));
		colorR = new JTextField("" + line.getColor().getRed());
		south.add(colorR);

		south.add(new JLabel(" Color green "));
		colorG = new JTextField("" + line.getColor().getGreen());
		south.add(colorG);

		south.add(new JLabel(" Color blue "));
		colorB = new JTextField("" + line.getColor().getBlue());
		south.add(colorB);

		this.add(south, BorderLayout.SOUTH);

		this.setSize(100, 100);
	}

	@Override
	public void checkEditing() {
		aX = Integer.parseInt(startX.getText());
		aY = Integer.parseInt(startY.getText());
		bX = Integer.parseInt(endX.getText());
		bY = Integer.parseInt(endY.getText());

		r = Integer.parseInt(colorR.getText());
		g = Integer.parseInt(colorG.getText());
		b = Integer.parseInt(colorB.getText());

		if (aX < 0 || aY < 0 || bX < 0 || bY < 0 || r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
			throw new RuntimeException();
		}
	}

	@Override
	public void acceptEditing() {
		line.setA(new Point(aX, aY));
		line.setB(new Point(bX, bY));
		line.setColor(new Color(r, g, b));
	}

}
