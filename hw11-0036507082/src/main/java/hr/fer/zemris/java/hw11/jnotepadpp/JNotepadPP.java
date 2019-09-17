package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.StyledEditorKit.BoldAction;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Class JNotepadPP defines Notepad.
 * 
 * Users can create new documents, open existing documents, save documents and
 * many other actions.
 * 
 * @author Filip
 */
public class JNotepadPP extends JFrame {

	private MultipleDocumentModel model;

	private Action createNew;
	private Action openDocument;
	private Action saveDocument;
	private Action saveAsDocument;
	private Action closeDocument;
	private Action cutAction;
	private Action copyAction;
	private Action pasteAction;
	private Action statAction;
	private Action exitAction;

	private Action hrAction;
	private Action enAction;
	private Action deAction;
	private ILocalizationProvider flp;

	private Action upperAction;
	private Action lowerAction;
	private Action invertAction;

	private Action ascAction;
	private Action descAction;
	private Action unqAction;

	private JLabel lenLabel = new JLabel();
	private JLabel infLabel = new JLabel();
	private JLabel timeLabel = new JLabel();
	private Timer timer;

	/**
	 * Default constructor.
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction.actionPerformed(null);
			}
		});

		setTitle("JNotepad++");
		setSize(1400, 740);
		
		flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);
		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				statusInfo();
			}
		});
		initGUI();

		
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// 2015/05/15 15:35:24
				Date date = new Date();
				timeLabel.setText(format.format(date));
			}
		});
		timer.setInitialDelay(0);
		timer.start();

		model.addMultipleDocumentListener(new MultipleDocumentListener() {
			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				currentModel.getTextComponent().addCaretListener(e -> statusInfo());
				statusInfo();
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}
		});
		setLocationRelativeTo(null);
	}

	/**
	 * Initialization method.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		DefaultMultipleDocumentModel dmdm = new DefaultMultipleDocumentModel(this);
		cp.add(dmdm);
		model = dmdm;
		initActions();
		configureActions();
		createMenus();
		createToolbar();
	}

	/**
	 * Helper method for configuring actions.
	 */
	private void configureActions() {
		createNew.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		createNew.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);

		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);

		saveAsDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control J"));
		saveAsDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_J);

		closeDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		closeDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_Q);

		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);

		cutAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		cutAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);

		copyAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		copyAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);

		pasteAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		pasteAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);

		statAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control T"));
		statAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);

		hrAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control H"));
		hrAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_H);

		enAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control M"));
		enAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_M);

		deAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control N"));
		deAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);

		upperAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control U"));
		upperAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);

		lowerAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control L"));
		lowerAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);

		invertAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control I"));
		invertAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);

		ascAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
		ascAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);

		descAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control D"));
		descAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);

		unqAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control R"));
		unqAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_R);

	}

	/**
	 * Helper method for creating menus.
	 */
	private void createMenus() {
		JMenuBar mb = new JMenuBar();

		JMenu file = new LJMenu("file", flp);

		file.add(new JMenuItem(createNew));
		file.add(new JMenuItem(openDocument));
		file.addSeparator();
		file.add(new JMenuItem(saveDocument));
		file.add(new JMenuItem(saveAsDocument));
		file.addSeparator();
		file.add(new JMenuItem(closeDocument));
		file.add(new JMenuItem(exitAction));

		JMenu edit = new LJMenu("edit", flp);

		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));

		JMenu tools = new LJMenu("tools", flp);
		JMenu chCase = new LJMenu("chCase", flp);
		JMenu sort = new LJMenu("sort", flp);
		tools.add(chCase);
		chCase.add(new JMenuItem(upperAction));
		chCase.add(new JMenuItem(lowerAction));
		chCase.add(new JMenuItem(invertAction));
		tools.add(sort);
		sort.add(new JMenuItem(ascAction));
		sort.add(new JMenuItem(descAction));
		tools.addSeparator();
		tools.add(new JMenuItem(unqAction));
		tools.add(new JMenuItem(statAction));

		JMenu lang = new LJMenu("language", flp);
		lang.add(new JMenuItem(hrAction));
		lang.add(new JMenuItem(enAction));
		lang.add(new JMenuItem(deAction));

		mb.add(file);
		mb.add(edit);
		mb.add(tools);
		mb.add(lang);
		setJMenuBar(mb);
	}

	/**
	 * Helper method for creting toolbar.
	 */
	private void createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		JButton newBtn = new JButton(createNew);
		newBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/new.png"));
		tb.add(newBtn);
		JButton oepnBtn = new JButton(openDocument);
		oepnBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/open.png"));
		tb.add(oepnBtn);
		JButton saveBtn = new JButton(saveDocument);
		saveBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/save.png"));
		tb.add(saveBtn);
		JButton saveAsBtn = new JButton(saveAsDocument);
		saveAsBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/saveas.png"));
		tb.add(saveAsBtn);
		JButton closeBtn = new JButton(closeDocument);
		closeBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/close.png"));
		tb.add(closeBtn);
		JButton exitBtn = new JButton(exitAction);
		exitBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/exit.png"));
		tb.add(exitBtn);

		tb.addSeparator();

		JButton cutBtn = new JButton(cutAction);
		cutBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/cut.png"));
		tb.add(cutBtn);
		JButton copyBtn = new JButton(copyAction);
		copyBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/copy.png"));
		tb.add(copyBtn);
		JButton pasteBtn = new JButton(pasteAction);
		pasteBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/paste.png"));
		tb.add(pasteBtn);

		tb.addSeparator();

		JButton statBtn = new JButton(statAction);
		statBtn.setIcon(((DefaultMultipleDocumentModel) model).getImageIcon("icons/stat.png"));
		tb.add(statBtn);

		getContentPane().add(tb, BorderLayout.PAGE_START);

		JPanel tbStatus = new JPanel();
		tbStatus.setLayout(new BorderLayout());
		tbStatus.add(lenLabel, BorderLayout.LINE_START);
		tbStatus.add(infLabel, BorderLayout.CENTER);
		tbStatus.add(timeLabel, BorderLayout.LINE_END);

		getContentPane().add(tbStatus, BorderLayout.PAGE_END);
	}

	/**
	 * Helper method for setting state.
	 * 
	 * @param state
	 */
	private void setState(boolean state) {
		saveDocument.setEnabled(state);
		saveAsDocument.setEnabled(state);
		closeDocument.setEnabled(state);
		pasteAction.setEnabled(state);
		statAction.setEnabled(state);
		upperAction.setEnabled(state);
		lowerAction.setEnabled(state);
		invertAction.setEnabled(state);
		ascAction.setEnabled(state);
		descAction.setEnabled(state);
		unqAction.setEnabled(state);

	}

	/**
	 * Helper method for initialization actions.
	 */
	private void initActions() {

		createNew = new LocalizableAction("new", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				setState(true);
				model.createNewDocument();
				statusInfo();
			}
		};

		openDocument = new LocalizableAction("open", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				setState(true);
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Open File");
				if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION)
					return;

				Path openedFilePath = jfc.getSelectedFile().toPath();
				try {
					model.loadDocument(openedFilePath);
				} catch (Exception exp) {
					if (!Files.isReadable(openedFilePath)) {
						JOptionPane.showMessageDialog(JNotepadPP.this, "File is not readable.", "Error",
								JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				statusInfo();
			}
		};

		saveDocument = new LocalizableAction("save", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Path openedFilePath = model.getCurrentDocument().getFilePath();
				if (openedFilePath == null) {
					saveAsDocument.actionPerformed(e);
					return;
				}
				try {
					model.saveDocument(model.getCurrentDocument(), openedFilePath);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Error, document not saved.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(JNotepadPP.this, "Saved.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		saveDocument.setEnabled(false);

		saveAsDocument = new LocalizableAction("saveAs", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("SaveAs file");
				if (jfc.showSaveDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Document not saved.", "Information",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				Path openedFilePath = jfc.getSelectedFile().toPath();
				if (Files.exists(openedFilePath)) {
					int btn = JOptionPane.showConfirmDialog(JNotepadPP.this,
							"File already exists. Do you want to override it?", "Save",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (btn == JOptionPane.NO_OPTION || btn == JOptionPane.CLOSED_OPTION
							|| btn == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				try {
					model.saveDocument(model.getCurrentDocument(), openedFilePath);
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(JNotepadPP.this, "Error, document not saved.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				JOptionPane.showMessageDialog(JNotepadPP.this, "Saved.", "Information",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		saveAsDocument.setEnabled(false);

		closeDocument = new LocalizableAction("close", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getCurrentDocument().isModified()) {
					int btn = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save file?", "Save",
							JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
					if (btn == JOptionPane.YES_OPTION) {
						saveDocument.actionPerformed(e);
					} else if (btn == JOptionPane.CLOSED_OPTION || btn == JOptionPane.CANCEL_OPTION) {
						return;
					}
				}
				model.closeDocument(model.getCurrentDocument());
				if (model.getNumberOfDocuments() == 0) {
					setState(false);
				}
				statusInfo();
			}
		};
		closeDocument.setEnabled(false);

		cutAction = new LocalizableAction("cut", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Action action = new DefaultEditorKit.CutAction();
				action.actionPerformed(e);
			}
		};
		cutAction.setEnabled(false);

		copyAction = new LocalizableAction("copy", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Action action = new DefaultEditorKit.CopyAction();
				action.actionPerformed(e);
			}
		};
		copyAction.setEnabled(false);

		pasteAction = new LocalizableAction("paste", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Action action = new DefaultEditorKit.PasteAction();
				action.actionPerformed(e);
			}
		};
		pasteAction.setEnabled(false);

		statAction = new LocalizableAction("stat", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = model.getCurrentDocument().getTextComponent().getText();
				String output = "Your document has " + text.length() + " characters, " + text.replace(" ", "").length()
						+ " non-blank characters and " + text.split("\r\n|\r|\n").length + " lines.";
				JOptionPane.showMessageDialog(JNotepadPP.this, output, "Statistical info",
						JOptionPane.INFORMATION_MESSAGE);
			}
		};
		statAction.setEnabled(false);

		exitAction = new LocalizableAction("exit", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (model.getNumberOfDocuments() != 0) {
					int n = model.getNumberOfDocuments();
					for (int i = 0; i < n; i++) {
						if (model.getCurrentDocument().isModified()) {
							int btn = JOptionPane.showConfirmDialog(JNotepadPP.this, "Do you want to save file?",
									"Save", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
							if (btn == JOptionPane.YES_OPTION) {
								saveDocument.actionPerformed(e);
							} else if (btn == JOptionPane.CLOSED_OPTION || btn == JOptionPane.CANCEL_OPTION) {
								return;
							}
						}
						model.closeDocument(model.getCurrentDocument());
						statusInfo();
					}
				}
				timer.stop();
				dispose();
			}
		};

		hrAction = new LocalizableAction("hr", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("hr");
			}
		};

		enAction = new LocalizableAction("en", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("en");
			}
		};

		deAction = new LocalizableAction("de", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				LocalizationProvider.getInstance().setLanguage("de");
			}
		};

		upperAction = new LocalizableAction("up", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int start = Math.min(caret.getDot(), caret.getMark());
				int len = Math.abs(caret.getDot() - caret.getMark());
				if (len == 0)
					return;

				String text;
				try {
					text = doc.getText(start, len);
					text = text.toUpperCase();
					doc.remove(start, len);
					doc.insertString(start, text, null);
				} catch (BadLocationException e1) {
				}
			}
		};
		upperAction.setEnabled(false);

		lowerAction = new LocalizableAction("low", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int start = Math.min(caret.getDot(), caret.getMark());
				int len = Math.abs(caret.getDot() - caret.getMark());
				if (len == 0)
					return;

				String text;
				try {
					text = doc.getText(start, len);
					text = text.toLowerCase();
					doc.remove(start, len);
					doc.insertString(start, text, null);
				} catch (BadLocationException e1) {
				}
			}
		};
		lowerAction.setEnabled(false);

		invertAction = new LocalizableAction("inv", flp) {

			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int start = Math.min(caret.getDot(), caret.getMark());
				int len = Math.abs(caret.getDot() - caret.getMark());
				if (len == 0)
					return;

				String text;
				try {
					text = doc.getText(start, len);
					text = toggleText(text);
					doc.remove(start, len);
					doc.insertString(start, text, null);
				} catch (BadLocationException e1) {
				}
			}

			private String toggleText(String text) {
				char[] chars = text.toCharArray();
				for (int i = 0; i < chars.length; i++) {
					char c = chars[i];
					if (Character.isUpperCase(c)) {
						chars[i] = Character.toLowerCase(c);
					} else if (Character.isLowerCase(c)) {
						chars[i] = Character.toUpperCase(c);
					}
				}
				return new String(chars);
			}
		};
		invertAction.setEnabled(false);

		ascAction = new LocalizableAction("asc", flp) {
			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int car = caret.getDot();
				int mark = caret.getMark();
				String all = null;
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (car == 0)
						break;
					if (all.charAt(0) == '\n') {
						car++;
						break;
					} else {
						car--;
					}
				}
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (mark == doc.getLength() - 1)
						break;
					if (all.charAt(all.length() - 1) == '\n') {
						mark--;
						break;
					} else {
						mark++;
					}
				}
				try {
					String text = all;
					doc.remove(car, mark - car);
					text = sortSelected(true, text);
					doc.insertString(car, text, null);
				} catch (BadLocationException e1) {
				}
			}
		};
		ascAction.setEnabled(false);

		descAction = new LocalizableAction("desc", flp) {
			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int car = caret.getDot();
				int mark = caret.getMark();
				String all = null;
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (car == 0)
						break;
					if (all.charAt(0) == '\n') {
						car++;
						break;
					} else {
						car--;
					}
				}
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (mark == doc.getLength() - 1)
						break;
					if (all.charAt(all.length() - 1) == '\n') {
						mark--;
						break;
					} else {
						mark++;
					}
				}
				try {
					String text = all;
					doc.remove(car, mark - car);
					text = sortSelected(false, text);
					doc.insertString(car, text, null);
				} catch (BadLocationException e1) {
				}
			}
		};
		descAction.setEnabled(false);

		unqAction = new LocalizableAction("unq", flp) {
			@Override
			public void actionPerformed(ActionEvent e) {
				Document doc = model.getCurrentDocument().getTextComponent().getDocument();
				Caret caret = model.getCurrentDocument().getTextComponent().getCaret();
				int car = caret.getDot();
				int mark = caret.getMark();
				String all = null;
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (car == 0)
						break;
					if (all.charAt(0) == '\n') {
						car++;
						break;
					} else {
						car--;
					}
				}
				while (true) {
					try {
						all = doc.getText(car, mark - car);
					} catch (BadLocationException e1) {
					}
					if (mark == doc.getLength() - 1)
						break;
					if (all.charAt(all.length() - 1) == '\n') {
						mark--;
						break;
					} else {
						mark++;
					}
				}
				try {
					String text = all;
					doc.remove(car, mark - car);
					text = unique(text);
					doc.insertString(car, text, null);
				} catch (BadLocationException e1) {
				}
			}
		};
		unqAction.setEnabled(false);

	}

	/**
	 * Helper method for unique line text.
	 * 
	 * @param text given text
	 * @return
	 */
	private String unique(String text) {
		Set<String> lines = new LinkedHashSet<String>(Arrays.asList(text.split("\\r?\\n")));
		StringBuilder sb = new StringBuilder();
		lines.forEach((line) -> sb.append(line).append("\n"));
		String s = sb.substring(0, sb.length() - 1);
		return new String(s);
	}

	/**
	 * Helper method for sorting text
	 * 
	 * @param asc
	 * @param text
	 * @return
	 */
	private String sortSelected(boolean asc, String text) {
		Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
		Collator collator = Collator.getInstance(locale);
		List<String> lines = new ArrayList<String>(Arrays.asList(text.split("\\r?\\n")));
		if (asc) {
			Collections.sort(lines, collator);
		} else {
			Collections.sort(lines, collator.reversed());
		}
		StringBuilder sb = new StringBuilder();
		lines.forEach((line) -> sb.append(line).append("\n"));
		return new String(sb.substring(0, sb.length() - 1));
	}

	/**
	 * Helper method for info.
	 */
	private void statusInfo() {
		if (model.getCurrentDocument() == null || model.getNumberOfDocuments() == 0) {
			lenLabel.setText("");
			infLabel.setText("");
		} else {
			JTextArea textArea = model.getCurrentDocument().getTextComponent();
			lenLabel.setText(flp.getString("len") + ":" + textArea.getText().length());
			try {
				int sel = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
				infLabel.setText("                            " + flp.getString("ln") + ":"
						+ (textArea.getLineOfOffset(textArea.getCaretPosition()) + 1) + "  " + flp.getString("col")
						+ ":"
						+ (textArea.getCaretPosition()
								- textArea.getLineStartOffset(textArea.getLineOfOffset(textArea.getCaretPosition())))
						+ "  " + flp.getString("sel") + ":" + sel);
				if (sel == 0) {
					cutAction.setEnabled(false);
					copyAction.setEnabled(false);
					upperAction.setEnabled(false);
					lowerAction.setEnabled(false);
					invertAction.setEnabled(false);
					ascAction.setEnabled(false);
					descAction.setEnabled(false);
					unqAction.setEnabled(false);
				} else {
					cutAction.setEnabled(true);
					copyAction.setEnabled(true);
					upperAction.setEnabled(true);
					lowerAction.setEnabled(true);
					invertAction.setEnabled(true);
					ascAction.setEnabled(true);
					descAction.setEnabled(true);
					unqAction.setEnabled(true);
				}
			} catch (BadLocationException ig) {
			}
		}
	}

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}

}
