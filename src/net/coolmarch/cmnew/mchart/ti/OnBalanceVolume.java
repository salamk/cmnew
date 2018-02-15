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
import net.coolmarch.cmnew.common.Quote;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.OHLCDataItem;

/**
 *
 * @author salam
 */
public class OnBalanceVolume {

    TimeSeriesCollection timeSeriesCollection;
    TimeSeriesCollection tcClose;
    private ArrayList<OHLCDataItem> quoteList;

    public OnBalanceVolume(ArrayList<OHLCDataItem> quoteList, Date minDate, Date maxDate, int days) {

        this.quoteList = quoteList;
        ArrayList<DateValuePair> dvpList = new ArrayList<DateValuePair>();
        Collections.reverse(quoteList);
        TimeSeries ts = new TimeSeries("OBV");
        OHLCDataItem q = (OHLCDataItem) quoteList.get(0);
        double prevObv = 0;
        double prevClose = q.getClose().doubleValue();

        for (int i = 1; i <= quoteList.size() - 1; i++) {
            OHLCDataItem item = (OHLCDataItem) quoteList.get(i);
            Date d = item.getDate();
            double close = item.getClose().doubleValue();

            double volume = item.getVolume().doubleValue();
            double diff = close - prevClose;
            double currentObv = 0;
            if (diff > 0) {
                currentObv = prevObv + volume;
            } else if (diff < 0) {
                currentObv = prevObv - volume;
            } else {
                currentObv = prevObv;
            }

            prevObv = currentObv;
            prevClose = close;

            DateValuePair dvp = new DateValuePair(d, currentObv);
            dvpList.add(dvp);
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
                tsclose.add(new Day(d), 0);
                ts.add(new Day(d), dvp.getValue());
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
