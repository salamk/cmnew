/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.daemon;

import cmnew.CoolmarchConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author salam
 */
public class NewsTickerDaemon implements Runnable {

    public static final ArrayList<String> tickerList = new ArrayList<>();
    private static final long pulse_delay = 500000;

    public NewsTickerDaemon() {
    }

    public void run() {
        while (true) {
            tickerList.clear();
            try{
                
                long t = new Date().getTime();
                URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER+"/abscripts/getnewsticker.php?t="+t);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String line = "";
                while((line = br.readLine())!=null){
                    line = line.replaceAll("<br />", "");
                    tickerList.add(line);
                }
                
                br.close();
                
            }catch(IOException ioe){
                
            }
            try {
                Thread.sleep(pulse_delay);
            } catch (InterruptedException e) {

            }
        }

    }

}
