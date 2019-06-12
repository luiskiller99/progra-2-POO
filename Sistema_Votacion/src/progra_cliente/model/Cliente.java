package progra_cliente.model;

//import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente extends Thread {

	final String Host = "127.0.0.1";
	final int port = 5000;
	//DataInputStream in;
	//DataOutputStream out;
	//String Nombre = "";
	//int orden = 0;
	//Socket socket = null;
        //String carta_ret;

	public Cliente() {}
        @Override
	public void run() {
            while(true){
                leer_candidatos();
            }
        }
        /*
	@Override
	public void run() {
            while(true){
            
		try {
			socket = new Socket(Host, port);
			out = new DataOutputStream(socket.getOutputStream());
			enviar_mensaje(Nombre);
			
			// envia nombre
			 
			if (orden == 0)
				enviar_mensaje("si");
			
			 //si quiere el orde especifico
			
			else
				enviar_mensaje("no");
		} catch (ConnectException e) {
			System.out.println("error conección");
		} catch (IOException e) {
			System.out.println("error");
		}
		leer_mensaje();
            }
	}
        /*
	public void inicializar(String nombre, int ord) {
		Nombre = nombre;
		orden = ord;
	}

	public void leer_mensaje() {
		try {
			in = new DataInputStream(socket.getInputStream());
			while (true) {
				String mensaje = in.readUTF();
				carta_ret = mensaje;
				System.out.println(mensaje);
				if (mensaje.contains("gano")) {
					JOptionPane.showMessageDialog(null, Nombre + ", Has ganado el juego");
					System.out.println("gano, cerró conección");
					break;
				} else if (mensaje.contains("perdio")) {
					JOptionPane.showMessageDialog(null, Nombre + ", Has perdido el juego");
					System.out.println("perdio, cerró conección");
					socket.close();
					break;
				}
			}
		} catch (IOException e) {
			System.out.println("error al leer");
		}
	}

	public void enviar_mensaje(String msj) {
		try {
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(msj);
		} catch (IOException E) {
			System.out.println("error envio");
		}
	}

	public String optener_carta() {
		return carta_ret;
	}
        */
        
        //codigo cliente
        public void enviar_votos(ArrayList<Renglon_votacion> v){
            try{
                ServerSocket myServerSocket = new ServerSocket(port);
                Socket skt = myServerSocket.accept();                   
                try{
                    ObjectOutputStream objectOutput = new ObjectOutputStream(skt.getOutputStream());
                    objectOutput.writeObject(v);               
                } catch (IOException e){e.printStackTrace();} 
            } catch (IOException e){e.printStackTrace();}
        }
        public ArrayList<Renglon_votacion> leer_candidatos(){
           ArrayList<Renglon_votacion> titleList = new ArrayList<Renglon_votacion>();
           try {       
                Socket socket = new Socket(Host,port);
                try {
                    ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream()); //Error Line!
                    try {
                        Object object = objectInput.readObject();
                        titleList =  (ArrayList<Renglon_votacion>) object;
                        System.out.println(titleList.get(1).Nombre_de_candidato);
                    } catch (ClassNotFoundException e) {
                        System.out.println("The title list has not come from the server");
                        e.printStackTrace();
                    }
                } catch (IOException e) {
                    System.out.println("The socket for reading the object has problem");
                    e.printStackTrace();
                }           
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
           return titleList;
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
