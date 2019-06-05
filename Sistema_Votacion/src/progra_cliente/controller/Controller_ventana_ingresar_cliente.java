
package progra_cliente.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Luis Elizondo
 */
public class Controller_ventana_ingresar_cliente implements Initializable {
    @FXML private TextField ced;
    @FXML private TextField con;
    @Override public void initialize(URL url, ResourceBundle rb) {}    
    String getCedula(){return ced.getText();}
    String getContrasena(){return con.getText();}
    @FXML private void ingresar(){
        System.err.println(getCedula()+","+getContrasena());
        //levanta ventana si las credenciales correctas        
        try {
            URL fxml = getClass().getClassLoader().getResource("progra_cliente/view/ventana_votacion.fxml");
            FXMLLoader fxmlloader = new FXMLLoader(fxml);
            Stage stage = new Stage();
            stage.setTitle("Votación");
            stage.setScene(new Scene(fxmlloader.load()));
            stage.show();
        } catch (IOException e) {}
    }
}
