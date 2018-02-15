/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart.ti;

import com.tictactec.ta.lib.Core;
import com.tictactec.ta.lib.MInteger;
import com.tictactec.ta.lib.RetCode;
import java.util.ArrayList;
import java.util.Date;
import net.coolmarch.cmnew.common.Quote;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.xy.OHLCDataItem;

/**
 *
 * @author salam
 */
public class TATR {

    private final int TOTAL_PERIODS;
    private final int PERIODS_AVERAGE;
    private TimeSeries ts;
    private TimeSeries tsClose;
    private ArrayList<OHLCDataItem> quoteList;

    public TATR(ArrayList<OHLCDataItem> quoteList,
            Date minDate, Date maxDate, int days) {
        this.quoteList = quoteList;

        TOTAL_PERIODS = quoteList.size();
        PERIODS_AVERAGE = days;
        ts = new TimeSeries("SMA-" + days);
        tsClose = new TimeSeries("Close");

        double[] closePrice = new double[TOTAL_PERIODS];
        double[] highPrice = new double[TOTAL_PERIODS];
        double[] lowPrice =  new double[TOTAL_PERIODS];
        
        double[] out = new double[TOTAL_PERIODS];
        MInteger begin = new MInteger();
        MInteger length = new MInteger();
        for (int i = 0; i < closePrice.length; i++) {
            OHLCDataItem q = (OHLCDataItem)quoteList.get(i);
            closePrice[i] = q.getClose().doubleValue();
            highPrice[i] = q.getHigh().doubleValue();
            lowPrice[i] = q.getLow().doubleValue();
        }

        Core c = new Core();
        
        RetCode retCode = c.atr(0, closePrice.length-1, highPrice, lowPrice, 
                closePrice, PERIODS_AVERAGE, begin, length, out);
        
        

        if (retCode == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output End:" + length.value);

            for (int i = 0; i <= length.value; i++) {
                OHLCDataItem q = quoteList.get(i);
                Date d = q.getDate();
                double qclose = q.getClose().doubleValue();

                if (d.before(minDate) || d.after(maxDate)) {
                    ;//do nothing
                } else {
                    this.tsClose.add(new Day(d), qclose);
                    this.ts.add(new Day(d), out[i]);
                }

            }

        } else {
            System.out.println("Error");
        }
    }

    public TimeSeries getTs() {
        return ts;
    }

    public void setTs(TimeSeries ts) {
        this.ts = ts;
    }

    public TimeSeries getTsClose() {
        return tsClose;
    }

    public void setTsClose(TimeSeries tsClose) {
        this.tsClose = tsClose;
    }

}
