/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author sania
 */
public class FLNormalCharts {

    private ArrayList<Color> colorList;

    public FLNormalCharts() {
        colorList = new ArrayList<Color>();
        setColorSpace();
    }

    public JFreeChart getInvestmentInChart(TreeMap<String, Double> map) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        double other = 0;
        for (String client : map.keySet()) {
            double pc = map.get(client);
            if (pc < 5) {
                other += pc;
            } else {
                dataset.setValue(client, new Double(pc));
            }

            dataset.setValue("Other", new Double(other));
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Portfolio",
                dataset,
                true,
                true,
                false);

        chart.removeLegend();
        chart.setTitle("");
        chart.getPlot().setOutlineVisible(false);
        chart.getPlot().setNoDataMessage("--No Data--");

        PiePlot plot = (PiePlot) chart.getPlot();
        PieRenderer renderer = new PieRenderer();
        renderer.setColor(plot, dataset);
        return chart;

    }

    private void setColorSpace() {

    }

}
