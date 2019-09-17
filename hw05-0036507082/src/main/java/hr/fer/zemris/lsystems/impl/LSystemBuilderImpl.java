package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Class LSystemBuilderImpl implements LSystemBuilder. It is used for building
 * LSystem from text or setters.
 * 
 * @author Filip
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary<Character, String> productions;
	private Dictionary<Character, Command> actions;
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;

	/**
	 * Default constructor.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<>();
		actions = new Dictionary<>();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1.0;
		origin = new Vector2D(0, 0);
		angle = 0.0;
		axiom = "";
	}

	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		origin = new Vector2D(x, y);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = angle;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(symbol, production);
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		double d;
		String[] parts = action.split("\\s+");

		if (actions.get(symbol) != null) {
			throw new LSystemException("Symbol is used.");
		}
		if (parts.length != 2 && parts.length != 1) {
			throw new LSystemException("Wrong registerCommand.");
		}
		if (parts.length == 3) {
			if (!parts[0].isBlank()) {
				throw new LSystemException("Wrong registerCommand.");
			}
		}

		switch (parts[0]) {
		case "draw":
			d = stringToDouble(parts[1]);
			DrawCommand dc = new DrawCommand(d);
			actions.put(symbol, dc);
			break;
		case "skip":
			d = stringToDouble(parts[1]);
			SkipCommand sc = new SkipCommand(d);
			actions.put(symbol, sc);
			break;
		case "scale":
			d = stringToDouble(parts[1]);
			ScaleCommand scom = new ScaleCommand(d);
			actions.put(symbol, scom);
			break;
		case "rotate":
			d = stringToDouble(parts[1]);
			RotateCommand rc = new RotateCommand(d * Math.PI / 180);
			actions.put(symbol, rc);
			break;
		case "push":
			PushCommand pc = new PushCommand();
			actions.put(symbol, pc);
			break;
		case "pop":
			PopCommand pcom = new PopCommand();
			actions.put(symbol, pcom);
			break;
		case "color":
			ColorCommand cc = new ColorCommand(Color.decode("#" + parts[1]));
			actions.put(symbol, cc);
			break;
		default:
			throw new LSystemException("Wrong action.");
		}
		return this;
	}

	/**
	 * Helper method stringToDouble.
	 * 
	 * @param s given string
	 * @return double from string or throw exception
	 */
	private double stringToDouble(String s) {
		try {
			Double d = Double.parseDouble(s);
			return d;
		} catch (NumberFormatException exc) {
			throw new LSystemException("Unable to parse.");
		}
	}

	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		for (String line : lines) {
			int i = 0;
			String[] parts = line.split("\\s+");

			if (parts.length == 0 || line.isEmpty()) {
				continue;
			}
			if (parts[i].isBlank()) {
				i++;
			}

			switch (parts[i]) {
			case "origin":
				if (parts.length - i == 3) {
					origin = new Vector2D(stringToDouble(parts[++i]), stringToDouble(parts[++i]));
				} else {
					throw new LSystemException("Unable to parse.");
				}
				break;
			case "angle":
				if (parts.length - i == 2) {
					angle = stringToDouble(parts[++i]);
				} else {
					throw new LSystemException("Unable to parse.");
				}
				break;
			case "unitLength":
				if (parts.length - i == 2) {
					unitLength = stringToDouble(parts[++i]);
				} else {
					throw new LSystemException("Unable to parse.");
				}
				break;
			case "unitLengthDegreeScaler":
				degreeScalerHelper(parts, i);
				break;
			case "command":
				if (parts[i + 1].length() != 1) {
					throw new LSystemException("Unable to parse.");
				}
				if (parts.length - i == 3) {
					this.registerCommand(parts[++i].charAt(0), parts[++i]);
					break;
				}
				if (parts.length - i == 4) {
					char symbol = parts[++i].charAt(0);
					String action = parts[++i] + " " + parts[++i];
					this.registerCommand(symbol, action);
					break;
				}
				throw new LSystemException("Unable to parse.");
			case "axiom":
				if (parts.length - i == 2) {
					axiom = parts[++i];
				} else {
					throw new LSystemException("Unable to parse.");
				}
				break;
			case "production":
				if (parts.length - i != 3 || parts[i + 1].length() != 1) {
					throw new LSystemException("Unable to parse.");
				}
				this.registerProduction(parts[++i].charAt(0), parts[++i]);
				break;
			default:
				throw new LSystemException("Wrong action.");
			}

		}
		return this;
	}

	/**
	 * Helper method for unitLengthDegreeScaler.
	 * 
	 * @param parts given string parts
	 * @param i     given i
	 */
	private void degreeScalerHelper(String[] parts, int i) {
		if (parts.length < 2 || parts.length > 5) {
			throw new LSystemException("Unable to parse.");
		}
		StringBuilder sb = new StringBuilder();
		i++;
		for (; i < parts.length; i++) {
			sb.append(parts[i]);
		}
		String ss = new String(sb);
		String[] parts2 = ss.split("/");
		if (parts2.length == 1) {
			unitLengthDegreeScaler = stringToDouble(parts2[0]);
		} else if (parts2.length == 2) {
			double a = stringToDouble(parts2[0]);
			double b = stringToDouble(parts2[1]);
			unitLengthDegreeScaler = a / b;
		} else
			throw new LSystemException("Unable to parse.");
	}

	@Override
	public LSystem build() {
		return new LSystemClass(this);
	}

	/**
	 * Class LSystemClass is implementation of LSystem.
	 * 
	 * @author Filip
	 */
	private static class LSystemClass implements LSystem {

		LSystemBuilderImpl builder;

		/**
		 * Constructor for LSystemClass.
		 * 
		 * @param builder
		 */
		private LSystemClass(LSystemBuilderImpl builder) {
			this.builder = builder;
		}

		@Override
		public String generate(int level) {
			StringBuilder sb = new StringBuilder();
			sb.append(builder.axiom);
			while (level > 0) {
				for (int i = 0; i < sb.length(); i++) {
					char c = sb.charAt(i);
					String value = builder.productions.get(c);
					if (value != null) {
						sb.replace(i, i + 1, value);
						i += value.length() - 1;
					}
				}
				level--;
			}
			return new String(sb);
		}

		@Override
		public void draw(int level, Painter painter) {
			Context context = new Context();
			TurtleState turtleState = new TurtleState(builder.origin, new Vector2D(1, 0).rotated(builder.angle * Math.PI/180),
					Color.BLACK, builder.unitLength * Math.pow(builder.unitLengthDegreeScaler, level));
			context.pushState(turtleState);
			String s = generate(level);
			for (int i = 0; i < s.length(); i++) {
				Command command = builder.actions.get(s.charAt(i));
				if (command == null)
					continue;
				command.execute(context, painter);
			}
		}
	}

}
