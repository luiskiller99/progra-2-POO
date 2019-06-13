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
import javafx.scene.control.TextField;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
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
        if(!isTextOnly(textNombreCandidato.getText())){
            JOptionPane.showMessageDialog(null, "Nombre del candidato no valido", "Error", 0);
            textNombreCandidato.clear();
            return;
        }
        if(!isTextOnly(textPartidoPolitico.getText())){
            JOptionPane.showMessageDialog(null, "Nombre del Partido politico no valido", "Error", 0);
            textPartidoPolitico.clear();
            return;
        }
        if(!isNumbersOnly(textCedula.getText())){
            JOptionPane.showMessageDialog(null, "Cedula no valida", "Error", 0);
            textCedula.clear();
            return;
        }
        int cedula=Integer.parseInt(textCedula.getText());
        if(cedula>1000000000||cedula<99999999){
            JOptionPane.showMessageDialog(null, "Cedula no valida", "Error", 0);
            textCedula.clear();
            return;
        }
        try {
            SistemaServidor.aniadirCandidato(textNombreCandidato.getText(), textPartidoPolitico.getText(), cedula);
        } catch (Exception ex) {
            Logger.getLogger(ControladorVentanaAniadirCandidatos.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    private boolean isTextOnly(String s){
        String regrex = "[A-Za-z\\s]+";
        return s.matches(regrex);
    }
    private boolean isNumbersOnly(String s){
        String regrex="[0-9]";
        return s.matches(regrex);
    }
    
}
