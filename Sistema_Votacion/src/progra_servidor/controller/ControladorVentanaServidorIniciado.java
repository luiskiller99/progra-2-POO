/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import progra_servidor.model.SistemaServidor;

/**
 * FXML Controller class
 *
 * @author irsac
 */
public class ControladorVentanaServidorIniciado implements Initializable {

	@FXML
	private Button botonTerminarVotacion;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@FXML
	private void finalizarVotacion(ActionEvent event) {
		((Stage) botonTerminarVotacion.getScene().getWindow()).close();
                SistemaServidor.cerrarServidor();
            try {
                SistemaServidor.inicializarConteoDeVotos();
            } catch (Exception ex) {
                Logger.getLogger(ControladorVentanaServidorIniciado.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

}
