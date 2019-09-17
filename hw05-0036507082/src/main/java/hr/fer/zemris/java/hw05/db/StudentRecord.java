package hr.fer.zemris.java.hw05.db;

import java.util.Objects;

/**
 * Class StudentRecord defines data structure and a set of methods for working
 * with it.
 * 
 * Users can get jmbag, last name, first name and grade.
 * 
 * @author Filip
 */
public class StudentRecord {

	private String jmbag;
	private String lastName;
	private String firstName;
	private String finalGrade;

	/**
	 * Constructor. Initialize record with given parameters.
	 * 
	 * @param jmbag
	 * @param lastName
	 * @param firstName
	 * @param finalGrade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, String finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for jmbag.
	 * 
	 * @return
	 */
	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for lastName.
	 * 
	 * @return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for firstName.
	 * 
	 * @return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for finalGrade.
	 * 
	 * @return
	 */
	public String getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof StudentRecord))
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
}
