/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_cliente.model;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author metal
 */
public class FXMain extends Application {   
    @Override
    public void start(Stage primaryStage) {        
        Parent root1;
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("venatana_inicio.fxml"));        
            root1 = fxmlLoader.load();                                    
            primaryStage.setTitle("Sistema de votación");
            primaryStage.setScene(new Scene(root1));  
            primaryStage.show();  
        }catch(IOException e){}                               
    }  
    public static void main(String[] args) {
        launch(args);        
    }
    
}
