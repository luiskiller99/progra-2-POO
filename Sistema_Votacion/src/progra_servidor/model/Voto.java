/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

import java.util.ArrayList;

/**
 *
 * @author zEstebanCruz
 */
public class Voto extends ArrayList<Integer> {

	public Integer actual() {
		try {
			return this.get(0);
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	public Integer siguiente() {
		try {
			return this.get(1);
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

	public Integer modificarActual() {
		try {
			this.remove(0);
			return this.get(0);
		} catch (IndexOutOfBoundsException e) {
			return 0;
		}
	}

}
