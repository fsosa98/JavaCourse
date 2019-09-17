package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Interface Environment defines shell environment.
 * 
 * Users can read, write, get commands, set and get shell symbols.
 * 
 * @author Filip
 */
public interface Environment {

	/**
	 * Method for reading line.
	 * 
	 * @return line
	 * @throws ShellIOException
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method for writing text.
	 * 
	 * @param text given text
	 * @throws ShellIOException
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method for writing text.
	 * 
	 * @param text given text
	 * @throws ShellIOException
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Commands getter.
	 * 
	 * @return commands
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * MultilineSymbol getter.
	 * 
	 * @return multiline symbol
	 */
	Character getMultilineSymbol();

	/**
	 * MultilineSymbol setter.
	 * 
	 * @param symbol given symbol
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * PromptSymbol getter.
	 * 
	 * @return prompt symbol
	 */
	Character getPromptSymbol();

	/**
	 * PromptSymbol setter.
	 * 
	 * @param symbol given symbol
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * MorelinesSymbol getter.
	 * 
	 * @return morelines symbol
	 */
	Character getMorelinesSymbol();

	/**
	 * MorelinesSymbol setter.
	 * 
	 * @param symbol given symbol
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * CurrentDirectory getter.
	 * 
	 * @return path of current directory
	 */
	Path getCurrentDirectory();

	/**
	 * CurrentDirectory setter.
	 * 
	 * @param given path
	 */
	void setCurrentDirectory(Path path);

	/**
	 * SharedData getter.
	 * 
	 * @param key given key
	 * @return shared data with given key
	 */
	Object getSharedData(String key);

	/**
	 * SharedData setter.
	 * 
	 * @param key   given key
	 * @param value given value
	 */
	void setSharedData(String key, Object value);

}
