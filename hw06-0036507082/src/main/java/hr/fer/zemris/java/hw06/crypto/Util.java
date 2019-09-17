package hr.fer.zemris.java.hw06.crypto;

import java.util.Objects;

/**
 * Class Util is used for base converting.
 * 
 * Users can convert from string to byte array or from byte array to string.
 * 
 * @author Filip
 */
public class Util {

	/**
	 * This method convert string to byte array.
	 * 
	 * @param keyText given string
	 * @return byte array
	 */
	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText);
		if (keyText.length() == 0) {
			return new byte[0];
		}
		int n = keyText.length();
		if (n % 2 == 1) {
			throw new IllegalArgumentException("KeyText has odd length.");
		}
		String valid = new String("0123456789abcdef");

		byte[] data = new byte[n / 2];
		for (int i = 0; i < n; i += 2) {
			if (valid.indexOf(keyText.toLowerCase().charAt(i)) == -1
					|| valid.indexOf(keyText.toLowerCase().charAt(i + 1)) == -1) {
				throw new IllegalArgumentException("Illegal character.");
			}
			data[i / 2] = (byte) ((Character.digit(keyText.charAt(i), 16) << 4)
					+ Character.digit(keyText.charAt(i + 1), 16));
		}
		return data;
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
