package hr.fer.zemris.java.hw17.jvdraw.editors;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextField;

import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;

/**
 * Class FilledCircleEditor is editor class extends GeometricalObjectEditor.
 * 
 * @author Filip
 */
public class FilledCircleEditor extends GeometricalObjectEditor {

	private static final long serialVersionUID = 1L;
	private FilledCircle circle;
	private JTextField sX;
	private JTextField sY;
	private JTextField rad;
	private JTextField colorR1;
	private JTextField colorG1;
	private JTextField colorB1;
	private JTextField colorR2;
	private JTextField colorG2;
	private JTextField colorB2;
	private int srX;
	private int srY;
	private int radd;
	private int r1;
	private int g1;
	private int b1;
	private int r2;
	private int g2;
	private int b2;

	/**
	 * Constructor
	 * 
	 * @param circle
	 */
	public FilledCircleEditor(FilledCircle circle) {
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

		this.add(new JLabel(" Color line red "));
		colorR1 = new JTextField("" + circle.getColorLine().getRed());
		this.add(colorR1);
		this.add(new JLabel(" Color line green "));
		colorG1 = new JTextField("" + circle.getColorLine().getGreen());
		this.add(colorG1);
		this.add(new JLabel(" Color line blue "));
		colorB1 = new JTextField("" + circle.getColorLine().getBlue());
		this.add(colorB1);

		this.add(new JLabel(" Color fill red "));
		colorR2 = new JTextField("" + circle.getColorFill().getRed());
		this.add(colorR2);
		this.add(new JLabel(" Color fill green "));
		colorG2 = new JTextField("" + circle.getColorFill().getGreen());
		this.add(colorG2);
		this.add(new JLabel(" Color fill blue "));
		colorB2 = new JTextField("" + circle.getColorFill().getBlue());
		this.add(colorB2);

		this.setSize(100, 100);
	}

	@Override
	public void checkEditing() {
		srX = Integer.parseInt(sX.getText());
		srY = Integer.parseInt(sY.getText());
		radd = Integer.parseInt(rad.getText());

		r1 = Integer.parseInt(colorR1.getText());
		g1 = Integer.parseInt(colorG1.getText());
		b1 = Integer.parseInt(colorB1.getText());

		r2 = Integer.parseInt(colorR2.getText());
		g2 = Integer.parseInt(colorG2.getText());
		b2 = Integer.parseInt(colorB2.getText());

		if (srX < 0 || srY < 0 || radd < 0 || r1 < 0 || g1 < 0 || b1 < 0 || r1 > 255 || g1 > 255 || b1 > 255 || r2 < 0
				|| g2 < 0 || b2 < 0 || r2 > 255 || g2 > 255 || b2 > 255) {
			throw new RuntimeException();
		}
	}

	@Override
	public void acceptEditing() {
		circle.setS(new Point(srX, srY));
		circle.setR(radd);
		circle.setColorLine(new Color(r1, g1, b1));
		circle.setColorFill(new Color(r2, g2, b2));
	}

}
