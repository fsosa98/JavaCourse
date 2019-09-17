package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class MkdirShellCommand defines mkdir command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class MkdirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (arguments.isEmpty()) {
			env.writeln("Command takes a single argument: directory name.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		try {
			Path path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(arguments)).normalize();
			if (Files.isDirectory(path)) {
				env.writeln("The directory already exists.");
			} else {
				Files.createDirectories(path);
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
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("mkdir:");
		desc.add("Creates the appropriate directory structure.");
		desc.add("Command takes a single argument: directory name.");
		return Collections.unmodifiableList(desc);
	}

}
