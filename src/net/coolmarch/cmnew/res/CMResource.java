/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.res;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.coolmarch.cmnew.common.CommonIdentification;

/**
 *
 * @author salam
 */
public final class CMResource {

    public static final String resName = "klkkppo";
    private final String cid = "yUP1pxC3y0UJDsv4nkCrXHZCqz4fZ6";

    public CMResource(String resName) {

    }

    public CMResource() {
        checkAttr();
    }

    public ImageIcon getIcon(String iconName) {

        ImageIcon icon = new ImageIcon(getClass().getResource(iconName));

        if (icon == null) {
            icon = new ImageIcon(getClass().getResource("notfound.png"));
        }

        return icon;
    }

    private void checkAttr() {
        CommonIdentification ci = new CommonIdentification();
        boolean a = ci.authenticateCode(this.getClass().getSimpleName(), cid);
        if (!a) {
            JOptionPane.showMessageDialog(null, "System failure ||| PANIC Signal");
        }
    }

    public static void main(String[] args) {
        System.exit(0);
    }
}
