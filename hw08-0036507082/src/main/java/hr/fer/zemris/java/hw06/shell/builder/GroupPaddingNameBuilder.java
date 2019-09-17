package hr.fer.zemris.java.hw06.shell.builder;

/**
 * Class GroupPaddingNameBuilder is implementation of NameBuilder.
 * 
 * Users can execute command.
 * 
 * @author Filip
 */
public class GroupPaddingNameBuilder implements NameBuilder {

	private int index;
	private char padding;
	private int minWidth;

	/**
	 * Constructor with given index, padding and min width.
	 * 
	 * @param index    given index
	 * @param padding  given padding
	 * @param minWidth given width
	 */
	public GroupPaddingNameBuilder(int index, char padding, int minWidth) {
		this.index = index;
		this.padding = padding;
		this.minWidth = minWidth;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		for (int i = 0; i < minWidth - 1; i++) {
			sb.append(padding);
		}
		sb.append(result.group(index));
	}
}
