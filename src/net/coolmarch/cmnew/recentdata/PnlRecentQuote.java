/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.recentdata;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import net.coolmarch.cmnew.common.CDataDaemon;
import net.coolmarch.cmnew.common.DailyData;
import net.coolmarch.daemon.DataFetchingDaemon;

/**
 *
 * @author salam
 */
public class PnlRecentQuote extends javax.swing.JPanel {

    /**
     * Creates new form PnlRecentQuote
     */
    private JTable table;
    private ArrayList<DailyData> ddl;

    public PnlRecentQuote() {
        initComponents();
        table = new JTable();
        JScrollPane scroller = new JScrollPane(table);
        pnl.add(scroller);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        cHyperLink1 = new acr.component.CHyperLink();
        jSeparator1 = new javax.swing.JToolBar.Separator();
        cHyperLink2 = new acr.component.CHyperLink();
        jSeparator2 = new javax.swing.JToolBar.Separator();
        cHyperLink3 = new acr.component.CHyperLink();
        pnl = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        cHyperLink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/arrow_refresh16.png"))); // NOI18N
        cHyperLink1.setText("Refresh");
        cHyperLink1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink1MouseClicked(evt);
            }
        });
        jToolBar1.add(cHyperLink1);
        jToolBar1.add(jSeparator1);

        cHyperLink2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/filter16.png"))); // NOI18N
        cHyperLink2.setText("Filter:Cap>x");
        cHyperLink2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink2MouseClicked(evt);
            }
        });
        jToolBar1.add(cHyperLink2);
        jToolBar1.add(jSeparator2);

        cHyperLink3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/filter16.png"))); // NOI18N
        cHyperLink3.setText("Filter:Volume>x");
        cHyperLink3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink3MouseClicked(evt);
            }
        });
        jToolBar1.add(cHyperLink3);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        pnl.setLayout(new java.awt.BorderLayout());

        jLabel1.setBackground(new java.awt.Color(204, 204, 255));
        jLabel1.setText("In addition to the use of filters provided you can also sort data by double click on column header. To start press refresh button. [Data is sync in aproximately 5-10 Mins delay]");
        jLabel1.setOpaque(true);
        pnl.add(jLabel1, java.awt.BorderLayout.PAGE_START);

        add(pnl, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cHyperLink1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink1MouseClicked
        // TODO add your handling code here:
        //CDataDaemon cdd = new CDataDaemon();
        ddl = new ArrayList<DailyData>(DataFetchingDaemon.imap.values());
        table.setModel(new DailyDataTableModel(ddl));
        table.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_cHyperLink1MouseClicked

    private void cHyperLink2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink2MouseClicked
        // TODO add your handling code here:
        String answer = (String) JOptionPane.showInputDialog(null,
                (String) "Show only symbols having "
                + "\nMarket Cap Greater Then: ", "Cap > Then: ?",
                JOptionPane.OK_CANCEL_OPTION, null, null, "100000000");

        double cap = Double.parseDouble(answer);
        showCap(cap);


    }//GEN-LAST:event_cHyperLink2MouseClicked

    private void cHyperLink3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink3MouseClicked
        // TODO add your handling code here:
        String answer = (String) JOptionPane.showInputDialog(null,
                (String) "Show only symbols having "
                + "\nVolume Greater Then: ", "Volume > Then: ?",
                JOptionPane.OK_CANCEL_OPTION, null, null, "100000");
        double v = Double.parseDouble(answer);
        showVol(v);

    }//GEN-LAST:event_cHyperLink3MouseClicked

    private void showVol(double v) {
        ArrayList<DailyData> qlist = new ArrayList<DailyData>();
        Iterator i = ddl.iterator();
        while (i.hasNext()) {
            DailyData dd = (DailyData) i.next();
            double volume = (dd.getVolume());
            if (volume > v) {
                qlist.add(dd);
            }
        }

        TableModel tableModel = new DailyDataTableModel(qlist);
        this.table.setModel(tableModel);
    }

    public void setNewModel(ArrayList<DailyData> dailyDataList) {
        CDataDaemon cdd = new CDataDaemon();
        ddl = dailyDataList;
        table.setModel(new DailyDataTableModel(ddl));
        table.setAutoCreateRowSorter(true);
    }

    private void showCap(double cap) {
        ArrayList<DailyData> qlist = new ArrayList<DailyData>();
        Iterator i = ddl.iterator();
        while (i.hasNext()) {
            DailyData dd = (DailyData) i.next();
            double c = (dd.getMcap());
            if (c > cap) {
                qlist.add(dd);
            }
        }

        TableModel tableModel = new DailyDataTableModel(qlist);
        this.table.setModel(tableModel);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private acr.component.CHyperLink cHyperLink1;
    private acr.component.CHyperLink cHyperLink2;
    private acr.component.CHyperLink cHyperLink3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JToolBar.Separator jSeparator1;
    private javax.swing.JToolBar.Separator jSeparator2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnl;
    // End of variables declaration//GEN-END:variables
}
