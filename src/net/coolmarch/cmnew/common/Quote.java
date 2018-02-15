/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



/**
 *
 * @author salam
 */
public class Quote {
    private String symbol;
    private Date date;
    private double open, high, low, close, volume;
    public Quote(){
        date = new Date();
        open = 0;
        high = 0;
        low = 0;
        close = 0;
        volume = 0;
        symbol = "";
    }

    public Quote(String symbol, Date date, double open, double high, 
            double low, double close, double volume) {
        
        this.symbol = symbol;
        this.date = date;
        this.open = open;
        this.high = high;
        this.low = low;
        this.close = close;
        this.volume = volume;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getClose() {
        return close;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
    
    public String toString(){
        String str = "";
        DecimalFormat df = new DecimalFormat("#.##");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        
        symbol = CommonText.monosizeLeft(symbol, 12);
        String sopen = CommonText.monosizeLeft(df.format(open), 8);
        String shigh = CommonText.monosizeLeft(df.format(high), 8);
        String slow = CommonText.monosizeLeft(df.format(low), 8);
        String sclose = CommonText.monosizeLeft(df.format(close), 8);
        String svolume = CommonText.monosizeLeft(df.format(volume), 12);
        String sdate = CommonText.monosizeLeft(sdf.format(date), 12);
        
        str+=sdate+"|"+sopen+"|"+shigh+"|"+slow+"|"+sclose+"|"+svolume;
        return str;
    }
    
    
    
}
