package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class ExitShellCommand defines exit command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.isEmpty()) {
			env.writeln("Exit shell command must be called without arguments.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("exit:");
		desc.add("Shell terminates when user gives exit command.");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
