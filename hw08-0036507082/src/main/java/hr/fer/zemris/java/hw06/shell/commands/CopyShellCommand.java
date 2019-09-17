package hr.fer.zemris.java.hw06.shell.commands;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class CopyShellCommand defines copy command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class CopyShellCommand implements ShellCommand {

	Path[] paths = new Path[2];

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		int current = 0;
		char[] data = arguments.toCharArray();

		current = skipBlanks(current, data);
		try {
			current = parse(0, current, data, env);
			current = skipBlanks(current, data);
			current = parse(1, current, data, env);
		} catch (Exception exc) {
			env.writeln("Invalid path.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		if (paths[0].equals(paths[1]) || Files.isDirectory(paths[0]) || !Files.exists(paths[0])) {
			env.writeln("Wrong path.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		if (Files.isDirectory(paths[1])) {
			paths[1] = env.getCurrentDirectory().resolve(paths[1].toString() + "/" + paths[0].getFileName())
					.normalize();
		}

		if (Files.exists(paths[1])) {
			env.write("Do you want to overwrite it(Y/N): ");
			String answer = env.readLine();
			if (!answer.equals("Y")) {
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		}

		try (InputStream is = Files.newInputStream(paths[0], StandardOpenOption.READ)) {
			try (OutputStream os = Files.newOutputStream(paths[1], StandardOpenOption.CREATE)) {
				byte[] buff = new byte[1024];
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					os.write(buff, 0, r);
				}
				env.writeln("Done.");
			}
		} catch (Exception exc) {
			env.writeln("Invalid path.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("copy:");
		desc.add("Copy source file to destination file.");
		desc.add("Command takes two arguments: source and destination path.");
		return Collections.unmodifiableList(desc);
	}

	/**
	 * Helper method for skipping blanks.
	 * 
	 * @param currentIndex
	 * @param data
	 * @return
	 */
	private int skipBlanks(int currentIndex, char[] data) {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
			} else
				break;
		}
		return currentIndex;
	}

	/**
	 * Helper method for parsing.
	 * 
	 * @param index   given index
	 * @param current given current
	 * @param data    given data
	 * @return index
	 */
	private int parse(int index, int current, char[] data, Environment env) {
		if (data[current] == '\"') {
			StringBuilder sb = new StringBuilder();
			sb.append(data[current++]);
			while (true) {
				if (current >= data.length) {
					throw new ShellIOException();
				}
				if (data[current] == '\"') {
					sb.append(data[current++]);
					break;
				}
				sb.append(data[current++]);
			}
			paths[index] = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(new String(sb))).normalize();
		} else {
			StringBuilder sb = new StringBuilder();
			while (true) {
				if (current >= data.length || data[current] == ' ') {
					break;
				}
				sb.append(data[current++]);
			}
			paths[index] = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(new String(sb))).normalize();
		}
		return current;
	}

}
