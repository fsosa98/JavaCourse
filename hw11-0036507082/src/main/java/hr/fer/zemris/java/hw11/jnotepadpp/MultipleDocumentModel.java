package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Interface MultipleDocumentModel is multiple document model.
 * 
 * @author Filip
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates new document.
	 * 
	 * @return model
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter for current document
	 * 
	 * @return model
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * This method loads document with given path.
	 * 
	 * @param path
	 * @return
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * This method save document with given model.
	 * 
	 * @param model
	 * @param newPath
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * This method close document with given model.
	 * 
	 * @param model
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * This method adds document.
	 * 
	 * @param l
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * This method removes.
	 * 
	 * @param l
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * This method returns number of documents.
	 * 
	 * @return
	 */
	int getNumberOfDocuments();

	/**
	 * This method returns document.
	 * 
	 * @param index
	 * @return
	 */
	SingleDocumentModel getDocument(int index);
}
