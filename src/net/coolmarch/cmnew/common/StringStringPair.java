/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

/**
 *
 * @author salam
 */
public class StringStringPair {
    private String string1;
    private String string2;
    
    public StringStringPair(String string1, String string2){
        this.string1 = string1;
        this.string2 = string2;
    }

    public StringStringPair() {
    }

    public String getString1() {
        return string1;
    }

    public void setString1(String string1) {
        this.string1 = string1;
    }

    public String getString2() {
        return string2;
    }

    public void setString2(String string2) {
        this.string2 = string2;
    }
    
    
}
