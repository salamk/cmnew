/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.daemon;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.DailyData;

/**
 *
 * @author coolmarch
 */
public class DataFetchingDaemon {

    public static final ConcurrentHashMap<String, Double> vmMap
            = new ConcurrentHashMap<>();
    public static final ConcurrentHashMap<String, String> emap
            = new ConcurrentHashMap<>();

    public static final ConcurrentHashMap<String, DailyData> imap
            = new ConcurrentHashMap<>();

    private static String refreshTime = "";

    public DataFetchingDaemon() {

    }

    public void getPulse() {
        vmMap.clear();
        emap.clear();
        imap.clear();

        CMImporter cmi = new CMImporter("rc.zip");
        ArrayList<String> lines = cmi.getLines();
        for (String line : lines) {
            if (line.startsWith("@")) {
                line = line.replaceFirst("@", "");
                if (line.contains("_")) {
                    String[] toke = line.split(",");
                    String symbol = toke[0];
                    String sclose = toke[1];

                    emap.put(symbol, sclose);
                } else {
                    String[] toke = line.split(",");
                    String symbol = toke[0];
                    String sclose = toke[1];

                    vmMap.put(symbol, Double.parseDouble(sclose));
                }

            } else if (line.startsWith("#")) {
                line = line.replaceAll("#", "");
                String[] toke = line.split(",");
                String code = toke[0];
                String op = toke[1];
                String hi = toke[2];
                String lo = toke[3];
                String cl = toke[4];
                String vo = toke[5];
                String ch = toke[6];
                String pch = toke[7];
                String mc = toke[8];
                String hld = toke[9];
                String sec = toke[10];
                String cat = toke[11];

                Double open = Double.parseDouble(op);
                Double high = Double.parseDouble(hi);
                Double low = Double.parseDouble(lo);
                Double close = Double.parseDouble(cl);
                Double volume = Double.parseDouble(vo);
                Double change = Double.parseDouble(ch);
                Double pchange = Double.parseDouble(pch);
                Double mcap = Double.parseDouble(mc);
                Double hldiff = Double.parseDouble(hld);

                DailyData dd = new DailyData();
                dd.setCategory(cat);
                dd.setChange(change);
                dd.setClose(close);
                dd.setHigh(high);
                dd.setHlDiff(hldiff);
                dd.setLow(low);
                dd.setMcap(mcap);
                dd.setOpen(open);
                dd.setPchange(pchange);
                dd.setSector(sec);
                dd.setSymbol(code);
                dd.setVolume(volume);

                imap.put(code, dd);
            } else if (line.startsWith("_")) {
                line = line.replaceAll("_", "");
                refreshTime = line;
            }
        }

        System.out.println("The VMAP HAS SIZE: " + vmMap.size());
        System.out.println("The EMAP HAS SIZE: " + emap.size());
        System.out.println("The IMAP HAS SIZE: " + imap.size());

    }
    
    public static String getRefreshTime(){
        return refreshTime;
    }

}
