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
import java.util.TreeMap;
import javax.swing.JPanel;
import net.coolmarch.cmnew.common.CommonText;

/**
 *
 * @author sania
 */
public class GListPanel extends JPanel {

    private GradientPaint gradient = new GradientPaint(0, 0, Color.red, 175,
            175, new Color(0, 153, 0), true);

    private Date date;
    private final int textLength;
    private final TreeMap<String, T_DD> spmap;
    public static final int R_ALIGN = 0;
    public static final int L_ALIGN = 1;
    private final int textAlign;
    private final int valueOffset;

    public GListPanel(TreeMap<String, T_DD> tmap, int textLength, int textAlign, int valueOffset) {
        this.spmap = tmap;
        this.textLength = textLength;
        this.textAlign = textAlign;
        this.valueOffset = valueOffset;
    }

    public void paintComponent(Graphics g) {
        clear(g);
        Graphics2D g2d = (Graphics2D) g;
        double x = 0;
        double y = 10;
        double fact = 15;

        DecimalFormat td = new DecimalFormat("#.##");
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 12));

        for (String str : spmap.keySet()) {
            T_DD vals = spmap.get(str);
            double val1 = vals.getValue1();
            double val2 = vals.getValue2();
            if (textAlign == R_ALIGN) {
                str = CommonText.monosize(str, textLength);
            } else {
                str = CommonText.monosizeLeft(str, textLength);
            }

            String spcIn = td.format(val1);
            String spcOut = td.format(Math.abs(val2));
            
            spcIn = CommonText.monosizeLeft(spcIn, 5);
            spcOut = CommonText.monosizeLeft(spcOut, 5);
            
            spcIn = "[ "+spcIn+" ]";
            spcOut = "[ "+spcOut+" ]";

            g2d.setPaint(Color.blue);
            g2d.drawString(str, (float) (10), (float) y + 10);
            g2d.setPaint(gradient);
            String spc = "[ " + spcIn + " ]   [ " + spcOut + " ]";
            g2d.setPaint(new Color(0,128,0));
            g2d.drawString(spcIn, (float) (valueOffset), (float) y + 10);
            g2d.setPaint(new Color(255,0,0));
            g2d.drawString(spcOut, (float) (valueOffset+65), (float) y + 10);
            g2d.setPaint(Color.blue);

            y += fact;
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
