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
 * @author sania
 */
public class WilliamPercentRange {

    private TimeSeriesCollection tsc;
    private TimeSeriesCollection tscClose;
    private ArrayList<OHLCDataItem> quoteList;

    public WilliamPercentRange(ArrayList<OHLCDataItem> quoteList) {
        this.quoteList = quoteList;
    }

    public WilliamPercentRange(ArrayList<OHLCDataItem>quoteList, Date minDate, 
            Date maxDate, int days) {
        
        this.quoteList = quoteList;
        
        ArrayList<DateValuePair> wprList = new ArrayList<>();
        Collections.reverse(quoteList);
        for (int i = days; i <= quoteList.size() - 1; i++) {
            double[] hvals = new double[days];
            double[] lvals = new double[days];

            for (int j = 0; j <= days - 1; j++) {
                OHLCDataItem q = quoteList.get(i - j);
                double high = q.getHigh().doubleValue();
                double low = q.getLow().doubleValue();
                hvals[j] = high;
                lvals[j] = low;
            }

            OHLCDataItem q = quoteList.get(i);

            double high = getHigh(hvals);
            double low = getLow(lvals);
            double close = q.getClose().doubleValue();

            double f1 = high - close;
            double f2 = high - low;
            double wpr = (f1 / f2) * (-100);

            Date dt = q.getDate();

            DateValuePair dvp = new DateValuePair(dt, wpr);
            wprList.add(dvp);

        }
        
        Collections.reverse(quoteList);
                TimeSeries ts = new TimeSeries("William %R");
                TimeSeries tsclose = new TimeSeries("Close Price");
                
        System.out.println("Min : " + minDate.toString());
        System.out.println("Max : " + maxDate.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (int i = 0; i <= wprList.size() - 1; i++) {
            DateValuePair dvp = wprList.get(i);
            Date d = dvp.getDate();
            if (d.before(minDate) || d.after(maxDate)) {
            } else {
                tsclose.add(new Day(d), 0);
                ts.add(new Day(d), dvp.getValue());
                System.out.println(dvp.toString());
            }
        }

        tsc = new TimeSeriesCollection(ts);
        tscClose = new TimeSeriesCollection(tsclose);
        
    }

    public TimeSeriesCollection getTsc() {
        return tsc;
    }

    public void setTsc(TimeSeriesCollection tsc) {
        this.tsc = tsc;
    }

    public TimeSeriesCollection getTscClose() {
        return tscClose;
    }

    public void setTscClose(TimeSeriesCollection tscClose) {
        this.tscClose = tscClose;
    }
    
    


    public double getHigh(double[] vals) {
        double high = vals[0];
        for (int i = 1; i <= vals.length - 1; i++) {
            if (vals[i] > high) {
                high = vals[i];
            }
        }

        return high;

    }

    public double getLow(double[] vals) {
        double low = vals[0];
        for (int i = 1; i <= vals.length - 1; i++) {
            if (vals[i] < low) {
                low = vals[i];
            }
        }

        return low;
    }

}
