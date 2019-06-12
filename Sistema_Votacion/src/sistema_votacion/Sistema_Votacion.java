/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_votacion;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author zEstebanCruz
 */
public class Sistema_Votacion extends Application {

	Scene scene;

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(ClassLoader.getSystemClassLoader().getResource("progra_servidor/view/VentanaInicioServer.fxml"));
			scene = new Scene(root);

			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException ex) {
			System.out.println("Error: " + ex.getMessage());
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}