/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author zEstebanCruz
 */
public final class Stv {

	private final ArrayList<Voto> votos;
	private final int plazas;
	private final int CANT_PARA_PLAZA;
	Map<Integer, ArrayList<Voto>> votosPorCandidato;

	public Stv(ArrayList<Voto> todosLosVotos, int plazasPorLlenar) throws Exception {
		if (todosLosVotos.size() < plazasPorLlenar)
			throw new Exception("Las plazas por llenar no debe ser mayor a la cantidad de votos emitidos.");
		votosPorCandidato = new HashMap<>();
		votos = todosLosVotos;
		plazas = plazasPorLlenar;
		CANT_PARA_PLAZA = votos.size() / (plazas + 1) + 1;
	}

	private void transferirVoto(final Map<Integer, ArrayList<Voto>> votosPorSiguienteOpcion,
															final Integer candidatoAQuitarVoto, final Integer candidatoAPonerVoto) {
		Voto votoTransfiriendo = votosPorSiguienteOpcion.get(candidatoAPonerVoto).remove(0);
		votosPorCandidato.get(candidatoAQuitarVoto)
			.remove(votoTransfiriendo);
		if (votoTransfiriendo.siguiente() != 0
				&& votosPorCandidato.containsKey(votoTransfiriendo.siguiente()))
			votosPorCandidato.get(votoTransfiriendo.modificarActual())//La siguiente opcion de ese voto es igual al parámetro candidatoAPonerVoto
				.add(votoTransfiriendo);
	}

	private Map<Integer, ArrayList<Voto>> contarVotos(final ArrayList<Voto> votos) {
		Map<Integer, ArrayList<Voto>> votosContados = new HashMap<>();
		for (Voto voto : votos) {
			while (voto.siguiente() != 0 && !votosPorCandidato.containsKey(voto.siguiente()))
				voto.modificarActual();
			if (voto.siguiente() != 0)
				votosContados.putIfAbsent(voto.siguiente(), new ArrayList<>()).add(voto);
		}
		return votosContados;
	}

	private Map<Integer, Float> getPorcentaje(Map<Integer, ArrayList<Voto>> votosAContar, int sumaVotos) {
		Map<Integer, Float> porcentajes = new HashMap<>();
		for (Entry<Integer, ArrayList<Voto>> candidato : votosAContar.entrySet())
			if (candidato.getKey() != 0)
				porcentajes.put(candidato.getKey(), (float) candidato.getValue().size() / (float) sumaVotos);
		return porcentajes;
	}

	public void contarTodosLosVotos() {
		votosPorCandidato = new HashMap<>();
		votos.forEach((voto) -> {
			if (voto.actual() != 0) {
				votosPorCandidato.putIfAbsent(voto.actual(), new ArrayList<>());
				votosPorCandidato.get(voto.actual()).add(voto);
			}
		});
	}

	public void repartirLosVotosSobrantes() {
		int cantVotosARepartir;
		for (Entry<Integer, ArrayList<Voto>> candidato : votosPorCandidato.entrySet())
			if (candidato.getValue().size() > CANT_PARA_PLAZA) { //Solo para los que tienen más votos que la cantidad para plaza
				//Para cada uno de los que tienen más de los votos necesarios
				Map<Integer, ArrayList<Voto>> votosDeXCandidato = contarVotos(votosPorCandidato.get(candidato.getKey()));
				ArrayList<Entry<Integer, Float>> porcentajeDeCandidatoPorSiguienteOpcion;
				porcentajeDeCandidatoPorSiguienteOpcion = new ArrayList<>(getPorcentaje(votosDeXCandidato, votosPorCandidato.get(candidato.getKey()).size()).entrySet());
				cantVotosARepartir = candidato.getValue().size() - CANT_PARA_PLAZA;
				if (cantVotosARepartir < 10)
					porcentajeDeCandidatoPorSiguienteOpcion.sort((e1, e2) -> Math.round((e2.getValue() - e1.getValue()) * 100));
				for (Entry<Integer, Float> porcentaje : porcentajeDeCandidatoPorSiguienteOpcion)
					for (int i = 0;
							 i < porcentaje.getValue() * (float) cantVotosARepartir
							 && candidato.getValue().size() - CANT_PARA_PLAZA > 0;
							 i++)
						transferirVoto(votosDeXCandidato, candidato.getKey(), porcentaje.getKey());
			}
	}

	public void eliminarPartidoConMenosVotos() {
		Entry<Integer, ArrayList<Voto>> candidatoConMenosVotos
																		= Collections.min(votosPorCandidato.entrySet(),
																											(e1, e2) -> e1.getValue().size() - e2.getValue().size());
		Voto votoTransfiriendo;
		while (!candidatoConMenosVotos.getValue().isEmpty()) {
			votoTransfiriendo = candidatoConMenosVotos.getValue().remove(0);
			if (votoTransfiriendo.siguiente() != 0 && votosPorCandidato.containsKey(votoTransfiriendo.siguiente()))
				votosPorCandidato.get(votoTransfiriendo.modificarActual())//La siguiente opcion de ese voto es igual al parámetro candidatoAPonerVoto
					.add(votoTransfiriendo);
		}
		votosPorCandidato.remove(candidatoConMenosVotos.getKey());
	}

	public boolean plazasLlenadas() {
		return (votosPorCandidato.size() == plazas);
	}

	public void algoritmoCompleto() {
		contarTodosLosVotos();
		while (!plazasLlenadas()) {
			repartirLosVotosSobrantes();
			if (!plazasLlenadas())
				eliminarPartidoConMenosVotos();
		}
	}
}
