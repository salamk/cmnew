/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JPanel;
import net.coolmarch.cmnew.common.CommonText;

/**
 *
 * @author sania
 */
public class GSectorPercentCap extends JPanel {

    private GradientPaint gradient = new GradientPaint(0, 0, Color.red, 175,
            175, new Color(0, 153, 0), true);

    private ArrayList<SectorCap> sclist;

    public GSectorPercentCap(ArrayList<SectorCap> sclist) {
        this.sclist = sclist;
    }

    public void paintComponent(Graphics g) {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));

        double x = 325;
        double y = 10;
        double fact = 15;
        // Set s = spmap.keySet();
        Iterator i = sclist.iterator();
        while (i.hasNext()) {
            SectorCap sc = (SectorCap) i.next();
            String secName = sc.getSector().toLowerCase();
            secName = CommonText.monosizeLeft(secName, 25);
            String cat = sc.getCategory();
            cat = CommonText.monosize(cat, 25);
            double marketCap = sc.getCap();
            
            GradientPaint gc = null;
            
            String stats = sc.getSectorProgress();
            if(stats.compareToIgnoreCase("negative")==0){
                gc = new GradientPaint(0,0,Color.red,175,175,Color.magenta, true );
            }else if(stats.compareToIgnoreCase("positive")==0){
                gc = new GradientPaint(0,0,Color.green,175,175,Color.magenta, true );
            }else{
                gc = new GradientPaint(0,0,Color.blue,175,175,Color.magenta, true );
            }

            if (marketCap > 0.1) {
                DecimalFormat td = new DecimalFormat("#.##");
                String n = td.format(marketCap);

                n = CommonText.monosize(n, 5);
                String str = cat + " : " + secName + " [" + n + "] ";
                g2d.setPaint(Color.blue);
                g2d.drawString(str, (float) (5), (float) y + 10);
                g2d.setPaint(gc);
                g2d.fill(getRect(x + 150, y, marketCap * 8, 10));

                y += fact;
            }

        }

    }

    private Rectangle2D.Double getRect(double x, double y,
            double w, double h) {
        return new Rectangle2D.Double(x, y, w, h);
    }

    private Ellipse2D.Double getEllipse(double x, double y,
            double r1, double r2) {
        return new Ellipse2D.Double(x, y, r1, r2);
    }

    private void clear(Graphics g) {
        super.paintComponent(g);
    }

}
