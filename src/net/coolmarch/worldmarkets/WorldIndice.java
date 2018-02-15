/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.worldmarkets;

/**
 *
 * @author sania
 */
public class WorldIndice {
    private String exchangeName;
    private String exchangeIndexOpen;
    private String exchangeGainLoss;
    private String exchangeDayHigh, exchangeDayLow;
    private String exchangeLastTrade;
    private String exchangeIndexChange;

    public String getExchangeDayHigh() {
        return exchangeDayHigh;
    }

    public void setExchangeDayHigh(String exchangeDayHigh) {
        this.exchangeDayHigh = exchangeDayHigh;
    }

    public String getExchangeDayLow() {
        return exchangeDayLow;
    }

    public void setExchangeDayLow(String exchangeDayLow) {
        this.exchangeDayLow = exchangeDayLow;
    }

    public String getExchangeLastTrade() {
        return exchangeLastTrade;
    }

    public void setExchangeLastTrade(String exchangeLastTrade) {
        this.exchangeLastTrade = exchangeLastTrade;
    }

    public String getExchangeIndexChange() {
        return exchangeIndexChange;
    }

    public void setExchangeIndexChange(String exchangeIndexChange) {
        this.exchangeIndexChange = exchangeIndexChange;
    }
    

    public WorldIndice() {
    }


    public String getExchangeName() {
        return exchangeName;
    }

    public void setExchangeName(String exchangeName) {
        this.exchangeName = exchangeName;
    }

    public String getExchangeIndexOpen() {
        return exchangeIndexOpen;
    }

    public void setExchangeIndexOpen(String exchangeIndexValue) {
        this.exchangeIndexOpen = exchangeIndexValue;
    }

    public String getExchangeGainLoss() {
        return exchangeGainLoss;
    }

    public void setExchangeGainLoss(String exchangeGainLoss) {
        this.exchangeGainLoss = exchangeGainLoss;
    }

    
    
    
}
