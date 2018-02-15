/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.Quote;

/**
 *
 * @author salam
 */
public class HistoricalData {

    private ArrayList<Quote> quoteList;
    private String symbol;

    public HistoricalData(String symbol) {
        quoteList = new ArrayList<Quote>();
        CMImporter cmi = new CMImporter(symbol+".zip");
        //quoteList = cmi.getQuoteList();
        ArrayList<String> lines = cmi.getLines();
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (String line : lines) {
            String[] toke = line.split(",");
            String vdate = toke[0];
            String vopen = toke[1];
            String vhigh = toke[2];
            String vlow = toke[3];
            String vclose = toke[4];
            String vvolume = toke[5];
            Date d = new Date();
            try {
                d = sdf.parse(vdate);
            } catch (Exception e) {
            }

            double open = Double.parseDouble(vopen);
            double high = Double.parseDouble(vhigh);
            double low = Double.parseDouble(vlow);
            double close = Double.parseDouble(vclose);
            double volume = Double.parseDouble(vvolume);

            Quote q = new Quote(symbol, d, open, high, low, close, volume);
            quoteList.add(q);

        }

    }


    public ArrayList<Quote> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(ArrayList<Quote> quoteList) {
        this.quoteList = quoteList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
