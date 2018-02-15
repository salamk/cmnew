/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart.ti;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class ADLine {
    TimeSeriesCollection timeSeriesCollection;
    TimeSeriesCollection tcClose;
    private ArrayList<OHLCDataItem> quoteList;
    
    public ADLine(ArrayList<OHLCDataItem> quoteList, 
            Date minDate, Date maxDate, int days) {

        this.quoteList = quoteList;
        ArrayList<DateValuePair> dvpList = new ArrayList<DateValuePair>();
       // Collections.reverse(quoteList);
        TimeSeries ts = new TimeSeries("ADL");
        double prevAdl = 0;

        for (int i = 0; i <= quoteList.size() - 1; i++) {
            OHLCDataItem item = (OHLCDataItem) quoteList.get(i);
            Date d = item.getDate();
            double high = item.getHigh().doubleValue();
            double low = item.getLow().doubleValue();
            double close = item.getClose().doubleValue();
            double volume = item.getVolume().doubleValue();
            double x = close - low;
            double y = high - close;
            double z = high - low;
            
            System.out.println(x+", "+y+", "+z+", "+high+", "+low+", "+close);

            double mfm = (x - y) / z;
            double mfv = mfm * volume;
            double adl = prevAdl + mfv;
            prevAdl = adl;
            
            System.out.println(mfm+"=="+mfv+"=="+adl);

            DateValuePair dvp = new DateValuePair(d, adl);
            dvpList.add(dvp);
        }

       // Collections.reverse(quoteList);
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
