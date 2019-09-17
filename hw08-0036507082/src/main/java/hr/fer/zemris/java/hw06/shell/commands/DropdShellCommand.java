package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

public class DropdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			env.writeln("Dropd shell command must be called without arguments.");
		} else {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
			if (stack == null || stack.isEmpty()) {
				env.writeln("Nema pohranjenih direktorija.");
			} else {
				stack.pop();
			}
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "dropd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("dropd:");
		desc.add("Delete directory from stack.");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
