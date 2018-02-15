/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author sania
 */
public class ColorList {
    private ArrayList<Color> colorList;
    public ColorList(){
        colorList = new ArrayList<Color>();
        
        colorList.add(new Color(77,77,77));
        colorList.add(new Color(93,165,218));
        colorList.add(new Color(250,164,58));
        colorList.add(new Color(96,189,104));
        colorList.add(new Color(241,124,176));
        colorList.add(new Color(178,145,47));
        colorList.add(new Color(178,118,178));
        colorList.add(new Color(222,207,63));
        colorList.add(new Color(241,88,84));
        
    }
    
    public ArrayList<Color> getColorList(){
        return colorList;
    }
    
}
