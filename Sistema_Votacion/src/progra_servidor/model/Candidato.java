/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

/**
 *
 * @author zEstebanCruz
 */
public class Candidato {

	private String nombre, partido;
	private int cedula;

	@Override
	public String toString() {
		return "\nNombre: " + nombre + "\nPartido: " + partido + "\nCedula:" + cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPartido() {
		return partido;
	}

	public void setPartido(String partido) {
		this.partido = partido;
	}

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public Candidato(String nombre, String partido, int cedula) {
		this.nombre = nombre;
		this.partido = partido;
		this.cedula = cedula;
	}

}
