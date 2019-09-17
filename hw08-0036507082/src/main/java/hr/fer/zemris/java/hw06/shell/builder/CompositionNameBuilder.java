package hr.fer.zemris.java.hw06.shell.builder;

import java.util.List;

/**
 * Class CompositionNameBuilder is implementation of NameBuilder.
 * 
 * Users can execute, get list of builders.
 * 
 * @author Filip
 */
public class CompositionNameBuilder implements NameBuilder {

	private List<NameBuilder> builders;

	/**
	 * Constructor with given list.
	 * 
	 * @param builders
	 */
	public CompositionNameBuilder(List<NameBuilder> builders) {
		this.builders = builders;
	}

	@Override
	public void execute(FilterResult result, StringBuilder sb) {
		builders.forEach((builder) -> builder.execute(result, sb));
	}

	/**
	 * Getter for list of builders.
	 * 
	 * @return list
	 */
	public List<NameBuilder> get() {
		return builders;
	}

}
