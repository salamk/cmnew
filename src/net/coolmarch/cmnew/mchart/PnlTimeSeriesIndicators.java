/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.util.ArrayList;
import java.util.Date;
import net.coolmarch.cmnew.common.Quote;
import net.coolmarch.cmnew.mchart.ti.ADLine;
import net.coolmarch.cmnew.mchart.ti.OnBalanceVolume;
import net.coolmarch.cmnew.mchart.ti.RelativeStrengthIndex;
import net.coolmarch.cmnew.mchart.ti.TATR;
import net.coolmarch.cmnew.mchart.ti.TEMA;
import net.coolmarch.cmnew.mchart.ti.TMACD;
import net.coolmarch.cmnew.mchart.ti.TSMA;
import net.coolmarch.cmnew.mchart.ti.WilliamPercentRange;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataItem;

/**
 *
 * @author salam
 */
public class PnlTimeSeriesIndicators extends javax.swing.JPanel {

    /**
     * Creates new form PnlTimeSeries
     */
    private String currentIndicator = "";
    private Date startDate;
    private Date endDate;
    private ArrayList<OHLCDataItem> quoteList;

    public PnlTimeSeriesIndicators(ArrayList<OHLCDataItem> qlist) {
        initComponents();
        //this.quoteList = quoteList;
        this.quoteList = qlist;
        startDate = new Date();
        endDate = new Date();
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
        jToolBar1 = new javax.swing.JToolBar();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        jPanel2 = new javax.swing.JPanel();
        cmbIndicator = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfDays = new javax.swing.JTextField();
        pnlChart = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);
        jToolBar1.add(jSeparator1);

        cmbIndicator.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ADL", "WPR", "RSI", "OBV", "SMA", "EMA", "ATR" }));
        cmbIndicator.setSelectedIndex(2);

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("net/coolmarch/cmnew/mchart/Bundle"); // NOI18N
        jButton1.setText(bundle.getString("PnlTimeSeriesIndicators.jButton1.text")); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel2.setText(bundle.getString("PnlTimeSeriesIndicators.jLabel2.text")); // NOI18N

