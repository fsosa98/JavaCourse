package hr.fer.zemris.java.custom.scripting.node;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.elems.*;

/**
 * Class ForLoopNode defines data structure and a set of methods for working
 * with it.
 * 
 * @author Filip
 */
public class ForLoopNode extends Node {

	private ElementVariable variable;
	private Element startExpression;
	private Element endExpression;
	private Element stepExpression;

	/**
	 * Constructor with given:
	 * 
	 * @param variable
	 * @param startExpression
	 * @param endExpression
	 * @param stepExpression
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {
		Objects.requireNonNull(variable, "Variable is null.");
		Objects.requireNonNull(startExpression, "StartExpression is null.");
		Objects.requireNonNull(endExpression, "EndExpression is null.");

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	/**
	 * Getter for variable.
	 * 
	 * @return variable
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Getter for startExpression.
	 * 
	 * @return startExpression
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Getter for endExpression.
	 * 
	 * @return endExpression
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Getter for stepExpression.
	 * 
	 * @return stepExpression
	 */
	public Element getStepExpression() {
		return stepExpression;
	}
}
