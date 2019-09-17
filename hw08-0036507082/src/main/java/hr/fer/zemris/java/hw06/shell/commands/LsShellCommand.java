package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Class LsShellCommand defines ls command.
 * 
 * Users can execute command, get command name and get command description.
 * 
 * @author Filip
 */
public class LsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			Path path = env.getCurrentDirectory().resolve(SplittingUtility.stringToPath(arguments)).normalize();
			if (!Files.isDirectory(path)) {
				env.writeln("Given argument is not directory.");
			} else {
				File[] files = path.toFile().listFiles();
				for (File file : files) {
					env.write(file.isDirectory() ? "d" : "-");
					env.write(file.canRead() ? "r" : "-");
					env.write(file.canWrite() ? "w" : "-");
					env.write(file.canExecute() ? "x " : "- ");
					int length = String.valueOf(file.length()).length();
					env.write((" ").repeat(10 - length) + file.length() + " ");

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
							LinkOption.NOFOLLOW_LINKS);
					BasicFileAttributes attributes = faView.readAttributes();
					FileTime fileTime = attributes.creationTime();
					String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));

					env.write(formattedDateTime + " " + file.getName() + "\n");
				}
			}
		} catch (Exception e) {
			env.writeln("Invalid argument.");
		}

		env.write(env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<String>();
		desc.add("ls:");
		desc.add("Writes a directory listing.");
		desc.add("Command takes a single argument – directory.");
		return Collections.unmodifiableList(desc);
	}

}
