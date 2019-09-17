package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Class BarChartComponent defines bar chart component.
 * 
 * Defines component for given bar chart.
 * 
 * @author Filip
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = -7984102835838938438L;

	private BarChart barChart;
	private static final int SPACE1 = 8;
	private static final int CONST = 3;
	private int SPACE;
	private int width;
	private int height;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param barChart given barChart
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		SPACE = String.valueOf(barChart.yMax).length() * SPACE1;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform saveAT = g2d.getTransform();
		width = this.getWidth() - 6 * SPACE;
		height = this.getHeight() - 6 * SPACE;

		FontMetrics font = g2d.getFontMetrics();
		int stringLength = font.bytesWidth(barChart.xAxis.getBytes(), 0, barChart.xAxis.length());
		g2d.drawString(barChart.xAxis, this.getWidth() / 2 - stringLength / 2, this.getHeight() - SPACE);

		stringLength = font.bytesWidth(barChart.yAxis.getBytes(), 0, barChart.yAxis.length());
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(barChart.yAxis, -(this.getHeight() / 2 + stringLength / 2), SPACE);

		g2d.setTransform(saveAT);

		int numOfX = barChart.getValues().size();
		int diffX = width / numOfX;
		int diffY = height / ((barChart.yMax - barChart.yMin) / barChart.space);
		int yZero = this.getHeight() - 3 * SPACE;

		int k = 0;
		for (int j = barChart.yMin; j <= barChart.yMax; j += barChart.space) {
			g2d.setColor(Color.YELLOW);
			g2d.setStroke(new BasicStroke(1));
			if (j != barChart.yMin) {
				g2d.drawLine(3 * SPACE, yZero - diffY * (k), this.getWidth() - 3 * SPACE, yZero - diffY * (k));
			}

			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(5));
			String s = Integer.toString(j);
			stringLength = font.bytesWidth(s.getBytes(), 0, s.length());
			g2d.drawString(s, 3 * SPACE - stringLength - 2 * CONST, yZero - diffY * (k) + 5);
			k++;
		}

		for (int i = 0; i < numOfX; i++) {
			g2d.setColor(new Color(255, 128, 0));
			g2d.fillRect(3 * SPACE + i * diffX,
					(int) (yZero - diffY * (barChart.values.get(i).getY() / (double) barChart.space)), diffX,
					(int) (diffY * (barChart.values.get(i).getY() / (double) barChart.space)));

			g2d.setColor(Color.YELLOW);
			g2d.setStroke(new BasicStroke(2));
			g2d.drawLine(3 * SPACE + diffX * (i + 1), this.getHeight() - 3 * SPACE, 3 * SPACE + diffX * (i + 1),
					3 * SPACE);

			g2d.setColor(Color.BLACK);
			g2d.setStroke(new BasicStroke(5));
			String s = Integer.toString(i + 1);
			stringLength = font.bytesWidth(s.getBytes(), 0, s.length());
			g2d.drawString(s, 3 * SPACE + diffX / 2 + i * diffX - stringLength / 2, this.getHeight() - 2 * SPACE);
		}

		g2d.setColor(Color.GRAY);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawLine(3 * SPACE - CONST, this.getHeight() - 3 * SPACE, this.getWidth() - 3 * SPACE + CONST,
				this.getHeight() - 3 * SPACE);
		g2d.drawLine(this.getWidth() - 3 * SPACE, this.getHeight() - 3 * SPACE - CONST,
				this.getWidth() - 3 * SPACE + CONST, this.getHeight() - 3 * SPACE);
		g2d.drawLine(this.getWidth() - 3 * SPACE, this.getHeight() - 3 * SPACE + CONST,
				this.getWidth() - 3 * SPACE + CONST, this.getHeight() - 3 * SPACE);
		g2d.drawLine(3 * SPACE, this.getHeight() - 3 * SPACE + CONST, 3 * SPACE, 3 * SPACE - CONST);
		g2d.drawLine(3 * SPACE - CONST, 3 * SPACE, 3 * SPACE, 3 * SPACE - CONST);
		g2d.drawLine(3 * SPACE + CONST, 3 * SPACE, 3 * SPACE, 3 * SPACE - CONST);

	}

}
