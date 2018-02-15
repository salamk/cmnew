/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.util.ArrayList;
import net.coolmarch.cmnew.common.DailyData;
import net.coolmarch.daemon.DataFetchingDaemon;

/**
 *
 * @author salam
 */
public class CurrentDataDaemon {

    private ArrayList<DailyData> quoteList;

    public CurrentDataDaemon() {
        quoteList = new ArrayList(DataFetchingDaemon.imap.values());
    }

    public ArrayList<DailyData> getQuoteList() {
        return quoteList;
    }

    public void setQuoteList(ArrayList<DailyData> quoteList) {
        this.quoteList = quoteList;
    }


}
