package progra_servidor.model;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import com.sun.mail.util.BASE64DecoderStream;
import com.sun.mail.util.BASE64EncoderStream;
import java.io.UnsupportedEncodingException;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

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
		key = ObjectLoaderAndSaver.cargarObjeto("key.sk", SecretKey.class);
		if (key == null)
			key = KeyGenerator.getInstance("DES").generateKey();
	}

	public static String encrypt(String str) {
		try {
			return new String(BASE64EncoderStream.encode(ecipher.doFinal(str.getBytes("UTF8"))));
		} catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}

	public static String decrypt(String str) {
		try {
			return new String(dcipher.doFinal(BASE64DecoderStream.decode(str.getBytes())), "UTF8");
		} catch (UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return null;
	}
}
