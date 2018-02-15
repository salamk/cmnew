/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.importops;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;
import net.coolmarch.cmnew.common.CDataDaemon;
import net.coolmarch.cmnew.common.CMDownloader;
import net.coolmarch.cmnew.common.DailyData;

/**
 * This class is attached with PnlBuildCache from where it get gui components
 * tfStatus, taStatus and btnStart. This gui is handled in this code.
 *
 * @author salam
 */
public class CMImportDaemon implements Runnable {

    private JTextArea taStatus;
        static int counter = 0;
        
    public CMImportDaemon(JTextArea taStatus) {
        this.taStatus = taStatus;
        counter = 0;
        DefaultCaret caret = (DefaultCaret) taStatus.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

    }

    public void run() {
        taStatus.setText(new java.util.Date().toString());
        taStatus.append("\nCollecting information, Please wait....");
        CDataDaemon cdd = new CDataDaemon();
        ArrayList<DailyData> quoteList = cdd.getQuoteList();
        Collections.sort(quoteList, new Comparator<DailyData>() {
            @Override
            public int compare(DailyData c1, DailyData c2) {
                return Double.compare(c1.getMcap(), c2.getMcap());
            }
        });
        
        Collections.reverse(quoteList);
        
        

        taStatus.append("\nTOC Created sucessfull : "
                + "Content size is: "+quoteList.size());
        
        CMDownloader cmd = new CMDownloader();
        for(int i=0; i<=100-1; i++){
            DailyData dd = quoteList.get(i);
            String symbol = dd.getSymbol();
            
           // taStatus.append("\n: File Name: "+symbol);
            
            String mf = symbol+".zip";
            String pf = "P_"+symbol+".zip";
            
            boolean ok1 = cmd.downloadFile(mf);
            boolean ok2 = cmd.downloadFile(pf);
            
            if(ok1 && ok2){
                counter++;
                taStatus.append("\nFile ("+counter+" of 100) imported . Download OK/-");
            }
        }
        
        taStatus.append("\nFinished...");
        taStatus.append("\nPlease close the window now");

    }

}
