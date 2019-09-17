package hr.fer.zemris.java.hw05.db;

/**
 * Interface IFieldValueGetter defines a single method which can be used for getting string
 * from student record.
 * 
 * @author Filip
 */
public interface IFieldValueGetter {

	/**
	 * This method is used for getting string from student record.
	 * 
	 * @param record given record
	 * @return
	 */
	public String get(StudentRecord record);
	
}
