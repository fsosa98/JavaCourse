package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class PopdShellCommand defines pop command that poppes path from stack.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class PopdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Popd shell command must be called without arguments.");
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			if (stack == null || stack.isEmpty()) {
				env.writeln("Stack is empty.");
			} else {
				Path path = stack.pop();
				try {
					env.setCurrentDirectory(path);
					env.writeln("Done");
				} catch (ShellIOException exc) {
					env.writeln("There is no such directory.");
				}
			}
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "popd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("popd:");
		desc.add("Poppes path from stack and and sets current path.");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
