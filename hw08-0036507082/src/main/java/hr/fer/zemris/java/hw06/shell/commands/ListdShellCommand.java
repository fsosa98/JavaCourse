package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class ListdShellCommand defines list command for listing paths from shared
 * data.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class ListdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Listd shell command must be called without arguments.");
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			if (stack == null || stack.isEmpty()) {
				env.writeln("Nema pohranjenih direktorija.");
			} else {
				List<Path> list = new ArrayList<Path>(stack);
				Collections.reverse(list);
				list.forEach((path) -> env.writeln(path.toString()));
			}
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "listd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("listd:");
		desc.add("Lists all paths form stack");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
