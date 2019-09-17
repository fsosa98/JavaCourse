package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class PushdShellCommand defines push command that pushes path from stack.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class PushdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Path path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(arguments)).normalize();
			if (!Files.isDirectory(path)) {
				env.writeln("Given argument is not directory.");
			} else {
				@SuppressWarnings("unchecked")
				Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
				if (stack == null) {
					stack = new Stack<Path>();
					env.setSharedData("cdstack", stack);
				}
				stack.push(env.getCurrentDirectory());
				env.setCurrentDirectory(path);
				env.writeln("Done.");
			}
		} catch (Exception e) {
			env.writeln("Invalid argument.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "pushd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("pushd:");
		desc.add("Pushes current directory on stack and sets current directory.");
		desc.add("Command takes a single argument – directory.");
		return Collections.unmodifiableList(desc);
	}

}
