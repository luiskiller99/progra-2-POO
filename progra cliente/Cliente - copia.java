package sample;


import javax.swing.*;
import java.net.*;
import java.io.*;

public class Cliente extends Thread{
    final String Host = "127.0.0.1";
    final int port = 5002;
    DataInputStream in;
    DataOutputStream out;
    String Nombre ="";
    int orden=0;
    Socket socket=null;
    String carta_ret;


    public Cliente(){}
    @Override
    public void run(){
        try{
            socket = new Socket(Host, port);
            out = new DataOutputStream(socket.getOutputStream());
            enviar_mensaje(Nombre);/**envia nombre*/
            if(orden==0)
                enviar_mensaje("si");/**si quiere el orde especifico*/
            else
                enviar_mensaje("no");
        }
        catch (ConnectException e){
            System.out.println("error conección");
        }
        catch (IOException e){
            System.out.println("error");
        }
        leer_mensaje();
    }
    public void inicializar(String nombre, int ord){Nombre=nombre;orden=ord;}
    public void leer_mensaje(){
        try {
            in = new DataInputStream(socket.getInputStream());
            while (true) {
                String mensaje = in.readUTF();
                carta_ret=mensaje;
                System.out.println(mensaje);
                if (mensaje.contains("gano") ){
                    JOptionPane.showMessageDialog(null,Nombre+", Has ganado el juego");
                    System.out.println("gano, cerró conección");
                    break;
                }
                else if (mensaje.contains("perdio")) {
                    JOptionPane.showMessageDialog(null,Nombre+", Has perdido el juego");
                    System.out.println("perdio, cerró conección");
                    socket.close();
                    break;
                }
            }
        }catch (IOException e){
            System.out.println("error al leer");
        }
    }
    public void enviar_mensaje(String msj){
        try {
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(msj);
        }
        catch (IOException E){System.out.println("error envio");}
    }
    public void cerrar_conección(){
        try{
            socket.close();
            System.out.println("Conección cerrada");
        }catch (IOException R){
            System.out.println("Error al cerrar conección");
        }
    }
    public String optener_carta(){ return carta_ret; }
}

