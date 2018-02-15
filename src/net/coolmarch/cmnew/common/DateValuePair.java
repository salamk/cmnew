/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author salam
 */
public class DateValuePair {
    private Date date;
    private double value;

    public DateValuePair(Date date, double value) {
        this.date = date;
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    public String toString(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String dt = sdf.format(date);
        String str = dt+" , "+value;
        return str;
    }
    
}
