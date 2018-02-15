/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import net.coolmarch.cmnew.common.CommonTasks;

/**
 *
 * @author salam
 */
public class CMUConfig {

    //configString = a + b + c;
    //where a = user, b = pwd, c = checkStr
    //on server side the script will parse the string and stripp off these
    //three values to check into the database for validity
    private static boolean isSet = false;
    private static final String csig = "dseqHKLewq432jILSania90432ParasiowKjl";
    private static final String clientId = "ytkH43ewFBcTexQazmn98HCDswel07l1";
    private static final String login_script_code = "AUT";

    public CMUConfig(String userId,String password, String callerId) {

        if (callerId.compareTo("lkiutSania543iosaParasiTr894UEZt") != 0) {
            JOptionPane.showMessageDialog(null, "Engine encountered panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

        } else if (csig.compareTo(
                "dseqHKLewq432jILSania90432ParasiowKjl") != 0) {
                        JOptionPane.showMessageDialog(null, "-Engine encountered"
                                + " panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

            
        } else {
            
            String cid = createConfigString(userId, password);
            String config_str = login_script_code+cid;
            
            CommonTasks ct = new CommonTasks();
            String url = ct.getServerMainUrl()+config_str;
            
           // System.out.println(url);

            try {

                URL lc = new URL(url);
                BufferedReader rd = new BufferedReader(
                        new InputStreamReader(lc.openStream()));

                String result = "";
                String lineIdentifier = "ELN>";
                String line = "";
                while ((line = rd.readLine()) != null) {
                    if(line.startsWith(lineIdentifier)){
                        line = line.substring(4);
                        result = line;
                        
                    }
                }
                
                rd.close();

              //  System.out.println("The result is now: "+result);

                if (result.compareTo("0") == 0) {
                    isSet = true;
                    System.setProperty("SESSION_CONFIG_ID", cid);
                } else if (result.compareTo("1") == 0) {
                    isSet = false;
                    JOptionPane.showMessageDialog(null, "User ID / Password "
                            + "incorrect");
                    System.setProperty("SESSION_CONFIG_ID", "Incorrect Login");
                } else if (result.compareTo("2") == 0) {
                    isSet = false;
                    JOptionPane.showMessageDialog(null, "Account is expired\n"
                            + "Please renew your account");
                    System.setProperty("SESSION_CONFIG_ID", "Account Expired");
                } else if (result.compareTo("3") == 0) {
                    isSet = false;
                    JOptionPane.showMessageDialog(null, "Account suspended"
                            + "\nPlease contact system administrator");
                    System.setProperty("SESSION_CONFIG_ID", "Account Suspend");
                }

            } catch (IOException ioe) {
             //   System.out.println(ioe.getMessage());
            }
        }
    }
    
    public String createConfigString(String userId, String password){
        String configStr = "";
        CommonTasks ct = new CommonTasks();
        password = ct.getMd5(password);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        
        String sysDate = sdf.format(new Date());
        
        String s = userId+ct.getSeparator()+""
                + ""+password+ct.getSeparator()+sysDate;
        
        String b64s = ct.encodeBase64(s);
        configStr = ct.getBase64UrlSafe(b64s);
        
        return configStr;
    }
    
    public boolean isSet(){
        return isSet;
    }


}
