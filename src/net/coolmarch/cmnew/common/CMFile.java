/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import cmnew.CoolmarchConstants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author coolmarch
 */
public final class CMFile {

    ArrayList<String> lines = new ArrayList<>();
    private final String cid = "zeSnOLFlgZo2Mw5ylb0tpOVN5GtLFn";

    public CMFile(String file_name) {
        checkAttr();
        String surl = CoolmarchConstants.COOLMARCH_SERVER+"/abscripts/"
                + "getfile.php?fname=" + file_name + ""
                + "&puck=" + System.getProperty("puck");

        try {
            
            URL url = new URL(surl);
            BufferedReader br = 
                    new BufferedReader(
                            new InputStreamReader(url.openStream()));
            
            String line = "";
            while((line = br.readLine())!= null){
                lines.add(line);
            }
            br.close();
        } catch (MalformedURLException e) {
            System.out.println(e.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    
    public ArrayList<String> getLines(){
        return lines;
    }
    
        private void checkAttr() {
        CommonIdentification ci = new CommonIdentification();
        boolean a = ci.authenticateCode(this.getClass().getSimpleName(), cid);
        if (!a) {
            JOptionPane.showMessageDialog(null, "System failure ||| PANIC Signal");
        }
    }


}
