package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface MultipleDocumentListener is listener interface.
 * 
 * It is uses as a listener
 * 
 * @author Filip
 */
public interface MultipleDocumentListener {

	/**
	 * Method is called when current document changes.
	 * 
	 * @param previousModel
	 * @param currentModel
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Method is called when document is added.
	 * 
	 * @param model
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Method is called when document is removed.
	 * 
	 * @param model
	 */
	void documentRemoved(SingleDocumentModel model);
}
