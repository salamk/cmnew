/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.worldmarkets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

/**
 *
 * @author sania
 */
public class WorldIndiceOps {

    public WorldIndiceOps() {
    }

    public void getKseQuotes() {
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -1); // from 1 year ago
        try{
        Stock google = YahooFinance.get("^KSE");
        List<HistoricalQuote> googleHistQuotes = google.getHistory(from, to, Interval.DAILY);
        for(HistoricalQuote hq : googleHistQuotes){
            String dt = new SimpleDateFormat("yyyy-MM-dd").format(hq.getDate());
            
            System.out.println(dt+","+hq.getOpen()+","+hq.getHigh()+","+hq.getLow()+","+hq.getClose()+","+hq.getVolume());
        }
        }catch(Exception e){
        
        }
// googleHistQuotes is the same as google.getHistory() at this point
// provide some parameters to the getHistory method to send a new request to Yahoo Finance
    }

    public ArrayList<WorldIndice> getWorldIndice(String[] indiceList) {
        //System.out.println("Entering Major World Market Indice Data ....");
        ArrayList<WorldIndice> al = new ArrayList<WorldIndice>();
        //http://download.finance.yahoo.com/d/quotes.csv?s=^DJI,^IXIC,^GSPC,^TNX&f=snl1d1t1c1ohg
        String indice_prefix = "";
        for (int i = 0; i <= indiceList.length - 1; i++) {
            indice_prefix += indiceList[i] + ",";
        }

        int len = indice_prefix.length();
        indice_prefix = indice_prefix.substring(0, len - 1);

        String url = "http://download.finance.yahoo.com/d/"
                + "quotes.csv?s=" + indice_prefix + "&f=snl1c1ohg";
        //System.out.println("Connecting to finance.yahoo.com....");
        try {
            InputStream input = new URL(url).openStream();
            // StringBuffer buff = new StringBuffer();
            String str = "";
            BufferedReader br = new BufferedReader(new InputStreamReader(input));
            if (input != null) {
                //System.out.println("Website found / Waiting for reply..");
                while ((str = br.readLine()) != null) {
                    //System.out.println(str);
                    String[] toke = str.split(",");
                    String exchangeSymbol = toke[0];
                    String exchangeName = toke[1];
                    String exchangeLastTrade = toke[2];
                    String exchangeIndexChange = toke[3];
                    String exchangeIndexOpen = toke[4];
                    String exchangeDayHigh = toke[5];
                    String exchangeDayLow = toke[6];

                    WorldIndice wi = new WorldIndice();
                    wi.setExchangeDayHigh(exchangeDayHigh);
                    wi.setExchangeDayLow(exchangeDayLow);
                    //wi.setExchangeGainLoss(exc);
                    wi.setExchangeIndexChange(exchangeIndexChange);
                    wi.setExchangeLastTrade(exchangeLastTrade);
                    wi.setExchangeName(exchangeName);
                    wi.setExchangeIndexOpen(exchangeIndexOpen);

                    al.add(wi);
                }

                //System.out.println("HTTP Status / OK -");
            }
        } catch (IOException ioe) {
            //System.out.println("IO Error encountered..");
            //System.out.println(ioe.getMessage());
        }
        ////System.out.println(url);
        return al;
    }

    public static void main(String[] args) {
        WorldIndiceOps ops = new WorldIndiceOps();
        ops.getKseQuotes();
//        String[] indice_list = {"^DJI", "^IXIC", "^GSPC", "^HSI", "^JKSE"};
//        WorldIndiceOps ops = new WorldIndiceOps();
//        ArrayList<WorldIndice> al = ops.getWorldIndice(indice_list);
//        Iterator i = al.iterator();
//        while(i.hasNext()){
//            WorldIndice wi = (WorldIndice)i.next();
//            //System.out.println(wi.toString());
//        }
    }

}
