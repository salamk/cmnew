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
public class TMACD {
    private final int TOTAL_PERIODS;
    private final int PERIODS_AVERAGE;
    private TimeSeries tsMacd;
    private TimeSeries tsSignal;
    private TimeSeries tsHistogram;
    private TimeSeries tsClose;
    private ArrayList<OHLCDataItem> quoteList;

    public TMACD(ArrayList<OHLCDataItem> quoteList, Date minDate, Date maxDate, int days) {
        this.quoteList = quoteList;

        TOTAL_PERIODS = quoteList.size();
        PERIODS_AVERAGE = days;
        
        tsMacd = new TimeSeries("MACD-" + days);
        tsSignal = new TimeSeries("Sig");
        tsHistogram = new TimeSeries("His");
        
        tsClose = new TimeSeries("Close");

        double[] closePrice = new double[TOTAL_PERIODS];
        double[] outMacd = new double[TOTAL_PERIODS];
        double[] outSignal = new double[TOTAL_PERIODS];
        double[] outHistogram = new double[TOTAL_PERIODS];
        
        MInteger begin = new MInteger();
        MInteger l = new MInteger();
        for (int i = 0; i < closePrice.length; i++) {
            closePrice[i] = (quoteList.get(i)).getClose().doubleValue();
        }

        Core c = new Core();
//        int startIdx = 0;
//        int endIdx = closePrice.length-1;
//        //inReal = closePrice
//        int fastPeriod = 26;
//        int slowPeriod = 12;
//        int signalPeriod = 9;
        
//        RetCode retCode = c.ema(0, closePrice.length - 1, closePrice,
//                PERIODS_AVERAGE, begin, length, out);

 //retCode = lib.macd(0,close.length-1,close,15,26,9,outBegIdx,outNbElement,macd,signal,hist);
 
 RetCode retCode = c.macd(0, closePrice.length-1, closePrice, 15, 
               26, 9, begin, l, outMacd, 
               outSignal, outHistogram);

        if (retCode == RetCode.Success) {
            System.out.println("Output Begin:" + begin.value);
            System.out.println("Output End:" + l.value);

            for (int i = 0; i <= l.value; i++) {
                OHLCDataItem q = quoteList.get(i);
                Date d = q.getDate();
                double qclose = q.getClose().doubleValue();

                if (d.before(minDate) || d.after(maxDate)) {
                    ;//do nothing
                } else {
                    this.tsClose.add(new Day(d), qclose);
                    this.tsMacd.add(new Day(d), outMacd[i]);
                    this.tsSignal.add(new Day(d), outSignal[i]);
                    this.tsHistogram.add(new Day(d), outHistogram[i]);
                }

            }

        } else {
            System.out.println("Error");
        }
    }

    public TimeSeries getTsMacd() {
        return tsMacd;
    }

    public void setTsMacd(TimeSeries tsMacd) {
        this.tsMacd = tsMacd;
    }

    public TimeSeries getTsSignal() {
        return tsSignal;
    }

    public void setTsSignal(TimeSeries tsSignal) {
        this.tsSignal = tsSignal;
    }

    public TimeSeries getTsHistogram() {
        return tsHistogram;
    }

    public void setTsHistogram(TimeSeries tsHistogram) {
        this.tsHistogram = tsHistogram;
    }

    public TimeSeries getTsClose() {
        return tsClose;
    }

    public void setTsClose(TimeSeries tsClose) {
        this.tsClose = tsClose;
    }

    
    
    
}
