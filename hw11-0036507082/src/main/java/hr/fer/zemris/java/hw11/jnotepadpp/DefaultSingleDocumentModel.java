package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Class DefaultSingleDocumentModel is default single document model.
 * 
 * This class is implementation of SingleDocumentModel.
 * 
 * @author Filip
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	private JTextArea textArea;
	private Path filePath;
	private boolean modified;
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor with given path and text.
	 * 
	 * @param filePath given path
	 * @param text     given text
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		textArea = new JTextArea(text);
		this.filePath = filePath;
		listeners = new ArrayList<SingleDocumentListener>();
		textArea.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent e) {
				modified = true;
				documentModifyStatus();
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				modified = true;
				documentModifyStatus();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				modified = true;
				documentModifyStatus();
			}
		});
	}

	/**
	 * Helper method for listeners.
	 */
	private void documentModifyStatus() {
		listeners.forEach((l) -> l.documentModifyStatusUpdated(this));
	}

	/**
	 * Helper method for listeners.
	 */
	private void documentFilePath() {
		listeners.forEach((l) -> l.documentFilePathUpdated(this));
	}

	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		Objects.requireNonNull(path);
		this.filePath = path;
		documentFilePath();
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		documentModifyStatus();
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

}
