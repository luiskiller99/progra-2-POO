package progra_servidor.model;

import com.google.gson.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author zEstebanCruz
 */
public class ObjectLoaderAndSaver {

	private static final Gson G = new Gson();

	private ObjectLoaderAndSaver() {
	}

	public static <T extends Object> T cargarObjeto(String filePath, Class<T> T) {
		try {
			return G.fromJson(new FileReader(filePath), T);
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	public static <T extends Object> void guardarObjeto(String filePath, Class<T> T) {
		try (FileWriter f = new FileWriter(filePath)) {
			f.write(G.toJson(T));
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
}
