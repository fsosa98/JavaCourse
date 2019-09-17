package hr.fer.zemris.java.hw05.db;

/**
 * Class FieldValueGetters represents field value getters.
 * 
 * @author Filip
 */
public class FieldValueGetters {

	/**
	 * FIRST_NAME
	 */
	public static final IFieldValueGetter FIRST_NAME = s -> s.getFirstName();
	
	/**
	 * LAST_NAME
	 */
	public static final IFieldValueGetter LAST_NAME = s -> s.getLastName();
	
	/**
	 * JMBAG
	 */
	public static final IFieldValueGetter JMBAG = s -> s.getJmbag();
	
}
