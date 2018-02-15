/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.awt.Font;
import java.util.ArrayList;
import java.util.Collections;
import net.coolmarch.cmnew.common.TickData;
import net.coolmarch.cmnew.res.CMResource;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.DefaultOHLCDataset;
import org.jfree.data.xy.OHLCDataItem;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.HorizontalAlignment;

/**
 *
 * @author coolmarch
 */
public class MyChartFactory {

    public MyChartFactory() {
    }

    public static ChartPanel getStaticChart(int day) {
        CMResource cmr = new CMResource();
        String symbol = "static";
        // PDataDaemon tdf = new PDataDaemon(symbol);
        PVImporter pvi = new PVImporter(symbol, 10);
        ArrayList<TickData> tlist = pvi.getPVStaticData(day);
        Collections.reverse(tlist);
        PDataChart pvc = new PDataChart(tlist);
        ChartPanel cp = pvc.getChartPanel();
        JFreeChart chart = cp.getChart();
        TextTitle tt = new TextTitle("100-Index - Last 10 days");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(tt);

        return cp;
    }

    public static ChartPanel getPreviousDayStaticChart() {
        String symbol = "static";
        CUPriceVolumeChart cu = new CUPriceVolumeChart(symbol, 1);
        ChartPanel cpi = cu.getChartPanel2();
        JFreeChart chart = cpi.getChart();

        TextTitle tt = new TextTitle(symbol + "Previous Days");
        Font font = new Font("Verdana", Font.BOLD, 10);
        tt.setFont(font);
        tt.setHorizontalAlignment(HorizontalAlignment.CENTER);
        chart.setTitle(tt);

        return cpi;

    }

    public static ChartPanel getCandleStickChart(ArrayList<OHLCDataItem> ohlcList) {
        OHLCDataItem[] data = ohlcList.toArray(new OHLCDataItem[ohlcList.size()]);
        XYDataset dataset = new DefaultOHLCDataset("", data);
        CandleDailyChart yearChart = new CandleDailyChart(dataset);

        ChartPanel cp = yearChart.getChartPanel();
        cp.zoomInBoth(0.5, 0.5);
        cp.getChart().getXYPlot().getDomainAxis().setAutoRange(true);
        cp.getChart().getXYPlot().getRangeAxis().setAutoRange(true);
        return cp;
    }

}
