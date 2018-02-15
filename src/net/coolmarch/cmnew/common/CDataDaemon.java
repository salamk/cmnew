/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import cmnew.CoolmarchConstants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author salam
 */
public class CDataDaemon {

    private ArrayList<DailyData> quoteList;
    private String symbol;

    public CDataDaemon() {
        quoteList = new ArrayList <>();
//        CMImporter cmi = new CMImporter("current.zip");
//        ArrayList<String> lines = cmi.getLines();

        ArrayList<String> lines = new ArrayList();
        try{
            Date d = new Date();
            long t = d.getTime();
            URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER+"/abscripts/getrecentquote.php?ttime="+t);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while((line = br.readLine())!= null){
                line = line.replaceAll("<br />", "");
                lines.add(line);
            }
            br.close();
        
        }catch(Exception e){
        
        }
        
        for (String line : lines) {
            //System.out.println(line);

            String[] toke = line.split(",");
            String s = toke[0];
            String open = toke[1];
            String high = toke[2];
            String low = toke[3];
            String close = toke[4];
            String change = toke[5];
            String volume = toke[6];
           // String pchange = toke[8];
           // String mcap = toke[9];

            DailyData dd = new DailyData();
            double op = Double.parseDouble(open);
            double hi = Double.parseDouble(high);
            double lo = Double.parseDouble(low);
            double cl = Double.parseDouble(close);
            double ch = Double.parseDouble(change);
            double vl = Double.parseDouble(volume);
            
            dd.setSymbol(s);
            dd.setOpen(op);
            dd.setHigh(hi);
            dd.setLow(lo);
            dd.setClose(cl);
            dd.setVolume(vl);
            dd.setChange(ch);
            double pchange = (ch/cl)*100;
            double mcap = vl*cl;
            dd.setPchange(pchange);
            dd.setMcap(mcap);
            dd.setQuoteDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));

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
