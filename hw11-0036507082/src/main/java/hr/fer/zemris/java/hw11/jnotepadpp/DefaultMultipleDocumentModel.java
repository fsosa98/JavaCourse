package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Class DefaultMultipleDocumentModel is default multiple document model.
 * 
 * This class is implementation of MultipleDocumentModel.
 * 
 * @author Filip
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	private List<SingleDocumentModel> documentsList;
	private SingleDocumentModel currentDocument;
	private SingleDocumentModel previousModel;
	private List<MultipleDocumentListener> listeners;

	/**
	 * Constructor with given frame.
	 * 
	 * @param frame given frame
	 */
	public DefaultMultipleDocumentModel(JFrame frame) {
		documentsList = new ArrayList<SingleDocumentModel>();
		listeners = new ArrayList<MultipleDocumentListener>();

		addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				if (documentsList.size() != 0 && getSelectedIndex() != -1) {
					previousModel = currentDocument;
					currentDocument = documentsList.get(getSelectedIndex());
					currDocChanged(previousModel, currentDocument);
				}
				frame.setTitle(
						(currentDocument.getFilePath() != null ? currentDocument.getFilePath().toString() : "(unnamed)")
								+ " - JNotepad++");
			}
		});
	}

	/**
	 * Getter for image icon.
	 * 
	 * @param path given path
	 * @return image icon
	 */
	public ImageIcon getImageIcon(String path) {
		byte[] bytes = null;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				throw new IllegalArgumentException("Wrong path.");
			}
			try {
				bytes = is.readAllBytes();
			} catch (IOException e) {
				throw new RuntimeException("Error");
			}
		} catch (Exception e) {
		}

		return new ImageIcon(new ImageIcon(bytes).getImage().getScaledInstance(17, 17, Image.SCALE_SMOOTH));
	}

	/**
	 * Helper method for listeners.
	 * 
	 * @param previousModel given model
	 * @param currentModel  given model
	 */
	private void currDocChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
		if (previousModel == null && currentModel == null)
			throw new IllegalArgumentException("Invalid arguments.");
		listeners.forEach((l) -> l.currentDocumentChanged(previousModel, currentModel));
	}

	/**
	 * Helper method for listeners.
	 * 
	 * @param model given model
	 */
	private void docAdded(SingleDocumentModel model) {
		listeners.forEach((l) -> l.documentAdded(model));
	}

	/**
	 * Helper method for listeners.
	 * 
	 * @param model given model
	 */
	private void docRemoved(SingleDocumentModel model) {
		listeners.forEach((l) -> l.documentRemoved(model));
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documentsList.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		SingleDocumentModel mod = newDocument(null, "");
		return mod;
	}

	/**
	 * Helper method for new Document.
	 * 
	 * @param path given path
	 * @param text given text
	 * @return model
	 */
	private SingleDocumentModel newDocument(Path path, String text) {
		SingleDocumentModel newDocModel = new DefaultSingleDocumentModel(path, text);
		documentsList.add(newDocModel);
		newDocModel.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				String ic = model.isModified() ? "icons/redDisk.png" : "icons/blueDisk.png";
				setIconAt(documentsList.indexOf(model), getImageIcon(ic));
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(documentsList.indexOf(model), model.getFilePath().getFileName().toString());
				setToolTipTextAt(documentsList.indexOf(model), model.getFilePath().toAbsolutePath().toString());
			}
		});
		addModelToTab(newDocModel);
		return newDocModel;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path);
		previousModel = currentDocument;

		int br = 0;
		for (SingleDocumentModel model : documentsList) {
			br++;
			if (model.getFilePath() == null)
				continue;
			if (model.getFilePath().equals(path)) {
				System.out.println("vec postoji");
				currentDocument = model;
				currDocChanged(previousModel, currentDocument);
				this.setSelectedIndex(br-1);
				return currentDocument;
			}
		}
		if (!Files.isReadable(path))
			throw new IllegalArgumentException("Wrong path.");
		String text = null;
		try {
			text = Files.readString(path);
		} catch (IOException e) {
		}
		SingleDocumentModel newDocModel = new DefaultSingleDocumentModel(path, text);
		documentsList.add(newDocModel);
		newDocModel.addSingleDocumentListener(new SingleDocumentListener() {

			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				String ic = model.isModified() ? "icons/redDisk.png" : "icons/blueDisk.png";
				setIconAt(documentsList.indexOf(model), getImageIcon(ic));
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				setTitleAt(documentsList.indexOf(model), model.getFilePath().getFileName().toString());
				setToolTipTextAt(documentsList.indexOf(model), model.getFilePath().toAbsolutePath().toString());
			}
		});
		addModelToTab(newDocModel);
		currentDocument = newDocModel;

		currDocChanged(previousModel, currentDocument);
		docAdded(newDocModel);

		return currentDocument;
	}

	/**
	 * Helper method for adding model to tab.
	 * 
	 * @param model given model
	 */
	private void addModelToTab(SingleDocumentModel model) {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(new JScrollPane(model.getTextComponent()), BorderLayout.CENTER);
		addTab(model.getFilePath() != null ? model.getFilePath().getFileName().toString() : "(unnamed)",
				getImageIcon("icons/blueDisk.png"), panel,
				model.getFilePath() != null ? model.getFilePath().toAbsolutePath().toString() : "(unnamed)");
		setSelectedComponent(panel);
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		if (newPath == null) {
			newPath = model.getFilePath();
		}
		for (SingleDocumentModel docModel : documentsList) {
			if (docModel.equals(model) || docModel.getFilePath() == null)
				continue;
			if (docModel.getFilePath().equals(newPath)) {
				throw new IllegalArgumentException("Wrong path.");
			}
		}
		model.setFilePath(newPath);
		try {
			Files.writeString(newPath, model.getTextComponent().getText());
		} catch (IOException e1) {
			return;
		}
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		int index = documentsList.indexOf(model);
		removeTabAt(index);
		documentsList.remove(model);

		if (documentsList.size() != 0) {
			currentDocument = documentsList.get(documentsList.size() - 1);
		}
		setSelectedIndex(documentsList.indexOf(currentDocument));
		docRemoved(model);
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documentsList.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {
		return documentsList.get(index);
	}

}
