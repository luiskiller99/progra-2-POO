/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

import com.google.gson.Gson;

/**
 *
 * @author zEstebanCruz
 */
public class ConversorDeObjetos {

	private static final Gson G = new Gson();

	public static <T extends Object> T convertirAObjeto(String json, Class<T> T) {
		return G.fromJson(json, T);
	}

	public static <T extends Object> String convertirAJsonString(T T) {
		return G.toJson(T);
	}
}
