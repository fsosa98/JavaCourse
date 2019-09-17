package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.ShellIOException;

/**
 * Class SplittingUtility is helper class with single method.
 * 
 * Users can get string path from string.
 * 
 * @author Filip
 */
public class SplittingUtility {

	/**
	 * This method return string path with supported escaping from string.
	 * 
	 * @param input given string
	 * @return string path
	 */
	public static String stringToPath(String input) {
		if (!input.contains("\"")) {
			return input;
		}

		int current = 0;
		char[] data = input.toCharArray();

		StringBuilder sb = new StringBuilder();

		if (data[current] == '\"') {
			current++;
			while (true) {
				if (current >= data.length) {
					throw new ShellIOException("Invalid argument");
				}
				if (data[current] == '\"') {
					return new String(sb);
				} else if (data[current] == '\\') {
					if (data[current + 1] == '\\' || data[current + 1] == '\"') {
						sb.append(data[++current]);
						current++;
						continue;
					} else {
						sb.append(data[current++]);
						sb.append(data[current++]);
						continue;
					}
				}
				sb.append(data[current++]);
			}
		} else {
			throw new ShellIOException("Invalid argument");
		}
	}

}
