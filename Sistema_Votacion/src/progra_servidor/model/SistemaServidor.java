/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import progra_servidor.controller.ControladorDeGrafico;

/**
 *
 * @author zEstebanCruz
 */
public class SistemaServidor {

	private static Servidor servidor;
	private static ArrayList<Candidato> candidatos = new ArrayList<>();
	private static Stv stv;
	private static ArrayList<Integer> personasQueYaVotaron = new ArrayList<>();

	public static void iniciarServidor() {
		servidor = new Servidor(5000);
	}

	public static boolean haySuficientesCandidatosRegistrados() {
		return candidatos.size() >= 5 * 2 + 1;
	}

	public static void aniadirCandidato(String nombre, String partido, int cedula) throws Exception {
		if (candidatos.stream().anyMatch((candidato) -> candidato.getCedula() == cedula))
			throw new Exception("Ya existe un candidato con ese número de cédula.");
		else
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

	public static void mostrarResultadoDeLaVotacion(String tipoGrafico) {
		stv.contarTodosLosVotos();
		ControladorDeGrafico graficador = new ControladorDeGrafico(stv.votosPorCandidato, "Candidatos");
		graficador.mostrarGrafico("Resultado de votacion", "Votos por candidato", tipoGrafico);
		Timer t = new Timer();
		t.schedule(
			new java.util.TimerTask() {
			@Override
			public void run() {
				actualizarGrafico(graficador, false);
				t.cancel();
			}
		},
			5000
		);
	}

	private static void actualizarGrafico(ControladorDeGrafico grafico, boolean flag) {
		if (stv.plazasLlenadas())
			JOptionPane.showMessageDialog(grafico.getV().getChartPanel(),
																		"Se ha terminado de evaluar los votos.",
																		"Resultado de votación", JOptionPane.INFORMATION_MESSAGE);
		else {
			if (flag)
				stv.eliminarPartidoConMenosVotos();
			else
				stv.eliminarPartidoConMenosVotos();
			grafico.actualizarValores(stv.votosPorCandidato);
			Timer t = new java.util.Timer();
			t.schedule(
				new java.util.TimerTask() {
				@Override
				public void run() {
					actualizarGrafico(grafico, flag != true);
					t.cancel();
				}
			},
				5000
			);
		}
	}

	public static boolean puedeVotar(int cedula) {
		if (personasQueYaVotaron.contains(cedula))
			return false;
		try (RandomAccessFile raf = new RandomAccessFile(new File(ClassLoader.getSystemClassLoader().getResource("progra_servidor/model/CA.txt").toURI()), "r")) {
			int largoDeLinea = 121,
				inicio = 0,
				fin = (int) (raf.length() / largoDeLinea),
				mitad,
				resultadoComparacion;
			byte[] lineBuffer = new byte[largoDeLinea];
			while (inicio <= fin) {
				mitad = (inicio + fin) / 2;
				raf.seek(mitad * largoDeLinea); // jump to this line in the file
				raf.read(lineBuffer); // read the line from the file
				String line = new String(lineBuffer); // convert the line to a String

				resultadoComparacion = Integer.parseInt(line.split(",")[0]) - cedula;
				if (resultadoComparacion == 0)
					return true;
				else if (resultadoComparacion < 0)
					inicio = mitad + 1;
				else
					fin = mitad - 1;
			}
		} catch (IOException ex) {
		} catch (URISyntaxException ex) {
		}
		return false;
	}

	public static void main(String[] args) {
		puedeVotar(109720432);
	}

}
