package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.Objects;
import java.util.function.Function;

/**
 * Class CalcLayout is layout class.
 * 
 * Users can add and remove layout components, get minimum, maximum or preferred
 * layout size.
 * 
 * @author Filip
 */
public class CalcLayout implements LayoutManager2 {

	private static final int rows = 5;
	private static final int columns = 7;
	private int space;
	private Component[][] components = new Component[rows][columns];

	/**
	 * Default constructor.
	 */
	public CalcLayout() {
		this(0);
	}

	/**
	 * Constructor initializes CalcLayout with given space.
	 * 
	 * @param space given space
	 */
	public CalcLayout(int space) {
		if (space < 0) {
			throw new IllegalArgumentException("Space must be greater or equal 0.");
		}
		this.space = space;
	}

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (components[i][j].equals(comp)) {
					components[i][j] = null;
					return;
				}
			}
		}
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, (c) -> c.getPreferredSize());
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, (c) -> c.getMinimumSize());
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int maxWidth = parent.getWidth() - (insets.left + insets.right);
		int maxHeight = parent.getHeight() - (insets.top + insets.bottom);
		int cellWidth = (maxWidth - (columns - 1) * space) / columns;
		int cellHeight = (maxHeight - (rows - 1) * space) / rows;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Component c = components[i][j];
				if (c == null)
					continue;
				int x = cellWidth * j + j * space+insets.left;
				int y = cellHeight * i + i * space+insets.top;
				if (i == 0 && j == 0) {
					components[i][j].setBounds(x, y, cellWidth * 5 + 4 * space, cellHeight);
				} else {
					components[i][j].setBounds(x, y, cellWidth, cellHeight);
				}
			}
		}
	}

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Component is null.");
		Objects.requireNonNull(constraints, "Constraints is null.");
		RCPosition pos;

		if (constraints instanceof RCPosition) {
			pos = (RCPosition) constraints;
		} else if (constraints instanceof String) {
			String[] parts = ((String) constraints).split(",");
			try {
				int x = Integer.parseInt(parts[0]);
				int y = Integer.parseInt(parts[1]);
				pos = new RCPosition(x, y);
			} catch (Exception e) {
				throw new CalcLayoutException();
			}
		} else {
			throw new CalcLayoutException();
		}

		checkPosition(pos.getRow(), pos.getColumn());
		if (components[pos.getRow() - 1][pos.getColumn() - 1] != null) {
			throw new CalcLayoutException();
		}
		components[pos.getRow() - 1][pos.getColumn() - 1] = comp;
	}

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, (c) -> c.getMaximumSize());
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Helper method for checking position with given parameters.
	 * 
	 * @param r given rows
	 * @param s given columns
	 */
	private void checkPosition(int r, int s) {
		if ((r < 1 || r > 5) || (s < 1 || s > 7)) {
			throw new CalcLayoutException();
		}
		if (r == 1 && (s > 1 && s < 6)) {
			throw new CalcLayoutException();
		}

	}

	/**
	 * This method calculates layout size with given container and function.
	 * 
	 * @param parent   given container
	 * @param function given function
	 * @return Dimension
	 */
	private Dimension getLayoutSize(Container parent, Function<Component, Dimension> function) {
		int minWidth = 0;
		int minHeight = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Component c = components[i][j];
				if (c == null || function.apply(c) == null)
					continue;
				int width = function.apply(c).width;
				int height = function.apply(c).height;

				if (i == 0 && j == 0) {
					width = width - 4 * space;
					width = width / 5;
				}

				minWidth = Math.max(minWidth, width);
				minHeight = Math.max(minHeight, height);

			}
		}
		Insets insets = parent.getInsets();
		minWidth = insets.left + insets.right + (columns - 1) * space + minWidth * columns;
		minHeight = insets.top + insets.bottom + (rows - 1) * space + minHeight * rows;

		return new Dimension(minWidth, minHeight);
	}

}
