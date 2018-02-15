/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmnew;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.Enumeration;
import javax.swing.JFrame;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;
import net.coolmarch.cmlsys.Splash;

/**
 *
 * @author salam
 */
public final class Cmnew {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
//        MetalLookAndFeel.setCurrentTheme(new DefaultMetalTheme());
        Enumeration keys = UIManager.getDefaults().keys();
//        try{
//            UIManager.setLookAndFeel(new DefaultMetalTheme());
//        }catch(Exception e){
//        
//        }
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) {
                FontUIResource orig = (FontUIResource) value;
                Font font = new Font("Arial", 0, 11);
                UIManager.put(key, new FontUIResource(font));
            }

        }

        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        if (defaults.get("Table.alternateRowColor") == null) {
            defaults.put("Table.alternateRowColor", new Color(253, 249, 255));
        }

        JFrame frame = new JFrame();
        Splash splash = new Splash(frame);
        frame.setLayout(new BorderLayout());
        
        frame.getContentPane().add(splash);
        frame.setSize(675,370+50);
        frame.setResizable(false);
        
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
