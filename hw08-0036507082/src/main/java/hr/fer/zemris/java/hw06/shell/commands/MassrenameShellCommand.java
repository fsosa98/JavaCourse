package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.builder.FilterResult;
import hr.fer.zemris.java.hw06.shell.builder.NameBuilder;
import hr.fer.zemris.java.hw06.shell.builder.NameBuilderParser;

/**
 * Class MassrenameShellCommand defines massrename command. Massrename command
 * has options for filtering and grouping given directories. Users can move
 * renamed and filtered files from given directory to another directory.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class MassrenameShellCommand implements ShellCommand {

	private Path[] paths = new Path[2];
	private String cmd;
	private String mask;
	private String other = null;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		arguments = arguments.trim();
		// DIR1 DIR2 CMD MASK other
		int current = 0;
		char[] data = arguments.toCharArray();

		try {
			// DIR1, DIR2
			current = parseDIR(0, current, data, env);
			current = skipBlanks(current, data);
			current = parseDIR(1, current, data, env);

			// CMD
			current = skipBlanks(current, data);
			current = parseCMD(current, data);

			// MASK
			current = skipBlanks(current, data);
			current = parseMask(current, data, true);

			// other
			current = skipBlanks(current, data);
			current = parseMask(current, data, false);

			if (current < arguments.length()) {
				env.writeln("Invalid arguments.");
				env.write(env.getPromptSymbol() + " ");
				return ShellStatus.CONTINUE;
			}
		} catch (Exception exc) {
			env.writeln("Invalid arguments.");
			env.write(env.getPromptSymbol() + " ");
			return ShellStatus.CONTINUE;
		}

		try {
			switch (cmd) {
			case "filter":
				if (other != null) {
					env.writeln("Invalid arguments.");
					break;
				}
				List<FilterResult> list = filter(paths[0], mask);
				for (FilterResult result : list) {
					env.writeln(result.toString());
				}
				break;
			case "groups":
				if (other != null) {
					env.writeln("Invalid arguments.");
					break;
				}
				List<FilterResult> list2 = filter(paths[0], mask);
				for (FilterResult result : list2) {
					env.write(result.toString());
					for (int i = 0; i < result.numberOfGroups() + 1; i++) {
						env.write(" " + i + ": " + result.group(i));
					}
					env.writeln("");
				}
				break;
			case "show":
				if (other == null) {
					env.writeln("Invalid arguments.");
					break;
				}
				NameBuilderParser parser = new NameBuilderParser(other);
				NameBuilder builder = parser.getNameBuilder();
				List<FilterResult> list3 = filter(paths[0], mask);
				for (FilterResult result : list3) {
					StringBuilder sb = new StringBuilder();
					builder.execute(result, sb);
					env.writeln(result.toString() + " => " + sb);
				}
				break;
			case "execute":
				if (other == null) {
					env.writeln("Invalid arguments.");
					break;
				}
				NameBuilderParser parser2 = new NameBuilderParser(other);
				NameBuilder builder2 = parser2.getNameBuilder();
				List<FilterResult> list4 = filter(paths[0], mask);
				for (FilterResult result : list4) {
					StringBuilder sb = new StringBuilder();
					builder2.execute(result, sb);
					String newName = new String(sb);
					Path path = Paths.get(paths[0].toString() + "/" + result.toString());
					Path move = paths[1].resolve(newName);
					Files.move(path, move, StandardCopyOption.REPLACE_EXISTING);
					env.writeln(paths[0] + "/" + result.toString() + " => " + paths[1] + "/" + newName);
				}
				break;
			}
		} catch (Exception ex) {
			env.writeln("Invalid arguments.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "massrename";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("massrename:");
		desc.add("This command is used for mass renaming/moving files.");
		desc.add("Command takes source directory path, destination directory path, cmd, mask and option.");
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
	 * Helper method for parsing DIR.
	 * 
	 * @param index   given index
	 * @param current given current
	 * @param data    given data
	 * @return index
	 */
	private int parseDIR(int index, int current, char[] data, Environment env) {
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

	/**
	 * Helper method for parsing CMD.
	 * 
	 * @param current
	 * @param data
	 * @return
	 */
	private int parseCMD(int current, char[] data) {
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (current >= data.length || data[current] == ' ') {
				break;
			}
			sb.append(data[current++]);
		}
		String s = new String(sb);
		if (s.equals("filter") || s.equals("groups") || s.equals("show") || s.equals("execute")) {
			cmd = s;
			return current;
		}
		throw new ShellIOException();
	}

	/**
	 * Helper method for parsing mask.
	 * 
	 * @param current
	 * @param data
	 * @param isMask
	 * @return
	 */
	private int parseMask(int current, char[] data, boolean isMask) {
		if (current >= data.length) {
			return current;
		}
		StringBuilder sb = new StringBuilder();
		if (data[current] == '\"') {
			current++;
			while (true) {
				if (current + 1 < data.length && data[current] == '\\' && data[current + 1] == '\\') {
					current++;
					sb.append(data[current++]);
				}
				if (current >= data.length) {
					throw new ShellIOException();
				}
				if (data[current] == '\"') {
					current++;
					break;
				}
				sb.append(data[current++]);
			}
		} else {
			while (true) {
				if (current >= data.length || data[current] == ' ') {
					break;
				}
				sb.append(data[current++]);
			}
		}
		if (isMask) {
			mask = new String(sb);
		} else {
			other = new String(sb);
		}
		return current;
	}

	/**
	 * This method is used for filtering files from given directory.
	 * 
	 * @param dir     given directory
	 * @param pattern given pattern for filtering
	 * @return filtered list
	 * @throws IOException
	 */
	private List<FilterResult> filter(Path dir, String pattern) throws IOException {
		List<FilterResult> list = new ArrayList<FilterResult>();

		Pattern pat = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
		File[] files = dir.toFile().listFiles();
		for (File file : files) {
			Matcher matcher = pat.matcher(file.getName());
			if (matcher.matches()) {
				FilterResult result = new FilterResult(matcher, file.toPath());
				list.add(result);
			}
		}
		return list;
	}

}
