package hr.fer.zemris.java.hw14.model;

/**
 * Class Polls defines data structure of poll.
 * 
 * @author Filip
 */
public class Polls {

	private long id;
	private String title;
	private String message;

	/**
	 * Default constructor.
	 */
	public Polls() {
	}

	/**
	 * Getter for id.
	 * 
	 * @return id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Setter for id.
	 * 
	 * @param id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Getter for title.
	 * 
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for title.
	 * 
	 * @param title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for message.
	 * 
	 * @return message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for message.
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
