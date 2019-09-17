package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class CharsetShellCommand defines charset command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class CharsetShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		if (!arguments.isEmpty()) {
			env.writeln("Charset shell command must be called without arguments.");
		} else {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			charsets.forEach((k, v) -> env.writeln(k));
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("charset:");
		desc.add("Lists names of supported charsets for your Java platform.");
		desc.add("Command takes no arguments.");
		return Collections.unmodifiableList(desc);
	}

}
