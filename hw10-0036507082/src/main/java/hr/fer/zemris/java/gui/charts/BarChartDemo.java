package hr.fer.zemris.java.gui.charts;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class BarChartDemo is demo class for bar chart.
 * 
 * @author Filip
 */
public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param component
	 */
	public BarChartDemo(BarChartComponent component) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(500, 500);
		getContentPane().add(component);
	}

	/**
	 * This is main method with single parameter.
	 * 
	 * @param args given path
	 */
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("Invalid number of arguments.");
			System.exit(1);
		}

		List<String> lines = null;

		try {
			lines = Files.readAllLines(Paths.get(args[0]));
		} catch (Exception e) {
			System.out.println("Invalid path.");
			System.exit(1);
		}
		if (lines.size() < 6) {
			System.out.println("Invalid number of lines.");
			System.exit(1);
		}
		List<XYValue> values = new ArrayList<XYValue>();
		String[] parts = lines.get(2).split("\\s+");
		for (String part : parts) {
			String[] partsXY = part.split(",");
			try {
				values.add(new XYValue(Integer.parseInt(partsXY[0]), Integer.parseInt(partsXY[1])));
			} catch (Exception e) {
				System.out.println("Invalid input.");
				System.exit(1);
			}
		}

		int yMin = 0;
		int yMax = 0;
		int space = 0;
		try {
			yMin = Integer.parseInt(lines.get(3));
			yMax = Integer.parseInt(lines.get(4));
			space = Integer.parseInt(lines.get(5));
		} catch (Exception e) {
			System.out.println("Invalid input.");
			System.exit(1);
		}

		BarChart model = new BarChart(values, lines.get(0), lines.get(1), yMin, yMax, space);

		SwingUtilities.invokeLater(() -> {
			new BarChartDemo(new BarChartComponent(model)).setVisible(true);
		});
	}

}
