/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart.ti;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import net.coolmarch.cmnew.common.DateValuePair;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataItem;

/**
 *
 * @author salam
 */
public class RelativeStrengthIndex {

    TimeSeriesCollection timeSeriesCollection;
    TimeSeriesCollection tcClose;
    private ArrayList<OHLCDataItem> quoteList;

    public RelativeStrengthIndex(ArrayList<OHLCDataItem> quoteList, Date minDate, Date maxDate, int days) {
        this.quoteList = quoteList;
        ArrayList<DateValuePair> dvpList = new ArrayList<DateValuePair>();
        Collections.reverse(quoteList);
        TimeSeries ts = new TimeSeries("RSI");

        int counter = 0;
        double avgGain = 0;
        double avgLoss = 0;

        for (int i = 0; i <= quoteList.size() - 1; i++) {
            OHLCDataItem q = (OHLCDataItem) quoteList.get(i);
            double change = q.getClose().doubleValue() - q.getOpen().doubleValue();
            double rs = 0;
            double rsi = 0;

            if (counter <= days) {
                if (change < 0) {
                    avgLoss += Math.abs(change);
                } else {
                    avgGain += Math.abs(change);
                }

                if (counter % days == 0 && counter != 0) {
                    //  System.out.println("Now reached at index: " + counter + " ..");
                    avgGain = avgGain / days;
                    avgLoss = avgLoss / days;
                    //  System.out.println("Avg-Gain = " + avgGain + " and avg-Loss = " + avgLoss);
                    rs = avgGain / avgLoss;
                    rsi = 100 - (100 / (1 + rs));
//                    RSIData rsd = new RSIData(d, rsi);
                    dvpList.add(new DateValuePair(q.getDate(), rsi));
                }
            } else {

                double loss = 0;
                double gain = 0;

                if (change < 0) {
                    gain = 0;
                    loss += Math.abs(change);
                } else if (change > 0) {
                    loss = 0;
                    gain += Math.abs(change);
                }

                avgGain = ((avgGain * (days - 1)) + gain) / days;
                avgLoss = ((avgLoss * (days - 1)) + loss) / days;

                rs = avgGain / avgLoss;
                rsi = 100 - (100 / (1 + rs));
//                RSIData rsd = new RSIData(d, rsi);
                dvpList.add(new DateValuePair(q.getDate(), rsi));

            }

            counter++;

        }

        Collections.reverse(quoteList);
        TimeSeries tsclose = new TimeSeries("Close Price");

        System.out.println("Min : " + minDate.toString());
        System.out.println("Max : " + maxDate.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i <= dvpList.size() - 1; i++) {
            DateValuePair dvp = dvpList.get(i);
            Date d = dvp.getDate();
            if (d.before(minDate) || d.after(maxDate)) {
            } else {
                tsclose.addOrUpdate(new Day(d), 0);
                ts.addOrUpdate(new Day(d), dvp.getValue());
                System.out.println(dvp.toString());
            }
        }

        timeSeriesCollection = new TimeSeriesCollection(ts);
        tcClose = new TimeSeriesCollection(tsclose);

    }

    public TimeSeriesCollection getTimeSeriesCollection() {
        return timeSeriesCollection;
    }

    public void setTimeSeriesCollection(TimeSeriesCollection timeSeriesCollection) {
        this.timeSeriesCollection = timeSeriesCollection;
    }

    public TimeSeriesCollection getTcClose() {
        return tcClose;
    }

    public void setTcClose(TimeSeriesCollection tcClose) {
        this.tcClose = tcClose;
    }

}
