/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

/**
 *
 * @author sania
 */
public class IntradayDailyQuote {
    
    private double dailyQuotePrice;
    private int dailyQuoteVolume;
    private int index;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
    public IntradayDailyQuote(){
    }

    public double getDailyQuotePrice() {
        return dailyQuotePrice;
    }

    public void setDailyQuotePrice(double dailyQuotePrice) {
        this.dailyQuotePrice = dailyQuotePrice;
    }

    public int getDailyQuoteVolume() {
        return dailyQuoteVolume;
    }

    public void setDailyQuoteVolume(int dailyQuoteVolume) {
        this.dailyQuoteVolume = dailyQuoteVolume;
    }
    
    
}
