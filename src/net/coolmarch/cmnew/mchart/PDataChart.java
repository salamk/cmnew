/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import net.coolmarch.cmnew.common.TickData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author salam
 */
public class PDataChart {

    private ChartPanel cp;
    private ArrayList<TickData> al;

    public PDataChart(ArrayList<TickData> al) {
        if (al.size() == 0) {
            ;
        } else {
            this.al = al;
            Collections.reverse(al);
            DefaultCategoryDataset ds = (DefaultCategoryDataset) createDataset();
            JFreeChart chart = ChartFactory.createLineChart("", "",
                    "", ds, PlotOrientation.VERTICAL, false, true, true);
            chart.getCategoryPlot().getRangeAxis().setLowerBound(getMinimum());
            chart.getCategoryPlot().getRangeAxis().setUpperBound(getMaximump());
            chart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);
            chart.getCategoryPlot().getDomainAxis().setTickMarksVisible(false);
            cp = new ChartPanel(chart);
        }
    }

    public ChartPanel getChartPanel() {
        return cp;
    }

    private CategoryDataset createDataset() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH");

        for (TickData td : al) {
            double price = td.getDailyQuotePrice();
            Date d = td.getQuoteDate();
            dataset.addValue(price, "Price", sdf.format(d));
        }

        return dataset;
    }

    private double getMinimum() {
        double min = al.get(0).getDailyQuotePrice();
        for (TickData td : al) {
            double pr = td.getDailyQuotePrice();
            if (pr < min) {
                min = pr;
            }
        }

       // System.out.println(min);

        return min;
    }

    private double getMaximump() {
        double max = al.get(0).getDailyQuotePrice();
        for (TickData td : al) {
            double pr = td.getDailyQuotePrice();
            if (pr > max) {
                if (pr == 535500.0) {
                } else {
                    max = pr;
                }
            }
        }

       // System.out.println(max);

        return max;
    }

}
