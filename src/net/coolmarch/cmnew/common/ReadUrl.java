/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

/**
 *
 * @author coolmarch
 */
public class ReadUrl {

    private ArrayList<String> lines = new ArrayList<>();

    public ReadUrl(String surl) {
        try {

            URL url = new URL(surl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.length() == 0) {
                } else {
                    line = line.replaceAll("<br />", "");
                    lines.add(line);
                    System.out.println(line);
                }
            }

            br.close();

        } catch (Exception e) {

        }
    }

    public ArrayList<String> getLines() {
        return lines;
    }

}
