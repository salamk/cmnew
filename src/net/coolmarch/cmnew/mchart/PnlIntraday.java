/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import cmnew.CoolmarchConstants;
import cmnew.InterfaceMain2;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import javax.swing.ImageIcon;
import net.coolmarch.cmnew.common.TickData;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.Align;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleAnchor;
import org.jfree.ui.TextAnchor;

/**
 *
 * @author salam
 */
public final class PnlIntraday extends javax.swing.JPanel {

    private HashMap<Integer, Date> dmap;
    private String symbol;

    /**
     * Creates new form PnlIntraday
     */
    public PnlIntraday() {
        initComponents();
        CMResource cmr = new CMResource();
    }

    public PnlIntraday(String symbol, HashMap<Integer, Date> dmap) {
        initComponents();
        this.symbol = symbol;
        this.dmap = dmap;
        try {
            this.setPChart(10);
        } catch (Exception e) {
        }
        //this.setCurrentChart();
        PnlFundamentalTable pft = new PnlFundamentalTable(symbol);

        p1.add(pft);
//        DefaultCaret caret = (DefaultCaret) taReport.getCaret(); // ←
//        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);       // ←
//        scrollPane.setViewportView(taReport);
//        try{this.setCompanyReport();}catch(Exception e){}
//        //this.setStaticChart(10);
    }

    private void setCurrentChart() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series1 = new XYSeries("Price");
        XYSeries series2 = new XYSeries("Volume");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        double min = 100000;
        double max = 0;
        double last = 0;
        int counter = 0;
        try {
            URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER+"/abscripts/getquote.php?symbol=" + symbol);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";

