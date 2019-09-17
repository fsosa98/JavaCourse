package hr.fer.zemris.java.hw05.db;

/**
 * Interface IFilter defines a single method which can be used for testing given
 * student record.
 * 
 * @author Filip
 */
public interface IFilter {

	/**
	 * This method is used for testing given StudentRecord.
	 * 
	 * @param record given StudentRecord
	 * @return
	 */
	public boolean accepts(StudentRecord record);
	
}
