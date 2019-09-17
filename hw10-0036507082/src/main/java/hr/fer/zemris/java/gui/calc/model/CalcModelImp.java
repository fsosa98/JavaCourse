package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * Class CalcModelImp is implementation of CalcModel.
 * 
 * Users can add and remove listeners, insert digit and decimal point, swap
 * sign, clear input.
 * 
 * @author Filip
 */
public class CalcModelImp implements CalcModel {

	private boolean editable = true;
	private boolean sign = true;
	private String input = "";
	private double value;
	private Double activeOperand;
	private DoubleBinaryOperator pendingOperation;
	private boolean num = false;
	private List<CalcValueListener> listeners = new ArrayList<CalcValueListener>();

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return value;
	}

	@Override
	public void setValue(double value) {
		this.value = value;
		input = Double.toString(value);
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public void clear() {
		input = "";
		notifyListeners();
	}

	@Override
	public void clearAll() {
		input = "";
		activeOperand = null;
		pendingOperation = null;
		editable = true;
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException();
		sign = !sign;
		value = -value;
		if (sign) {
			input = input.substring(1);
		} else {
			input = "-" + input;
		}
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (!editable)
			throw new CalculatorInputException();
		if (input.contains(".") || input.equals("-"))
			throw new CalculatorInputException();
		if (input.isEmpty()) {
			if (!num) {
				throw new CalculatorInputException();
			} else {
				input += "0";
			}
		}
		input += ".";
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!editable)
			throw new CalculatorInputException();
		if (input.isEmpty() && digit == 0) {
			num = true;
			return;
		}
		String in = input + digit;
		try {
			double value = Double.parseDouble(in);
			if (value == Double.POSITIVE_INFINITY || value == Double.NEGATIVE_INFINITY || value == Double.NaN)
				throw new CalculatorInputException();
			this.value = value;
			input = in;
		} catch (Exception e) {
			throw new CalculatorInputException();
		}
		notifyListeners();
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (activeOperand == null)
			throw new IllegalStateException();
		return activeOperand;
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		this.pendingOperation = op;
	}

	@Override
	public String toString() {
		if (input.isEmpty()) {
			if (sign) {
				return "0";
			}
		}
		if (input.equals("-"))
			return "-0";
		return input;
	}

	/**
	 * This method notify all listeners.
	 */
	private void notifyListeners() {
		listeners.forEach((l) -> l.valueChanged(this));
	}

	/**
	 * Fail method.
	 * 
	 * @param s
	 */
	public void fail(String s) {
		input = s;
		editable = false;
		notifyListeners();
	}

}
