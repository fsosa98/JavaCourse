package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.editors.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.listeners.ColorChangeListener;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.models.DrawingObjectListModel;
import hr.fer.zemris.java.hw17.jvdraw.objects.Circle;
import hr.fer.zemris.java.hw17.jvdraw.objects.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.objects.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.objects.Line;
import hr.fer.zemris.java.hw17.jvdraw.providers.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.states.CircleTool;
import hr.fer.zemris.java.hw17.jvdraw.states.FilledCircleTool;
import hr.fer.zemris.java.hw17.jvdraw.states.LineTool;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.visitors.GeometricalObjectPainter;

/**
 * Class JVDraw is frame class. Implementation of paint
 * 
 * @author Filip
 */
public class JVDraw extends JFrame {

	private static final long serialVersionUID = 1L;
	DrawingModel model;
	JDrawingCanvas comp;
	JColorArea fgArea;
	JColorArea bgArea;

	private Action open;
	private Action save;
	private Action saveAs;
	private Action export;
	private Action exit;

	private Action fgColor;
	private Action bgColor;
	private Action line;
	private Action circle;
	private Action fcircle;

	private Path openedFilePath;

	String text;

	/**
	 * Constructor
	 */
	public JVDraw() {

		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exit.actionPerformed(null);
			}
		});
		setTitle("JVDraw");
		setSize(1400, 740);

		initGUI();

	}

	/**
	 * Initialization method
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		initActions();
		createMenus();
		createToolbar();

		model = new DrawingModelImpl();
		comp = new JDrawingCanvas(model);

		comp.setSelectedTool(new LineTool(model, fgArea));
		comp.paintComponents(this.getGraphics());

		cp.add(comp, BorderLayout.CENTER);

		JColorLabel label = new JColorLabel(fgArea, bgArea);

		fgArea.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				label.setText(label.getTeext());
			}
		});

		bgArea.addColorChangeListener(new ColorChangeListener() {

			@Override
			public void newColorSelected(IColorProvider source, Color oldColor, Color newColor) {
				label.setText(label.getTeext());
			}
		});

		label.setText(label.getTeext());
		cp.add(label, BorderLayout.PAGE_END);

		DrawingObjectListModel dolm = new DrawingObjectListModel(model);

		JList<GeometricalObject> list = new JList<GeometricalObject>(dolm);
		list.addKeyListener(new KeyAdapter() {
	
			@Override
			public void keyPressed(KeyEvent e) {
				GeometricalObject o = list.getSelectedValue();
				if (e.getKeyCode() == KeyEvent.VK_DELETE) {
					model.remove(o);
				}
				if (e.getKeyCode() == KeyEvent.VK_PLUS) {
					model.changeOrder(o, -1);
				} 
				if (e.getKeyCode() == KeyEvent.VK_MINUS) {
					model.changeOrder(o, +1);
				}
			}
		});
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int i = list.getSelectedIndex();
					GeometricalObjectEditor editor = dolm.getElementAt(i).createGeometricalObjectEditor();

					if (JOptionPane.showConfirmDialog(JVDraw.this, editor, "",
							JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
						try {
							editor.checkEditing();
							editor.acceptEditing();
						} catch (Exception exc) {
							exc.printStackTrace();
						}
					}
				}
			}

		});

		model.addDrawingModelListener(dolm);

		JPanel east = new JPanel(new GridLayout(1, 0));
		east.add(new JScrollPane(list));
		cp.add(east, BorderLayout.EAST);

	}

	/**
	 * Initialization actions method
	 */
	private void initActions() {

		line = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comp.setSelectedTool(new LineTool(model, fgArea));
			}
		};

		circle = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comp.setSelectedTool(new CircleTool(model, fgArea));
			}
		};

		fcircle = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				comp.setSelectedTool(new FilledCircleTool(model, fgArea, bgArea));
			}
		};

		open = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open File");
				if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION)
					return;

				model.clear();
				model.clearModifiedFlag();
				openedFilePath = jfc.getSelectedFile().toPath();
				try {
					List<String> lines = Files.readAllLines(openedFilePath);
					parse(lines);
				} catch (Exception exp) {
					if (!Files.isReadable(openedFilePath)) {
						JOptionPane.showMessageDialog(JVDraw.this, "File is not readable.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
			}

			private void parse(List<String> lines) {
				for (String line : lines) {
					String[] parts = line.trim().split("\\s+");
					try {
						if (parts[0].equals("LINE")) {
							Line newLine = new Line(new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
									new Point(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])),
									new Color(Integer.parseInt(parts[5]), Integer.parseInt(parts[6]),
											Integer.parseInt(parts[7])));
							model.add(newLine);
						} else if (parts[0].equals("CIRCLE")) {
							Circle newCircle = new Circle(
									new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
									Integer.parseInt(parts[3]), new Color(Integer.parseInt(parts[4]),
											Integer.parseInt(parts[5]), Integer.parseInt(parts[6])));
							model.add(newCircle);
						} else if (parts[0].equals("FCIRCLE")) {
							FilledCircle newFCircle = new FilledCircle(
									new Point(Integer.parseInt(parts[1]), Integer.parseInt(parts[2])),
									Integer.parseInt(parts[3]),
									new Color(Integer.parseInt(parts[4]), Integer.parseInt(parts[5]),
											Integer.parseInt(parts[6])),
									new Color(Integer.parseInt(parts[7]), Integer.parseInt(parts[8]),
											Integer.parseInt(parts[9])));
							model.add(newFCircle);
						}
					} catch (Exception ignore) {

					}
				}

			}
		};

		save = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isModified() != true) {
					return;
				}
				if (openedFilePath == null) {
					saveAs.actionPerformed(e);
					return;
				}
				try {
					JVDraw.this.saveJvd();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error, document not saved.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(JVDraw.this, "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);

			}

		};

		saveAs = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("SaveAs file");
				if (jfc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JVDraw.this, "Document not saved.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Path openedFilePath2 = jfc.getSelectedFile().toPath();
				if (Files.exists(openedFilePath2)) {
					int btn = JOptionPane.showConfirmDialog(JVDraw.this,
							"File already exists. Do you want to override it?", "Save",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (btn == JOptionPane.NO_OPTION || btn == JOptionPane.CLOSED_OPTION
							|| btn == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				try {
					openedFilePath = openedFilePath2;
					JVDraw.this.saveJvd();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error, document not saved.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(JVDraw.this, "Saved.", "Information", JOptionPane.INFORMATION_MESSAGE);
			}
		};

		exit = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.isModified() == false) {
					dispose();
					return;
				}
				int btn = JOptionPane.showConfirmDialog(JVDraw.this, "Do you want to save file?", "Save",
						JOptionPane.YES_NO_CANCEL_OPTION);
				if (btn == JOptionPane.CANCEL_OPTION) {
					return;
				}
				if (btn == JOptionPane.YES_OPTION) {
					save.actionPerformed(e);
				}
				dispose();
			}
		};

		export = new AbstractAction() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setFileFilter(new FileNameExtensionFilter("Images", "png", "gif", "jpg"));
				if (jfc.showOpenDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION)
					return;
				File file = jfc.getSelectedFile();

				String exst = "";
				if (file.toString().lastIndexOf('.') == -1) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error, document not exported.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				} else {
					exst = file.toString().substring(file.toString().lastIndexOf('.') + 1);
				}
				if (!exst.equals("jpg") && !exst.equals("png") && !exst.equals("gif")) {
					JOptionPane.showMessageDialog(JVDraw.this, "Error, document not exported.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
				for (int i = 0; i < model.getSize(); i++) {
					GeometricalObject o = model.getObject(i);
					o.accept(bbcalc);
				}
				Rectangle box = bbcalc.getBoundingBox();
				BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
				Graphics2D g = image.createGraphics();
				g.translate(-box.x, -box.y);// ... apply translation transform to g for -box.x, -box.y

				GeometricalObjectPainter painter = new GeometricalObjectPainter(g);// ... draw objects using
																					// GeometricalObjectPainter
																					// initialized with g
				for (int i = 0; i < model.getSize(); i++) {
					GeometricalObject o = model.getObject(i);
					o.accept(painter);
				}

				g.dispose();
				try {
					ImageIO.write(image, exst, file);
					JOptionPane.showMessageDialog(JVDraw.this, "Document exported.", "Info",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(JVDraw.this, "Error, document not exported.", "Error",
							JOptionPane.ERROR_MESSAGE);
				} // Tell-user-that-images-is-exported.

			}
		};

	}

	/**
	 * Helper method for saving
	 */
	private void saveJvd() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < model.getSize(); i++) {
			GeometricalObject obj = model.getObject(i);
			if (obj instanceof Line) {
				Line line = (Line) obj;
				sb.append("LINE " + line.getA().x + " " + line.getA().y + " " + line.getB().x + " " + line.getB().y
						+ " " + line.getColor().getRed() + " " + line.getColor().getGreen() + " "
						+ line.getColor().getBlue());
			} else if (obj instanceof Circle) {
				Circle circle = (Circle) obj;
				sb.append("CIRCLE " + circle.getS().x + " " + circle.getS().y + " " + circle.getR() + " "
						+ circle.getColor().getRed() + " " + circle.getColor().getGreen() + " "
						+ circle.getColor().getBlue());
			} else if (obj instanceof FilledCircle) {
				FilledCircle fCircle = (FilledCircle) obj;
				sb.append("FCIRCLE " + fCircle.getS().x + " " + fCircle.getS().y + " " + fCircle.getR() + " "
						+ fCircle.getColorLine().getRed() + " " + fCircle.getColorLine().getGreen() + " "
						+ fCircle.getColorLine().getBlue() + " " + fCircle.getColorFill().getRed() + " "
						+ fCircle.getColorFill().getGreen() + " " + fCircle.getColorFill().getBlue());
			} else {
				continue;
			}
			sb.append("\n");

		}
		try {
			Files.writeString(openedFilePath, sb.toString());
		} catch (IOException ign) {
		}
	}

	/**
	 * Initialization toolbar method
	 */
	private void createToolbar() {

		JToolBar tb = new JToolBar();

		fgArea = new JColorArea(Color.red);
		fgArea.setSize(getPreferredSize());
		tb.add(fgArea);

		bgArea = new JColorArea(Color.blue);
		bgArea.setSize(getPreferredSize());
		tb.add(bgArea);

		ButtonGroup btnGroup = new ButtonGroup();

		JToggleButton lineBtn = new JToggleButton(line);
		lineBtn.setText("Line");
		tb.add(lineBtn);
		btnGroup.add(lineBtn);
		lineBtn.setSelected(true);

		JToggleButton circleBtn = new JToggleButton(circle);
		circleBtn.setText("Circle");
		tb.add(circleBtn);
		btnGroup.add(circleBtn);

		JToggleButton fcircleBtn = new JToggleButton(fcircle);
		fcircleBtn.setText("Filled Circle");
		tb.add(fcircleBtn);
		btnGroup.add(fcircleBtn);

		getContentPane().add(tb, BorderLayout.PAGE_START);

	}

	/**
	 * Initialization menues method
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new JMenu("File");

		JMenuItem o = new JMenuItem(open);
		o.setText("Open");
		file.add(o);

		file.addSeparator();

		JMenuItem s = new JMenuItem(save);
		s.setText("Save");
		file.add(s);
		JMenuItem ss = new JMenuItem(saveAs);
		ss.setText("SaveAs");
		file.add(ss);

		file.addSeparator();

		JMenuItem e = new JMenuItem(export);
		e.setText("Export");
		file.add(e);

		mb.add(file);
		setJMenuBar(mb);
	}

	/**
	 * This is main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JVDraw().setVisible(true);
		});
	}

}
