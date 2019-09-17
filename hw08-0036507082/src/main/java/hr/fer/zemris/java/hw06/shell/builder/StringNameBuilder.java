package hr.fer.zemris.java.hw06.shell.builder;

/**
 * Class StringNameBuilder is implementation of NameBuilder.
 * 
 * Users can execute command.
 * 
 * @author Filip
 */
public class StringNameBuilder implements NameBuilder {

	private String text;

	/**
	 * Constructor with given text.
	 * 
	 * @param text given text
	 */
	public StringNameBuilder(String text) {
		this.text = text;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		sb.append(text);
	}

}
