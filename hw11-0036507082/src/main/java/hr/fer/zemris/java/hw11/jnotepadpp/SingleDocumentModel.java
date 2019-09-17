package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Interface SingleDocumentModel is single document model.
 * 
 * @author Filip
 */
public interface SingleDocumentModel {

	/**
	 * Getter for text component.
	 * 
	 * @return
	 */
	JTextArea getTextComponent();

	/**
	 * Getter for path.
	 * 
	 * @return
	 */
	Path getFilePath();

	/**
	 * Setter for path
	 * 
	 * @param path
	 */
	void setFilePath(Path path);

	/**
	 * Getter for modified.
	 * 
	 * @return
	 */
	boolean isModified();

	/**
	 * Setter for modified
	 * 
	 * @param modified
	 */
	void setModified(boolean modified);

	/**
	 * Adds listener.
	 * 
	 * @param l
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes listener.
	 * 
	 * @param l
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
