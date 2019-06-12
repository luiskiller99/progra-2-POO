/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import progra_cliente.model.Cliente;
import progra_servidor.model.Servidor;

/**
 *
 * @author metal
 */
public class prueba_sockets extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnc = new Button();
        btnc.setText("cliente");
        btnc.setTranslateX(0);
        btnc.setTranslateY(50);        
        
        Button btns = new Button();
        btns.setText("servidor");
        
        btns.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {                
                Servidor server=new Servidor();
                server.start();
            }
        });
        
        btnc.setOnAction(new EventHandler<ActionEvent>() {            
            @Override
            public void handle(ActionEvent event) {       
                Cliente cliente=new Cliente();
                cliente.start();
            }
        });
        
        StackPane root = new StackPane();
        root.getChildren().add(btns);
        root.getChildren().add(btnc);
        
        Scene scene = new Scene(root, 300, 250);
        
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
