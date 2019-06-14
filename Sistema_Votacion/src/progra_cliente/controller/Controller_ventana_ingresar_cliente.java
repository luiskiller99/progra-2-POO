package progra_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import progra_cliente.model.Cliente;

/**
 * @author Luis Elizondo / jjosh
 */
public class Controller_ventana_ingresar_cliente implements Initializable {

    private Cliente Modelo;

    @FXML
    private TextField ced, con;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }

    String getCedula() {
        return ced.getText();
    }

    String getContrasena() {
        return con.getText();
    }

    @FXML
    private void ingresar() {
        Modelo = new Cliente();
        Modelo.run();
        try {
            if (Modelo.ValidarCredenciales(Integer.parseInt(getCedula()), Integer.parseInt(getContrasena()))) {
                try {
                    URL fxml = getClass().getClassLoader().getResource("progra_cliente/view/ventana_votacion.fxml");
                    FXMLLoader fxmlloader = new FXMLLoader(fxml);
                    Stage stage = new Stage();
                    stage.setTitle("Votación");
                    stage.setScene(new Scene(fxmlloader.load()));
                    stage.show();
                } catch (IOException e){}
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Los datos no son válidos", ButtonType.OK);
                alert.show();
            }
        } catch (NumberFormatException N) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Los datos no son válidos", ButtonType.OK);
            alert.show();
        } catch (IOException ex) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Error al conectarse con servidor", ButtonType.OK);
            alert.show();
        }

    }
}
