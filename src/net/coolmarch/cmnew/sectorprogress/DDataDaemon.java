/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.DailyData;

/**
 *
 * @author salam
 */
public class DDataDaemon {

    private ArrayList<DailyData> quoteList;
    private String symbol;

    public DDataDaemon(Date fDate, String configStr) {
        quoteList = new ArrayList<>();
        String fDateStr = new SimpleDateFormat("yyyy-MM-dd").format(fDate);

        //String symbol = "P_"+scode;
        this.symbol = "D_" + fDateStr + ".zip";
        CMImporter cmi = new CMImporter(symbol);
        ArrayList<String> lines = cmi.getLines();
        //  System.out.println("............ .... ..");
        String qdate = new SimpleDateFormat("yyyy-MM-dd").format(fDate);
        for (String line : lines) {
            System.out.println(line);
            String[] toke = line.split(",");

            String symbol = toke[0];
            String open = toke[1];
            String high = toke[2];
            String low = toke[3];
            String close = toke[4];
            String volume = toke[5];
            String change = toke[6];
            String pchange = toke[7];
            String mcap = toke[8];
            String hldiff = toke[9];
            String sector = "";
            String category = "";
            if (toke[10] != null) {
                sector = toke[10];
            }

            if (toke[11] != null) {
                category = toke[11];
            }

            DailyData dd = new DailyData();

            dd.setSymbol(symbol);
            dd.setOpen(Double.parseDouble(open));
            dd.setHigh(Double.parseDouble(high));
            dd.setLow(Double.parseDouble(low));
            dd.setClose(Double.parseDouble(close));
            dd.setVolume(Double.parseDouble(volume));
            dd.setChange(Double.parseDouble(change));
            dd.setPchange(Double.parseDouble(pchange));
            dd.setMcap(Double.parseDouble(mcap));
            dd.setHlDiff(Double.parseDouble(hldiff));
            dd.setQuoteDate(qdate);
            dd.setSector(sector);
            dd.setCategory(category);

            quoteList.add(dd);

        }

    }

    public ArrayList<DailyData> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(ArrayList<DailyData> quoteList) {
        this.quoteList = quoteList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

}
