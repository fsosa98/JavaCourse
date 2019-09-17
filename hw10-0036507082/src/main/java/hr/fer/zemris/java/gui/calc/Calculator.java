package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelImp;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Class Calculator defines calculator.
 * 
 * Users can run basic mathematical operations (add, sub, multiply, division,
 * trigonometric function, logarithmic function).
 * 
 * @author Filip
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;
	private CalcModel calcModel = new CalcModelImp();
	private boolean clear = false;
	private Stack<Double> stack = new Stack<>();

	/**
	 * Default constructor.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Java Calculator v1.0");
		setSize(500, 500);
		setLocation(20, 20);
		initGUI();
	}

	/**
	 * Helper method which initialize all graphical components.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));

		// display
		JLabel display = display("0");
		cp.add(display, new RCPosition(1, 1));

		// equal
		JButton equal = new JButton("=");
		cp.add(equal, new RCPosition(1, 6));
		equal.addActionListener((m) -> {
			if (calcModel.isActiveOperandSet() && calcModel.getPendingBinaryOperation() != null) {
				calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
						calcModel.getValue()));
				calcModel.setPendingBinaryOperation(null);
			}
		});

		// clr
		JButton clr = new JButton("clr");
		cp.add(clr, new RCPosition(1, 7));
		clr.addActionListener((m) -> {
			calcModel.clear();
		});

		// div
		JButton div = new JButton("/");
		cp.add(div, new RCPosition(2, 6));
		div.addActionListener((m) -> {
			binaryOp((x, y) -> x / y);
		});

		// mul
		JButton mul = new JButton("*");
		cp.add(mul, new RCPosition(3, 6));
		mul.addActionListener((m) -> {
			binaryOp((x, y) -> x * y);
		});

		// sub
		JButton sub = new JButton("-");
		cp.add(sub, new RCPosition(4, 6));
		sub.addActionListener((m) -> {
			binaryOp((x, y) -> x - y);
		});

		// add
		JButton add = new JButton("+");
		cp.add(add, new RCPosition(5, 6));
		add.addActionListener((m) -> {
			binaryOp((x, y) -> x + y);
		});

		// reset
		JButton res = new JButton("res");
		cp.add(res, new RCPosition(2, 7));
		res.addActionListener((m) -> {
			calcModel.clearAll();
		});

		// push
		JButton push = new JButton("push");
		cp.add(push, new RCPosition(3, 7));
		push.addActionListener((m) -> {
			stack.push(calcModel.getValue());
		});

		// pop
		JButton pop = new JButton("pop");
		cp.add(pop, new RCPosition(4, 7));
		pop.addActionListener((m) -> {
			if (stack.isEmpty()) {
				((CalcModelImp) calcModel).fail("ERROR");
			} else {
				calcModel.setValue(stack.pop());
			}
		});

		JButton sin = new JButton("sin");
		JButton cos = new JButton("cos");
		JButton tan = new JButton("tan");
		JButton ctg = new JButton("ctg");
		JButton log = new JButton("log");
		JButton ln = new JButton("ln");
		JButton pow = new JButton("x^n");

		// inv
		JCheckBox inv = new JCheckBox("Inv");
		cp.add(inv, new RCPosition(5, 7));
		inv.addActionListener((m) -> {
			if (inv.isSelected()) {
				sin.setText("arcsin");
				cos.setText("arccos");
				tan.setText("arctan");
				ctg.setText("arcctg");
				log.setText("10^x");
				ln.setText("e^x");
				pow.setText("x^(1/n)");
			} else {
				sin.setText("sin");
				cos.setText("cos");
				tan.setText("tan");
				ctg.setText("ctg");
				log.setText("log");
				ln.setText("ln");
				pow.setText("x^n");
			}
		});

		// dot
		JButton dot = new JButton(".");
		cp.add(dot, new RCPosition(5, 5));
		dot.addActionListener((m) -> calcModel.insertDecimalPoint());

		// sign
		JButton sign = new JButton("+/-");
		cp.add(sign, new RCPosition(5, 4));
		sign.addActionListener((m) -> calcModel.swapSign());

		// 1/x
		JButton onex = new JButton("1/x");
		cp.add(onex, new RCPosition(2, 1));
		onex.addActionListener((m) -> calcModel.setValue(1. / calcModel.getValue()));

		// sin

		cp.add(sin, new RCPosition(2, 2));
		sin.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(Math.asin(Double.parseDouble(display.getText())));
			} else {
				calcModel.setValue(Math.sin(Double.parseDouble(display.getText())));
			}
		});

		// cos
		cp.add(cos, new RCPosition(3, 2));
		cos.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(Math.acos(Double.parseDouble(display.getText())));
			} else {
				calcModel.setValue(Math.cos(Double.parseDouble(display.getText())));
			}
		});

		// tan
		cp.add(tan, new RCPosition(4, 2));
		tan.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(Math.atan(Double.parseDouble(display.getText())));
			} else {
				calcModel.setValue(Math.tan(Double.parseDouble(display.getText())));
			}
		});

		// ctg
		cp.add(ctg, new RCPosition(5, 2));
		ctg.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(1.0 / Math.atan(Double.parseDouble(display.getText())));
			} else {
				calcModel.setValue(1.0 / Math.tan(Double.parseDouble(display.getText())));
			}
		});

		// log
		cp.add(log, new RCPosition(3, 1));
		log.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(Math.pow(10, calcModel.getValue()));
			} else {
				calcModel.setValue(Math.log10(calcModel.getValue()));
			}
		});

		// ln
		cp.add(ln, new RCPosition(4, 1));
		ln.addActionListener((m) -> {
			if (inv.isSelected()) {
				calcModel.setValue(Math.pow(Math.E, calcModel.getValue()));
			} else {
				calcModel.setValue(Math.log(calcModel.getValue()));
			}
		});

		// pow
		cp.add(pow, new RCPosition(5, 1));
		pow.addActionListener((m) -> {
			if (inv.isSelected()) {
				binaryOp((x, y) -> Math.pow(x, 1. / y));
			} else {
				binaryOp(Math::pow);
			}
		});

		// numbers
		JButton[] number = new JButton[10];
		number[0] = new JButton("0");
		number[0].setFont(number[0].getFont().deriveFont(30f));
		number[0].addActionListener((m) -> {
			if (clear) {
				calcModel.clear();
				clear = false;
			}
			calcModel.insertDigit(0);
		});
		cp.add(number[0], new RCPosition(5, 3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				number[i * 3 + j] = new JButton(Integer.toString(i * 3 + j + 1));
				number[i * 3 + j].setFont(number[i * 3 + j].getFont().deriveFont(30f));
				int k = i * 3 + j;
				number[k].addActionListener((m) -> {
					if (clear) {
						calcModel.clear();
						clear = false;
					}
					calcModel.insertDigit(k + 1);
				});
				cp.add(number[i * 3 + j], new RCPosition(4 - i, 3 + j));
			}
		}
	}

	/**
	 * Helper method for binary operations.
	 * 
	 * @param op
	 */
	private void binaryOp(DoubleBinaryOperator op) {
		if (clear)
			return;
		try {
			calcModel.setValue(calcModel.getPendingBinaryOperation().applyAsDouble(calcModel.getActiveOperand(),
					calcModel.getValue()));
		} catch (Exception e) {
		}
		calcModel.setActiveOperand(calcModel.getValue());
		calcModel.setPendingBinaryOperation(op);
		clear = true;
	}

	/**
	 * Helper method for display.
	 * 
	 * @param text given text
	 * @return
	 */
	private JLabel display(String text) {
		JLabel l = new JLabel(text);
		l.setHorizontalAlignment(SwingConstants.RIGHT);
		l.setBackground(Color.YELLOW);
		l.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		l.setFont(l.getFont().deriveFont(30f));
		l.setOpaque(true);
		calcModel.addCalcValueListener((m) -> l.setText(m.toString()));
		return l;
	}

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Calculator().setVisible(true);
		});
	}
}
