/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_cliente.model;

/**
 *
 * @author metal
 */
public class Renglon_votacion {
    String Nombre_de_candidato;
    int pos;
    public Renglon_votacion(String Nombre_de_candidato, int pos) {
        this.Nombre_de_candidato = Nombre_de_candidato;
        this.pos = pos;
    }
    public String getNombre_de_candidato() {
        return Nombre_de_candidato;
    }
    public int getPos() {
        return pos;
    }
    
}
