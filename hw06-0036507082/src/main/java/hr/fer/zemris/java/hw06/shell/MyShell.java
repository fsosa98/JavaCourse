package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Class MyShell defines shell implementation.
 * 
 * Users can call commands such as cat, charsets, copy, exit, help, hexdump, ls,
 * mkdir, symbol, tree.
 * 
 * @author Filip
 */
public class MyShell {

	/**
	 * This is main method.
	 * 
	 * @param args ignored
	 */
	public static void main(String[] args) {
		System.out.print("Welcome to MyShell v 1.0\n> ");

		Scanner scanner = new Scanner(System.in);
		EnvironmentImpl env = new EnvironmentImpl(scanner);
		ShellStatus status = ShellStatus.CONTINUE;
		do {
			String l = env.readLine();
			if (l.split(" ").length == 1) {
				l += " ";
			}

			String commandName = new String();
			String arguments = new String();
			String[] input = parse(l);
			commandName = input[0];
			arguments = input[1];

			ShellCommand command = env.commands.get(commandName);
			if (command != null) {
				status = command.executeCommand(env, arguments);
			} else {
				env.writeln("Wrong command.");
				env.write(env.getPromptSymbol() + " ");
			}

		} while (status != ShellStatus.TERMINATE);

		scanner.close();

	}

	/**
	 * Helper method for parsing.
	 * 
	 * @param l given line
	 * @return command and arguments
	 */
	private static String[] parse(String l) {
		int current = 0;
		char[] data = l.toCharArray();
		current = skipBlanks(current, data);
		StringBuilder sb = new StringBuilder();
		while (true) {
			if (current >= data.length || data[current] == ' ') {
				current++;
				break;
			}
			sb.append(data[current++]);
		}
		return new String[] { sb.toString(), l.substring(current).trim() };
	}

	/**
	 * This method skips blanks to the next character.
	 * 
	 * @param currentIndex given index
	 * @param data         given data
	 * @return current index
	 */
	private static int skipBlanks(int currentIndex, char[] data) {
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
	 * Class EnvironmentImpl is implementation of environment.
	 * 
	 * @author Filip
	 */
	private static class EnvironmentImpl implements Environment {

		SortedMap<String, ShellCommand> commands;
		private Character promptSymbol = '>';
		private Character multilineSymbol = '|';
		private Character morelinesSymbol = '\\';
		private Scanner scanner;

		/**
		 * Default constructor.
		 */
		private EnvironmentImpl(Scanner scanner) {
			this.scanner = scanner;
			commands = new TreeMap<>();

			commands.put("cat", new CatShellCommand());
			commands.put("charsets", new CharsetShellCommand());
			commands.put("copy", new CopyShellCommand());
			commands.put("exit", new ExitShellCommand());
			commands.put("help", new HelpShellCommand());
			commands.put("hexdump", new HexdumpShellCommand());
			commands.put("ls", new LsShellCommand());
			commands.put("mkdir", new MkdirShellCommand());
			commands.put("symbol", new SymbolShellCommand());
			commands.put("tree", new TreeShellCommand());
		}

		@Override
		public String readLine() throws ShellIOException {
			String line = scanner.nextLine();
			StringBuilder sb = new StringBuilder();

			while (line.endsWith(getMorelinesSymbol().toString())) {
				sb.append(line.substring(0, line.length() - 1));
				write(getMultilineSymbol() + " ");
				line = scanner.nextLine();
			}
			sb.append(line);
			return new String(sb);
		}

		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(commands);
		}

		@Override
		public Character getMultilineSymbol() {
			return multilineSymbol;
		}

		@Override
		public void setMultilineSymbol(Character symbol) {
			multilineSymbol = symbol;
		}

		@Override
		public Character getPromptSymbol() {
			return promptSymbol;
		}

		@Override
		public void setPromptSymbol(Character symbol) {
			promptSymbol = symbol;
		}

		@Override
		public Character getMorelinesSymbol() {
			return morelinesSymbol;
		}

		@Override
		public void setMorelinesSymbol(Character symbol) {
			morelinesSymbol = symbol;
		}

	}

}
