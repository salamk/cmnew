/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.sectorprogress;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import net.coolmarch.cmnew.common.DailyData;
import net.coolmarch.cmnew.login.CMCLogging;

/**
 *
 * @author sania
 */
public class PnlSectorPerformance extends javax.swing.JPanel {

    public static final int COMPACT = 0;
    public static final int DETAILED = 1;

    /**
     * Creates new form PnlSectorPerformance
     */
    public PnlSectorPerformance(Date d, int type) {
        initComponents();
        if (type == DETAILED) {
            
            DDataDaemon ddd = new DDataDaemon(d, CMCLogging.getConfigStr());
            ArrayList<DailyData> ddl = ddd.getQuoteList();

            final MarketProgress mp = new MarketProgress(ddl);
            ArrayList<SectorCap> sclist = mp.getSectorCap();
            Collections.sort(sclist, new ScComparator());

            GSectorPercentCap gpc = new GSectorPercentCap(sclist);
            pnlChart.removeAll();
            pnlChart.add(gpc);
            pnlChart.update(pnlChart.getGraphics());
            pnlChart.validate();
            DecimalFormat df = new DecimalFormat("#.##");
            cmb.removeAllItems();
            for (SectorCap sc : sclist) {
                String sec = sc.getSector();
                double cap = sc.getCap();
                String str = sec + "-" + df.format(cap);
                cmb.addItem(str);
            }

            cmb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = (String) cmb.getSelectedItem();
                    String[] toke = str.split("-");
                    String sector = toke[0];
                    ArrayList<SectorCap> list = mp.getCompaniesPerformance(sector);
                    Collections.sort(list, new ScComparator());
                    DefaultTableModel model = (DefaultTableModel) tbl1.getModel();
                    model.setRowCount(0);
                    DecimalFormat df = new DecimalFormat("#.##");

                    Iterator i = list.iterator();
                    while (i.hasNext()) {
                        SectorCap sc = (SectorCap) i.next();
                        String sec = sc.getSector();
                        double cap = sc.getCap();
                        Vector v = new Vector();
                        v.add(sec);
                        v.add(df.format(cap));
                        model.addRow(v);
                    }

                    pnlChart.update(pnlChart.getGraphics());
                    pnlChart.validate();

                }
            });

        } else if (type == COMPACT) {
            CurrentDataDaemon ddd = new CurrentDataDaemon();
            ArrayList<DailyData> ddl = ddd.getQuoteList();

            final MarketProgress mp = new MarketProgress(ddl);
            ArrayList<SectorCap> sclist = mp.getSectorCap();
            Collections.sort(sclist, new ScComparator());

            GSectorPercentCap gpc = new GSectorPercentCap(sclist);
            pnlChart.removeAll();
            pnlChart.add(gpc);
            pnlChart.update(pnlChart.getGraphics());
            pnlChart.validate();
            DecimalFormat df = new DecimalFormat("#.##");
            cmb.removeAllItems();
            for (SectorCap sc : sclist) {
                String sec = sc.getSector();
                double cap = sc.getCap();
                String str = sec + "-" + df.format(cap);
                cmb.addItem(str);
            }

            cmb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    String str = (String) cmb.getSelectedItem();
                    String[] toke = str.split("-");
                    String sector = toke[0];
                    ArrayList<SectorCap> list = mp.getCompaniesPerformance(sector);
                    Collections.sort(list, new ScComparator());
                    DefaultTableModel model = (DefaultTableModel) tbl1.getModel();
                    model.setRowCount(0);
                    DecimalFormat df = new DecimalFormat("#.##");

                    Iterator i = list.iterator();
                    while (i.hasNext()) {
                        SectorCap sc = (SectorCap) i.next();
                        String sec = sc.getSector();
                        double cap = sc.getCap();
                        Vector v = new Vector();
                        v.add(sec);
                        v.add(df.format(cap));
                        model.addRow(v);
                    }

                    pnlChart.update(pnlChart.getGraphics());
                    pnlChart.validate();

                }
            });

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

        jScrollPane2 = new javax.swing.JScrollPane();
        tbl1 = new javax.swing.JTable();
        pnlChart = new javax.swing.JPanel();
        cmb = new javax.swing.JComboBox<>();

        jScrollPane2.setBorder(null);

        tbl1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Company", "%-Cap"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tbl1);
        if (tbl1.getColumnModel().getColumnCount() > 0) {
            tbl1.getColumnModel().getColumn(0).setResizable(false);
            tbl1.getColumnModel().getColumn(0).setPreferredWidth(70);
            tbl1.getColumnModel().getColumn(1).setResizable(false);
        }

        pnlChart.setLayout(new java.awt.BorderLayout());

        cmb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmb, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cmbActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmb;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JTable tbl1;
    // End of variables declaration//GEN-END:variables
}

class ScComparator implements Comparator<SectorCap> {

    public int compare(SectorCap s1, SectorCap s2) {
        return Double.compare(s2.getCap(), s1.getCap());
    }
}
