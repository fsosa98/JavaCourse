package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface ShellCommand defines shell commands.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public interface ShellCommand {

	/**
	 * This method is used for executing command.
	 * 
	 * @param env       given environment
	 * @param arguments given arguments
	 * @return shell status
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Name getter
	 * 
	 * @return return command name
	 */
	String getCommandName();

	/**
	 * Command description getter
	 * 
	 * @return command description
	 */
	List<String> getCommandDescription();

}
