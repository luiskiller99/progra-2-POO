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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
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
		if (JOptionPane.showConfirmDialog(
			null, "¿Está seguro de que sea finalizar la votación?", "Confirmar", JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
			try {
				SistemaServidor.inicializarConteoDeVotos();
				((Stage) botonTerminarVotacion.getScene().getWindow()).close();
				SistemaServidor.cerrarServidor();
				JOptionPane.showMessageDialog(null, "Iniciando conteo de votos", "Aviso", 1);
				SistemaServidor.mostrarResultadoDeLaVotacion("");
			} catch (Exception ex) {
				new Alert(Alert.AlertType.ERROR, "No se han recibido suficientes votos para terminar la votación.").showAndWait();
			}
	}

}
