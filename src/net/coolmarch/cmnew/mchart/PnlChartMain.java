/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import cmnew.InterfaceMain2;
import ij.ImageJ;
import ij.ImagePlus;
import ij.io.Opener;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.coolmarch.cmnew.common.CImporter;
import net.coolmarch.cmnew.common.CoolMarchImageJ;
import net.coolmarch.cmnew.common.Quote;
import net.coolmarch.cmnew.mchart.ti.TEMA;
import net.coolmarch.cmnew.mchart.ti.TSMA;
import net.coolmarch.cmnew.res.CMResource;
import net.coolmarch.daemon.ChartValueMarker;
import net.coolmarch.daemon.DataFetchingDaemon;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYAnnotation;
import org.jfree.chart.annotations.XYLineAnnotation;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.chart.renderer.xy.HighLowRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleEdge;

/**
 *
 * @author salam
 */
public final class PnlChartMain extends javax.swing.JPanel {

    private final PnlCandleStickSetting pcs;
    private HashMap<String, OHLCDataItem> quoteMap;
    private final HashMap<String, Integer> layer;
    private final ArrayList<OHLCDataItem> quoteList;
    private final PnlIntraday pid;
    private final PnlTimeSeriesIndicators pti;
    private Date startDate;
    private Date endDate;
    private ChartPanel cp;
    private int dayCount = 90;
    private boolean sma1 = false;
    private boolean sma2 = false;
    private boolean sma3 = false;
    private boolean ema1 = false;
    private static int seriesNumber = 0;
    private final String symbol;

    /**
     * Creates new form PnlChartMain
     */
    public PnlChartMain(String symbol) {

        initComponents();

        this.symbol = symbol;
        CMResource cmr = new CMResource();
        this.btnTimeSelection.setIcon(cmr.getIcon("calendar16.png"));
        this.btnTools.setIcon(cmr.getIcon("tools_16.png"));
        this.btnIndicators.setIcon(cmr.getIcon("dchart16.png"));
        pcs = new PnlCandleStickSetting();
        layer = new HashMap<>();
        quoteMap = new HashMap<>();

//        HistoricalData hd = new HistoricalData(symbol);
//        quoteList = hd.getQuoteList();
        CImporter ci = new CImporter(symbol, CImporter.CACHE_SERVER_SUPPLIMENT);
        quoteList = ci.getOhlcCollection();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i <= quoteList.size() - 1; i++) {
            OHLCDataItem q = quoteList.get(i);
            Date d = q.getDate();
            String dt = sdf.format(d);
            quoteMap.put(dt, q);
        }

        HashMap<Integer, Date> dmap = new HashMap<>();
        for (int i = 0; i <= 7 - 1; i++) {
            OHLCDataItem q = quoteList.get(i);
            Date d = q.getDate();
            dmap.put(i + 1, d);
        }

