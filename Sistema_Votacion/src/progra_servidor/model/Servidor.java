package progra_servidor.model;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import progra_cliente.model.Renglon_votacion;
public class Servidor extends Thread {	
	private ServerSocket server;	
        private Socket socket;
	private int port = 5000;
	public Servidor() {}
        @Override
	public void run() {            
                try {
                    server = new ServerSocket(port);
                    socket = server.accept();
                } catch (ConnectException e) {System.out.println("error conección");
		} catch (IOException e) {System.out.println("error");}
                //leer_votos();          
                ArrayList<Renglon_votacion> v = new ArrayList<Renglon_votacion>();
                v.add(new Renglon_votacion("luis",3));
                enviar_candidatos(v);
        }                        
        public void enviar_candidatos(ArrayList<Renglon_votacion> v){            
            try{
                ObjectOutputStream objectOutput = new ObjectOutputStream(socket.getOutputStream());
                objectOutput.writeObject(v); 
                System.out.println("envio datos con exito");
            } catch (IOException e){e.printStackTrace();}
        }
        public ArrayList<Renglon_votacion> leer_votos(){
           ArrayList<Renglon_votacion> titleList = new ArrayList<Renglon_votacion>();
           try {
               while(true){
                    ObjectInputStream objectInput = new ObjectInputStream(socket.getInputStream());
                    try {
                        Object object = objectInput.readObject();
                        titleList =  (ArrayList<Renglon_votacion>) object;
                        System.out.println(titleList.get(0).getNombre_de_candidato());
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
