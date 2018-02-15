/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

/**
 *
 * @author sania
 */
// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 
import java.awt.Color;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.ImageIcon;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.*;
import org.jfree.data.time.*;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Align;

public class PriceVolumeChart {

    private static TimeSeries ptimeSeries;
    private static TimeSeries vtimeSeries;
    private static String symbol;
    private static Date date;
    private ArrayList al;
    private ChartPanel chartPanel;

    public PriceVolumeChart(String s, ArrayList<TickData> al, Date d) {

        symbol = s;
        date = d;
        this.al = al;
        setTimeSeries(al);

        JFreeChart jfreechart = createIconicChart();
        chartPanel = new ChartPanel(jfreechart, true, true, true, false, true);

    }

    public PriceVolumeChart(String s, ArrayList<TickData> al, Date d, String type) {

        symbol = s;
        date = d;
        this.al = al;
        setTimeSeries(al);

        JFreeChart jfreechart = createChart();
        chartPanel = new ChartPanel(jfreechart, true, true, true, false, true);

    }

    public ChartPanel getPriceVolumeChartPanel() {
        return chartPanel;
    }

    public JFreeChart createChart() {
        XYDataset xydataset = createPriceDataset(al);
        String s = symbol;
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart(s,
                "", "", xydataset, true, true, false);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setLowerMargin(0.40000000000000002D);
        DecimalFormat decimalformat = new DecimalFormat("00.00");
        numberaxis.setNumberFormatOverride(decimalformat);
        XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        xyitemrenderer.setBaseToolTipGenerator(
                new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                        new SimpleDateFormat("d-MMM-yyyy"),
                        new DecimalFormat("0.00")));

        NumberAxis numberaxis1 = new NumberAxis("");
        numberaxis1.setUpperMargin(1.0D);
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.setDataset(1, createVolumeDataset(al));
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.mapDatasetToRangeAxis(1, 1);

        XYBarRenderer xybarrenderer
                = new XYBarRenderer(0.20000000000000001D);

        xybarrenderer.setBaseToolTipGenerator(
                new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                        new SimpleDateFormat("d-MMM-yyyy"),
                        new DecimalFormat("0,000.00")));

        xyplot.setRenderer(1, xybarrenderer);

        //  ChartUtilities.applyCurrentTheme(jfreechart);
        xybarrenderer.setBarPainter(new StandardXYBarPainter());
        xybarrenderer.setShadowVisible(false);
        return jfreechart;
    }

    public JFreeChart createIconicChart() {
        XYDataset xydataset = createPriceDataset(al);
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("", "", "",
                xydataset, false, false, false);

        XYPlot xyplot = (XYPlot) jfreechart.getPlot();

        NumberAxis numberaxis = (NumberAxis) xyplot.getRangeAxis();
        numberaxis.setLowerMargin(0.40000000000000002D);
        DecimalFormat decimalformat = new DecimalFormat("00.00");
        numberaxis.setNumberFormatOverride(decimalformat);
        XYItemRenderer xyitemrenderer = xyplot.getRenderer();

        xyitemrenderer.setBaseToolTipGenerator(
                new StandardXYToolTipGenerator("{0}: ({1}, {2})",
                        new SimpleDateFormat("d-MMM-yyyy"),
                        new DecimalFormat("0.00")));

        xyitemrenderer.setBaseOutlinePaint(Color.magenta);
        NumberAxis numberaxis1 = new NumberAxis("");
        numberaxis1.setUpperMargin(1.0D);

        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.setDataset(1, createVolumeDataset(al));
        xyplot.setRangeAxis(1, numberaxis1);
        xyplot.mapDatasetToRangeAxis(1, 1);

        XYBarRenderer xybarrenderer = new XYBarRenderer(0.20000000000000001D);
        xybarrenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator(
                "{0}: ({1}, {2})", new SimpleDateFormat("d-MMM-yyyy"),
                new DecimalFormat("0,000.00")));

        xyplot.setRenderer(1, xybarrenderer);
        xybarrenderer.setBarPainter(new StandardXYBarPainter());
        xybarrenderer.setShadowVisible(false);
        
        return jfreechart;
    }

    private void setTimeSeries(ArrayList<TickData> al) {
        ptimeSeries = new TimeSeries("");
        vtimeSeries = new TimeSeries("");

        Iterator i = al.iterator();
        int counter = 0;
        int hour = 1;
        while (i.hasNext()) {
            TickData dq = (TickData) i.next();
            double price = dq.getDailyQuotePrice();
            int volume = dq.getDailyQuoteVolume();
            //final Hour hour = new Hour(date);
            //series.add(new Minute(1, hour), 10.2);
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            int day = c.get(Calendar.DAY_OF_MONTH);

            int month = c.get(Calendar.MONTH);

            int year = c.get(Calendar.YEAR);

            c.add(Calendar.MINUTE, counter);

            Second ms = new Second(c.getTime());
            ptimeSeries.add(ms, price);
            vtimeSeries.add(ms, volume);
            counter++;
            hour++;
        }
    }

    private static XYDataset createPriceDataset(ArrayList<TickData> al) {
        return new TimeSeriesCollection(ptimeSeries);
    }

    private static IntervalXYDataset createVolumeDataset(ArrayList<TickData> al) {
        return new TimeSeriesCollection(vtimeSeries);
    }

}