            while ((line = br.readLine()) != null) {
                line = line.replaceAll("<br />", "");
                String[] toke = line.split(",");
                String ttime = toke[0];
                String sclose = toke[5];
                Date d = null;
                try {
                    d = sdf.parse(ttime);
                } catch (ParseException pe) {

                }

                double close = Double.parseDouble(sclose);

                double x = d.getTime();
                series1.add(x, close);

                if (close < min) {
                    min = close;
                }

                if (close > max) {
                    max = close;
                }

                if (counter == 0) {
                    last = close;
                }

                counter++;

            }
            br.close();
            dataset.addSeries(series1);

        } catch (Exception e) {

        }

        JFreeChart chart = ChartFactory.createXYAreaChart(
                "", "", "",
                dataset, PlotOrientation.VERTICAL, false, false, false);

        MyXYAreaRenderer renderer = new MyXYAreaRenderer();
        renderer.setSeriesFillPaint(0, new Color(12, 150, 140));
        renderer.setOutline(true);
        renderer.setSeriesOutlinePaint(0, Color.black);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(3.5f));
        chart.getXYPlot().setRenderer(0, renderer);
        chart.getPlot().setNoDataMessage("--No Data--");

        ValueAxis range = (ValueAxis) chart.getXYPlot().getRangeAxis();
        chart.getXYPlot().getDomainAxis().setTickLabelsVisible(false);

        final Marker target = new ValueMarker(last);
        target.setPaint(Color.red);
        target.setStroke(new BasicStroke(2.5f));
        target.setLabel("Current Price :" + last);
        target.setLabelAnchor(RectangleAnchor.TOP_RIGHT);
        target.setLabelTextAnchor(TextAnchor.BOTTOM_RIGHT);
        chart.getXYPlot().addRangeMarker(target);
        chart.setBackgroundPaint(p1.getBackground());
        chart.getXYPlot().setBackgroundPaint(p1.getBackground());

        CMResource cmr = new CMResource();
        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.3f);
        TextTitle tt = new TextTitle(symbol + " today (Delay:5 Min");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(tt);

        try {
            range.setRange(min, max);
        } catch (Exception e) {
        }
        ChartPanel cp = new ChartPanel(chart);
        cp.setBackground(p1.getBackground());
        this.setP1Chart(cp);

    }

    public void setP1Chart(ChartPanel cp) {
        cp.getChart().getXYPlot().setBackgroundPaint(p1.getBackground());
        p1.add(cp);
    }

    public void setP2Chart(ChartPanel cp) {
        p2.removeAll();
        TextTitle tt = new TextTitle(symbol + " Value@Risk");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp.getChart().setTitle(tt);

        p2.add(cp);
        if (p2.getGraphics() != null) {
            p2.update(p2.getGraphics());
            p2.validate();
        }

    }

    public void setP4Chart(ChartPanel cp) {
        p4.removeAll();
        TextTitle tt = new TextTitle(symbol + " Previous Day");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp.getChart().setTitle(tt);

        p4.add(cp);
        if (p4.getGraphics() != null) {
            p4.update(p4.getGraphics());
            p4.validate();
        }

    }

    public void setSevenDayCandles() {
        symbol = "";
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        scrollPane = new javax.swing.JScrollPane();
        taReport = new javax.swing.JTextArea();
        p1 = new javax.swing.JPanel();
        p2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        pTickChart = new javax.swing.JPanel();
        pnlDchart = new javax.swing.JPanel();
        p4 = new javax.swing.JPanel();

        taReport.setEditable(false);
        taReport.setBackground(new java.awt.Color(0, 0, 0));
        taReport.setColumns(20);
        taReport.setFont(new java.awt.Font("Dialog", 0, 10)); // NOI18N
        taReport.setRows(5);
        taReport.setDisabledTextColor(java.awt.Color.green);
        taReport.setEnabled(false);
        scrollPane.setViewportView(taReport);

        setLayout(new java.awt.GridLayout(1, 4, 2, 0));

        p1.setLayout(new java.awt.BorderLayout());
        add(p1);

        p2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                p2MouseClicked(evt);
            }
        });
        p2.setLayout(new java.awt.BorderLayout());
        add(p2);

        jPanel1.setLayout(new java.awt.BorderLayout());

        pTickChart.setLayout(new java.awt.BorderLayout());

        pnlDchart.setLayout(new java.awt.BorderLayout());
        pTickChart.add(pnlDchart, java.awt.BorderLayout.CENTER);

        jPanel1.add(pTickChart, java.awt.BorderLayout.CENTER);

        add(jPanel1);

        p4.setLayout(new java.awt.BorderLayout());
        add(p4);
    }// </editor-fold>//GEN-END:initComponents

    private void p2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_p2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_p2MouseClicked

    public void setPChart(int day) throws Exception {
        //Date d = dmap.get(day);
        CMResource cmr = new CMResource();
        // PDataDaemon tdf = new PDataDaemon(symbol);
        PVImporter pvi = new PVImporter(symbol, 10);
        ArrayList<TickData> tlist = pvi.getPData(symbol, day);
        Collections.reverse(tlist);
        PDataChart pvc = new PDataChart(tlist);
        ChartPanel cp = pvc.getChartPanel();
        cp.setBackground(pnlDchart.getBackground());
        JFreeChart chart = cp.getChart();
        chart.setBackgroundPaint(pnlDchart.getBackground());
        cp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                ChartViewerFrame cv = new ChartViewerFrame(cp, pnlDchart);
                cv.setSize(640, 400);
                cv.setLocationRelativeTo(null);
                cv.setVisible(true);
                cv.setAlwaysOnTop(true);
            }
        });

        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.3f);
        TextTitle tt = new TextTitle(symbol + " Last 10 days");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(tt);

        pnlDchart.removeAll();
        pnlDchart.add(cp);
        pnlDchart.update(pnlDchart.getGraphics());
        pnlDchart.validate();
        InterfaceMain2.registerChart(symbol + "-cpv", chart);

        CUPriceVolumeChart cu = new CUPriceVolumeChart(symbol, 1);
        ChartPanel cpi = cu.getChartPanel2();
        cpi.setBackground(p4.getBackground());
        chart = cpi.getChart();
        chart.setBackgroundPaint(p4.getBackground());
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);
        chart.getPlot().setBackgroundAlpha(0.3f);

        cpi.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                ChartViewerFrame cv = new ChartViewerFrame(cpi, p4);
                cv.setSize(640, 400);
                cv.setLocationRelativeTo(null);
                cv.setVisible(true);
                cv.setAlwaysOnTop(true);
            }
        });

        tt = new TextTitle(symbol + "Previous Days");
        //Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(tt);
        InterfaceMain2.registerChart(symbol + "-cp10", chart);

        p4.removeAll();
        p4.add(cpi);
        p4.update(p4.getGraphics());
        p4.validate();

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel p1;
    private javax.swing.JPanel p2;
    private javax.swing.JPanel p4;
    private javax.swing.JPanel pTickChart;
    private javax.swing.JPanel pnlDchart;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextArea taReport;
    // End of variables declaration//GEN-END:variables
}
