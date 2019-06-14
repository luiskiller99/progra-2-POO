package progra_cliente.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    private Cliente Modelo;
    @FXML
    private VBox vbox_arreglo_candidatos;
    private ObservableList<Integer> items;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        items = FXCollections.observableArrayList();
        int j=0;
        for(Candidato i:Cliente.candidatos){
            agregar_candidato(i.getNombre(), i.getCedula(), i.getPartido());
            items.add(j++);
        }
    }

    @FXML
    private void enviar_voto(ActionEvent event) {
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
            Modelo.enviar_votos(obtener_votos());
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

    private void agregar_elemento_combobox(int n) {
        items.add(n);
    }

    private Voto obtener_votos() {
        Voto votos = new Voto();
        for (int j = 1; j < items.size(); j++) {
            for (int i = 0; i < vbox_arreglo_candidatos.getChildren().size(); i++) {
                HBox s = (HBox) vbox_arreglo_candidatos.getChildren().get(i);
                Label ll = (Label) s.getChildren().get(0);
                ComboBox<Integer> c = (ComboBox<Integer>) s.getChildren().get(1);
                if (c.getValue() != null) {
                    if (c.getValue() == j) {
                        String ced = ll.getText().substring(0, 5);
                        votos.add(Integer.parseInt(ced));
                        break;
                    }
                }
            }
        }
        return votos;
    }

    @FXML
    private void enviar_en_blanco(ActionEvent event) {
    }
}
