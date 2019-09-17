package hr.fer.zemris.java.webserver;

/**
 * Interface IWebWorker is web worker interface.
 * 
 * Users can process request.
 * 
 * @author Filip
 */
public interface IWebWorker {

	/**
	 * Method for processing request with given context.
	 * 
	 * @param context given context
	 * @throws Exception
	 */
	public void processRequest(RequestContext context) throws Exception;

}
