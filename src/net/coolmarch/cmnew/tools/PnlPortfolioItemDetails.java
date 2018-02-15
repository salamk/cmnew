/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tools;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.TickData;
import net.coolmarch.cmnew.mchart.PDataChart;
import net.coolmarch.cmnew.mchart.PDataDaemon;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.ui.Align;
import org.jfree.ui.RectangleAnchor;

/**
 *
 * @author coolmarch
 */
public class PnlPortfolioItemDetails extends javax.swing.JPanel {

    /**
     * Creates new form PnlPortfolioItemDetails
     */
    private static final ArrayList<SymbolInformation> siList = new ArrayList<>();

    public PnlPortfolioItemDetails() {
        initComponents();
        CMImporter cmi = new CMImporter("symbol_statistics.zip");
        ArrayList<String> lines = cmi.getLines();
        for (String line : lines) {
            //System.out.println("Line is: >>>>: "+l);
            String h = "", l = "", w = "";
            String[] toke = line.split(",");
            String symbol = toke[0];
            if (toke[1] == null) {
                h = "0";
            } else {
                h = toke[1];
            }
            if (toke[2] == null) {
                l = "0";
            } else {
                l = toke[2];
            }

            try {
                if (toke[3] == null) {
                    w = l;
                } else {
                    w = toke[3];
                }
            } catch (Exception e) {
                w = l;
            }

            double fiftyTwoWeekHigh = Double.parseDouble(h);
            double fiftyTwoWeekLow = Double.parseDouble(l);
            double wcRate = Double.parseDouble(w);

            SymbolInformation si = new SymbolInformation();
            si.setFiftyTwoWeekHigh(fiftyTwoWeekHigh);
            si.setFiftyTwoWeekLow(fiftyTwoWeekLow);
            si.setSymbol(symbol);
            si.setWcRate(wcRate);

            siList.add(si);

        }

    }
    
    public void clear(){
        this.tfCurrentRate.setText("");
        this.tfFiftyTwoWeekHigh.setText("");
        this.tfFiftyTwoWeekLow.setText("");
        this.tfPercentDrop.setText("");
        this.tfPl.setText("");
        this.tfQty.setText("");
        this.tfRate.setText("");
        this.tfRate.setText("");
//        this.tfWc.setText("");
        
    }

    public SymbolInformation getSymbolInformation(String symbol) {
        for (SymbolInformation si : siList) {
            if (si.getSymbol().compareToIgnoreCase(symbol) == 0) {
                return si;
            }
        }

        return null;
    }

    public void set52WHigh(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfFiftyTwoWeekHigh.setText(df.format(value));
    }

    public void set52WLow(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfFiftyTwoWeekLow.setText(df.format(value));
    }

//    public void setWCase(double value) {
//        DecimalFormat df = new DecimalFormat("#.##");
//        this.tfWc.setText(df.format(value));
//    }

    public void setQty(int value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfQty.setText(df.format(value));
    }

    public void setRate(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfRate.setText(df.format(value));
    }

    public void setCurrentRate(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfCurrentRate.setText(df.format(value));
    }

    public void setPl(double value) {
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfPl.setText(df.format(value));
    }

    public void setChart(String symbol) {
        CMResource cmr = new CMResource();
        PDataDaemon tdf = new PDataDaemon(symbol);
        ArrayList<TickData> tlist = tdf.getQuoteList();
        PDataChart pvc = new PDataChart(tlist);
        ChartPanel cp = pvc.getChartPanel();
        cp.setBackground(p.getBackground());
        JFreeChart chart = cp.getChart();
        chart.setBackgroundPaint(p.getBackground());

        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.3f);

        double cpos = 0;
        try {
            cpos = Double.parseDouble(this.tfCurrentRate.getText());
        } catch (Exception e) {
        }
        
        chart.getCategoryPlot().clearRangeMarkers();
        ValueMarker vm = new ValueMarker(cpos);
        vm.setPaint(Color.blue);
        vm.setLabelAnchor(RectangleAnchor.CENTER);
        vm.setLabelBackgroundColor(Color.yellow);
        vm.setLabelPaint(Color.blue);
        vm.setLabel(symbol);
        String info = symbol + " Current: " + cpos;
        chart.getCategoryPlot().addRangeMarker(vm);

        //renderer.setSeriesPaint(1, Color.magenta);
        p.removeAll();
        p.add(cp);
        p.update(p.getGraphics());
        p.validate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        tfCurrentRate = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfPl = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        tfQty = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfRate = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        tfFiftyTwoWeekHigh = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfFiftyTwoWeekLow = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfPercentDrop = new javax.swing.JTextField();
        cHyperLink3 = new acr.component.CHyperLink();
        tfPercentDropPl = new javax.swing.JTextField();
        p = new javax.swing.JPanel();

