/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author irsac
 */
public class ControladorInicioServer implements Initializable {

    @FXML
    private Button botonAniadirCandidato;
    @FXML
    private Button botonIniciarVotacion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    

    @FXML
    private void abrirVentanaAniadirCandidato(ActionEvent event) {
        Stage stagg = (Stage) botonAniadirCandidato.getScene().getWindow();
	stagg.close();
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("progra_servidor/view/VentanaAniadirCandidatos.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException e) {

        }
    }

    @FXML
    private void abrirVentanaServidorIniciado(ActionEvent event) {
        Stage stagg = (Stage) botonIniciarVotacion.getScene().getWindow();
        stagg.close();
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("progra_servidor/view/VentanaServidorIniciado.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
        } catch (IOException e) {

        }
    }
    
}
