/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.daemon;

import org.jfree.chart.ChartPanel;

/**
 *
 * @author coolmarch
 */
public class ChartValueMarker {
    
    private String category;
    private String symbol;
    private ChartPanel cp;
    private int id;
    
    public ChartValueMarker(ChartPanel cp, String category, String symbol){
        this.cp = cp;
        this.category = category;
        this.symbol = symbol;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public ChartPanel getCp() {
        return cp;
    }

    public void setCp(ChartPanel cp) {
        this.cp = cp;
    }
    
    
}
