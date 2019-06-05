package progra_cliente.model;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * @author metal
 */
public class FXMain extends Application {   
    @Override
    public void start(Stage primaryStage) {        
        try{                    
            URL fxml = getClass().getClassLoader().getResource("progra_cliente/view/ventana_ingresar_cliente.fxml");
            FXMLLoader fxmlloader = new FXMLLoader(fxml);
            primaryStage.setTitle("Sistema de votación");
            primaryStage.setScene(new Scene(fxmlloader.load()));  
            primaryStage.show();  
        }catch(IOException e){}                               
    }  
    public static void main(String[] args) {
        launch(args);        
    }
    
}
