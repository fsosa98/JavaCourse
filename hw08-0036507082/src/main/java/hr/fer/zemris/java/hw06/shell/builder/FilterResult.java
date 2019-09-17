package hr.fer.zemris.java.hw06.shell.builder;

import java.nio.file.Path;
import java.util.regex.Matcher;

/**
 * Class FilterResult defines data structure and a set of methods for working
 * with it.
 * 
 * Users can get number of groups or group with given index.
 * 
 * @author Filip
 */
public class FilterResult {

	private Path path;
	private Matcher matcher;

	/**
	 * Constructor with given matcher and path.
	 * 
	 * @param matcher given matcher
	 * @param path    given path
	 */
	public FilterResult(Matcher matcher, Path path) {
		this.path = path;
		this.matcher = matcher;
	}

	@Override
	public String toString() {
		return path.getFileName().toString();
	}

	/**
	 * This method returns number of groups.
	 * 
	 * @return
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}

	/**
	 * This method returns group with given index.
	 * 
	 * @param index given index
	 * @return group
	 */
	public String group(int index) {
		return matcher.group(index);
	}

}
