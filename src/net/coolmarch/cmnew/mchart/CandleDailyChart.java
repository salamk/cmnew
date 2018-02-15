/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.awt.Color;
import java.awt.Image;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.Align;

/**
 *
 * @author salam
 */
public class CandleDailyChart {

    private ChartPanel cp;
    private static HashMap<String, Integer> layer;
    private static int seriesNumber = 0;
    private Date startDate;
    private Date endDate;

    public CandleDailyChart(XYDataset dataset) {
        CMResource cmr = new CMResource();
        DateAxis domainAxis = new DateAxis("");
        NumberAxis rangeAxis = new NumberAxis("");
        CandlestickRenderer renderer = new CandlestickRenderer();
        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);
        layer = new HashMap<String, Integer>();
        renderer.setSeriesPaint(0, Color.black);
        renderer.setDrawVolume(true);
        //renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_SMALLEST);
        if (dataset.getItemCount(0) <= 90) {
            renderer.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);
        } else if(dataset.getItemCount(0)<= 300){
            renderer.setCandleWidth(3.5);
        } else if(dataset.getItemCount(0) > 300){
            renderer.setCandleWidth(2.5);
        }
        renderer.setSeriesToolTipGenerator(0, null);

        rangeAxis.setAutoRangeIncludesZero(false);

        domainAxis.setTimeline(
                SegmentedTimeline.newMondayThroughFridayTimeline());
        JFreeChart chart = new JFreeChart("", null,
                mainPlot, false);

        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.3f);
        rangeAxis.setAutoRangeIncludesZero(false);

        cp = new ChartPanel(chart, true, true, true, true, false);
        cp.zoomInBoth(0.5, 0.5);
        cp.getChart().getXYPlot().getDomainAxis().setAutoRange(true);
        cp.getChart().getXYPlot().getRangeAxis().setAutoRange(true);

    }

    public ChartPanel getChartPanel() {
        return cp;
    }

    public void setOverlay(TimeSeriesCollection ts, Color c, String identifier) {
        seriesNumber = seriesNumber + 1;
        XYPlot mainPlot = cp.getChart().getXYPlot();
        mainPlot.setDataset(seriesNumber, ts);
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        mainPlot.setRenderer(seriesNumber, r);
        r.setSeriesPaint(0, c);
        layer.put(identifier, seriesNumber);
    }

    public void removeOverlay(String identifier) {
        int sr = this.getOverlaySeriesNumber(identifier);
        if (sr == -1) {
        } else {
            // System.out.println("Series Number is: " + seriesNumber);
            XYPlot mainPlot = cp.getChart().getXYPlot();
            mainPlot.setDataset(sr, null);
            mainPlot.setRenderer(sr, null);
            layer.remove(identifier);
        }

    }

    public int getOverlaySeriesNumber(String identifier) {
        int sn = -1;
        if (layer.containsKey(identifier)) {
            sn = layer.get(identifier);
        } else {
            sn = -1;
        }

        return sn;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

}
