package hr.fer.zemris.java.hw06.shell.commands;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class TreeShellCommand defines tree command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class TreeShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		try {
			Path path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(arguments)).normalize();
			if (!Files.isDirectory(path)) {
				env.writeln("Given argument is not directory.");
			} else {
				Files.walkFileTree(path, new FileVisitor<Path>() {

					private int level = 0;

					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						env.writeln(("  ").repeat(2 * level) + dir.getFileName().toString());
						level++;
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						env.writeln(("  ").repeat(2 * level) + file.getFileName().toString());
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						level--;
						return FileVisitResult.CONTINUE;
					}
				});
			}
		} catch (Exception e) {
			env.writeln("Invalid argument.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;

	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("tree:");
		desc.add("Prints tree from given directory.");
		desc.add("Expects a single argument: directory name.");
		return Collections.unmodifiableList(desc);
	}

}
