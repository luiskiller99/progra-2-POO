package progra_servidor.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import progra_cliente.model.Renglon_votacion;

public class Servidor extends Thread {

	private ServerSocket server;
	private Socket socket = null;
	private DataOutputStream outd;
	private DataInputStream ind;
	private final int puerto = 9999;

	public Servidor() {
	}

	@Override public void run() {
		try {
			server = new ServerSocket(puerto);
			while (true) {
				System.out.println("Server: Esperando Conexion");
				socket = server.accept();
				ind = new DataInputStream(socket.getInputStream());
				int ced = ind.readInt();
				boolean d = SistemaServidor.puedeVotar(ced);
				outd = new DataOutputStream(socket.getOutputStream());
				outd.writeBoolean(d);
				//outd.writeBoolean(false); 
				if (d) enviar_candidatos();
				System.out.println("Server: Cliente Conectado");
			}
		} catch (IOException e) {
			System.out.println("Server: Error conexión -> " + e.getMessage());
		}
	}

	public void enviar_candidatos() {
		try {
			for (int i = 0; i < SistemaServidor.candidatos.size(); i++) {
				outd = new DataOutputStream(socket.getOutputStream());
				outd.writeBoolean(true);//no permite que  ciclo en cliente termine
				outd.writeUTF(SistemaServidor.candidatos.get(i).getNombre());//envia nombre
				outd.writeUTF(SistemaServidor.candidatos.get(i).getPartido());//envia Partido
				outd.writeInt(SistemaServidor.candidatos.get(i).getCedula());//envia cedula
				System.out.println("Server: envio candidato");
			}
			outd.writeBoolean(false);//termina ciclo en cliente
		} catch (IOException e) {
			System.out.println("error envio");
		}
	}

	public Voto leer_votos() {
		Voto votos = new Voto();
		try {
			ind = new DataInputStream(socket.getInputStream());
			boolean t = true;
			while (t) {
				if (!ind.readBoolean()) break;
				ind = new DataInputStream(socket.getInputStream());
				int pos = ind.readInt();
				votos.add(pos);
			}
		} catch (IOException e) {
			System.out.println("error conectando para leer");
		}
		return votos;
	}

	public void cerrar_conección() {
		try {
			socket.close();
			System.out.println("Conección cerrada");
		} catch (IOException R) {
			System.out.println("Error al cerrar conección");
		}
	}
}
