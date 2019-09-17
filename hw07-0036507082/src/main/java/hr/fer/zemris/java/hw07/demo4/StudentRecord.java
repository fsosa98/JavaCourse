package hr.fer.zemris.java.hw07.demo4;

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
	private double pointsMI;
	private double pointsZI;
	private double pointsLab;
	private int finalGrade;

	/**
	 * Constructor. Initialize record with given parameters.
	 * 
	 * @param jmbag      given jmbag
	 * @param lastName   given last name
	 * @param firstName  given first name
	 * @param mi         given pointsMI
	 * @param zi         given pointsZI
	 * @param lab        given pointsLab
	 * @param finalGrade given final grade
	 */
	public StudentRecord(String jmbag, String lastName, String firstName, double pointsMI, double pointsZI,
			double pointsLab, int finalGrade) {
		this.jmbag = jmbag;
		this.lastName = lastName;
		this.firstName = firstName;
		this.pointsMI = pointsMI;
		this.pointsZI = pointsZI;
		this.pointsLab = pointsLab;
		this.finalGrade = finalGrade;
	}

	/**
	 * Getter for pointsMI.
	 * 
	 * @return
	 */
	public double getpointsMi() {
		return pointsMI;
	}

	/**
	 * Getter for pointsZI.
	 * 
	 * @return
	 */
	public double getpointsZi() {
		return pointsZI;
	}

	/**
	 * Getter for pointsLab.
	 * 
	 * @return
	 */
	public double getpointsLab() {
		return pointsLab;
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
	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public String toString() {
		return jmbag + "\t" + lastName + "\t" + firstName + "\t" + pointsMI + "\t" + pointsZI + "\t" + pointsLab + "\t"
				+ finalGrade;
	}

}
