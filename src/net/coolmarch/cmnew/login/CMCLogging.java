/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.login;

import javax.swing.JOptionPane;

/**
 *
 * @author salam
 */
public class CMCLogging {

    private static boolean is_set = false;
    private static String config_str = "-1";
    private static final String csig = "dsdew424$23ParasSANIaewLPdfsae";

    public CMCLogging() {
    }

    public void setLogging(boolean flag, String callerId, String recieverId) {
        if (callerId.compareTo("lkiutSania543iosaParasiTr894UEZt") != 0) {

            JOptionPane.showMessageDialog(null, "Engine encountered panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

        } else if (recieverId.compareTo(csig) != 0) {

            JOptionPane.showMessageDialog(null, "Engine encountered panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

        } else {

            CMCLogging.is_set = flag;
        }
    }

    public void setConfig(String cStr, String callerId, String recieverId) {
        if (callerId.compareTo("lkiutSania543iosaParasiTr894UEZt") != 0) {

            JOptionPane.showMessageDialog(null, "Engine encountered panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

        } else if (recieverId.compareTo(csig) != 0) {

            JOptionPane.showMessageDialog(null, "Engine encountered panic signal"
                    + "\nCoolmarch:Genuine component is missing"
                    + "\nSystem Response Failure - Fatal Error");

        } else {

            CMCLogging.config_str = cStr;
        }
    }

    public static boolean is_set() {
        return is_set;
    }
    
    public static String getConfigStr(){
        return config_str;
    }
    
}
