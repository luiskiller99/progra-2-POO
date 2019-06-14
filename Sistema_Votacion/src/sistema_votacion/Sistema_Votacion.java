/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistema_votacion;

import java.io.IOException;
import java.util.Arrays;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import progra_servidor.model.Candidato;
import progra_servidor.model.SistemaServidor;

/**
 *
 * @author zEstebanCruz
 */
public class Sistema_Votacion extends Application {

	Scene scene;

	@Override
	public void start(Stage primaryStage) {
		SistemaServidor.candidatos.addAll(Arrays.asList(new Candidato("Luis", "PLN", 123412300),
																										new Candidato("Joshua", "PLN", 123412301),
																										new Candidato("Esteban", "PAC", 123412302),
																										new Candidato("Esteban", "PAC", 123412303),
																										new Candidato("Esteban", "PAC", 123412304),
																										new Candidato("Esteban", "PAC", 123412305),
																										new Candidato("Esteban", "PAC", 123412306),
																										new Candidato("Esteban", "PAC", 123412307),
																										new Candidato("Esteban", "PAC", 123412308),
																										new Candidato("Esteban", "PAC", 123412309),
																										new Candidato("Esteban", "PAC", 123412310),
																										new Candidato("Esteban", "PAC", 123412311),
																										new Candidato("Esteban", "PAC", 123412321),
																										new Candidato("Esteban", "PAC", 123412331),
																										new Candidato("Esteban", "PAC", 123412341),
																										new Candidato("Esteban", "PAC", 123412351),
																										new Candidato("Esteban", "PAC", 123412351)));
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
