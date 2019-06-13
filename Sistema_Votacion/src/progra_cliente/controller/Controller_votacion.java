
package progra_cliente.controller;
import progra_cliente.model.Renglon_votacion;
import java.net.URL;
import java.util.ArrayList;
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
/** 
 * @author Luis Elizondo
 */
public class Controller_votacion implements Initializable {
    /**
     * agregar_candidato("LUIS"); solo se nececita el nombre
     * 
     * 
     * agregar_elemento_combobox(0);
     * agregar_elemento_combobox(1); se agregan de esta manera por si es de uno en uno o de 5 en 5 por ejemplo
     * agregar_elemento_combobox(2);
     * 
     * 
     * obtener_votos(); retorna todos los botos, en cada posicion el objeto retorna nombre y valor     
     * 
     * 
     * enviar_voto();
     * enviar_en_blanco();se ejecutan al precionar los botones respectivamente
     * 
     */
    @FXML private VBox vbox_arreglo_candidatos;    
    private ObservableList<Integer> items;    
    @Override public void initialize(URL url, ResourceBundle rb) {
        items = FXCollections.observableArrayList();      
        items.addAll(0,1,2,3);
        agregar_candidato("luis");
        agregar_candidato("carlos");
        agregar_candidato("andres");        
        
    }    
    @FXML private void enviar_voto(ActionEvent event) {
        for (int i = 0; i < vbox_arreglo_candidatos.getChildren().size() ; i++) {
            int cont=0;
            HBox s = (HBox) vbox_arreglo_candidatos.getChildren().get(i); 
            ComboBox<Integer> c = (ComboBox<Integer>) s.getChildren().get(1);                           
            for (int j = 0; j < vbox_arreglo_candidatos.getChildren().size(); j++) {                
                HBox ss = (HBox) vbox_arreglo_candidatos.getChildren().get(j); 
                ComboBox<Integer> cc = (ComboBox<Integer>) ss.getChildren().get(1);                           
                if(c.getValue() == cc.getValue())cont++;
            }
            if(cont>1){
                 Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Tiene selecciones iguales en dos o mas candidatos, corríjalas", ButtonType.OK);
                 alert.show();
            }else{
                //enviar voto
            }            
        }
    }    
    @FXML private void enviar_en_blanco(ActionEvent event) {}
    private void agregar_candidato(String nombre_candidato){
        Label n = new Label(nombre_candidato);          
        n.setMinSize(300, 14);    
        n.setPrefSize(300, 14);                
        ComboBox<Integer> num = new ComboBox(items);
        num.setMinSize(96, 25);
        num.setPrefSize(96, 25);        
        HBox r = new HBox();
        r.getChildren().addAll(n,num);        
        r.setMinSize(416, 44);
        r.setPrefSize(416, 44);        
        vbox_arreglo_candidatos.getChildren().add(r);        
    }
    private void agregar_elemento_combobox(int n){items.add(n);}
    private ArrayList<Renglon_votacion> obtener_votos(){
        ArrayList<Renglon_votacion> renglon = new ArrayList<>();
        for(int i = 0 ; i < vbox_arreglo_candidatos.getChildren().size() ; i++ ){
            HBox s = (HBox) vbox_arreglo_candidatos.getChildren().get(i); 
            Label ll = (Label) s.getChildren().get(0);            
            ComboBox<Integer> c = (ComboBox<Integer>) s.getChildren().get(1);                
            if(c.getValue()!=null){
                Renglon_votacion ff = new Renglon_votacion(ll.getText(), c.getValue());
                renglon.add(ff);
            }
            else{
                //-1 hace referencia a espacios en blanco
                Renglon_votacion ff = new Renglon_votacion(ll.getText(), -1);
                renglon.add(ff);
            }
        }
        return renglon;
    }
}
