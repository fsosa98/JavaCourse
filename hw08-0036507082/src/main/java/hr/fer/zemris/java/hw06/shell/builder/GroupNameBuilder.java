package hr.fer.zemris.java.hw06.shell.builder;

/**
 * Class GroupNameBuilder is implementation of NameBuilder.
 * 
 * Users can execute command.
 * 
 * @author Filip
 */
public class GroupNameBuilder implements NameBuilder {

	private int index;

	/**
	 * Constructor with given index.
	 * 
	 * @param index given index
	 */
	public GroupNameBuilder(int index) {
		this.index = index;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(result.group(index));
	}
}
