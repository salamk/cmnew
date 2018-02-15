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
public class SymbolCap {

    private String symbol;
    private double cap;

    public SymbolCap(String symbol, double cap) {
        this.symbol = symbol;
        this.cap = cap;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

}
