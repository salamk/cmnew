/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import ij.ImageJ;
import ij.Menus;
import java.awt.Menu;
import java.awt.MenuBar;

/**
 *
 * @author salam
 */
public final class CoolMarchImageJ {

    private ImageJ ij;

    public CoolMarchImageJ() {
        ij = new ImageJ();
        MenuBar mb = new MenuBar();
        Menu m = Menus.getSaveAsMenu();
        mb.add(m);
        ij.setMenuBar(mb);

    }


    public ImageJ getImageProcessor() {
        return ij;
    }
}
