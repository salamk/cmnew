/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import cmnew.InterfaceMain2;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.ui.Align;
import org.jfree.ui.HorizontalAlignment;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 *
 * @author coolmarch
 */
public class PnlIndexView extends javax.swing.JPanel {

    /**
     * Creates new form PnlIndexView
     */
    public PnlIndexView() {
        initComponents();
    }

    public void showIndices() {
        Color bg = pnlDaily.getBackground();
        CMResource cmr = new CMResource();
        //CUPriceVolumeChart c = new CUPriceVolumeChart("static", 1);
        ChartPanel cp = MyChartFactory.getPreviousDayStaticChart();
        //ChartPanel cp = c.getChartPanel2();
        cp.setBackground(bg);
        JFreeChart chart = cp.getChart();
        chart.getXYPlot().setBackgroundPaint(bg);
        chart.setBackgroundPaint(bg);
        ImageIcon icon = cmr.getIcon("chartbgimg.png");
        Image image = icon.getImage();
        chart.setBackgroundImage(image);
        chart.setBackgroundImageAlignment(Align.CENTER);

        TextTitle tt = new TextTitle();
        tt.setText("kse-100: Previous Day");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp.getChart().setTitle(tt);
        InterfaceMain2.registerChart("KSE100-PDAY", cp.getChart());

        cp.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int clickCount = me.getClickCount();
                if (clickCount == 2) {
                    ChartViewerFrame cvf = new ChartViewerFrame(cp, pnlPrevDay);
                    cvf.setSize(640, 400);
                    cvf.setLocationRelativeTo(null);
                    cvf.setVisible(true);
                }
            }
        });

        pnlPrevDay.add(cp);
        pnlPrevDay.update(pnlPrevDay.getGraphics());
        pnlPrevDay.validate();

        ChartPanel cp2 = MyChartFactory.getStaticChart(10);
        cp2.setBackground(pnlDaily.getBackground());
        cp2.getChart().getCategoryPlot().setBackgroundPaint(bg);
        cp2.getChart().setBackgroundPaint(bg);
        cp2.getChart().setBackgroundImage(image);
        cp2.getChart().setBackgroundImageAlignment(Align.CENTER);
        tt = new TextTitle();
        tt.setText("kse-100: Last 10 Days");
        //Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp2.getChart().setTitle(tt);
        
        InterfaceMain2.registerChart("KSE100-P10DAY", cp2.getChart());

        cp2.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int clickCount = me.getClickCount();
                if (clickCount == 2) {
                    ChartViewerFrame cvf = new ChartViewerFrame(cp2, pnlTenDay);
                    cvf.setSize(640, 400);
                    cvf.setLocationRelativeTo(null);
                    cvf.setVisible(true);
                }
            }
        });

        this.pnlTenDay.add(cp2);
        pnlTenDay.update(pnlTenDay.getGraphics());
        pnlTenDay.validate();

        float width = 3.0f;
        
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // from 1 year ago
        ArrayList<OHLCDataItem> ohlcList = this.getKseQuoteFromYahoo("daily", from, to);
        Collections.reverse(ohlcList);
        ChartPanel cp3 = MyChartFactory.getCandleStickChart(ohlcList);
        CandlestickRenderer renderer = (CandlestickRenderer) cp3.getChart().getXYPlot().getRenderer();
        renderer.setCandleWidth(width);

        tt = new TextTitle();
        tt.setText("kse-100: Daily");
        //Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp3.getChart().setTitle(tt);
        
        InterfaceMain2.registerChart("KSE100-DAILY", cp3.getChart());

        cp3.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int clickCount = me.getClickCount();
                if (clickCount == 2) {
                    ChartViewerFrame cvf = new ChartViewerFrame(cp3, pnlDaily);
                    cvf.setSize(640, 400);
                    cvf.setLocationRelativeTo(null);
                    cvf.setVisible(true);
                }
            }
        });

        pnlDaily.add(cp3);
        pnlDaily.update(pnlDaily.getGraphics());
        pnlDaily.validate();
        
        
        
        from = Calendar.getInstance();
        to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5); // from 1 year ago
        ohlcList = this.getKseQuoteFromYahoo("weekly", from, to);
        Collections.reverse(ohlcList);
        ChartPanel cp4 = MyChartFactory.getCandleStickChart(ohlcList);
        renderer = (CandlestickRenderer) cp4.getChart().getXYPlot().getRenderer();
        renderer.setCandleWidth(width);
        
        InterfaceMain2.registerChart("KSE100-WEEKLY", cp4.getChart());

        cp4.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                int clickCount = me.getClickCount();
                if (clickCount == 2) {
                    ChartViewerFrame cvf = new ChartViewerFrame(cp4, pnlWeekly);
                    cvf.setSize(640, 400);
                    cvf.setLocationRelativeTo(null);
                    cvf.setVisible(true);
                }
            }
        });
        
        tt = new TextTitle();
        tt.setText("kse-100: Weekly");
        //Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        cp4.getChart().setTitle(tt);


        pnlWeekly.add(cp4);
        pnlWeekly.update(pnlWeekly.getGraphics());
        pnlWeekly.validate();
        
        
