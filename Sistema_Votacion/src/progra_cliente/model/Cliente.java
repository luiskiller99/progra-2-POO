package progra_cliente.model;

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import progra_servidor.model.Candidato;

public class Cliente extends Thread {//ip publica   //ip local
	final String Host = "192.168.56.1";//"192.168.56.1";//"201.191.254.54";//"127.0.0.1";
	final int port = 9999;	
	Socket socket = null;         
        
        DataInputStream ind;
        DataOutputStream outd;
    
	public Cliente() {}
        @Override
	public void run() {                
            try {socket = new Socket(Host, port);                                                                           
            } catch (IOException e) {System.out.println("Cliente: error -> "+e.toString());}                                   
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
        public ArrayList<Candidato> leer_candidatos(){
           ArrayList<Candidato> candidatos = new ArrayList<>();           
           try {
               ind = new DataInputStream(socket.getInputStream());
               boolean t = true;
               while(t){
                    if(!ind.readBoolean())break;//si para de leer
                    ind = new DataInputStream(socket.getInputStream());                    
                    String nombre = ind.readUTF();//lee nombre
                    String partido = ind.readUTF();//lee partido
                    int cedula = ind.readInt();//lee cedula
                    candidatos.add(new Candidato(nombre,partido,cedula));                    
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
