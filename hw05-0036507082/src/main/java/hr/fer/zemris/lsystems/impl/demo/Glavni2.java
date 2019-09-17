package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * Class Glavni2 for testing purpose.
 *
 * @author Filip
 */
public class Glavni2 {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		LSystemViewer.showLSystem(createKochCurve2(LSystemBuilderImpl::new));
	}
	
	/**
	 * Method for creating Koch curve.
	 * 
	 * @param provider given provider
	 * @return LSystem
	 */
	private static LSystem createKochCurve2(LSystemBuilderProvider provider) {
		String[] data = new String[] {
				"origin                 0.05 0.4",
				"angle                  0",
				"unitLength             0.9",
				"unitLengthDegreeScaler 1.0 / 3.0",
				"",
				"command F draw 1",
				"command + rotate 60",
				"command - rotate -60",
				"",
				"axiom F",
				"",
				"production F F+F--F+F"
		};
		return provider.createLSystemBuilder().configureFromText(data).build();
	}
	
}
