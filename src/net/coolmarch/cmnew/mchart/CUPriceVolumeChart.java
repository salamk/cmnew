/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import net.coolmarch.cmnew.common.TickData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYBarPainter;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author coolmarch
 */
public class CUPriceVolumeChart {

    private String symbol;
    private int days;
    private ArrayList<TickData> al;

    public CUPriceVolumeChart(String symbol, int days) {
        this.symbol = symbol;
        this.days = days;
        PVImporter pv = new PVImporter();
        if (symbol.compareToIgnoreCase("static") == 0) {
            al = pv.getStaticData(days);
        } else {
            al = pv.getData(symbol, days);
        }
        Collections.sort(al, new Comparator<TickData>() {
            public int compare(TickData o1, TickData o2) {
                return o1.getQuoteDate().compareTo(o2.getQuoteDate());
            }
        });

    }
    
    public ArrayList<TickData> getTickList(){
        return al;
    }

    public ChartPanel getChartPanel() {
        Collections.reverse(al);
        DefaultCategoryDataset ds = (DefaultCategoryDataset) createDataset();
        JFreeChart chart = ChartFactory.createLineChart("", "",
                "", ds, PlotOrientation.VERTICAL, false, true, true);
        chart.getCategoryPlot().getRangeAxis().setLowerBound(getMinimum());
        chart.getCategoryPlot().getRangeAxis().setUpperBound(getMaximump());
        chart.getCategoryPlot().getDomainAxis().setTickLabelsVisible(false);
        chart.getCategoryPlot().getDomainAxis().setTickMarksVisible(false);
        ChartPanel cp = new ChartPanel(chart);
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

        //  System.out.println(min);
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

        //  System.out.println(max);
        return max;
    }

    public ChartPanel getChartPanel2() {

        TimeSeries ptimeSeries = new TimeSeries("");
        TimeSeries vtimeSeries = new TimeSeries("");

        Iterator i = al.iterator();
        int counter = 0;
        Calendar cal = Calendar.getInstance();
        while (i.hasNext()) {
            cal.add(Calendar.MILLISECOND, counter);
            TickData dq = (TickData) i.next();
            double price = dq.getDailyQuotePrice();
            int volume = dq.getDailyQuoteVolume();
            Date d = dq.getQuoteDate();
            Millisecond ms = new Millisecond(cal.getTime());
            ptimeSeries.add(ms, price);
            vtimeSeries.add(ms, volume);
            counter++;
        }

        XYDataset ds = new TimeSeriesCollection(ptimeSeries);
        JFreeChart chart = ChartFactory.createTimeSeriesChart("", "", "",
                ds, false, false, false);
        XYPlot xyplot = chart.getXYPlot();

        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setLowerMargin(0.40000000000000002D);
        DecimalFormat decimalformat = new DecimalFormat("00.00");
        numberaxis.setNumberFormatOverride(decimalformat);
//        XYItemRenderer xyitemrenderer = xyplot.getRenderer();
//        xyitemrenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
//                "{0}: ({1}, {2})", new SimpleDateFormat("d-MMM-yyyy"),
//                new DecimalFormat("0.00")));

        NumberAxis numberaxis1 = new NumberAxis("");
        numberaxis1.setUpperMargin(1.0D);
        xyplot.setRangeAxis(1, numberaxis1);
        chart.getXYPlot().setDataset(1, new TimeSeriesCollection(vtimeSeries));
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.mapDatasetToRangeAxis(1, 1);
        XYBarRenderer xybarrenderer = new XYBarRenderer(0.20000000000000001D);
//        xybarrenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
//                "{0}: ({1}, {2})", new SimpleDateFormat("d-MMM-yyyy"),
//                new DecimalFormat("0,000.00")));
        xyplot.setRenderer(1, xybarrenderer);
        ChartUtilities.applyCurrentTheme(chart);
        xybarrenderer.setBarPainter(new StandardXYBarPainter());
        xybarrenderer.setShadowVisible(false);
        chart.getXYPlot().getDomainAxis().setTickLabelsVisible(false);
        numberaxis1.setTickLabelsVisible(false);

        ChartPanel cp = new ChartPanel(chart);
        return cp;
    }

}
