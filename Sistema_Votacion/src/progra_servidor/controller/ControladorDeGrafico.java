/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package progra_servidor.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryToPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.util.TableOrder;
import progra_servidor.model.Voto;

/**
 *
 * @author zEstebanCruz
 */
public class ControladorDeGrafico {

	DefaultCategoryDataset dataset;
	ChartFrame v;
	String categoria;
	Map<Integer, ArrayList<Voto>> valoresEnGrafico;

	public ControladorDeGrafico(Map<Integer, ArrayList<Voto>> valores, String labelCategoria) {
		dataset = new DefaultCategoryDataset();
		categoria = labelCategoria;
		valoresEnGrafico = valores;
		valores.forEach((candidato, valor) -> {
			dataset.setValue(valor.size(), candidato, candidato);
		});
	}

	public synchronized void actualizarValores(Map<Integer, ArrayList<Voto>> valores) {
		valores.forEach((candidato, valor) -> {
			dataset.setValue(valor.size(), candidato, candidato);
		});
		List<Integer> lista = new ArrayList<>(dataset.getColumnKeys());
		lista.forEach((candidato) -> {
			if (!valores.containsKey(candidato))
				dataset.removeColumn(candidato);
		});
	}

	public void mostrarGrafico(String titulo, String labelValores, String tipoGrafico) {
		JFreeChart grafico;
		switch (tipoGrafico) {
			case "barras":
				grafico = ChartFactory.createBarChart(titulo, categoria, labelValores, dataset);
				break;
			case "lineas":
				grafico = ChartFactory.createLineChart(titulo, categoria, labelValores, dataset);
				break;
			case "pizza":
			default:
				grafico = ChartFactory.createPieChart(titulo, new CategoryToPieDataset(dataset, TableOrder.BY_ROW, 0));
				break;
		}
		if (v == null) {
			v = new ChartFrame(titulo, grafico);
			v.pack();
			v.setVisible(true);
		}
	}

}
