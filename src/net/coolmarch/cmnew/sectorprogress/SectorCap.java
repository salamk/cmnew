/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

/**
 *
 * @author salam
 */
public class SectorCap {

    private String sector;
    private String category;
    private double cap;
    private String sectorProgress;
    private double closePrice;
    private double change;

    public SectorCap(){
    
    }
    
    public SectorCap(String sector, double cap) {
        this.sector = sector;
        this.cap = cap;
    }

    public SectorCap(String sector, String category, double cap) {
        this.sector = sector;
        this.cap = cap;
        this.category = category;
    }
    
    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public double getChange() {
        return change;
    }

    public void setChange(double change) {
        this.change = change;
    }

    public String getSectorProgress() {
        return sectorProgress;
    }

    public void setSectorProgress(String sectorProgress) {
        this.sectorProgress = sectorProgress;
    }

    public SectorCap(String sector, String category, String stats, double cap) {
        this.sector = sector;
        this.cap = cap;
        this.category = category;
        this.sectorProgress = stats;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

}
