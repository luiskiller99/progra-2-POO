/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zEstebanCruz
 */
public class SistemaServidor {

	private static Servidor servidor;
	private static ArrayList<Candidato> candidatos;
	private static Stv stv;

	public static void iniciarServidor() {
		servidor = new Servidor(5000);
	}

	public static boolean haySuficientesCandidatosRegistrados() {
		return candidatos.size() >= 5 * 2 + 1;
	}

	public static void aniadirCandidato(String nombre, String partido, int cedula) {
		candidatos.add(new Candidato(nombre, partido, cedula));
	}

	public static void guardarVoto(Voto voto) {
		try (FileWriter f = new FileWriter("votos.db", true)) {
			f.write('\n' + Encriptador.encrypt(ConversorDeObjetos.convertirAJsonString(voto)));
		} catch (IOException ex) {
		}
	}

	public static void inicializarConteoDeVotos() throws Exception {
		try (BufferedReader bf = new BufferedReader(new FileReader("votos.db"))) {
			ArrayList<Voto> votos = new ArrayList<>();
			String line;
			while (null != (line = bf.readLine()))
				votos.add(ConversorDeObjetos.convertirAObjeto(Encriptador.decrypt(line), Voto.class));
			stv = new Stv(votos, 5);
		} catch (IOException ex) {
		}
	}

	public static void cerrarServidor() {
		servidor.cerrar_connecion();
	}

	public static void mostrarResultadoDeLaVotacion() {

	}

}
