/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import net.coolmarch.cmnew.common.CMFile;

/**
 *
 * @author coolmarch
 */
public class PnlFundamentalTable extends javax.swing.JPanel {

    /**
     * Creates new form PnlFundamentalTable
     */
    
    private String symbol;
    public PnlFundamentalTable(String symbol) {
        initComponents();
        this.symbol = symbol;
        HashMap<String, String> fmap = new HashMap<>();
        try {
            InputStream in = getClass().getResourceAsStream("cdata.csv");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith(symbol)) {
                    String[] token = line.split(",");
                    String year = token[16];
                    fmap.put(year, line);
                }
            }

        } catch (Exception e) {

        }

        setData(fmap);
    }

    private void setData(HashMap<String, String> fmap) {
        setTableData("2013", fmap.get("2013"));
        setTableData("2014", fmap.get("2014"));
        setTableData("2015", fmap.get("2015"));
    }

    private void setCompanyReport() throws Exception {
        String text = "";
        CMFile cmf = new CMFile(symbol + "_a.txt");
        ArrayList<String> lines = cmf.getLines();
        for (String line : lines) {
            line = line.toLowerCase();
            text+=(line + "\n");
        }
        
        tpane.setText(text);
    }

    private void setTableData(String year, String line) {
        int col = 1;
        if (year.compareToIgnoreCase("2013") == 0) {
            col = 1;
        } else if (year.compareToIgnoreCase("2014") == 0) {
            col = 2;
        } else if (year.compareToIgnoreCase("2015") == 0) {
            col = 3;
        }

        try {
            String[] toke = line.split(",");
            for (int i = 0; i <= toke.length - 3; i++) {
                tbl.setValueAt(toke[i + 1], i, col);
            }
        } catch (Exception e) {

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tpaneScroller = new javax.swing.JScrollPane();
        tpane = new javax.swing.JTextPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();
        cHyperLink1 = new acr.component.CHyperLink();

        tpaneScroller.setViewportView(tpane);

        setLayout(new java.awt.BorderLayout());

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Year-End", null, null, null},
                {"Paidup Capital", null, null, null},
                {"Face Value", null, null, null},
                {"# of Shares", null, null, null},
                {"Equity", null, null, null},
                {"Asset", null, null, null},
                {"Income", null, null, null},
                {"Expenses", null, null, null},
                {"PL Before Tax", null, null, null},
                {"Taxation", null, null, null},
                {"PL After Tax", null, null, null},
                {"Cash Dividend", null, null, null},
                {"Stock Dividend", null, null, null},
                {"Total Dividend", null, null, null},
                {"# Shareholder", null, null, null}
            },
            new String [] {
                "Parameter", "2013", "2014", "2015"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);

        cHyperLink1.setText("Click here to view Announcement History");
        cHyperLink1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink1MouseClicked(evt);
            }
        });
        add(cHyperLink1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void cHyperLink1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink1MouseClicked
        // TODO add your handling code here:
        showCompanyReport();
    }//GEN-LAST:event_cHyperLink1MouseClicked

    private void showCompanyReport(){
        try{
            setCompanyReport();
            //JOptionPane.showMessageDialog(null, tpaneScroller);
            JDialog dlg = new JDialog();
            dlg.getContentPane().add(tpaneScroller);
            dlg.setSize(640,400);
            dlg.setLocationRelativeTo(null);
            dlg.setVisible(true);
        }catch(Exception e){
        
        }
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private acr.component.CHyperLink cHyperLink1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextPane tpane;
    private javax.swing.JScrollPane tpaneScroller;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        PnlFundamentalTable pft = new PnlFundamentalTable("PTC");
        JOptionPane.showMessageDialog(null, pft);
    }

}
