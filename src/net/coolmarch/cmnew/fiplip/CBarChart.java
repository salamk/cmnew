/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;

/**
 *
 * @author sania
 */
public class CBarChart {

    public CBarChart() {
    }

    public JFreeChart createSimpleBarChart(CategoryDataset dataset) {
        JFreeChart barChart = ChartFactory.createBarChart(
                "",
                "Category",
                "Score",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        return barChart;

    }

    public JFreeChart createComplexBarChart(CategoryDataset dataset) {
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "",
//                "Category",
//                "Score",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);

        JFreeChart barChart = ChartFactory.createBarChart3D("", "", "", dataset);

        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);
        return barChart;

    }

    public JFreeChart createSliceDetailsBarchart(CategoryDataset dataset) {
//        JFreeChart barChart = ChartFactory.createBarChart(
//                "",
//                "Category",
//                "Score",
//                dataset,
//                PlotOrientation.VERTICAL,
//                true, true, false);

        JFreeChart barChart = ChartFactory.createBarChart3D("", "", "", dataset);
        barChart.getCategoryPlot().setOrientation(PlotOrientation.HORIZONTAL);
        
        CategoryPlot plot = (CategoryPlot) barChart.getPlot();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.STANDARD);
        return barChart;

    }

}