        tfDays.setText(bundle.getString("PnlTimeSeriesIndicators.tfDays.text")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(cmbIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tfDays, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 615, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(cmbIndicator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1)
                .addComponent(jLabel2)
                .addComponent(tfDays, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jToolBar1.add(jPanel2);

        jPanel1.add(jToolBar1, java.awt.BorderLayout.PAGE_END);

        pnlChart.setLayout(new java.awt.BorderLayout());
        jPanel1.add(pnlChart, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String indicator = (String) cmbIndicator.getSelectedItem();
        showIndicator(indicator);

    }//GEN-LAST:event_jButton1ActionPerformed

    private void showIndicator(String indicator) {
        if (indicator.compareToIgnoreCase("WPR") == 0) {
            showWpr();
            currentIndicator = "WPR";
        } else if (indicator.compareToIgnoreCase("OBV") == 0) {
            showObv();
            currentIndicator = "OBV";
        } else if (indicator.compareToIgnoreCase("RSI") == 0) {
            showRsi();
            currentIndicator = "RSI";
        } else if (indicator.compareToIgnoreCase("ADL") == 0) {
            showAdl();
            currentIndicator = "ADL";
        } else if (indicator.compareToIgnoreCase("MACD") == 0) {
            showMacd();
            currentIndicator = "MACD";
        } else if (indicator.compareToIgnoreCase("SMA") == 0) {
            showSMA();
            currentIndicator = "SMA";
        } else if (indicator.compareToIgnoreCase("EMA") == 0) {
            showEMA();
            currentIndicator = "EMA";
        } else if (indicator.compareToIgnoreCase("ATR") == 0) {
            showATR();
            currentIndicator = "ATR";
        }

    }

    public void redraw(String indicator) {
        showIndicator(indicator);
    }

    public String getCurrentIndicator() {
        return currentIndicator;
    }

    public void setCurrentIndicator(String currentIndicator) {
        this.currentIndicator = currentIndicator;
    }

    private void setChart(TimeSeriesCollection tsc) {
        pnlChart.removeAll();
        TimeSeriesChart tchart = new TimeSeriesChart(tsc);
        JFreeChart chart = tchart.getChart();
        ChartPanel cp = new ChartPanel(chart);
        pnlChart.add(cp);
        cp.addOverlay(tchart.getCrosshairOverlay());
        cp.setHorizontalAxisTrace(true);
        cp.setVerticalAxisTrace(true);

        if (pnlChart.getGraphics() != null) {
            pnlChart.update(pnlChart.getGraphics());
            pnlChart.validate();
        } else {

        }
    }

    public void showWpr() {
        int day = Integer.parseInt(tfDays.getText());
        WilliamPercentRange wpr = new WilliamPercentRange(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeriesCollection tsc = wpr.getTsc();
        setChart(tsc);

    }

    public void showObv() {
        int day = Integer.parseInt(tfDays.getText());
        OnBalanceVolume obv = new OnBalanceVolume(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeriesCollection tsc = obv.getTimeSeriesCollection();
        setChart(tsc);
    }

    public void showAdl() {
        int day = Integer.parseInt(tfDays.getText());
        ADLine adl = new ADLine(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeriesCollection tsc = adl.getTimeSeriesCollection();
        setChart(tsc);
    }

    public void showRsi() {
        int day = Integer.parseInt(tfDays.getText());
        RelativeStrengthIndex rsi = new RelativeStrengthIndex(quoteList,
                getEndDate(),
                getStartDate(), day);
        TimeSeriesCollection tsc = rsi.getTimeSeriesCollection();
        TimeSeriesChart tchart = new TimeSeriesChart(tsc);
        tchart.getRangeAxis().setAutoRangeIncludesZero(true);
        tchart.getRangeAxis().setUpperBound(100);
        tchart.drawHorizontalLine(30);
        tchart.drawHorizontalLine(80);
        JFreeChart chart = tchart.getChart();
        ChartPanel cp = new ChartPanel(chart);
        cp.addOverlay(tchart.getCrosshairOverlay());
        cp.setHorizontalAxisTrace(true);
        cp.setVerticalAxisTrace(true);
        pnlChart.removeAll();
        pnlChart.add(cp);
        pnlChart.update(pnlChart.getGraphics());
        pnlChart.validate();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbIndicator;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTextField tfDays;
    // End of variables declaration//GEN-END:variables

    private void showMacd() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int day = Integer.parseInt(tfDays.getText());
        TMACD macd = new TMACD(quoteList, getEndDate(),
                getStartDate(), day);

        TimeSeries ms = macd.getTsMacd();
        TimeSeries msig = macd.getTsSignal();
        TimeSeries mhis = macd.getTsHistogram();

        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(ms);
        tsc.addSeries(msig);
        tsc.addSeries(mhis);

        TimeSeriesChart tchart = new TimeSeriesChart(tsc);
        tchart.getRangeAxis().setAutoRangeIncludesZero(true);
        //tchart.getRangeAxis().setUpperBound(100);
        tchart.drawHorizontalLine(0);
        //tchart.drawHorizontalLine(80);
        JFreeChart chart = tchart.getChart();
        ChartPanel cp = new ChartPanel(chart);
        cp.addOverlay(tchart.getCrosshairOverlay());
        cp.setHorizontalAxisTrace(true);
        cp.setVerticalAxisTrace(true);
        pnlChart.removeAll();
        pnlChart.add(cp);
        pnlChart.update(pnlChart.getGraphics());
        pnlChart.validate();

    }

    private void showSMA() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int day = Integer.parseInt(tfDays.getText());
        TSMA tsma = new TSMA(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeries close = tsma.getTsClose();
        TimeSeries sma = tsma.getTs();
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(close);
        tsc.addSeries(sma);
        setChart(tsc);
    }

    private void showEMA() {
        int day = Integer.parseInt(tfDays.getText());
        TEMA tema = new TEMA(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeries close = tema.getTsClose();
        TimeSeries sma = tema.getTs();
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(close);
        tsc.addSeries(sma);
        setChart(tsc);
    }

    private void showATR() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        int day = Integer.parseInt(tfDays.getText());
        TATR tema = new TATR(quoteList, getEndDate(),
                getStartDate(), day);
        TimeSeries atr = tema.getTs();
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        tsc.addSeries(atr);
        setChart(tsc);
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
