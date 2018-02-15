/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JPanel;
import net.coolmarch.cmnew.common.CommonText;

/**
 *
 * @author sania
 */
public class GMapPanel extends JPanel {

    private GradientPaint gradient = new GradientPaint(0, 0, Color.red, 175,
            175, new Color(0, 153, 0), true);

    private Date date;
    private int textLength;
    private TreeMap<String, Double> spmap;
    public static final int R_ALIGN = 0;
    public static final int L_ALIGN = 1;
    private int textAlign;
    private int valueOffset;

    public GMapPanel(TreeMap<String, Double> tmap) {
        Map sortedMap = new TreeMap(new ValueComparator(tmap));
        sortedMap.putAll(tmap);
        this.spmap = (TreeMap) sortedMap;
        this.textLength = 40;
        this.textAlign = R_ALIGN;
        this.valueOffset = 300;
    }

    public GMapPanel(TreeMap<String, Double> tmap, int textLength) {
        Map sortedMap = new TreeMap(new ValueComparator(tmap));
        sortedMap.putAll(tmap);
        this.spmap = (TreeMap) sortedMap;
        this.textLength = textLength;
        this.textAlign = R_ALIGN;
        this.valueOffset = 300;
    }

    public GMapPanel(TreeMap<String, Double> tmap, int textLength, int align) {
        Map sortedMap = new TreeMap(new ValueComparator(tmap));
        sortedMap.putAll(tmap);
        this.spmap = (TreeMap) sortedMap;
        this.textLength = textLength;
        if (align == 0 || align == 1) {
            this.textAlign = align;
        } else {
            this.textAlign = R_ALIGN;
        }
        
        this.valueOffset = 300;
    }

    public GMapPanel(TreeMap<String, Double> tmap, int textLength, int align,
            int valueOffset) {
        
        Map sortedMap = new TreeMap(new ValueComparator(tmap));
        sortedMap.putAll(tmap);
        this.spmap = (TreeMap) sortedMap;
        this.textLength = textLength;
        if (align == 0 || align == 1) {
            this.textAlign = align;
        } else {
            this.textAlign = R_ALIGN;
        }
        
        this.valueOffset = valueOffset;
    }

    public void paintComponent(Graphics g) {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;
        double x = 0;
        double y = 10;
        double fact = 15;
        Set s = spmap.keySet();
        DecimalFormat td = new DecimalFormat("#.##");

        double sum = 0;
        double n = 0;

        for (String sec : spmap.keySet()) {
            sum += spmap.get(sec);
            n++;
        }

        double avg = sum / n;

        for (String sector : spmap.keySet()) {
            double pc = spmap.get(sector);
            g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));
            if (!(pc == 0)) {
                String str = sector;
                if (textAlign == R_ALIGN) {
                    str = CommonText.monosize(str, textLength);
                } else {
                    str = CommonText.monosizeLeft(str, textLength);
                }

                String spc = td.format(pc);
                g2d.setPaint(Color.blue);
                g2d.drawString(str, (float) (10), (float) y + 10);
                g2d.setPaint(gradient);
                spc = "[ " + spc + " ]";
                g2d.drawString(spc, (float) (valueOffset), (float) y + 10);

                // g2d.fill(getRect(x, y, pc*10-avg, 10));
                y += fact;
            }
        }

    }

    private Rectangle2D.Double getRect(double x, double y, double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }

    private Ellipse2D.Double getEllipse(double x, double y, double r1, double r2) {
        return new Ellipse2D.Double(x, y, r1, r2);
    }

    private void clear(Graphics g) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        super.paintComponent(g);
    }

}
