package hr.fer.zemris.java.tecaj_13;

/**
 * Class Record defines data structure of one record.
 * 
 * @author Filip
 */
public class Record implements Comparable<Record> {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;

	/**
	 * Constructor with given parameters.
	 * 
	 * @param id
	 * @param firstName
	 * @param lastName
	 * @param nick
	 * @param email
	 * @param passwordHash
	 */
	public Record(Long id, String firstName, String lastName, String nick, String email, String passwordHash) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.nick = nick;
		this.email = email;
		this.passwordHash = passwordHash;
	}

	/**
	 * Constructor
	 */
	public Record() {
	}

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Setter for id
	 * 
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Getter for firstName
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Setter for firstName
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Getter for lastName
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Setter for lastName
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Getter for nick
	 * 
	 * @return nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * Setter for nick
	 * 
	 * @param nick
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * Getter for email
	 * 
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Setter for email
	 * 
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Getter for passwordHash
	 * 
	 * @return passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}

	/**
	 * Setter for passwordHash
	 * 
	 * @param passwordHash
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	@Override
	public int compareTo(Record o) {
		if (this.id == null) {
			if (o.id == null)
				return 0;
			return -1;
		} else if (o.id == null) {
			return 1;
		}
		return this.id.compareTo(o.id);
	}

}
