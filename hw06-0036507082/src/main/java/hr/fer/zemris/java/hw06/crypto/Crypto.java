package hr.fer.zemris.java.hw06.crypto;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.MessageDigest;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class Crypto is crypting class.
 * 
 * Allows the user to encrypt/decrypt given file using the AES cryptoalgorithm
 * and the 128-bit encryption key or calculate and check the SHA-256 file
 * digest.
 * 
 * @author Filip
 */
public class Crypto {

	/**
	 * This is main method.
	 * 
	 * @param args given arguments
	 */
	public static void main(String[] args) {

		try (Scanner scanner = new Scanner(System.in)) {
			if (args.length == 2) {

				String task = args[0];

				if (!task.equals("checksha")) {
					System.out.println("Illegal arguments.");
					System.exit(1);
				}

				Path path = Paths.get(args[1]);

				System.out.printf("Please provide expected sha-256 digest for %s:%n> ", args[1]);

				String givenDigest = scanner.nextLine();

				sha(path, args, givenDigest);

			}

			else if (args.length == 3) {

				String task = args[0];

				if (!task.equals("encrypt") && !task.equals("decrypt")) {
					System.out.println("Illegal arguments.");
					System.exit(1);
				}
				boolean encrypt = task.equals("encrypt");

				System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):%n> ");
				String keyText = scanner.nextLine();
				System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits):%n> ");
				String ivText = scanner.nextLine();

				encryptDerypt(encrypt, keyText, ivText, args);
			}

			else {
				System.out.println("Wrong number of arguments.");
				System.exit(1);
			}
		} catch (Exception exc) {
			System.out.println("Error.");
			System.exit(1);
		}
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
	private static void sha(Path path, String[] args, String givenDigest) {
		MessageDigest messageDigest;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");

			byte[] buff = new byte[4096];

			try (InputStream is = Files.newInputStream(path, StandardOpenOption.READ)) {
				while (true) {
					int r = is.read(buff);
					if (r < 1)
						break;
					messageDigest.update(buff, 0, r);
				}
				byte[] dig = messageDigest.digest();
				String digest = Util.bytetohex(dig);

				if (digest.equals(givenDigest)) {
					System.out.println("Digesting completed. Digest of " + args[1] + " matches expected digest.");
				} else {
					System.out.println("Digesting completed. Digest of " + args[1]
							+ " does not match the expected digest. Digest was: " + digest);
				}
			}
		} catch (Exception e) {
			System.out.println("Error.");
			System.exit(1);
		}
	}

	/**
	 * This is method for encrypting and decrypting files.
	 * 
	 * @param encrypt
	 * @param keyText
	 * @param ivText
	 * @param args
	 */
	private static void encryptDerypt(boolean encrypt, String keyText, String ivText, String[] args) {
		try {
			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);

			InputStream is = Files.newInputStream(Paths.get(args[1]), StandardOpenOption.READ);
			OutputStream os = Files.newOutputStream(Paths.get(args[2]), StandardOpenOption.CREATE);
			byte[] buff = new byte[4096];

			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(cipher.update(buff, 0, r));
			}
			os.write(cipher.doFinal());

			if (encrypt) {
				System.out.print("Encryption completed. ");
			} else {
				System.out.print("Decryption completed. ");
			}
			System.out.println("Generated file " + args[2] + " based on file " + args[1] + ".");
			is.close();
			os.close();

		} catch (Exception exc) {
			System.out.println("Error");
			System.exit(1);
		}
	}

}
