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
import javafx.stage.Stage;

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
		/*try {
		FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("progra_servidor/view/VentanaAniadirCandidatos.fxml"));
		Parent root = (Parent) fxmlLoader.load();
		Stage stage = new Stage();
		stage.setScene(new Scene(root));
		stage.show();
	} catch (IOException e) {
                    
	}*/
	}

}
