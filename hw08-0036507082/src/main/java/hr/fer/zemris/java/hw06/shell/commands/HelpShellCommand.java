package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class HelpShellCommand defines help command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		SortedMap<String, ShellCommand> commands = env.commands();

		if (arguments.isEmpty()) {
			commands.forEach((k, v) -> env.writeln(k));
		} else {
			if (commands.containsKey(arguments)) {
				List<String> list = commands.get(arguments).getCommandDescription();
				list.forEach((c) -> env.writeln(c));
			} else {
				env.writeln("Command help " + arguments + "does not exists");
			}
		}
		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("help:");
		desc.add("If started with no arguments lists names of all supported commands.");
		desc.add(
				"If started with single argument prints name and the description of selected command or prints appropriate error message if no such command exists.");
		return Collections.unmodifiableList(desc);
	}

}
