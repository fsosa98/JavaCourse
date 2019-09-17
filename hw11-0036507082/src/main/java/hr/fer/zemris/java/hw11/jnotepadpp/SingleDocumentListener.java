package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Interface SingleDocumentListener is listener interface.
 * 
 * It is uses as a listener
 * 
 * @author Filip
 */
public interface SingleDocumentListener {

	/**
	 * Method is called when document is modified.
	 * 
	 * @param model
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Method is called when path is modified.
	 * 
	 * @param model
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
