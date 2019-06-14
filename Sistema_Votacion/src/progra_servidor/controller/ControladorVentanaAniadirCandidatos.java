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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import progra_servidor.model.SistemaServidor;

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
		if (!isTextOnly(textNombreCandidato.getText())) {
			JOptionPane.showMessageDialog(null, "Nombre del candidato no valido", "Error", 0);
			textNombreCandidato.clear();
			return;
		}
		if (!isTextOnly(textPartidoPolitico.getText())) {
			JOptionPane.showMessageDialog(null, "Nombre del Partido politico no valido", "Error", 0);
			textPartidoPolitico.clear();
			return;
		}
		if (!isNumbersOnly(textCedula.getText())) {
			JOptionPane.showMessageDialog(null, "Cedula no valida", "Error", 0);
			textCedula.clear();
			return;
		}
		try {
			SistemaServidor.aniadirCandidato(textNombreCandidato.getText(), textPartidoPolitico.getText(), Integer.parseInt(textCedula.getText()));
		} catch (Exception ex) {
			new Alert(Alert.AlertType.INFORMATION, "Error al añadir candidato.").showAndWait();
		}
		textNombreCandidato.clear();
		textPartidoPolitico.clear();
		textCedula.clear();
		//crear candidato y añadirlo a una lista
	}

	@FXML
	private void cerrarVentana(ActionEvent event) {
		Stage stagg = (Stage) botonCerrarVentana.getScene().getWindow();
		stagg.close();
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(ClassLoader.getSystemClassLoader().getResource("progra_servidor/view/VentanaInicioServer.fxml"));
			Parent root = (Parent) fxmlLoader.load();
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
		} catch (IOException e) {

		}
	}

	private boolean isTextOnly(String s) {
		String regrex = "[A-Za-z\\s]+";
		return s.matches(regrex);
	}

	private boolean isNumbersOnly(String s) {
		String regrex = "[0-9]{9}";
		return s.matches(regrex);
	}

}
