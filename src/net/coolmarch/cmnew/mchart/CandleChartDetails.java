/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.awt.Color;
import java.awt.GradientPaint;
import javax.swing.JColorChooser;
import javax.swing.JOptionPane;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;

/**
 *
 * @author salam
 */
public class CandleChartDetails {

    private final CandlestickRenderer renderer;
    private final XYPlot mainPlot;

    public CandleChartDetails(JFreeChart chart) {
        this.renderer = (CandlestickRenderer) chart.getXYPlot().getRenderer();
        this.mainPlot = chart.getXYPlot();
    }
     
    public CandleChartDetails(XYPlot mainPlot, CandlestickRenderer renderer){
        this.mainPlot = mainPlot;
        this.renderer = renderer;
    }

    public CandleChartDetails() {
        renderer = null;
        mainPlot = null;
    }

    public void changeCandleOutlineColor(Color c) {
        renderer.setSeriesPaint(0, c);
    }

    public void flipVolumeBarVisibility() {
        if (renderer.getDrawVolume() == true) {
            renderer.setDrawVolume(false);
        } else {
            renderer.setDrawVolume(true);
        }
    }

    public void setVolumeBarColor() {
        Color color = getColor((Color) renderer.getVolumePaint());
        renderer.setVolumePaint(color);
    }

    public void flipRangeGridLineVisible() {
        if (mainPlot.isRangeGridlinesVisible()) {
            mainPlot.setRangeGridlinesVisible(false);
        } else {
            mainPlot.setRangeGridlinesVisible(true);
        }
    }

    public void flipDomainGridLineVisible() {
        if (mainPlot.isDomainGridlinesVisible()) {
            mainPlot.setDomainGridlinesVisible(false);
        } else {
            mainPlot.setDomainGridlinesVisible(true);
        }
    }

    public void setMainPlotBackground() {
        Color c = Color.white;
        try{
            
            c = getColor((Color) mainPlot.getBackgroundPaint());
        }catch(Exception e){
            c = Color.white;
        }
        
        mainPlot.setBackgroundPaint(c);
    }
    
    public void setMainPlotBackgroundGradient(){
        GradientColor gc = new GradientColor();
        int answer = JOptionPane.showConfirmDialog(null, gc);
        if(answer == JOptionPane.YES_OPTION){
            Color c1 = gc.getStartColor();
            Color c2 = gc.getEndColor();
            
            GradientPaint gp = new GradientPaint(0,0,c1,0,500, c2);
            mainPlot.setBackgroundPaint(gp);
            
        }
    }

    private Color getColor(Color previousColor) {
        Color c = JColorChooser.showDialog(null, "Choose a Color",
                previousColor);
        if (c == null) {
            c = previousColor;
        }

        return c;
    }
    
    public void setCandleColor(){
        CandleColorSetting ccs = new CandleColorSetting();
        int answer = JOptionPane.showConfirmDialog(null, ccs);
        if(answer == JOptionPane.YES_OPTION){
            Color gain = ccs.getGainColor();
            Color loss = ccs.getLossColor();
            Color outline = ccs.getOutlineColor();
            changeCandleColor(gain, loss, outline);
        }
    }
    
    private void changeCandleColor(Color gain, Color loss, Color outline){
        renderer.setUpPaint(gain);
        renderer.setDownPaint(loss);
        renderer.setSeriesPaint(0, outline);
    }
    
    private void setSMA(int days){
        
    }

}
