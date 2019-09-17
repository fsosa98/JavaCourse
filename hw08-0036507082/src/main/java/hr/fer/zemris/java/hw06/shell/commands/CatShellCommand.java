package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class CatShellCommand defines cat command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		String[] parts = arguments.split("\"");
		Path path;
		Charset charset = Charset.defaultCharset();

		if (parts.length == 1) {
			String[] args = arguments.split("\\s+");
			if (args.length == 1) {
				path = env.getCurrentDirectory().resolve(args[0]).normalize();
			} else if (args.length == 2) {
				path = env.getCurrentDirectory().resolve(args[0]).normalize();
				try {
					charset = Charset.forName(args[1]);
				} catch (Exception e) {
					env.writeln("Invalid charset.");
					env.write(env.getPromptSymbol() + " ");
					return ShellStatus.CONTINUE;
				}
			} else {
				env.writeln("Invalid arguments.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}

		}

		else if (parts.length == 2) {
			if (!parts[0].isBlank()) {
				env.writeln("Invalid arguments.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
			path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(parts[1])).normalize();
		}

		else if (parts.length == 3) {
			if (!parts[0].isBlank()) {
				env.writeln("Invalid arguments.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
			path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(parts[1])).normalize();
			try {
				charset = Charset.forName(parts[2]);
			} catch (Exception e) {
				env.writeln("Invalid charset.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}

		else {
			env.writeln("Invalid arguments.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Files.newInputStream(path, StandardOpenOption.READ), charset))) {
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				env.writeln(line);
			}
		} catch (Exception exc) {
			env.writeln("Wrong path.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("cat:");
		desc.add("Copy source file to destination file.");
		desc.add("Command takes two arguments: source and destination path.");
		return Collections.unmodifiableList(desc);
	}

}
