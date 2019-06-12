package progra_cliente.model;

//import javax.swing.*;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente extends Thread {
	final String Host = "127.0.0.1";
	final int port = 5000;	
	Socket socket = null;        
	public Cliente() {}
        @Override
	public void run() {            
                try {socket = new Socket(Host, port);                
                } catch (ConnectException e) {System.out.println("error conección");
		} catch (IOException e) {System.out.println("error");}
                leer_candidatos();          
        }                        
        public void enviar_votos(ArrayList<Renglon_votacion> v){            
            try{
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                objectOutput.writeObject(v); 
            } catch (IOException e){e.printStackTrace();}
        }
        public ArrayList<Renglon_votacion> leer_candidatos(){
           ArrayList<Renglon_votacion> titleList = new ArrayList<Renglon_votacion>();
           try {
               while(true){
                    ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
                    try {
                        Object object = objectInput.readObject();
                        titleList =  (ArrayList<Renglon_votacion>) object;
                        System.out.println(titleList.get(0).Nombre_de_candidato);
                    } catch (ClassNotFoundException e) {
                        System.out.println("The title list has not come from the server");
                        e.printStackTrace();
                    }
               }
           } catch (IOException e) {
               System.out.println("The socket for reading the object has problem");
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
