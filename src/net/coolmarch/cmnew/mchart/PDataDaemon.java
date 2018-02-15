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
import net.coolmarch.cmnew.common.TickData;

/**
 *
 * @author salam
 */
public class PDataDaemon {

    private ArrayList<TickData> quoteList;
    private String symbol;

    public PDataDaemon(String scode) {
        quoteList = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String symbol = "P_" + scode;
        CMImporter cmi = new CMImporter(symbol + ".zip");
        ArrayList<String> lines = cmi.getLines();

        for (String line : lines) {
          //  System.out.println(line);
            String[] toke = line.split(",");
            String price = toke[0];
            String volume = toke[1];
            String time = toke[2];

            if (time.contains("09:30:00")) {
            } else {
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

        //quoteList.remove(quoteList.size()-1);
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
