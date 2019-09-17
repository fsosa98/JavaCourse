package hr.fer.zemris.java.hw06.shell.commands;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class HexdumpShellCommand defines hexdump command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class HexdumpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		Path path = Paths.get(SplittingUtility.stringToPath(arguments));

		if (Files.isDirectory(path)) {
			env.writeln("Invalid path.");
		}

		else {
			try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
				byte[] buff = new byte[1];
				byte[] text = new byte[16];
				int counter = 0;
				int row = 0x0;
				boolean last = false;
				boolean first = true;
				while (true) {

					if (last) {
						if (counter % 16 == 0) {
							env.write(" | ");
							env.write(new String(text));
							break;
						} else if (counter % 8 == 0) {
							env.write("|");
						} else {
							if (first) {
								first = false;
							} else {
								env.write(" ");
							}
						}
						env.write("  ");
						counter++;
						continue;
					}

					if (counter % 16 == 0) {
						env.write(String.format("%08X", row) + ": ");
						row += 0x10;
						counter = 0;
					}

					int r = is.read(buff);
					if (r < 1) {
						last = true;
					}

					byte byt = buff[0];
					if (byt < 32 || byt > 127) {
						byt = 46;
					}
					env.write(Util.bytetohex(buff).toUpperCase());
					text[counter++] = byt;

					if (counter % 16 == 0) {
						env.write(" | ");
						env.writeln(new String(text));
						text = new byte[16];
					} else if (counter % 8 == 0) {
						env.write("|");
					} else {
						env.write(" ");
					}
				}
			} catch (Exception exc) {
				env.writeln("Invalid path.");
			}

		}

		env.write("\n" + env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("hexdump:");
		desc.add("It produces hex-output of given file.");
		desc.add("Command expects a single argument: file name.");
		return Collections.unmodifiableList(desc);
	}

}
