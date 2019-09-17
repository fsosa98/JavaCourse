package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.listeners.DrawingModelListener;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;

import hr.fer.zemris.java.hw17.jvdraw.states.Tool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Class JColorLabel is component class and implements DrawingModelListener.
 * 
 * @author Filip
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	private static final long serialVersionUID = 1L;

	private Tool selectedTool;
	private Supplier<Tool> supplier = new Supplier<Tool>() {
		public Tool get() {
			return selectedTool;
		};
	};
	private DrawingModel model;

	/**
	 * Constructor
	 * 
	 * @param model
	 */
	public JDrawingCanvas(DrawingModel model) {
		this.model = model;
		model.addDrawingModelListener(this);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				supplier.get().mouseClicked(e);
				repaint();
			}

		});

		addMouseMotionListener(new MouseAdapter() {

			@Override
			public void mouseMoved(MouseEvent e) {
				supplier.get().mouseMoved(e);
				repaint();
			}

		});
	}

	/**
	 * Setter for tool
	 * 
	 * @param selectedTool
	 */
	public void setSelectedTool(Tool selectedTool) {
		this.selectedTool = selectedTool;
	}

	@Override
	protected void paintComponent(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(new GeometricalObjectPainter(g2d));
		}

		if (supplier.get() != null) {
			supplier.get().paint(g2d);
		}
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
