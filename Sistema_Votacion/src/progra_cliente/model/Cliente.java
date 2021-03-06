package progra_cliente.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import progra_servidor.model.Candidato;
import progra_servidor.model.ConversorDeObjetos;
import progra_servidor.model.Encriptador;
import progra_servidor.model.Voto;

public class Cliente {

	//server PC luiskiller99
	final String Host = "192.168.43.13";
	final int port = 9999;
	static Socket socket = null;
	DataInputStream ind;
	DataOutputStream outd;
	public static ArrayList<Candidato> candidatos;

	public Cliente() {

	}

	public void GuardadoEmergencia(Voto v) {
		try (FileWriter f = new FileWriter("BackupVotos.db", true)) {
			f.write(Encriptador.encrypt(ConversorDeObjetos.convertirAJsonString(v)) + '\n');
		} catch (IOException ex) {
			System.out.println("Error de guardado");
		}
	}

	public boolean ValidarCredenciales(int ced, int contrasenia) throws IOException {
		outd = new DataOutputStream(socket.getOutputStream());
		outd.writeInt(ced);
		ind = new DataInputStream(socket.getInputStream());
		boolean d = ind.readBoolean();
		if ((contrasenia == 1234) && (d == true)) {
			leer_candidatos();
			return true;
		} else
			return false;
	}

	public void run() {
		try {
			socket = new Socket(Host, port);
		} catch (IOException e) {
			System.out.println("Cliente: error -> " + e.toString());
		}
	}

	public void enviar_votos(Voto v) {
		try {
			if (v.size() < 1) {
				outd = new DataOutputStream(socket.getOutputStream());
				outd.writeBoolean(true);
				outd.writeInt(-1);
			} else
				for (int i = 0; i < v.size(); i++) {
					outd = new DataOutputStream(socket.getOutputStream());
					outd.writeBoolean(true);
					outd.writeInt(v.get(i));
					System.out.println("mando voto: " + v.get(i));
				}
			outd.writeBoolean(false);
		} catch (IOException e) {
			System.out.println("error enviar");
			GuardadoEmergencia(v);
		}
	}

	public void leer_candidatos() {
		ArrayList<Candidato> candidatos = new ArrayList<>();
		try {
			ind = new DataInputStream(socket.getInputStream());
			boolean t = true;
			while (t) {
				if (!ind.readBoolean())
					break;//si para de leer
				ind = new DataInputStream(socket.getInputStream());
				String nombre = ind.readUTF();//lee nombre
				System.out.println("nombre: " + nombre);
				String partido = ind.readUTF();//lee partido
				int cedula = ind.readInt();//lee cedula
				candidatos.add(new Candidato(nombre, partido, cedula));
			}
		} catch (IOException e) {
			System.out.println("error io");
		}
		this.candidatos = candidatos;
	}

	public void cerrar_conexion() {
		try {
			socket.close();
			System.out.println("Conección cerrada");
		} catch (IOException R) {
			System.out.println("Error al cerrar conección");
		}
	}

	public void enviarVotosGuardados() {
		try {
			String[] s = new Scanner(new File("BackupVotos.db"))
				.useDelimiter("\\A").next().split("\n");
			for (String i : s)
				enviar_votos(ConversorDeObjetos.convertirAObjeto(Encriptador.decrypt(i), Voto.class));
			new File("BackupVotos.db").delete();
		} catch (FileNotFoundException ex) {
		}
	}

}
