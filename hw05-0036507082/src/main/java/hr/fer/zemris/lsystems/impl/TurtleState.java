package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Objects;

import hr.fer.zemris.math.Vector2D;

/**
 * Class TurtleState defines turtle state.
 * 
 * Users can get and set position, direction, color and shiftLength of turtle.
 * 
 * @author Filip
 */
public class TurtleState {

	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double shiftLength;

	/**
	 * Constructor which initializes TurtleState with given parameters.
	 * 
	 * @param position    given position
	 * @param direction   given direction
	 * @param color       given color
	 * @param shiftLength given shiftLength
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double shiftLength) {
		Objects.requireNonNull(position, "Position is null.");
		Objects.requireNonNull(direction, "Direction is null.");
		Objects.requireNonNull(color, "Color is null.");

		this.position = position;
		this.direction = direction;
		this.color = color;
		this.shiftLength = shiftLength;
	}

	/**
	 * This method return position of turtle.
	 * 
	 * @return Vector2D position
	 */
	public Vector2D getPosition() {
		return position;
	}

	/**
	 * TurtleState setter.
	 * 
	 * @param position given position
	 */
	public void setPosition(Vector2D position) {
		this.position = position;
	}

	/**
	 * This method return direction of turtle.
	 * 
	 * @return Vector2D direction
	 */
	public Vector2D getDirection() {
		return direction;
	}

	/**
	 * TurtleState setter.
	 * 
	 * @param position given position
	 */
	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	/**
	 * This method return color of turtle.
	 * 
	 * @return Color color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * TurtleState setter.
	 * 
	 * @param Color color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * This method return shift length of turtle.
	 * 
	 * @return double shift length
	 */
	public double getShiftLength() {
		return shiftLength;
	}

	/**
	 * TurtleState setter.
	 * 
	 * @param double shift length
	 */
	public void setShiftLength(double shiftLength) {
		this.shiftLength = shiftLength;
	}

	/**
	 * This method copy TurtleState.
	 * 
	 * @return new TurtleState
	 */
	public TurtleState copy() {
		return new TurtleState(position.copy(), direction.copy(), new Color(color.getRGB()), shiftLength);
	}

}
