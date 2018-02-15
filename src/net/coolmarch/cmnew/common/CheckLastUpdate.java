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

/**
 *
 * @author salam
 */
public final class CheckLastUpdate {


    public CheckLastUpdate() {
        try {
            String url = CommonTasks.getLast_update_url();
            URL lc = new URL(url);
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(lc.openStream()));

            String result = "";
            String lineIdentifier = "CMU>";
            String line = "";
            while ((line = rd.readLine()) != null) {
                if (line.startsWith(lineIdentifier)) {
                    line = line.substring(4);
                    System.setProperty("LAST_UPDATE_TIME", line);
                }
            }
            
            rd.close();

        }catch(IOException e){
         //   System.out.println("Exception "+e.getMessage());
        }
    }

}
