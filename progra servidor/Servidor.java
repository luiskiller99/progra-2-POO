package sample;
import jdk.nashorn.internal.ir.WhileNode;

import java.net.*;
import java.io.*;
import java.util.ArrayList;


public class Servidor extends Thread{

    private String mensaje="";
    private ServerSocket server;
    private Socket socket = null;
    private DataOutputStream out;
    DataInputStream in;
    private int puerto = 5000;
    String Nombre_Cliente="";
    String en_orden="";
    Player JUGADOR= new Player("");

    public Servidor(int pue){ puerto=pue; }
    @Override
    public void run(){
        try{
            server = new ServerSocket(puerto);
            /**espera a que se conecte*/
            socket = server.accept();
            System.out.println("Conectado");
            /**leer mensaje*/
            in = new DataInputStream(socket.getInputStream());
            Nombre_Cliente = in.readUTF();
            System.out.println("Nombre de cliente: " +Nombre_Cliente);
            en_orden = in.readUTF();
            System.out.println("Quiere orden de entrada: " +en_orden);
            JUGADOR=new Player(Nombre_Cliente);
        }
        catch (IOException e){
            System.out.println("Error, no conectado");
        }
    }
    public void send_mesagge(String holi){
        try {
            mensaje = holi;
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(mensaje);
        }catch (IOException E){
            System.out.println("Error, reconectar");
        }
    }
    public void reconectar(){
        try{
            //server = new ServerSocket(puerto);
            /** espera a que se conecte*/
            System.out.println("Esperando conección de cliente");
            socket = server.accept();
            System.out.println("Conectado");
            /**leer mensaje*/
            in = new DataInputStream(socket.getInputStream());
            Nombre_Cliente = in.readUTF();
            System.out.println("Nombre de cliente: " +Nombre_Cliente);
            en_orden = in.readUTF();
            System.out.println("Quiere orden de entrada: " +en_orden);
        }
        catch (IOException e){
            System.out.println("Error, no conectado");
        }
    }
    public void cerrar_connecion(){
        try{
            socket.close();
            socket=null;
            System.out.println("Conección cerrada");
        }catch (IOException R){
            System.out.println("Error al cerrar conección");
        }
    }
    public Player manda_jugador(){ return JUGADOR; }
}
