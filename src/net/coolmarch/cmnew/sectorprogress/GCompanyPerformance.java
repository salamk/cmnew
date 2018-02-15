/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author sania
 */
public class GCompanyPerformance extends JPanel {

    private ArrayList<SectorCap> sclist;

    
    

    public GCompanyPerformance(ArrayList<SectorCap> companiesList) {
        setLayout(new BorderLayout());
        this.sclist = companiesList;
    }

    private void createCompaniesChart() {
        DefaultCategoryDataset my_bar_chart_dataset = new DefaultCategoryDataset();
        Iterator i = sclist.iterator();
        int counter = 0;
        while (i.hasNext()) {
            if(counter<10){
            SectorCap sc = (SectorCap) i.next();
            my_bar_chart_dataset.addValue(sc.getCap(), "", sc.getSector());
            }else{
                break;
            }
            counter++;
        }

        JFreeChart bchart = ChartFactory.createBarChart("", "",
                "", my_bar_chart_dataset, PlotOrientation.HORIZONTAL,
                false, false, false);
        

        CategoryPlot plot = (CategoryPlot) bchart.getPlot();
        
        org.jfree.chart.renderer.category.BarRenderer rend = new BarRenderer();
        bchart.getCategoryPlot().setRenderer(rend);
        rend.setSeriesPaint(0, Color.blue);
        rend.setShadowVisible(false);
        rend.setSeriesOutlineStroke(0,new BasicStroke(0.0f));
        
        plot.setBackgroundPaint(getBackground());
        plot.setRangeGridlinePaint(Color.blue);

        //bchart.setBackgroundPaint(pnl.getBackground());
        //ChartPanel cp = new ChartPanel(bchart);
        ChartPanel cp = new ChartPanel(bchart, false);
        cp.getChart().setBackgroundPaint(getBackground());
        
        add(cp);
        this.update(this.getGraphics());
        validate();

    }


}
