package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class PopdShellCommand defines pop command that prints current path.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class PwdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.isEmpty()) {
			env.writeln("Pwd shell command must be called without arguments.");
		} else {
			env.writeln(env.getCurrentDirectory().toString());
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pwd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("pwd:");
		desc.add("Prints current absolute path.");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
