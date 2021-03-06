/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.fiplip;

import java.util.TreeMap;

/**
 *
 * @author sania
 */
public class PnlSectorByClient extends javax.swing.JPanel {

    /**
     * Creates new form PnlSectorByClient
     */
    public PnlSectorByClient(TreeMap<String,Double> map, String title) {
        initComponents();
        tfTitle.setText("      "+title);
        GMapPanel gbc = new GMapPanel(map);
        pnl.removeAll();
        pnl.add(gbc);
        pnl.update(pnl.getGraphics());
        pnl.validate();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl = new javax.swing.JPanel();
        tfTitle = new javax.swing.JTextField();

        setLayout(new java.awt.BorderLayout());

        pnl.setLayout(new java.awt.BorderLayout());
        add(pnl, java.awt.BorderLayout.CENTER);

        tfTitle.setEditable(false);
        tfTitle.setBorder(null);
        tfTitle.setDisabledTextColor(new java.awt.Color(0, 102, 102));
        tfTitle.setEnabled(false);
        add(tfTitle, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel pnl;
    private javax.swing.JTextField tfTitle;
    // End of variables declaration//GEN-END:variables
}
