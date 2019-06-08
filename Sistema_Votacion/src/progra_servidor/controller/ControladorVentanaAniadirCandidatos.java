/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author irsac
 */
public class ControladorVentanaAniadirCandidatos implements Initializable {

    @FXML
    private TextField textNombreCandidato;
    @FXML
    private TextField textPartidoPolitico;
    @FXML
    private TextField textCedula;
    @FXML
    private Button botonAniadirCandidato;
    @FXML
    private Button botonCerrarVentana;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void agregarCandidato(ActionEvent event) {
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
    }
    
}
