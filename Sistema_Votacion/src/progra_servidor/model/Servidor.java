package progra_servidor.model;

import java.net.*;
import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

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
				if (d) {
					enviar_candidatos();
					System.out.println("Server: Cliente Conectado");
					new Thread(() -> leer_votos(socket)).start();
				} else System.out.println("Un mae mamó la contraseña");
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

	public Voto leer_votos(final Socket s) {
		System.out.println("Escuchando Votos!");
		Voto votos;
		Timer t = new Timer();
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				Thread.currentThread().interrupt();
				t.cancel();
			}
		}, 2500);
		try {
			ind = new DataInputStream(s.getInputStream());
			while (true) {
				votos = new Voto();
				while (true) {
					System.out.println("Esperando que alguien mande un true");
					if (!ind.readBoolean()) break;
					System.out.println("Recibió un voto!");
					ind = new DataInputStream(s.getInputStream());
					int pos = ind.readInt();
					if (pos != -1)
						votos.add(pos);
				}
				SistemaServidor.guardarVoto(votos);
			}
		} catch (IOException e) {
			System.out.println("error conectando para leer");
		}
		return null;
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
