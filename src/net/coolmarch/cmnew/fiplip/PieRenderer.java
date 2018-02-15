/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author sania
 */
public class PieRenderer {

    

    public PieRenderer() {
        
    }

    public void setColor(PiePlot plot, DefaultPieDataset dataset) {
        List<Comparable> keys = dataset.getKeys();
        ColorList x = new ColorList();
        ArrayList<Color> clist = x.getColorList();
        Collections.shuffle(clist);
        for (int i = 0; i <= keys.size()-1; i++) {
            plot.setSectionPaint(keys.get(i), (Color)clist.get(i));
        }
    }
}
