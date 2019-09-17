package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class RCPosition defines position.
 * 
 * Users can get row and column.
 * 
 * @author Filip
 */
public class RCPosition {

	private int row;
	private int column;

	/**
	 * Constructor initializes RCPosition parameters.
	 * 
	 * @param row
	 * @param column
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row.
	 * 
	 * @return row
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column.
	 * 
	 * @return column
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}

}
