/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Image;
import javax.swing.ImageIcon;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.SegmentedTimeline;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.ui.Align;

/**
 *
 * @author salam
 */
public class TimeSeriesChart {

    private final JFreeChart chart;
    private final DateAxis domainAxis;
    private final NumberAxis rangeAxis;
    private final XYItemRenderer renderer;
    private final XYPlot mainPlot;
    private static Crosshair xCrosshair;
    private static Crosshair yCrosshair;
    private CrosshairOverlay crosshairOverlay;
    

    public TimeSeriesChart(TimeSeriesCollection tsc) {
//        Color bg = Color.black;

//        Color fg = new Color(0,255,0,255);
        CMResource cmr = new CMResource();
        domainAxis = new DateAxis("");
        domainAxis.setTimeline(
                SegmentedTimeline.newMondayThroughFridayTimeline());

        rangeAxis = new NumberAxis("");
        rangeAxis.setAutoRangeIncludesZero(false);

        crosshairOverlay = new CrosshairOverlay();
        this.xCrosshair = new Crosshair(
                Double.NaN, Color.GRAY, new BasicStroke(0f));
        this.xCrosshair.setLabelVisible(true);

        yCrosshair = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

        crosshairOverlay.addDomainCrosshair(xCrosshair);
        crosshairOverlay.addRangeCrosshair(yCrosshair);

        renderer = new XYLineAndShapeRenderer(true, false);
//      renderer.setSeriesPaint(0, fg);
        mainPlot = new XYPlot(tsc, domainAxis, rangeAxis, renderer);
//        mainPlot.setDomainGridlinePaint(Color.white);
//        mainPlot.setRangeGridlinePaint(Color.white);
//        mainPlot.setDomainGridlineStroke(new BasicStroke(0.5f));
        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();

//        mainPlot.setBackgroundPaint(bg);
        chart = new JFreeChart("", null,
                mainPlot, false);

        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.4f);

    }

    public Crosshair getxCrosshair() {
        return xCrosshair;
    }

    public static void setXCrosshairValue(double value) {
        xCrosshair.setValue(value);
    }

    public void setxCrosshair(Crosshair xCrosshair) {
        this.xCrosshair = xCrosshair;
    }

    public CrosshairOverlay getCrosshairOverlay() {
        return crosshairOverlay;
    }

    public void setCrosshairOverlay(CrosshairOverlay crosshairOverlay) {
        this.crosshairOverlay = crosshairOverlay;
    }

    public void addTimeSeriesCollection(TimeSeriesCollection ts, int seriesNumber, Color c) {
        mainPlot.setDataset(seriesNumber, ts);
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        mainPlot.setRenderer(seriesNumber, r);
        r.setSeriesPaint(0, c);
    }

    public JFreeChart getChart() {
        return chart;
    }

    public DateAxis getDomainAxis() {
        return domainAxis;
    }

    public NumberAxis getRangeAxis() {
        return rangeAxis;
    }

    public XYItemRenderer getRenderer() {
        return renderer;
    }

    public XYPlot getMainPlot() {
        return mainPlot;
    }

    public void drawHorizontalLine(double position) {
        ValueMarker marker = new ValueMarker(position);  // position is the value on the axis
        marker.setPaint(Color.black);
        mainPlot.addRangeMarker(marker);
    }
   
}
