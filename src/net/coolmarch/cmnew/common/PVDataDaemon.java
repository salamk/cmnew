/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author salam
 */
public class PVDataDaemon {

    private ArrayList<TickData> quoteList;
    private String symbol;
    private static final String scriptCode = "GHD";

    public PVDataDaemon(String scode, String configStr) {
        quoteList = new ArrayList<TickData>();
        String symbol = "PV_" + scode;
        CMImporter cmi = new CMImporter(symbol + ".zip");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ArrayList<String> lines = cmi.getLines();

        for (String line : lines) {
            String[] toke = line.split(",");
            String price = toke[0];
            String volume = toke[1];
            String time = toke[2];

            Date d = new Date();
            try {
                d = sdf.parse(time);
            } catch (Exception e) {
            }

            double pr = Double.parseDouble(price);
            int vo = Integer.parseInt(volume);

            TickData td = new TickData();
            td.setDailyQuotePrice(pr);
            td.setDailyQuoteVolume(vo);
            td.setQuoteDate(d);

            quoteList.add(td);

        }

    }

    public ArrayList<TickData> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(ArrayList<TickData> quoteList) {
        this.quoteList = quoteList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
