package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class SymbolShellCommand defines symbol command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] parts = arguments.split("\\s+");
		if (parts.length < 1 || parts.length > 3) {
			env.writeln("Command symbol with this arguments does not exists");
		}

		if (parts.length == 1) {
			switch (parts[0]) {
			case "PROMPT":
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
				break;
			case "MORELINES":
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
				break;
			case "MULTILINE":
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
				break;
			default:
				env.writeln("Command symbol " + arguments + "does not exists");
				break;
			}
		}

		else if (parts.length == 2) {
			if (parts[1].length() != 1) {
				env.writeln("Command symbol " + arguments + "does not exists");
			} else {
				switch (parts[0]) {
				case "PROMPT":
					env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + parts[1] + "'");
					env.setPromptSymbol(parts[1].charAt(0));
					break;
				case "MORELINES":
					env.writeln("Symbol for MORELINES changed from '" + env.getMorelinesSymbol() + "' to '" + parts[1]
							+ "'");
					env.setMorelinesSymbol(parts[1].charAt(0));
					break;
				case "MULTILINE":
					env.writeln("Symbol for MULTILINE changed from '" + env.getMultilineSymbol() + "' to '" + parts[1]
							+ "'");
					env.setMultilineSymbol(parts[1].charAt(0));
					break;
				default:
					env.writeln("Command symbol " + arguments + "does not exists");
					break;
				}
			}
		}

		else {
			env.writeln("Invalid command");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("symbol:");
		desc.add(
				"If started with single argument prints symol of argument or prints appropriate error message if no such command exists");
		desc.add(
				"If started with two arguments and second argument is cahracter prints name of symbol, old symbol and changed symbol or prints appropriate error message if no such command exists.");
		return Collections.unmodifiableList(desc);
	}

}