        pti = new PnlTimeSeriesIndicators(quoteList);
        pid = new PnlIntraday(symbol, dmap);
        drawChart();

    }

    private void drawChart() {
        CandleDailyChart cdc = new CandleDailyChart(getDataset(90, "MAIN_CHART"));
        cp = cdc.getChartPanel();
        pcs.setChart(cp.getChart());
        pnl.add(cp);
        
        InterfaceMain2.registerChart(symbol+"-cmain", cp.getChart());
        
        //value marker
        
        cp.addChartMouseListener(new ChartMouseListener() {
            double x = 0;
            double y = 0;

            public void chartMouseClicked(ChartMouseEvent cme) {
                int clickCount = cme.getTrigger().getClickCount();
                if (clickCount == 2) {

                    if (btnSpiral.isSelected()) {
                        drawSpiral(x, y);
                    } else if (btnHorizentalAxis.isSelected()) {
                        String val = tfsY.getText();
                        if (val == null) {

                        } else {
                            double v = Double.parseDouble(val);
                            ValueMarker vm = new ValueMarker(v, Color.blue, new BasicStroke(1.5f));
                            cme.getChart().getXYPlot().addRangeMarker(vm);
                        }

                    } else if (btnVerticalAxis.isSelected()) {
                        String val = tfsX.getText();

                        if (val == null) {

                        } else {
                            double v = Double.parseDouble(val);
                            ValueMarker vm = new ValueMarker(v, Color.blue, new BasicStroke(1.5f));
                            cme.getChart().getXYPlot().addDomainMarker(vm);
                        }

                    }
                }
            }

            public void chartMouseMoved(ChartMouseEvent event) {
                int mouseX = event.getTrigger().getX();
                int mouseY = event.getTrigger().getY();
                x = event.getChart().getXYPlot().getDomainCrosshairValue();
                y = event.getChart().getXYPlot().getRangeCrosshairValue();

                JFreeChart chart = cp.getChart();

                Point2D p = cp.translateScreenToJava2D(
                        new Point(mouseX, mouseY));

                XYPlot plot = (XYPlot) chart.getPlot();

                Rectangle2D plotArea
                        = cp.getChartRenderingInfo().getPlotInfo()
                        .getDataArea();

                DateAxis domainAxis = (DateAxis) plot.getDomainAxis();
                ValueAxis rangeAxis = (ValueAxis) plot.getRangeAxis();

                RectangleEdge domainAxisEdge = plot.getDomainAxisEdge();
                RectangleEdge rangeAxisEdge = plot.getRangeAxisEdge();

                double chartX = domainAxis.java2DToValue(p.getX(), plotArea,
                        domainAxisEdge);

                double chartY = rangeAxis.java2DToValue(p.getY(), plotArea,
                        rangeAxisEdge);

//                cp.setHorizontalAxisTrace(true);
//                cp.setVerticalAxisTrace(true);
                String str = "";

                Calendar cal = new GregorianCalendar();
                cal.setTimeInMillis((long) chartX);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                Date qdate = cal.getTime();

                try {

                    TimeSeriesChart.setXCrosshairValue(chartX);

                } catch (Exception e) {
                    ;
                }

                String date = sdf.format(qdate);
                DecimalFormat df = new DecimalFormat("#.##");
                String cd = "Date: " + date;
                OHLCDataItem q = quoteMap.get(date);
                if (q != null) {
                    //String code = q.getSymbol();
                    tfsX.setText(df.format(chartX));
                    tfsY.setText(df.format(chartY));

                    double open = q.getOpen().doubleValue();
                    double high = q.getHigh().doubleValue();
                    double low = q.getLow().doubleValue();
                    double close = q.getClose().doubleValue();
                    double volume = q.getVolume().doubleValue();
                    double change = close - open;

                    Color foreground = Color.black;
                    if (change > 0) {
                        foreground = (new Color(0, 153, 0));
                    } else if (change < 0) {
                        foreground = Color.red;
                    } else {
                        foreground = Color.blue;
                    }

                    tfsDate.setForeground(foreground);
                    tfsOpen.setForeground(foreground);
                    tfsHigh.setForeground(foreground);
                    tfsLow.setForeground(foreground);
                    tfsClose.setForeground(foreground);
                    tfsVolume.setForeground(foreground);
                    tfsChange.setForeground(foreground);

                    tfsDate.setText(date);
                    tfsOpen.setText(df.format(open));
                    tfsHigh.setText(df.format(high));
                    tfsLow.setText(df.format(low));
                    tfsClose.setText(df.format(close));
                    tfsVolume.setText(df.format(volume));
                    tfsChange.setText(df.format(change));

                } else {
                    tfsDate.setText(cd);
                    tfsOpen.setText("");
                    tfsHigh.setText("");
                    tfsLow.setText("");
                    tfsClose.setText("");
                    tfsVolume.setText("");
                    tfsChange.setText("");
                }
            }

        });

//        CandleDailyChart cdc2 = new CandleDailyChart(getDataset(22, ""));
//        pid.setP1Chart(cdc2.getChartPanel());
        ArrayList<OHLCDataItem> ql = new ArrayList();
        for (int i = 0; i <= 90; i++) {
            ql.add(quoteList.get(i));
        }

        PnlRiskAnalysis par = new PnlRiskAnalysis(ql);
        pid.setP2Chart(par.getChartPanel());

        pnlIndicators.removeAll();
        pnlIndicators.add(pid);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        jButton2 = new javax.swing.JButton();
        btnTimeSelection = new javax.swing.JButton();
        btnTools = new javax.swing.JButton();
        btnIndicators = new javax.swing.JButton();
        btnSma1 = new javax.swing.JToggleButton();
        btnSma2 = new javax.swing.JToggleButton();
        btnSma3 = new javax.swing.JToggleButton();
        btnEma1 = new javax.swing.JToggleButton();
        cmbRenderer = new javax.swing.JComboBox<>();
        cmbYearly = new javax.swing.JComboBox<>();
        btnHorizentalAxis = new javax.swing.JToggleButton();
        btnVerticalAxis = new javax.swing.JToggleButton();
        btnSpiral = new javax.swing.JToggleButton();
        btnTrace = new javax.swing.JToggleButton();
        btnChartBackground = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        btnGrid = new javax.swing.JToggleButton();
        jToggleButton2 = new javax.swing.JToggleButton();
        jToggleButton3 = new javax.swing.JToggleButton();
        jToggleButton4 = new javax.swing.JToggleButton();
        jToggleButton5 = new javax.swing.JToggleButton();
        tfStatus = new javax.swing.JTextField();
        jSplitPane1 = new javax.swing.JSplitPane();
        pnl = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfsDate = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfsOpen = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tfsHigh = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        tfsLow = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        tfsClose = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        tfsChange = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        tfsVolume = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        tfsX = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        tfsY = new javax.swing.JTextField();
        pnlIndicators = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jButton2.setText("Design");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        btnTimeSelection.setFocusable(false);
        btnTimeSelection.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTimeSelection.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTimeSelection.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimeSelectionActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTimeSelection);

        btnTools.setFocusable(false);
        btnTools.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTools.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTools.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnToolsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTools);

        btnIndicators.setFocusable(false);
        btnIndicators.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnIndicators.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIndicators.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIndicatorsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnIndicators);

        btnSma1.setText("S1");
        btnSma1.setFocusable(false);
        btnSma1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSma1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSma1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSma1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSma1);

        btnSma2.setText("S2");
        btnSma2.setFocusable(false);
        btnSma2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSma2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSma2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSma2ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSma2);

        btnSma3.setText("S3");
        btnSma3.setFocusable(false);
        btnSma3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSma3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSma3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSma3ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSma3);

        btnEma1.setText("E1");
        btnEma1.setFocusable(false);
        btnEma1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEma1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEma1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEma1ActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEma1);

        cmbRenderer.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Candlestick", "Line", "Connected Line", "OHLC" }));
        cmbRenderer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbRendererActionPerformed(evt);
            }
        });
        jToolBar1.add(cmbRenderer);

        cmbYearly.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2016", "2015", "2014", "2013", "2012", "2011", "2010", "2009", "2008" }));
        cmbYearly.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbYearlyActionPerformed(evt);
            }
        });
        jToolBar1.add(cmbYearly);

        btnHorizentalAxis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/hline.png"))); // NOI18N
        btnHorizentalAxis.setFocusable(false);
        btnHorizentalAxis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnHorizentalAxis.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnHorizentalAxis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHorizentalAxisActionPerformed(evt);
            }
        });
        jToolBar1.add(btnHorizentalAxis);

        btnVerticalAxis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/verticalline.png"))); // NOI18N
        btnVerticalAxis.setFocusable(false);
        btnVerticalAxis.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVerticalAxis.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVerticalAxis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerticalAxisActionPerformed(evt);
            }
        });
        jToolBar1.add(btnVerticalAxis);

        btnSpiral.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/dline.png"))); // NOI18N
        btnSpiral.setFocusable(false);
        btnSpiral.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSpiral.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSpiral.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSpiralActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSpiral);

        btnTrace.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/crosshair.png"))); // NOI18N
        btnTrace.setFocusable(false);
        btnTrace.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnTrace.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnTrace.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTraceActionPerformed(evt);
            }
        });
        jToolBar1.add(btnTrace);

        btnChartBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/backgroundFill.png"))); // NOI18N
        btnChartBackground.setFocusable(false);
        btnChartBackground.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnChartBackground.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnChartBackground.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartBackgroundActionPerformed(evt);
            }
        });
        jToolBar1.add(btnChartBackground);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/gradient16.png"))); // NOI18N
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton3);

        btnGrid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/drawing16.png"))); // NOI18N
        btnGrid.setFocusable(false);
        btnGrid.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnGrid.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnGrid.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGridActionPerformed(evt);
            }
        });
        jToolBar1.add(btnGrid);

        jToggleButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/verticalgridline.png"))); // NOI18N
        jToggleButton2.setFocusable(false);
        jToggleButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton2);

        jToggleButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/horizontalgridline.png"))); // NOI18N
        jToggleButton3.setFocusable(false);
        jToggleButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton3ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton3);

        jToggleButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/volumebar16.png"))); // NOI18N
        jToggleButton4.setFocusable(false);
        jToggleButton4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton4.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton4ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton4);

        jToggleButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/volumebarline.png"))); // NOI18N
        jToggleButton5.setFocusable(false);
        jToggleButton5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jToggleButton5.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToggleButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jToggleButton5ActionPerformed(evt);
            }
        });
        jToolBar1.add(jToggleButton5);

        tfStatus.setEditable(false);
        tfStatus.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        tfStatus.setDisabledTextColor(new java.awt.Color(0, 0, 51));
        tfStatus.setEnabled(false);
        jToolBar1.add(tfStatus);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        jSplitPane1.setDividerLocation(465);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnl.setLayout(new java.awt.BorderLayout());

        jToolBar2.setRollover(true);

        jLabel1.setText("Date");
        jPanel1.add(jLabel1);

        tfsDate.setColumns(7);
        tfsDate.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsDate);

        jLabel2.setText("Open");
        jPanel1.add(jLabel2);

        tfsOpen.setColumns(6);
        tfsOpen.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsOpen);

        jLabel3.setText("High");
        jPanel1.add(jLabel3);

        tfsHigh.setColumns(6);
        tfsHigh.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsHigh);

        jLabel4.setText("Low");
        jPanel1.add(jLabel4);

        tfsLow.setColumns(6);
        tfsLow.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsLow);

        jLabel5.setText("Close");
        jPanel1.add(jLabel5);

        tfsClose.setColumns(6);
        tfsClose.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsClose);

        jLabel7.setText("Ch");
        jPanel1.add(jLabel7);

        tfsChange.setColumns(6);
        tfsChange.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsChange);

        jLabel6.setText("Volume");
        jPanel1.add(jLabel6);

        tfsVolume.setColumns(7);
        tfsVolume.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsVolume);

        jLabel8.setText("x:");
        jPanel1.add(jLabel8);

        tfsX.setColumns(6);
        tfsX.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsX);

        jLabel9.setText("y:");
        jPanel1.add(jLabel9);

        tfsY.setColumns(6);
        tfsY.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jPanel1.add(tfsY);

        jToolBar2.add(jPanel1);

        pnl.add(jToolBar2, java.awt.BorderLayout.PAGE_END);

        jSplitPane1.setTopComponent(pnl);

        pnlIndicators.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(pnlIndicators);

        add(jSplitPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimeSelectionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimeSelectionActionPerformed
        // TODO add your handling code here:
        setTimeFrameDialog();

    }//GEN-LAST:event_btnTimeSelectionActionPerformed

    private void btnToolsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnToolsActionPerformed
        // TODO add your handling code here:
        pnlIndicators.removeAll();
        pnlIndicators.add(pti);
        pnlIndicators.update(pnlIndicators.getGraphics());
        pnlIndicators.validate();

    }//GEN-LAST:event_btnToolsActionPerformed

    private void btnIndicatorsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIndicatorsActionPerformed
        // TODO add your handling code here:
        pnlIndicators.removeAll();
        pnlIndicators.add(pid);
        pnlIndicators.update(pnlIndicators.getGraphics());
        pnlIndicators.validate();

    }//GEN-LAST:event_btnIndicatorsActionPerformed

    private void btnSma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSma1ActionPerformed
        // TODO add your handling code here:
        if (btnSma1.isSelected()) {
            showSma1();
            sma1 = true;
        } else {
            removeSma1();
            sma1 = false;
        }

    }//GEN-LAST:event_btnSma1ActionPerformed

    private void btnSma2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSma2ActionPerformed
        // TODO add your handling code here:
        if (btnSma2.isSelected()) {
            showSma2();
            sma2 = true;
        } else {
            removeSma2();
            sma2 = false;
        }

    }//GEN-LAST:event_btnSma2ActionPerformed

    private void btnSma3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSma3ActionPerformed
        // TODO add your handling code here:
        if (btnSma3.isSelected()) {
            showSma3();
            sma3 = true;
        } else {
            removeSma3();
            sma3 = false;
        }

    }//GEN-LAST:event_btnSma3ActionPerformed

    private void btnEma1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEma1ActionPerformed
        // TODO add your handling code here:
        if (btnEma1.isSelected()) {
            showEma1();
            ema1 = true;
        } else {
            removeEma1();
            ema1 = false;
        }

    }//GEN-LAST:event_btnEma1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        this.openExternalImageProcessor();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cmbRendererActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbRendererActionPerformed
        // TODO add your handling code here:
        this.setChartRenderer();
    }//GEN-LAST:event_cmbRendererActionPerformed

    private void cmbYearlyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbYearlyActionPerformed
        // TODO add your handling code here:
        this.showYearlyChart((String) cmbYearly.getSelectedItem());
    }//GEN-LAST:event_cmbYearlyActionPerformed

    private void btnHorizentalAxisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHorizentalAxisActionPerformed
        // TODO add your handling code here:
        if (btnHorizentalAxis.isSelected()) {
            cp.setVerticalAxisTrace(true);
        } else {
            cp.setVerticalAxisTrace(false);
        }
    }//GEN-LAST:event_btnHorizentalAxisActionPerformed

    private void btnVerticalAxisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerticalAxisActionPerformed
        // TODO add your handling code here:
        if (btnVerticalAxis.isSelected()) {
            cp.setHorizontalAxisTrace(true);
        } else {
            cp.setHorizontalAxisTrace(false);
        }
    }//GEN-LAST:event_btnVerticalAxisActionPerformed

    private void btnSpiralActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSpiralActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSpiralActionPerformed

    private void btnTraceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTraceActionPerformed
        // TODO add your handling code here:
        if (btnTrace.isSelected()) {
            cp.setHorizontalAxisTrace(true);
            cp.setVerticalAxisTrace(true);
        } else {
            cp.setHorizontalAxisTrace(false);
            cp.setVerticalAxisTrace(false);
        }

    }//GEN-LAST:event_btnTraceActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        this.setMainPlotBackgroundGradient();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnChartBackgroundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartBackgroundActionPerformed
        // TODO add your handling code here:
        this.setMainPlotBackground();
    }//GEN-LAST:event_btnChartBackgroundActionPerformed

    private void btnGridActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGridActionPerformed
        // TODO add your handling code here:
        this.flipGridLineVisible();
    }//GEN-LAST:event_btnGridActionPerformed

    private void jToggleButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton2ActionPerformed
        // TODO add your handling code here:
        this.flipDomainGridLineVisible();
    }//GEN-LAST:event_jToggleButton2ActionPerformed

    private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton3ActionPerformed
        // TODO add your handling code here:
        this.flipRangeGridLineVisible();
    }//GEN-LAST:event_jToggleButton3ActionPerformed

    private void jToggleButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton4ActionPerformed
        // TODO add your handling code here:
        this.flipVolumeBarVisibility();
    }//GEN-LAST:event_jToggleButton4ActionPerformed

    private void jToggleButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jToggleButton5ActionPerformed
        // TODO add your handling code here:
        this.setVolumeBarColor();
    }//GEN-LAST:event_jToggleButton5ActionPerformed

    public void openExternalImageProcessor() {
        boolean is_saved = saveChartAsPng();
        if (is_saved) {
            CoolMarchImageJ cmi = new CoolMarchImageJ();
            ImageJ ij = cmi.getImageProcessor();

            Opener op = new Opener();
            String path = System.getProperty("user.home");
            //op.open(path + "/dailypngchart.png");
            String sep = System.getProperty("file.separator");
            ImagePlus img = op.openImage(path + sep + "dailypngchart.png");
            img.show();

            ij.setVisible(true);
        }
    }

    private boolean saveChartAsPng() {
        boolean is_saved = false;
        JFreeChart jfc = cp.getChart();
        String path = System.getProperty("user.home");
        String sep = System.getProperty("file.separator");
        File pngFile = new File(path + sep + "dailypngchart.png");

        // int w = pnlChartArea.getPreferredSize().width;
        // int h = pnlChartArea.getPreferredSize().height;
        try {
            ChartUtilities.saveChartAsPNG(pngFile, jfc, 1280, 720);
            is_saved = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            is_saved = false;
        }

        return is_saved;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChartBackground;
    private javax.swing.JToggleButton btnEma1;
    private javax.swing.JToggleButton btnGrid;
    private javax.swing.JToggleButton btnHorizentalAxis;
    private javax.swing.JButton btnIndicators;
    private javax.swing.JToggleButton btnSma1;
    private javax.swing.JToggleButton btnSma2;
    private javax.swing.JToggleButton btnSma3;
    private javax.swing.JToggleButton btnSpiral;
    private javax.swing.JButton btnTimeSelection;
    private javax.swing.JButton btnTools;
    private javax.swing.JToggleButton btnTrace;
    private javax.swing.JToggleButton btnVerticalAxis;
    private javax.swing.JComboBox<String> cmbRenderer;
    private javax.swing.JComboBox<String> cmbYearly;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JToggleButton jToggleButton2;
    private javax.swing.JToggleButton jToggleButton3;
    private javax.swing.JToggleButton jToggleButton4;
    private javax.swing.JToggleButton jToggleButton5;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel pnl;
    private javax.swing.JPanel pnlIndicators;
    private javax.swing.JTextField tfStatus;
    private javax.swing.JTextField tfsChange;
    private javax.swing.JTextField tfsClose;
    private javax.swing.JTextField tfsDate;
    private javax.swing.JTextField tfsHigh;
    private javax.swing.JTextField tfsLow;
    private javax.swing.JTextField tfsOpen;
    private javax.swing.JTextField tfsVolume;
    private javax.swing.JTextField tfsX;
    private javax.swing.JTextField tfsY;
    // End of variables declaration//GEN-END:variables

    public XYDataset getDataset(int days, String type) {

        ArrayList<OHLCDataItem> ohlcDataItemList
                = new ArrayList<OHLCDataItem>();

        XYDataset dataset = null;

        if (days > quoteList.size()) {
            days = quoteList.size();
        }

        for (int i = 0; i <= days - 1; i++) {
            OHLCDataItem item = quoteList.get(i);
            ohlcDataItemList.add(item);
        }

        if (type.compareToIgnoreCase("MAIN_CHART") == 0) {

            this.startDate = quoteList.get(0).getDate();
            this.endDate = quoteList.get(days - 1).getDate();

            pti.setStartDate(startDate);
            pti.setEndDate(endDate);
        }

        Collections.reverse(ohlcDataItemList);
        OHLCDataItem[] data = ohlcDataItemList.toArray(
                new OHLCDataItem[ohlcDataItemList.size()]);

        dataset = new DefaultOHLCDataset("", data);

        return dataset;

    }

    private void showYearlyChart(String year) {

        ArrayList<OHLCDataItem> ohlcDataItemList
                = new ArrayList<OHLCDataItem>();

        XYDataset dataset = null;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i <= quoteList.size() - 1; i++) {
            OHLCDataItem item = quoteList.get(i);
            Date d = item.getDate();
            String dstr = sdf.format(d);
            if (dstr.startsWith(year)) {
                ohlcDataItemList.add(item);
            }

        }

        Collections.reverse(ohlcDataItemList);
        OHLCDataItem[] data = ohlcDataItemList.toArray(
                new OHLCDataItem[ohlcDataItemList.size()]);
        dataset = new DefaultOHLCDataset("", data);
        CandleDailyChart yearChart = new CandleDailyChart(dataset);

        ChartPanel cp = yearChart.getChartPanel();
        cp.zoomInBoth(0.5, 0.5);
        cp.getChart().getXYPlot().getDomainAxis().setAutoRange(true);
        cp.getChart().getXYPlot().getRangeAxis().setAutoRange(true);

        JFrame frame = new JFrame(symbol + year);
        frame.getContentPane().add(cp);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }

    private void setTimeFrameDialog() {
        TimeFrameSelection tfs = new TimeFrameSelection();
        int answer = JOptionPane.showConfirmDialog(null, tfs);
        if (answer == JOptionPane.YES_OPTION) {
            int days = tfs.getSelectedDays();
            this.dayCount = days;
            this.updateTimeChart(days);
            // cc.setDaysCount(days);
            String ci = pti.getCurrentIndicator();
            pti.redraw(ci);
            updateSMALayer();
        }
    }

    private void updateSMALayer() {
        if (sma1) {
            removeSma1();
            showSma1();
        }

        if (sma2) {
            removeSma2();
            showSma2();
        }

        if (sma3) {
            removeSma3();
            showSma3();
        }

    }

    public void updateTimeChart(int days) {
        System.out.println(days);
        cp.getChart().getXYPlot().setDataset(getDataset(days, "MAIN_CHART"));
        if (((String) cmbRenderer.getSelectedItem()).compareToIgnoreCase("Candlestick") == 0) {
            CandlestickRenderer renderer = (CandlestickRenderer) cp.getChart().getXYPlot().getRenderer();
            if (days <= 200) {
                renderer.setCandleWidth(CandlestickRenderer.WIDTHMETHOD_AVERAGE);
            } else {
                renderer.setCandleWidth(2.5f);
            }
        }

        ArrayList<OHLCDataItem> ql = new ArrayList();
        for (int i = 0; i <= days - 1; i++) {
            ql.add(quoteList.get(i));
        }

        PnlRiskAnalysis par = new PnlRiskAnalysis(ql);
        pid.setP2Chart(par.getChartPanel());

        updateSMALayer();
    }

    public void showSma1() {
        TSMA sma = new TSMA(quoteList, getEndDate(), getStartDate(), 15);
        TimeSeries ts = sma.getTs();
        TimeSeriesCollection tcol = new TimeSeriesCollection(ts);
        this.setOverlay(tcol, Color.red, "sma1");
        sma1 = true;
    }

    public void showSma2() {
        TSMA sma = new TSMA(quoteList, getEndDate(), getStartDate(), 30);
        TimeSeries ts = sma.getTs();
        TimeSeriesCollection tcol = new TimeSeriesCollection(ts);
        this.setOverlay(tcol, Color.blue, "sma2");
        sma2 = true;
    }

    public void showSma3() {
        TSMA sma = new TSMA(quoteList, getEndDate(), getStartDate(), 45);
        TimeSeries ts = sma.getTs();
        TimeSeriesCollection tcol = new TimeSeriesCollection(ts);
        this.setOverlay(tcol, Color.ORANGE, "sma3");
        sma3 = true;
    }

    public void removeSma1() {
        this.removeOverlay("sma1");
        sma1 = false;
    }

    public void removeSma2() {
        this.removeOverlay("sma2");
        sma2 = false;
    }

    public void removeSma3() {
        this.removeOverlay("sma3");
        sma3 = false;
    }

    public void setOverlay(TimeSeriesCollection ts, Color c, String identifier) {
        seriesNumber = seriesNumber + 1;
        XYPlot mainPlot = cp.getChart().getXYPlot();
        mainPlot.setDataset(seriesNumber, ts);
        XYLineAndShapeRenderer r = new XYLineAndShapeRenderer(true, false);
        mainPlot.setRenderer(seriesNumber, r);
        r.setSeriesPaint(0, c);
        layer.put(identifier, seriesNumber);
    }

    public void removeOverlay(String identifier) {
        XYPlot mainPlot = cp.getChart().getXYPlot();
        int sr = this.getOverlaySeriesNumber(identifier);
        if (sr == -1) {
        } else {
            mainPlot.setDataset(sr, null);
            mainPlot.setRenderer(sr, null);
            layer.remove(identifier);
            // seriesNumber--;
        }

    }

    public int getOverlaySeriesNumber(String identifier) {
        int sn = -1;
        if (layer.containsKey(identifier)) {
            sn = layer.get(identifier);
        } else {
            sn = -1;
        }

        return sn;
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

    private void showEma1() {
        TEMA ema = new TEMA(quoteList, getEndDate(), getStartDate(), 45);
        TimeSeries ts = ema.getTs();
        TimeSeriesCollection tcol = new TimeSeriesCollection(ts);
        this.setOverlay(tcol, Color.darkGray, "ema1");
        ema1 = true;

    }

    private void removeEma1() {
        this.removeOverlay("ema1");
        ema1 = false;

    }

    private void setConnectedLineRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesPaint(0, Color.orange);
        renderer.setSeriesStroke(0, new BasicStroke(1.5f));

        cp.getChart().getXYPlot().setRenderer(renderer);
    }

    private void setCandlestickRenderer() {
        CandlestickRenderer cr = new CandlestickRenderer();
        cr.setSeriesPaint(0, Color.black);
        cp.getChart().getXYPlot().setRenderer(cr);
    }

    private void setLineRenderer() {
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible(0, true);
        renderer.setSeriesShapesVisible(0, false);
        renderer.setSeriesPaint(0, Color.DARK_GRAY);

        cp.getChart().getXYPlot().setRenderer(renderer);
    }

    private void setOhlcRenderer() {
        HighLowRenderer r = new HighLowRenderer();
        r.setSeriesStroke(0, new BasicStroke(1.0f,
                BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL));
        r.setSeriesPaint(0, Color.black);
        cp.getChart().getXYPlot().setRenderer(r);
    }

    private void setChartRenderer() {
        int index = cmbRenderer.getSelectedIndex();
        if (index == 0) {
            this.setCandlestickRenderer();
        } else if (index == 1) {
            this.setLineRenderer();
        } else if (index == 2) {
            this.setConnectedLineRenderer();
        } else if (index == 3) {
            this.setOhlcRenderer();
        }
    }

    private void setDiagonalLine(double x, double y) {
        ValueAxis yAxis = cp.getChart().getXYPlot().getRangeAxis();
        ValueAxis xAxis = cp.getChart().getXYPlot().getDomainAxis();

        XYAnnotation diagonal = new XYLineAnnotation(x,
                y, xAxis.getRange().getUpperBound(),
                yAxis.getRange().getUpperBound());
        cp.getChart().getXYPlot().addAnnotation(diagonal);
    }

    private void drawSpiral(double x, double y) {
        setDiagonalLine(x, y);

    }

    private Color getColor(Color previousColor) {
        Color c = JColorChooser.showDialog(null, "Choose a Color",
                previousColor);
        if (c == null) {
            c = previousColor;
        }

        return c;
    }

    public void setMainPlotBackgroundGradient() {
        GradientColor gc = new GradientColor();
        int answer = JOptionPane.showConfirmDialog(null, gc);
        if (answer == JOptionPane.YES_OPTION) {
            Color c1 = gc.getStartColor();
            Color c2 = gc.getEndColor();

            GradientPaint gp = new GradientPaint(0, 0, c1, 0, 500, c2);
            cp.getChart().getXYPlot().setBackgroundPaint(gp);

        }
    }

    public void setMainPlotBackground() {
        Color c = Color.white;
        try {

            c = getColor((Color) cp.getChart().getXYPlot().getBackgroundPaint());
        } catch (Exception e) {
            c = Color.white;
        }

        cp.getChart().getXYPlot().setBackgroundPaint(c);
    }

    public void flipDomainGridLineVisible() {
        XYPlot mainPlot = cp.getChart().getXYPlot();
        if (mainPlot.isDomainGridlinesVisible()) {
            mainPlot.setDomainGridlinesVisible(false);
        } else {
            mainPlot.setDomainGridlinesVisible(true);
        }
    }

    public void flipRangeGridLineVisible() {
        XYPlot mainPlot = cp.getChart().getXYPlot();

        if (mainPlot.isRangeGridlinesVisible()) {
            mainPlot.setRangeGridlinesVisible(false);
        } else {
            mainPlot.setRangeGridlinesVisible(true);
        }
    }

    public void setVolumeBarColor() {
        CandlestickRenderer renderer = (CandlestickRenderer) cp.getChart().getXYPlot().getRenderer();
        Color color = getColor((Color) renderer.getVolumePaint());
        renderer.setVolumePaint(color);
    }

    public void flipVolumeBarVisibility() {
        CandlestickRenderer renderer = (CandlestickRenderer) cp.getChart().getXYPlot().getRenderer();
        if (renderer.getDrawVolume() == true) {
            renderer.setDrawVolume(false);
        } else {
            renderer.setDrawVolume(true);
        }
    }

    public void changeCandleOutlineColor() {
        Color c = getColor(Color.black);
        CandlestickRenderer renderer = (CandlestickRenderer) cp.getChart().getXYPlot().getRenderer();
        renderer.setSeriesPaint(0, c);
    }

    public void setCandleColor() {
        CandleColorSetting ccs = new CandleColorSetting();
        int answer = JOptionPane.showConfirmDialog(null, ccs);
        if (answer == JOptionPane.YES_OPTION) {
            Color gain = ccs.getGainColor();
            Color loss = ccs.getLossColor();
            Color outline = ccs.getOutlineColor();
            changeCandleColor(gain, loss, outline);
        }
    }

    private void changeCandleColor(Color gain, Color loss, Color outline) {
        CandlestickRenderer renderer
                = (CandlestickRenderer) cp.getChart().getXYPlot().getRenderer();
        renderer.setUpPaint(gain);
        renderer.setDownPaint(loss);
        renderer.setSeriesPaint(0, outline);
    }

    private void flipGridLineVisible() {
        XYPlot mainPlot = cp.getChart().getXYPlot();
        if (btnGrid.isSelected()) {
            mainPlot.setRangeGridlinesVisible(false);
            mainPlot.setDomainGridlinesVisible(false);
        } else {

            mainPlot.setRangeGridlinesVisible(true);
            mainPlot.setDomainGridlinesVisible(true);

        }
    }

    private void joinChartViewPeriod(int period) {

        ArrayList<OHLCDataItem> ohlcDataItemList
                = new ArrayList<OHLCDataItem>();

        XYDataset dataset = null;
        for (int i = 0; i <= quoteList.size() - 1; i++) {
            ArrayList<Quote> x = new ArrayList<>();

        }

        Collections.reverse(ohlcDataItemList);
        OHLCDataItem[] data = ohlcDataItemList.toArray(
                new OHLCDataItem[ohlcDataItemList.size()]);
        dataset = new DefaultOHLCDataset("", data);
        CandleDailyChart yearChart = new CandleDailyChart(dataset);

        ChartPanel cp = yearChart.getChartPanel();
        cp.zoomInBoth(0.5, 0.5);
        cp.getChart().getXYPlot().getDomainAxis().setAutoRange(true);
        cp.getChart().getXYPlot().getRangeAxis().setAutoRange(true);

        JFrame frame = new JFrame(symbol + period);
        frame.getContentPane().add(cp);
        frame.setSize(700, 500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    }


}
