/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tickanalysis;

import cmnew.InterfaceMain2;
import ij.ImageJ;
import ij.io.Opener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import javax.swing.ImageIcon;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import javax.swing.JOptionPane;
import net.coolmarch.cmnew.common.Quote;
import net.coolmarch.cmnew.common.TickData;
import net.coolmarch.cmnew.mchart.CUPriceVolumeChart;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.ui.Align;

/**
 *
 * @author sania
 */
public class PnlIntradayDailyQuote extends javax.swing.JPanel {

    /**
     * Creates new form PnlDailyQuote
     */
    private String symbol;
    private ArrayList<TickData> al;
    private JFreeChart jfc;
    public static final String intraday_file_path = "/home/sania/pdata/cache/intraday/";

    public PnlIntradayDailyQuote() {
        initComponents();
        setToolTipText("Intraday");
        al = new ArrayList<>();
    }

    public PnlIntradayDailyQuote(String symbol) {
        initComponents();
        setToolTipText("Intraday");
        this.symbol = symbol;
        
        
    }

    private void showChart(int days) {
        CUPriceVolumeChart cu = new CUPriceVolumeChart(symbol, days);
        al = cu.getTickList();
        ChartPanel cp = cu.getChartPanel2();
        cp.setBackground(this.getBackground());
        jfc = cp.getChart();
        jfc.setBackgroundPaint(this.getBackground());
        CMResource cmr = new CMResource();
        ImageIcon img = cmr.getIcon("chartbgimg.png");
        cp.getChart().getXYPlot().setBackgroundImage(img.getImage());
        cp.getChart().getXYPlot().setBackgroundImageAlignment(Align.CENTER);
        cp.getChart().getXYPlot().setBackgroundAlpha(0.3f);
        cp.getChart().getXYPlot().setDomainGridlinesVisible(true);
        cp.getChart().getXYPlot().setRangeGridlinesVisible(true);
        cp.setHorizontalAxisTrace(true);
        cp.setVerticalAxisTrace(true);
        
        String reg_id = symbol+"-iddq";
        InterfaceMain2.unregisterChart(reg_id);
        InterfaceMain2.registerChart(reg_id, jfc);
        
        pnl.removeAll();
        pnl.add(cp);
        pnl.update(pnl.getGraphics());
        pnl.validate();

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlFibonacci = new javax.swing.JPanel();
        cmbPc = new javax.swing.JComboBox();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        tfMax = new javax.swing.JTextField();
        tfMin = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        taFile = new javax.swing.JTextArea();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        pnl = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cmbInterval = new javax.swing.JComboBox();
        tfTick = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        cmbDays = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();

        cmbPc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0.236", "0.382", "0.50", "0.618", "0.764" }));

        jButton9.setText("Draw");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setText("Draw All");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jLabel3.setText("Min");

        jLabel4.setText("Max");

        javax.swing.GroupLayout pnlFibonacciLayout = new javax.swing.GroupLayout(pnlFibonacci);
        pnlFibonacci.setLayout(pnlFibonacciLayout);
        pnlFibonacciLayout.setHorizontalGroup(
            pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFibonacciLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tfMin)
                    .addComponent(tfMax)
                    .addComponent(cmbPc, 0, 98, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlFibonacciLayout.createSequentialGroup()
                        .addComponent(jButton9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton10))
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlFibonacciLayout.setVerticalGroup(
            pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFibonacciLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFibonacciLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbPc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton9)
                    .addComponent(jButton10))
                .addGap(48, 48, 48))
        );

        taFile.setColumns(20);
        taFile.setRows(5);
        jScrollPane3.setViewportView(taFile);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "No", "Price", "Volume"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);

        pnl.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnl.setLayout(new java.awt.BorderLayout());

        jLabel5.setText("Select days from combo at the right and press button with label \"Show\", to view the chart");
        pnl.add(jLabel5, java.awt.BorderLayout.PAGE_START);

        jToolBar1.setRollover(true);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEADING));

        jLabel1.setText("Tick Interval");
        jPanel1.add(jLabel1);

        cmbInterval.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "5", "8", "13", "21", "34", "55", "89", "144", "233", "377", "610", "987", "1597", "2584", "4181", "6765", "10946" }));
        cmbInterval.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbIntervalActionPerformed(evt);
            }
        });
        jPanel1.add(cmbInterval);

        tfTick.setColumns(4);
        jPanel1.add(tfTick);

        jButton3.setText("OHLCV");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jButton8.setText("FB");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton8);

        jButton6.setText("Drawing");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton6);

        jLabel2.setText("                                        ");
        jPanel1.add(jLabel2);

        cmbDays.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14" }));
        jPanel1.add(cmbDays);

        jButton1.setText("Show");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);

        jToolBar1.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnl, javax.swing.GroupLayout.DEFAULT_SIZE, 885, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnl, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        drawCandleChart();
    }//GEN-LAST:event_jButton3ActionPerformed


    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        double pc = Double.parseDouble((String) cmbPc.getSelectedItem());
        drawFibonacci(pc);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        int itemCount = cmbPc.getItemCount();
        for (int i = 0; i <= itemCount - 1; i++) {
            double pc = Double.parseDouble((String) cmbPc.getItemAt(i));
            drawFibonacci(pc);
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        JOptionPane.showConfirmDialog(null, pnlFibonacci);
    }//GEN-LAST:event_jButton8ActionPerformed

    private void cmbIntervalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbIntervalActionPerformed
        // TODO add your handling code here:
        tfTick.setText((String) this.cmbInterval.getSelectedItem());
    }//GEN-LAST:event_cmbIntervalActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        this.saveChartAsPng();
        ImageJ ij = new ImageJ();
        Opener op = new Opener();
        String path = System.getProperty("user.home");
        op.open(path + "/pngchart.png");
        ij.setVisible(true);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String d = (String)cmbDays.getSelectedItem();
        showChart(Integer.parseInt(d));
    }//GEN-LAST:event_jButton1ActionPerformed

    private void drawFibonacci(double pc) {
        double min = Double.parseDouble(tfMin.getText());
        double max = Double.parseDouble(tfMax.getText());
        double diff = max - min;
        //System.out.println("The diff is " + diff);
        double pcDiff = diff * pc;
        //System.out.println("The diff is " + pcDiff);
        double value = min + pcDiff;
        drawHorizontalLine(value);
    }

    private void drawHorizontalLine(double position) {
        //System.out.println("The diff is " + position);

        ValueMarker marker = new ValueMarker(position);  // position is the value on the axis
        marker.setPaint(Color.black);

        XYPlot plot = (XYPlot) jfc.getPlot();
        plot.addRangeMarker(marker);
    }

    private void drawHorizontalLine() {
        String answer = JOptionPane.showInputDialog("Value:");
        double position = Double.parseDouble(answer);

        ValueMarker marker = new ValueMarker(position);  // position is the value on the axis
        marker.setPaint(Color.black);

        XYPlot plot = (XYPlot) jfc.getPlot();
        plot.addRangeMarker(marker);
    }

    public void drawCandleChart() {
        List<OHLCDataItem> dataItems = new ArrayList<>();
        int tickInterval = Integer.parseInt(tfTick.getText());
        OHLCVDataConversion conv = new OHLCVDataConversion(al, tickInterval);
        ArrayList<Quote> quoteList = conv.getOhlcvQuote();

        //int[] selectedRows = tbl.getSelectedRows();
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i <= quoteList.size() - 1; i++) {
            Quote q = quoteList.get(i);

            double open = q.getOpen();
            double high = q.getHigh();
            double low = q.getLow();
            double close = q.getClose();
            double volume = q.getVolume();
            Date d = q.getDate();

            OHLCDataItem item = new OHLCDataItem(d, open, high, low, close, volume);
            dataItems.add(item);
        }

        Collections.reverse(dataItems);
        OHLCDataItem[] data = dataItems.toArray(new OHLCDataItem[dataItems.size()]);

        DefaultOHLCDataset dataset = null;
        dataset = new DefaultOHLCDataset("", data);

        DateAxis domainAxis = new DateAxis("Date");
        NumberAxis rangeAxis = new NumberAxis("Price");

        CandlestickRenderer renderer = new CandlestickRenderer();
        XYPlot mainPlot = new XYPlot(dataset, domainAxis, rangeAxis, renderer);

        renderer.setSeriesPaint(0, Color.BLACK);
        //renderer.setDrawVolume(true);
        //renderer.setVolumePaint(new Color(210,250,230));

        rangeAxis.setAutoRangeIncludesZero(false);

        // domainAxis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());
        jfc = new JFreeChart("", null, mainPlot, false);

        final ChartPanel chartPanel = new ChartPanel(jfc);
        chartPanel.setFillZoomRectangle(true);
        chartPanel.setVerticalAxisTrace(true);
        chartPanel.setHorizontalAxisTrace(true);
        jfc.setBackgroundPaint(this.getBackground());

        CMResource cmr = new CMResource();
        ImageIcon img = cmr.getIcon("chartbgimg.png");
        jfc.getXYPlot().setBackgroundImage(img.getImage());
        jfc.getXYPlot().setBackgroundImageAlignment(Align.CENTER);
        jfc.getXYPlot().setBackgroundAlpha(0.3f);
        jfc.getXYPlot().setDomainGridlinesVisible(true);
        jfc.getXYPlot().setRangeGridlinesVisible(true);

        pnl.removeAll();
        pnl.add(chartPanel, BorderLayout.CENTER);
        pnl.update(pnl.getGraphics());
        pnl.validate();

    }

    private void saveChartAsPng() {
        ChartPanel cp = (ChartPanel) pnl.getComponent(0);
        JFreeChart jfc = cp.getChart();
        String path = System.getProperty("user.home");
        File pngFile = new File(path + "/pngchart.png");
        int w = 1280;
        int h = 720;
        try {
            ChartUtilities.saveChartAsPNG(pngFile, jfc, w, h);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbDays;
    private javax.swing.JComboBox cmbInterval;
    private javax.swing.JComboBox cmbPc;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnl;
    private javax.swing.JPanel pnlFibonacci;
    private javax.swing.JTextArea taFile;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField tfMax;
    private javax.swing.JTextField tfMin;
    private javax.swing.JTextField tfTick;
    // End of variables declaration//GEN-END:variables
}

class OHLCVDataConversion {

    private final ArrayList<TickData> al;
    private final int tickInterval;

    public OHLCVDataConversion(ArrayList<TickData> al, int tickInterval) {
        this.al = al;
        this.tickInterval = tickInterval;
    }

    public ArrayList<Quote> getOhlcvQuote() {
        ArrayList<Quote> quoteList = new ArrayList<>();
        Vector v = new Vector();
        double intervalVolume = 0;
        Calendar cal = Calendar.getInstance();

        for (int i = 0; i <= al.size() - 1; i++) {
            TickData idq = (TickData) al.get(i);
            v.add(idq.getDailyQuotePrice());
            intervalVolume += idq.getDailyQuoteVolume();
            if (i % tickInterval == 0) {
                Quote q = createQuote(v);
                q.setVolume(intervalVolume);
                cal.add(Calendar.SECOND, tickInterval);
                q.setDate(cal.getTime());
                v.clear();
                intervalVolume = 0;
                quoteList.add(q);
            }
        }

        return quoteList;
    }

    public Quote createQuote(Vector v) {
        Quote q = new Quote();
        q.setOpen((double) v.get(0));
        q.setClose((double) v.get(v.size() - 1));
        double low = q.getOpen();
        double high = q.getOpen();
        for (int i = 0; i <= v.size() - 1; i++) {
            double val = (double) v.get(i);
            if (val < low) {
                low = val;
            }

            if (val > high) {
                high = val;
            }
        }
        q.setLow(low);
        q.setHigh(high);

        return q;
    }

}