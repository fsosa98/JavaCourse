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
 * Class CdShellCommand defines cd command for changing current directory.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class CdShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Path path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(arguments)).normalize();
			if (Files.isDirectory(path)) {
				env.setCurrentDirectory(path);
				env.writeln("Done");
			} else {
				env.writeln("Invalid argument.");
			}
		} catch (Exception e) {
			env.writeln("Invalid argument.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cd";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("cd:");
		desc.add("Sets given path to current directory.");
		desc.add("Command takes a single argument: directory name..");
		return Collections.unmodifiableList(desc);
	}

}
