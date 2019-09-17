package hr.fer.zemris.java.tecaj_13.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Class BlogUser defines data structure of one blog user.
 * 
 * @author Filip
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {

	private Long id;
	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;

	private List<BlogEntry> entries = new ArrayList<>();

	/**
	 * Getter for entries
	 * 
	 * @return entries
	 */
	@OneToMany(mappedBy = "blogUser")
	public List<BlogEntry> getEntries() {
		return entries;
	}

	/**
	 * Setter for entries
	 * 
	 * @param entries
	 */
	public void setEntries(List<BlogEntry> entries) {
		this.entries = entries;
	}

	/**
	 * Getter for id
	 * 
	 * @return id
	 */
	@Id
	@GeneratedValue
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
	@Column(length = 50, nullable = false)
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
	@Column(length = 50, nullable = false)
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
	@Column(length = 50, nullable = false, unique = true)
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
	@Column(length = 50, nullable = false)
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
	@Column(length = 50, nullable = false)
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BlogUser other = (BlogUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
