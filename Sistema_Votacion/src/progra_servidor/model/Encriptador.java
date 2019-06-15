package progra_servidor.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;

public class Encriptador {

	private static Cipher ecipher;
	private static Cipher dcipher;
	private static SecretKey key;

	public static boolean isInitialized() {
		return ecipher != null && dcipher != null && key != null;
	}

	public static void initialize() {
		try {
			// generate secret key using DES algorithm
			initKey();
			ecipher = Cipher.getInstance("DES");
			dcipher = Cipher.getInstance("DES");
			// initialize the ciphers with the given key
			ecipher.init(Cipher.ENCRYPT_MODE, key);
			dcipher.init(Cipher.DECRYPT_MODE, key);
		} catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	private static void initKey() throws NoSuchAlgorithmException {
		try {
			String input = new Scanner(new File("key.sk")).useDelimiter("\\A").next();
			if ("".equals(input)) throw new IOException("Llave vacía.");
			byte[] encoded = Base64.getDecoder().decode(input);
			key = new SecretKeySpec(encoded, "DES");
		} catch (IOException ex) {
			key = KeyGenerator.getInstance("DES").generateKey();
			try (FileWriter f = new FileWriter("key.sk")) {
				f.write(Base64.getEncoder().withoutPadding().encodeToString(key.getEncoded()));
				f.close();
			} catch (Exception ex1) {
			}
		}
	}

	public static String encrypt(String str) {
		try {
			return new String(BASE64EncoderStream.encode(ecipher.doFinal(str.getBytes("UTF8"))));
		} catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (NullPointerException e) {
			initialize();
			return encrypt(str);
		}
		return null;
	}

	public static String decrypt(String str) {
		try {
			return new String(dcipher.doFinal(BASE64DecoderStream.decode(str.getBytes())), "UTF8");
		} catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
			System.out.println("Error: " + e.getMessage());
		} catch (NullPointerException e) {
			initialize();
			return decrypt(str);
		}
		return null;
	}

}
