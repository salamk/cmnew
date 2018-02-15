/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.util.ArrayList;

/**
 *
 * @author salam
 */
public final class ExchangeStatus {
    
    private String exchangeState;
    private String exchangeVolume;
    private String exchangeValue;
    private String totalTrades;
    private String numAdvance;
    private String numDecline;
    private String numUnchanged;
    private String hundredIndexLevel;
    private String hundredIndexChange;
    private String thirtyIndexLevel;
    private String thirtyIndexChange;
    private String kmiThirtyIndexLevel;
    private String kmiThirtyIndexChange;
    private String allShareIndexLevel;
    private String allShareIndexChange;
    private String allShareIslamicIndexLevel;
    private String allShareIslamicIndexChange;
    
    public ExchangeStatus(){
        
    }
    
    public ExchangeStatus(ArrayList<String> info){
       if(info.isEmpty()){
           System.out.println("No exchange info found..");
       }else{
           String line = info.get(0);
           String[] toke = line.split(",");
           this.exchangeState = toke[0];
           this.exchangeVolume = toke[1];
           this.exchangeValue = toke[2];
           this.totalTrades = toke[3];
           
           line = info.get(1);
           toke = line.split(",");
           this.numAdvance = toke[0];
           this.numDecline = toke[1];
           this.numUnchanged = toke[2];
           
           line = info.get(2);
           toke = line.split(",");
           this.hundredIndexLevel = toke[1];
           this.hundredIndexChange = toke[2];
           
           line = info.get(3);
           toke = line.split(",");
           this.allShareIndexLevel = toke[1];
           this.allShareIndexChange = toke[2];
           
           line = info.get(4);
           toke = line.split(",");
           this.thirtyIndexLevel = toke[1];
           this.thirtyIndexChange = toke[2];
           
           line = info.get(5);
           toke = line.split(",");
           this.kmiThirtyIndexLevel = toke[1];
           this.kmiThirtyIndexChange = toke[2];
           
           line = info.get(6);
           toke = line.split(",");
           this.allShareIslamicIndexLevel = toke[1];
           this.allShareIslamicIndexChange = toke[2];
           
           
           
       }
    }

    public String getExchangeState() {
        return exchangeState;
    }

    public void setExchangeState(String exchangeState) {
        this.exchangeState = exchangeState;
    }

    public String getExchangeVolume() {
        return exchangeVolume;
    }

    public void setExchangeVolume(String exchangeVolume) {
        this.exchangeVolume = exchangeVolume;
    }

    public String getExchangeValue() {
        return exchangeValue;
    }

    public void setExchangeValue(String exchangeValue) {
        this.exchangeValue = exchangeValue;
    }

    public String getTotalTrades() {
        return totalTrades;
    }

    public void setTotalTrades(String totalTrades) {
        this.totalTrades = totalTrades;
    }

    public String getNumAdvance() {
        return numAdvance;
    }

    public void setNumAdvance(String numAdvance) {
        this.numAdvance = numAdvance;
    }

    public String getNumDecline() {
        return numDecline;
    }

    public void setNumDecline(String numDecline) {
        this.numDecline = numDecline;
    }

    public String getNumUnchanged() {
        return numUnchanged;
    }

    public void setNumUnchanged(String numUnchanged) {
        this.numUnchanged = numUnchanged;
    }

    public String getHundredIndexLevel() {
        return hundredIndexLevel;
    }

    public void setHundredIndexLevel(String hundredIndexLevel) {
        this.hundredIndexLevel = hundredIndexLevel;
    }

    public String getHundredIndexChange() {
        return hundredIndexChange;
    }

    public void setHundredIndexChange(String hundredIndexChange) {
        this.hundredIndexChange = hundredIndexChange;
    }

    public String getThirtyIndexLevel() {
        return thirtyIndexLevel;
    }

    public void setThirtyIndexLevel(String thirtyIndexLevel) {
        this.thirtyIndexLevel = thirtyIndexLevel;
    }

    public String getThirtyIndexChange() {
        return thirtyIndexChange;
    }

    public void setThirtyIndexChange(String thirtyIndexChange) {
        this.thirtyIndexChange = thirtyIndexChange;
    }

    public String getKmiThirtyIndexLevel() {
        return kmiThirtyIndexLevel;
    }

    public void setKmiThirtyIndexLevel(String kmiThirtyIndexLevel) {
        this.kmiThirtyIndexLevel = kmiThirtyIndexLevel;
    }

    public String getKmiThirtyIndexChange() {
        return kmiThirtyIndexChange;
    }

    public void setKmiThirtyIndexChange(String kmiThirtyIndexChange) {
        this.kmiThirtyIndexChange = kmiThirtyIndexChange;
    }

    public String getAllShareIndexLevel() {
        return allShareIndexLevel;
    }

    public void setAllShareIndexLevel(String allShareIndexLevel) {
        this.allShareIndexLevel = allShareIndexLevel;
    }

    public String getAllShareIndexChange() {
        return allShareIndexChange;
    }

    public void setAllShareIndexChange(String allShareIndexChange) {
        this.allShareIndexChange = allShareIndexChange;
    }

    public String getAllShareIslamicIndexLevel() {
        return allShareIslamicIndexLevel;
    }

    public void setAllShareIslamicIndexLevel(String allShareIslamicIndexLevel) {
        this.allShareIslamicIndexLevel = allShareIslamicIndexLevel;
    }

    public String getAllShareIslamicIndexChange() {
        return allShareIslamicIndexChange;
    }

    public void setAllShareIslamicIndexChange(String allShareIslamicIndexChange) {
        this.allShareIslamicIndexChange = allShareIslamicIndexChange;
    }
    
    
    
}
