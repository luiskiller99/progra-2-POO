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

        private Socket socket = null;        
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
