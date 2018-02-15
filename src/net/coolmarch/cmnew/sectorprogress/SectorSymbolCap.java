/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

/**
 *
 * @author salam
 */
public class SectorSymbolCap {

    private String sectorName;
    private String symbol;
    private double marketCap;

    public SectorSymbolCap(String sectorName, String symbol, double marketCap) {
        this.sectorName = sectorName;
        this.symbol = symbol;
        this.marketCap = marketCap;
    }

    public String getSectorName() {
        return sectorName;
    }

    public void setSectorName(String sectorName) {
        this.sectorName = sectorName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(double marketCap) {
        this.marketCap = marketCap;
    }

}
