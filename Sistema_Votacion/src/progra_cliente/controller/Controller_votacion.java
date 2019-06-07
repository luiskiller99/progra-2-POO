
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
/** 
 * @author Luis Elizondo
 */
public class Controller_votacion implements Initializable {
    @FXML private VBox vbox_arreglo_candidatos;    
    private ObservableList<Integer> items;    
    @Override public void initialize(URL url, ResourceBundle rb) {
        items = FXCollections.observableArrayList();        
    }    
    @FXML private void enviar_voto(ActionEvent event) {
        for (int i = 0; i < obtener_votos().size(); i++) {
            System.out.println(" nombre: "+
                    obtener_votos().get(i).getNombre_de_candidato()+
                    " valor: "+
                    obtener_votos().get(i).getPos());
        }
        
    }    
    @FXML private void enviar_en_blanco(ActionEvent event) {
        agregar_candidato("LUIS");
        agregar_elemento_combobox(0);
        agregar_elemento_combobox(1);
    }
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
