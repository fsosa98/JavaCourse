package hr.fer.zemris.java.webserver;

/**
 * Interface IDispatcher is dispatcher interface.
 * 
 * Users can dispatch request.
 * 
 * @author Filip
 */
public interface IDispatcher {

	/**
	 * Method for dispatching request with given url Path.
	 * 
	 * @param urlPath given url
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;

}
