/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import net.coolmarch.cmnew.login.CMCLogging;

/**
 *
 * @author salam
 */
public class RecentQuote {

    public RecentQuote() {

    }

    public ArrayList<String> getRecentData() {
        ArrayList<String> al = new ArrayList<>();
        CommonTasks ct = new CommonTasks();
        String serverMain = ct.getServerMainUrl();
        if (CMCLogging.is_set()) {
            String configString = CMCLogging.getConfigStr();
            String scriptCode = "GRD";
            configString = scriptCode + configString;
            String url = serverMain + configString;
          //  System.out.println(url);

            try {

                URL lc = new URL(url);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(lc.openStream()));

                String lineIdentifier = "ELN>";
                String line = "";
                while ((line = rd.readLine()) != null) {
                    if (line.startsWith(lineIdentifier)) {
                        line = line.substring(4);
                        line = line.replaceAll("<br />", "");
                        al.add(line);
                    }
                }
                
                rd.close();
            }catch(IOException ioe){
              //  System.out.println(ioe.getMessage());
            }

        }
        
        return al;
    }

}
