package hr.fer.zemris.java.hw06.shell.builder;

/**
 * Interface NameBuilder defines builder and single method for executing.
 * 
 * @author Filip
 */
public interface NameBuilder {

	/**
	 * This is method for executing given parameters.
	 * 
	 * @param result given filter result
	 * @param sb     given string builder
	 */
	void execute(FilterResult result, StringBuilder sb);

}
