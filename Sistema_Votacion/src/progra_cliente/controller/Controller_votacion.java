package progra_cliente.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import progra_cliente.model.Cliente;
import progra_servidor.model.Voto;
import progra_servidor.model.Candidato;

/**
 * @author Luis Elizondo
 */
public class Controller_votacion implements Initializable {

    /**
     * agregar_candidato("LUIS"); solo se nececita el nombre
     *
     *
     * agregar_elemento_combobox(0); agregar_elemento_combobox(1); se agregan de
     * esta manera por si es de uno en uno o de 5 en 5 por ejemplo
     * agregar_elemento_combobox(2);
     *
     *
     * obtener_votos(); retorna todos los botos, en cada posicion el objeto
     * retorna nombre y valor
     *
     *
     * enviar_voto(); enviar_en_blanco();se ejecutan al precionar los botones
     * respectivamente
     *
     */
    private Timer temporizador;
    private Cliente Modelo;
    @FXML
    private VBox vbox_arreglo_candidatos;
    private ObservableList<Integer> items;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
         
        temporizador = new Timer();
        
        temporizador.schedule(new TimerTask() {
            int s=0;
            @Override
            public void run() {
                s+=30;
                if(s!=120){
                    JOptionPane.showMessageDialog(null, "Ha pasado "+s+" segundos", "Información",JOptionPane.INFORMATION_MESSAGE);                                        
                }else{                    
                    JOptionPane.showMessageDialog(null, "Ha pasado su tiempo maximo de votación", "Información",JOptionPane.INFORMATION_MESSAGE);                    
                    temporizador.cancel();}                
            }
            
        } , 30000);
        
        items = FXCollections.observableArrayList();
        int j=0;
        for(Candidato i:Cliente.candidatos){
            agregar_candidato(i.getNombre(), i.getCedula(), i.getPartido());
            items.add(j++);
        }
    }

    @FXML private void enviar_voto(ActionEvent event) {
        int cont = 0;
        for (int i = 0; i < vbox_arreglo_candidatos.getChildren().size(); i++) {
            HBox s = (HBox) vbox_arreglo_candidatos.getChildren().get(i);
            ComboBox<Integer> c = (ComboBox<Integer>) s.getChildren().get(1);
            for (int j = 0; j < vbox_arreglo_candidatos.getChildren().size(); j++) {
                HBox ss = (HBox) vbox_arreglo_candidatos.getChildren().get(j);
                ComboBox<Integer> cc = (ComboBox<Integer>) ss.getChildren().get(1);
                if (c.getValue() != null && cc.getValue() != null) {
                    int c1 = c.getValue(), cc1 = cc.getValue();
                    if ((c1 == cc1) && (c1 != 0)) {
                        cont++;
                    }
                }
            }
            if (cont >= 2) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tiene selecciones iguales en dos o mas candidatos, corríjalas", ButtonType.OK);
                alert.show();
            } else {
            }
            cont = 0;
        }
        if (cont == 0) {
            // envia voto cierra ventana
            Modelo=new Cliente();
            Modelo.enviar_votos(obtener_votos());
            Modelo.cerrar_conexion();                                         
            Stage s =(Stage) vbox_arreglo_candidatos.getScene().getWindow();
            s.close();
            try {
                    URL fxml = getClass().getClassLoader().getResource("progra_cliente/view/ventana_ingresar_cliente.fxml");
                    FXMLLoader fxmlloader = new FXMLLoader(fxml);
                    Stage stage = new Stage();
                    stage.setTitle("Votación");
                    stage.setScene(new Scene(fxmlloader.load()));
                    stage.show();                                        
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Su voto ha sido enviado con éxito", ButtonType.OK);
                    alert.show();       
                } catch (IOException e){}
            
        }
    }
    private void agregar_candidato(String nombre_candidato, int cedula, String partido) {
        Label n = new Label(cedula + "\t" + nombre_candidato + "\t" + partido);
        n.setMinSize(300, 14);
        n.setPrefSize(300, 14);
        ComboBox<Integer> num = new ComboBox(items);
        num.setMinSize(96, 25);
        num.setPrefSize(96, 25);
        HBox r = new HBox();
        r.getChildren().addAll(n, num);
        r.setMinSize(416, 44);
        r.setPrefSize(416, 44);
        vbox_arreglo_candidatos.getChildren().add(r);
    }
    private void agregar_elemento_combobox(int n) {items.add(n);}
    private Voto obtener_votos() {
        Voto votos = new Voto();
        for (int j = 1; j < items.size(); j++) {
            for (int i = 0; i < vbox_arreglo_candidatos.getChildren().size(); i++) {
                HBox s = (HBox) vbox_arreglo_candidatos.getChildren().get(i);
                Label ll = (Label) s.getChildren().get(0);
                ComboBox<Integer> c = (ComboBox<Integer>) s.getChildren().get(1);
                if (c.getValue() != null) {
                    if (c.getValue() == j) {
                        String ced = ll.getText().substring(0, 9);
                        votos.add(Integer.parseInt(ced));
                        break;
                    }
                }
            }
        }
        return votos;
    }
    @FXML private void salir_de_votacion(ActionEvent event) {       
            int r = JOptionPane.showConfirmDialog(null, "Realmente desea salir, al confirmar enviaria un voto en blanco?", "Confirmar salida", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(r == JOptionPane.YES_OPTION){
                Modelo = new Cliente();
                Voto v = new Voto();
                Modelo.enviar_votos(v);
                Modelo.cerrar_conexion();                                            
                Stage s =(Stage) vbox_arreglo_candidatos.getScene().getWindow();
                s.close();            
                try {
                        URL fxml = getClass().getClassLoader().getResource("progra_cliente/view/ventana_ingresar_cliente.fxml");
                        FXMLLoader fxmlloader = new FXMLLoader(fxml);
                        Stage stage = new Stage();
                        stage.setTitle("Votación");
                        stage.setScene(new Scene(fxmlloader.load()));
                        stage.show();        
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Su voto ha sido enviado con éxito", ButtonType.OK);
                        alert.show();
                    } catch (IOException e){}
            }
    }            
}
