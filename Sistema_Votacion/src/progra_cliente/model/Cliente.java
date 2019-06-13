package progra_cliente.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Cliente extends Thread {
	final String Host = "127.0.0.1";
	final int port = 9999;	
	Socket socket = null;  
        ObjectInputStream in;
        ObjectOutputStream out;
        
        DataInputStream ind;
        DataOutputStream outd;
    
	public Cliente() {}
        @Override
	public void run() {                
            try {
                socket = new Socket(Host, port);                                                                           
            } catch (IOException e) {System.out.println("Cliente error io");}                                   
        }                        
        public void enviar_votos(ArrayList<Renglon_votacion> v){            
            try{
                //primero nombres
                for(int i=0;i<v.size();i++){
                    outd = new DataOutputStream(socket.getOutputStream());                    
                    outd.writeBoolean(true);                    
                    outd.writeUTF(v.get(i).getNombre_de_candidato());                                                            
                    outd = new DataOutputStream(socket.getOutputStream());                                        
                    outd.writeInt(v.get(i).getPos());                                         
                }   
                outd.writeBoolean(false);
            } catch (IOException e){System.out.println("error enviar");;}
        }
        public ArrayList<String> leer_candidatos(){
           ArrayList<String> candidatos = new ArrayList<String>();           
           try {
               ind = new DataInputStream(socket.getInputStream());
               boolean t = true;
               while(t){
                    if(!ind.readBoolean())break;
                    ind = new DataInputStream(socket.getInputStream());                    
                    String candidato = ind.readUTF();                         
                    candidatos.add(candidato);                    
                }   
           } catch (IOException e) {System.out.println("error io");}
           return candidatos;
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
