/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import progra_cliente.model.Cliente;
import progra_cliente.model.Renglon_votacion;
import progra_servidor.model.Candidato;
import progra_servidor.model.Servidor;

/**
 *
 * @author metal
 */
public class prueba_sockets extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnc = new Button();
        btnc.setText("cliente conectar"); 
        btnc.setTranslateX(0);
        btnc.setTranslateY(-150);        
        
        Button btnc1 = new Button();
        btnc1.setText("cliente resivir candidatos"); 
        btnc1.setTranslateX(0);
        btnc1.setTranslateY(-100);        
        
        Button btnc2 = new Button();
        btnc2.setText("cliente enviar votos"); 
        btnc2.setTranslateX(0);
        btnc2.setTranslateY(-50);       
        
        Button btns = new Button();
        btns.setText("servidor conectar");
        
        Button btns1 = new Button();
        btns1.setText("servidor enviar candidatos"); 
        btns1.setTranslateX(0); 
        btns1.setTranslateY(50);       
        
        Button btns2 = new Button();
        btns2.setText("servidor resivir botos"); 
        btns2.setTranslateX(0);
        btns2.setTranslateY(100);       
        
        Servidor server=new Servidor();
        Cliente cliente=new Cliente();
        
        btns.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {server.start();}
        });
        btnc.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {cliente.start();}
        });
        btns1.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Candidato> a= new ArrayList<>();
                a.add(new Candidato("carlos","PLN",4534));
                a.add(new Candidato("luis","PLN",4534));
                a.add(new Candidato("roxana","PLN",4534));
                a.add(new Candidato("eugenio","PLN",4534));
                server.enviar_candidatos(a);
            }
        });
        btnc1.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Candidato> candidatos = cliente.leer_candidatos();
                for(int i=0;i<candidatos.size();i++){
                    System.out.println("Cliente: lee candidato -> "+candidatos.get(i).getNombre());
                }
            }
        });
        btns2.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Renglon_votacion> votos=server.leer_votos();
                for(int i = 0 ; i < votos.size() ; i++){
                    System.out.println("Server: lee->"+votos.get(i).getNombre_de_candidato()+
                            ","+votos.get(i).getPos());
                }
            }
        });
        btnc2.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {
                ArrayList<Renglon_votacion> v = new ArrayList<>();
                v.add(new Renglon_votacion("mara",6));
                v.add(new Renglon_votacion("ganster",9));
                v.add(new Renglon_votacion("ladron",2));
                cliente.enviar_votos(v);
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btns);
        root.getChildren().add(btnc);
        root.getChildren().add(btns1);
        root.getChildren().add(btnc1);
        root.getChildren().add(btns2);
        root.getChildren().add(btnc2);        
        Scene scene = new Scene(root, 300, 300);
        
        primaryStage.setTitle("Pruebas sockets");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
