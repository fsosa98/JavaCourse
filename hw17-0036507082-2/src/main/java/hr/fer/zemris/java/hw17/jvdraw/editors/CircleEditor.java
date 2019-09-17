package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;

/**
 * Class CircleEditor is editor class extends GeometricalObjectEditor.
 * 
 * @author Filip
 */
public class CircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	private Circle circle;
	private JTextField sX;
	private JTextField sY;
	private JTextField rad;
	private JTextField colorR;
	private JTextField colorG;
	private JTextField colorB;
	private int srX;
	private int srY;
	private int radd;
	private int r;
	private int g;
	private int b;

	/**
	 * Constructor
	 * 
	 * @param circle
	 */
	public CircleEditor(Circle circle) {
		this.circle = circle;

		this.setLayout(new GridLayout(0, 6));

		this.add(new JLabel(" Center point x "));
		sX = new JTextField("" + circle.getS().x);
		this.add(sX);
		this.add(new JLabel(" Center point y "));
		sY = new JTextField("" + circle.getS().y);
		this.add(sY);

		this.add(new JLabel(" Radius "));
		rad = new JTextField("" + circle.getR());
		this.add(rad);

		this.add(new JLabel(" Color red "));
		colorR = new JTextField("" + circle.getColor().getRed());
		this.add(colorR);

		this.add(new JLabel(" Color green "));
		colorG = new JTextField("" + circle.getColor().getGreen());
		this.add(colorG);

		this.add(new JLabel(" Color blue "));
		colorB = new JTextField("" + circle.getColor().getBlue());
		this.add(colorB);

		this.setSize(100, 100);
	}

	@Override
	public void checkEditing() {
		srX = Integer.parseInt(sX.getText());
		srY = Integer.parseInt(sY.getText());
		radd = Integer.parseInt(rad.getText());

		r = Integer.parseInt(colorR.getText());
		g = Integer.parseInt(colorG.getText());
		b = Integer.parseInt(colorB.getText());

		if (srX < 0 || srY < 0 || radd < 0 || r < 0 || g < 0 || b < 0 || r > 255 || g > 255 || b > 255) {
			throw new RuntimeException();
		}
	}

	@Override
	public void acceptEditing() {
		circle.setS(new Point(srX, srY));
		circle.setR(radd);
		circle.setColor(new Color(r, g, b));
	}

}
