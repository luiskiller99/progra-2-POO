package progra_servidor.model;
import java.net.*;
import java.io.*;
import java.util.ArrayList;
import progra_cliente.model.Renglon_votacion;
public class Servidor extends Thread {	
	private ServerSocket server;
        private Socket socket = null;
        private ObjectOutputStream out;
        private ObjectInputStream in;
        
        private DataOutputStream outd;
        private DataInputStream ind;
        
        private final int puerto = 9999;
	public Servidor() {}
        @Override
	public void run() {           
            try{
                server = new ServerSocket(puerto);                
                System.out.println("Server: Esperando Conexion");            
                socket = server.accept();
                System.out.println("Server: Conectado");                                                                                                                                                            
            }catch (IOException e){System.out.println("Server: Error conexión");}                
        } 
        //terminada
        public void enviar_candidatos(ArrayList<String> v){            
            try{                                
                for(int i=0;i<v.size();i++){
                    outd = new DataOutputStream(socket.getOutputStream());                    
                    outd.writeBoolean(true);                    
                    outd.writeUTF(v.get(i));                     
                    System.out.println("Server: envio candidato");
                }
                outd.writeBoolean(false);
            } catch (IOException e){System.out.println("error envio");}
        }
        public ArrayList<Renglon_votacion> leer_votos(){
           ArrayList<Renglon_votacion> votos = new ArrayList<>();           
           try {
               ind = new DataInputStream(socket.getInputStream());
               boolean t=true;               
               while(t){
                    if(!ind.readBoolean())break;
                    ind = new DataInputStream(socket.getInputStream());
                    String candidato = ind.readUTF();            
                    int pos  = ind.readInt();
                    votos.add(new Renglon_votacion(candidato,pos));                    
               }
           } catch (IOException e) {System.out.println("error conectando para leer");}
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
