package hr.fer.zemris.java.hw16.jsdemo.servlets;

/**
 * Class Image defines data structure of one image.
 * 
 * @author Filip
 */
public class Image {

	private String name;
	private String description;
	private String[] tags;
	private String path;
	
	/**
	 * Constructor
	 * 
	 * @param name
	 * @param description
	 * @param tags
	 * @param path
	 */
	public Image(String name, String description, String[] tags, String path) {
		this.name = name;
		this.description = description;
		this.tags = tags;
		this.path = path;
	}

	/**
	 * Getter for name
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for description
	 * 
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for tags
	 * 
	 * @return tags
	 */
	public String[] getTags() {
		return tags;
	}

	/**
	 * Getter for path
	 * 
	 * @return path
	 */
	public String getPath() {
		return path;
	}
	
}
