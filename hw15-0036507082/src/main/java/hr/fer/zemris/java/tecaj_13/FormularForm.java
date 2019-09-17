package hr.fer.zemris.java.tecaj_13;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.tecaj_13.model.BlogUser;

/**
 * Class FormularForm defines data structure of formular.
 * 
 * @author Filip
 */
public class FormularForm {

	private String firstName;
	private String lastName;
	private String nick;
	private String email;
	private String passwordHash;

	Map<String, String> errors = new HashMap<>();

	/**
	 * Constructor
	 */
	public FormularForm() {
	}

	/**
	 * Getter for error
	 * 
	 * @param name
	 * @return
	 */
	public String getError(String name) {
		return errors.get(name);
	}

	/**
	 * Setter for error
	 * 
	 * @param name
	 * @param text
	 */
	public void setError(String name, String text) {
		errors.put(name, text);
	}

	/**
	 * Checks if contains error
	 * 
	 * @return
	 */
	public boolean containsError() {
		return !errors.isEmpty();
	}

	/**
	 * Provjerava ima li traženo svojstvo pridruženu pogrešku.
	 * 
	 * @param ime naziv svojstva za koje se ispituje postojanje pogreške
	 * @return <code>true</code> ako ima, <code>false</code> inače.
	 */
	public boolean containsError(String name) {
		return errors.containsKey(name);
	}

	/**
	 * Na temelju parametara primljenih kroz {@link HttpServletRequest} popunjava
	 * svojstva ovog formulara.
	 * 
	 * @param req objekt s parametrima
	 */
	public void fillFromHttpRequesta(HttpServletRequest req) {
		this.firstName = pripremi(req.getParameter("firstName"));
		this.lastName = pripremi(req.getParameter("lastName"));
		this.nick = pripremi(req.getParameter("nick"));
		this.email = pripremi(req.getParameter("email"));
		this.passwordHash = sha(pripremi(req.getParameter("passw")));
	}

	/**
	 * Na temelju predanog {@link Record}-a popunjava ovaj formular.
	 * 
	 * @param r objekt koji čuva originalne podatke
	 */
	public void fillFromBlogUser(BlogUser r) {
		this.firstName = r.getFirstName();
		this.lastName = r.getLastName();
		this.nick = r.getNick();
		this.email = r.getEmail();
		this.passwordHash = r.getPasswordHash();
	}

	/**
	 * Temeljem sadržaja ovog formulara puni svojstva predanog domenskog objekta.
	 * Metodu ne bi trebalo pozivati ako formular prethodno nije validiran i ako
	 * nije utvrđeno da nema pogrešaka.
	 * 
	 * @param r domenski objekt koji treba napuniti
	 */
	public void fillInBlogUser(BlogUser r) {
		r.setFirstName(this.firstName);
		r.setLastName(this.lastName);
		r.setNick(this.nick);
		r.setEmail(this.email);
		r.setPasswordHash(this.passwordHash);
	}

	/**
	 * Metoda obavlja validaciju formulara. Formular je prethodno na neki način
	 * potrebno napuniti. Metoda provjerava semantičku korektnost svih podataka te
	 * po potrebi registrira pogreške u mapu pogrešaka.
	 */
	public void validate() {
		errors.clear();

		if (this.firstName.isEmpty()) {
			errors.put("firstName", "Ime je obavezno!");
		} else if (this.firstName.length() > 50) {
			errors.put("firstName", "Ime ne smije biti dulje od 50.");
		}

		if (this.lastName.isEmpty()) {
			errors.put("lastName", "Prezime je obavezno!");
		} else if (this.lastName.length() > 50) {
			errors.put("lastName", "Prezime ne smije biti dulje od 50.");
		}

		if (this.nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan!");
		} else if (this.nick.length() > 50) {
			errors.put("nick", "Nadimak ne smije biti dulje od 50.");
		}

		if (this.email.isEmpty()) {
			errors.put("email", "EMail je obavezan!");
		} else {
			int l = email.length();
			int p = email.indexOf('@');
			if (l < 3 || p == -1 || p == 0 || p == l - 1) {
				errors.put("email", "EMail nije ispravnog formata.");
			}
		}

		if (this.passwordHash.isEmpty()) {
			errors.put("passw", "Lozinka je obavezna!");
		} else if (this.nick.length() > 50) {
			errors.put("passw", "Lozinka ne smije biti dulje od 50.");
		}
	}

	/**
	 * Pomoćna metoda koja <code>null</code> stringove konvertira u prazne
	 * stringove, što je puno pogodnije za uporabu na webu.
	 * 
	 * @param s string
	 * @return primljeni string ako je različit od <code>null</code>, prazan string
	 *         inače.
	 */
	private String pripremi(String s) {
		if (s == null)
			return "";
		return s.trim();
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

	/**
	 * This is method for checking sha-256 file digest. Message digest is a
	 * fixed-size binary digest which is calculated from arbitrary long data.
	 * Digests are used to verify if the data you have received (for example, when
	 * downloading the data from the Internet) arrived unchanged
	 * 
	 * @param path
	 * @param args
	 * @param givenDigest
	 */
	public static String sha(String givenDigest) {
		MessageDigest messageDigest;
		String digest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-1");

			byte[] buff = givenDigest.getBytes();
			messageDigest.update(buff, 0, buff.length);

			byte[] dig = messageDigest.digest();
			digest = bytetohex(dig);

		} catch (Exception e) {
			System.out.println("Error.");
			System.exit(1);
		}
		return digest;
	}

	/**
	 * This method convert byte array to string.
	 * 
	 * @param array given byte array
	 * @return converted string
	 */
	public static String bytetohex(byte[] array) {
		Objects.requireNonNull(array);
		StringBuilder sb = new StringBuilder();
		for (byte b : array) {
			sb.append(String.format("%02x", b));
		}
		return sb.toString();
	}

}
