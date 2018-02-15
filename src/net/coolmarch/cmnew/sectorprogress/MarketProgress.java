/* cdcx    b  
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.coolmarch.cmnew.common.DailyData;

/**
 *
 * @author salam
 */
public class MarketProgress {

    private ArrayList<DailyData> dlist;

    public MarketProgress(ArrayList<DailyData> dlist) {
        this.dlist = dlist;
    }

    private Set getSectorSet() {
        Set s = new HashSet();
        for (DailyData dd : dlist) {
            s.add((String) dd.getSector());
        }
        return s;
    }

    public ArrayList<SectorCap> getSectorCap() {
        ArrayList<SectorCap> list = new ArrayList<>();
        Set<String> s = getSectorSet();
        double sum = getSum();
        for (String sector : s) {
            double cap = getSectorCapitalization(sector);
            double pc = (cap / sum) * 100;
            String cat = getCategory(sector);
            System.out.println("The category is " + cat);
            String stats = this.getSectorStatus(sector);
            
            list.add(new SectorCap(sector, cat, stats, pc));
        }

        return list;
    }

    private String getCategory(String sector) {
        String cat = "";
        for (DailyData dd : dlist) {
            String sec = dd.getSector();
            if (sec.compareToIgnoreCase(sector) == 0) {
                cat = dd.getCategory();
                return cat;
            }
        }

        return cat;
    }

    public ArrayList<SectorCap> getCompaniesPerformance(String sector) {
        ArrayList<SectorCap> sc = new ArrayList<>();
        double sum = getSectorCapitalization(sector);
        for (DailyData dd : dlist) {
            String sec = dd.getSector();
            if (sec.compareToIgnoreCase(sector) == 0) {
                double cap = dd.getMcap();
                double pc = (cap / sum) * 100;
                sc.add(new SectorCap(dd.getSymbol(), pc));
            }
        }
        return sc;
    }

    private double getSectorCapitalization(String sector) {
        double cap = 0;
        for (DailyData dd : dlist) {
            String s = dd.getSector();
            if (s == null) {
            } else if (sector.compareToIgnoreCase(s) == 0) {
                cap += dd.getMcap();
            }
        }

        return cap;
    }
    
    
    private String getSectorStatus(String sector){
        double cap = 0;
        String stats = "";
        for (DailyData dd : dlist) {
            String s = dd.getSector();
            if (s == null) {
            } else if (sector.compareToIgnoreCase(s) == 0) {
                double c = dd.getMcap();
                double ch = dd.getChange();
                if(ch < 0){
                    c = -c;
                }
                
                cap += c;
            }
        }
        
        if(cap < 0){
            stats = "negative";
        }else if(cap == 0){
            stats = "neutral";
        }else{
            stats = "positive";
        }

        return stats;
    }

    private double getSum() {
        //Set<String> s = this.getSectorSet();
        double sum = 0;
        for (DailyData dd : dlist) {
            String sector = dd.getSector();
            if (sector == null) {
            } else {
                sum += dd.getMcap();
            }

        }

        return sum;
    }

}
