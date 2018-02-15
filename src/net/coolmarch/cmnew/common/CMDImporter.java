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
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author salam
 */
public final class CMDImporter {

    private ArrayList<String> lines = new ArrayList();

    public CMDImporter(String symbol) {
        try{
            long t = new Date().getTime();
            URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER+"/dcscr.php?symbol="+symbol+"&st="+t);
            System.out.println(url);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while((line = br.readLine())!=null){
                line = line.replaceAll("<br />", "");
                lines.add(line);
            }
        
        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<String> getLines() {
        return lines;
    }


}