//        from = Calendar.getInstance();
//        to = Calendar.getInstance();
//        from.add(Calendar.YEAR, -10); // from 1 year ago
//        ohlcList = this.getKseQuoteFromYahoo("monthly", from, to);
//        Collections.reverse(ohlcList);
//        ChartPanel cp5 = MyChartFactory.getCandleStickChart(ohlcList);
//        renderer = (CandlestickRenderer) cp5.getChart().getXYPlot().getRenderer();
//        renderer.setCandleWidth(width);
//
//        cp5.addMouseListener(new MouseAdapter() {
//            public void mouseClicked(MouseEvent me) {
//                int clickCount = me.getClickCount();
//                if (clickCount == 2) {
//                    ChartViewerFrame cvf = new ChartViewerFrame(cp5, pnlMonthly);
//                    cvf.setSize(640, 400);
//                    cvf.setLocationRelativeTo(null);
//                    cvf.setVisible(true);
//                }
//            }
//        });
//        
//        tt = new TextTitle();
//        tt.setText("kse-100: Monthly");
//        //Font font = new Font("Verdana", Font.BOLD, 10);
//        tt.setFont(font);
//        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
//        cp5.getChart().setTitle(tt);
//
//        
//
//        pnlMonthly.add(cp5);
//        pnlMonthly.update(pnlMonthly.getGraphics());
//        pnlMonthly.validate();
//
//        
        
    }

    private void detach(ChartPanel cp, JPanel parent) {

        JFrame frame = new JFrame();
        frame.getContentPane().add(cp);
        frame.setSize(640, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    private ArrayList<OHLCDataItem> getKseQuoteFromYahoo(String type, Calendar from, Calendar to) {
        ArrayList<OHLCDataItem> ohlcList = new ArrayList<>();
        try {
            Stock google = YahooFinance.get("^KSE");
            Interval itype = null;
            if (type.compareToIgnoreCase("daily") == 0) {
                itype = Interval.DAILY;
            } else if (type.compareToIgnoreCase("weekly") == 0) {
                itype = Interval.WEEKLY;
            } else if (type.compareToIgnoreCase("monthly") == 0) {
                itype = Interval.MONTHLY;
            }
            List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, itype);
            for (HistoricalQuote hq : googleHistQuotes) {
                OHLCDataItem item = new OHLCDataItem(hq.getDate().getTime(),
                        hq.getOpen().doubleValue(),
                        hq.getHigh().doubleValue(),
                        hq.getLow().doubleValue(),
                        hq.getClose().doubleValue(),
                        0);
                ohlcList.add(item);

            }
        } catch (Exception e) {

        }

        return ohlcList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlContainer = new javax.swing.JPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnlPrevDay = new javax.swing.JPanel();
        pnlTenDay = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        pnlDaily = new javax.swing.JPanel();
        pnlWeekly = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        tfW8 = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        pnlContainer.setLayout(new java.awt.GridLayout(2, 1));

        jSplitPane1.setDividerLocation(130);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlPrevDay.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlPrevDay.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setLeftComponent(pnlPrevDay);

        pnlTenDay.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlTenDay.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(pnlTenDay);

        pnlContainer.add(jSplitPane1);

        jSplitPane2.setDividerLocation(130);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlDaily.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlDaily.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setLeftComponent(pnlDaily);

        pnlWeekly.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnlWeekly.setLayout(new java.awt.BorderLayout());
        jSplitPane2.setRightComponent(pnlWeekly);

        pnlContainer.add(jSplitPane2);

        add(pnlContainer, java.awt.BorderLayout.CENTER);

        jToolBar1.setRollover(true);

        jButton1.setText("View");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        tfW8.setEditable(false);
        tfW8.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jToolBar1.add(tfW8);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tfW8.setText("Please wait ...");
        tfW8.update(tfW8.getGraphics());
        tfW8.validate();
        this.pnlDaily.removeAll();
        this.pnlPrevDay.removeAll();
        this.pnlTenDay.removeAll();
//        this.pnlWeekly.removeAll();
        showIndices();
        tfW8.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnlContainer;
    private javax.swing.JPanel pnlDaily;
    private javax.swing.JPanel pnlPrevDay;
    private javax.swing.JPanel pnlTenDay;
    private javax.swing.JPanel pnlWeekly;
    private javax.swing.JTextField tfW8;
    // End of variables declaration//GEN-END:variables
}