        tfCurrentRate.setEditable(false);
        tfCurrentRate.setForeground(new java.awt.Color(0, 0, 102));
        tfCurrentRate.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel4.setText("P/L");

        tfPl.setEditable(false);
        tfPl.setForeground(new java.awt.Color(0, 0, 102));
        tfPl.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel1.setText("Quantity");

        tfQty.setEditable(false);
        tfQty.setForeground(new java.awt.Color(0, 0, 102));
        tfQty.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel2.setText("Rate");

        tfRate.setEditable(false);
        tfRate.setForeground(new java.awt.Color(0, 0, 102));
        tfRate.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel3.setText("Current");

        jLabel13.setText("*PL if rate is @52 week high");

        jLabel14.setText("*PL if rate is @52 week low");

        jLabel5.setText("PL@52wh");

        tfFiftyTwoWeekHigh.setEditable(false);
        tfFiftyTwoWeekHigh.setForeground(new java.awt.Color(51, 0, 102));
        tfFiftyTwoWeekHigh.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel6.setText("PL@52wl");

        tfFiftyTwoWeekLow.setEditable(false);
        tfFiftyTwoWeekLow.setForeground(new java.awt.Color(51, 0, 102));
        tfFiftyTwoWeekLow.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        jLabel9.setText("PL-%Drop");

        tfPercentDrop.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tfPercentDropKeyPressed(evt);
            }
        });

        cHyperLink3.setText("write percentage and press enter");

        tfPercentDropPl.setEditable(false);
        tfPercentDropPl.setForeground(new java.awt.Color(51, 0, 102));
        tfPercentDropPl.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(6, 6, 6)
                                .addComponent(tfFiftyTwoWeekHigh, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(tfFiftyTwoWeekLow, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel13)
                            .addComponent(jLabel14)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfPercentDrop, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(4, 4, 4)
                        .addComponent(tfPercentDropPl, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cHyperLink3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfFiftyTwoWeekHigh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(tfFiftyTwoWeekLow, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(49, 49, 49)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfPercentDrop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9)
                    .addComponent(cHyperLink3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfPercentDropPl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tfQty, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfRate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(tfCurrentRate, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tfPl, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(tfQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfCurrentRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(tfPl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        p.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(p, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(p, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 16, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tfPercentDropKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tfPercentDropKeyPressed
        // TODO add your handling code here:
        int keyCode = evt.getKeyCode();
        if(keyCode == KeyEvent.VK_ENTER){
            calculatePercentDrop();
        }
    }//GEN-LAST:event_tfPercentDropKeyPressed


    private void calculatePercentDrop(){
        double current_rate = Double.parseDouble(tfCurrentRate.getText());
        double percent = Double.parseDouble(this.tfPercentDrop.getText());
        double thenPrice = current_rate - (current_rate*(percent/100));
        
        int qty = Integer.parseInt(tfQty.getText());
        double buy_rate = Double.parseDouble(this.tfRate.getText());
        double amount_actual = qty*buy_rate;
        double amount_then = qty*thenPrice;
        double pl = amount_then - amount_actual;
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfPercentDropPl.setText(df.format(pl));
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private acr.component.CHyperLink cHyperLink3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel p;
    private javax.swing.JTextField tfCurrentRate;
    private javax.swing.JTextField tfFiftyTwoWeekHigh;
    private javax.swing.JTextField tfFiftyTwoWeekLow;
    private javax.swing.JTextField tfPercentDrop;
    private javax.swing.JTextField tfPercentDropPl;
    private javax.swing.JTextField tfPl;
    private javax.swing.JTextField tfQty;
    private javax.swing.JTextField tfRate;
    // End of variables declaration//GEN-END:variables
}

class SymbolInformation {

    private String symbol;
    private double fiftyTwoWeekHigh;
    private double fiftyTwoWeekLow;
    private double wcRate;

    public SymbolInformation() {
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getFiftyTwoWeekHigh() {
        return fiftyTwoWeekHigh;
    }

    public void setFiftyTwoWeekHigh(double fiftyTwoWeekHigh) {
        this.fiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }

    public double getFiftyTwoWeekLow() {
        return fiftyTwoWeekLow;
    }

    public void setFiftyTwoWeekLow(double fiftyTwoWeekLow) {
        this.fiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public double getWcRate() {
        return wcRate;
    }

    public void setWcRate(double wcRate) {
        this.wcRate = wcRate;
    }

}
