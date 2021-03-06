/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tools;

import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import net.coolmarch.cmnew.common.CDataDaemon;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.DailyData;

/**
 *
 * @author coolmarch
 */
public class PnlSupportResistanceLevels extends javax.swing.JPanel {

    private JTable table;
    private JTable levelTable;
    private ArrayList<SRLevel> levelList;
    private ArrayList<LevelPresentation> lpList;
    private static String selectedEntity = "";

    /**
     * Creates new form PnlSupportResistanceLevels
     */
    public PnlSupportResistanceLevels() {
        initComponents();
        levelList = new ArrayList<>();
        lpList = new ArrayList<>();

        table = new JTable();
        JScrollPane scroller = new JScrollPane(table);
        pnlTable.add(scroller, BorderLayout.CENTER);

        levelTable = new JTable();
        JScrollPane lscroller = new JScrollPane(levelTable);
        pnlLevel.add(lscroller, BorderLayout.CENTER);
    }

    private ArrayList<SRLevel> createStandardSRLevel() {
        levelList.clear();
        CMImporter cmi = new CMImporter("pivotanalysis.zip");
        ArrayList<String> lines = cmi.getLines();
        for (String line : lines) {
            if (line.startsWith("#STPVPOINTD>")) {
                line = line.replaceAll("#STPVPOINTD>", "");
                String[] toke = line.split(",");
                String symbol = toke[0];
                String sp1 = toke[1];
                String sp2 = toke[2];
                String rs1 = toke[3];
                String rs2 = toke[4];

                double s1 = Double.parseDouble(sp1);
                double s2 = Double.parseDouble(sp2);
                double r1 = Double.parseDouble(rs1);
                double r2 = Double.parseDouble(rs2);

                SRLevel srl = new SRLevel(symbol, s1, s2, 0, r1, r2, 0);
                levelList.add(srl);
            }
        }

        return levelList;
    }
    
    private ArrayList<SRLevel> createFibonacciSRLevel() {
        levelList.clear();
        CMImporter cmi = new CMImporter("pivotanalysis.zip");
        ArrayList<String> lines = cmi.getLines();
        for (String line : lines) {
            if (line.startsWith("#FBPVPOINTD>")) {
                line = line.replaceAll("#FBPVPOINTD>", "");
                String[] toke = line.split(",");
                String symbol = toke[0];
                String sp1 = toke[1];
                String sp2 = toke[2];
                String sp3 = toke[3];
                String rs1 = toke[4];
                String rs2 = toke[5];
                String rs3 = toke[6];
                
                double s1 = Double.parseDouble(sp1);
                double s2 = Double.parseDouble(sp2);
                double s3 = Double.parseDouble(sp3);
                double r1 = Double.parseDouble(rs1);
                double r2 = Double.parseDouble(rs2);
                double r3 = Double.parseDouble(rs3);

                SRLevel srl = new SRLevel(symbol, s1, s2, s3, r1, r2, r3);
                levelList.add(srl);
            }
        }

        return levelList;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        pnlTable = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        pnlLevel = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jToolBar2 = new javax.swing.JToolBar();
        cHyperLink1 = new acr.component.CHyperLink();
        cHyperLink2 = new acr.component.CHyperLink();
        cHyperLink3 = new acr.component.CHyperLink();
        cHyperLink4 = new acr.component.CHyperLink();
        cHyperLink5 = new acr.component.CHyperLink();
        cHyperLink6 = new acr.component.CHyperLink();
        cTextField1 = new acr.component.CTextField();
        jPanel4 = new javax.swing.JPanel();
        cHyperLink7 = new acr.component.CHyperLink();
        cHyperLink10 = new acr.component.CHyperLink();
        cHyperLink11 = new acr.component.CHyperLink();
        cHyperLink12 = new acr.component.CHyperLink();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.GridLayout(1, 1));

        jToolBar1.setRollover(true);

        jButton1.setForeground(new java.awt.Color(0, 0, 153));
        jButton1.setText("Standard Pivot Points");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton1);

        jButton2.setForeground(new java.awt.Color(0, 0, 153));
        jButton2.setText("Fibonacci Pivot Points");
        jButton2.setFocusable(false);
        jButton2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton2.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jToolBar1.add(jButton2);

        jButton3.setText("Help");
        jButton3.setFocusable(false);
        jButton3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton3.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton3);
        jToolBar1.add(jPanel3);

        jPanel1.add(jToolBar1);

        add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jPanel2.setLayout(new java.awt.GridLayout(1, 2));

        pnlTable.setLayout(new java.awt.BorderLayout());
        jPanel2.add(pnlTable);

        jPanel5.setLayout(new java.awt.GridLayout(2, 1));

        pnlLevel.setBorder(javax.swing.BorderFactory.createTitledBorder("Symbol@SR"));
        pnlLevel.setLayout(new java.awt.BorderLayout());

        jPanel7.setLayout(new java.awt.GridLayout(2, 1));

        jToolBar2.setRollover(true);

        cHyperLink1.setText("@S1");
        cHyperLink1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink1MouseClicked(evt);
            }
        });
        jToolBar2.add(cHyperLink1);

        cHyperLink2.setText("  @S2");
        cHyperLink2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink2MouseClicked(evt);
            }
        });
        jToolBar2.add(cHyperLink2);

        cHyperLink3.setText("  @S3");
        cHyperLink3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink3MouseClicked(evt);
            }
        });
        jToolBar2.add(cHyperLink3);

        cHyperLink4.setText("  @R1");
        jToolBar2.add(cHyperLink4);

        cHyperLink5.setText("  @R2");
        jToolBar2.add(cHyperLink5);

        cHyperLink6.setText("  @R3");
        jToolBar2.add(cHyperLink6);

        cTextField1.setEditable(false);
        cTextField1.setForeground(new java.awt.Color(0, 102, 204));
        cTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cTextField1.setText("Price Reaching Support/Resistance Levels");
        jToolBar2.add(cTextField1);

        jPanel7.add(jToolBar2);

        jPanel4.setBackground(new java.awt.Color(221, 221, 232));

        cHyperLink7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/filter16.png"))); // NOI18N
        cHyperLink7.setText("Range");
        cHyperLink7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink7MouseClicked(evt);
            }
        });
        jPanel4.add(cHyperLink7);

        cHyperLink10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/filter16.png"))); // NOI18N
        cHyperLink10.setText("Volume");
        cHyperLink10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink10MouseClicked(evt);
            }
        });
        jPanel4.add(cHyperLink10);

        cHyperLink11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/filter16.png"))); // NOI18N
        cHyperLink11.setText("Cap   ");
        cHyperLink11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink11MouseClicked(evt);
            }
        });
        jPanel4.add(cHyperLink11);

        cHyperLink12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/reload.png"))); // NOI18N
        cHyperLink12.setText("Refresh");
        cHyperLink12.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink12MouseClicked(evt);
            }
        });
        jPanel4.add(cHyperLink12);

        jPanel7.add(jPanel4);

        pnlLevel.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        pnlLevel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel5.add(pnlLevel);

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Tick-Chart (10 days)"));
        jPanel5.add(jPanel6);

        jPanel2.add(jPanel5);

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        createStandardSRLevel();
        table.setModel(new PivotDataTableModel(levelList));
        table.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void cHyperLink1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink1MouseClicked
        // TODO add your handling code here:
        setSupportLevelMark("S1");
        selectedEntity = "S1";
        levelTable.setModel(new LevelMarkTableModel(lpList));
        levelTable.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_cHyperLink1MouseClicked

    private void cHyperLink11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink11MouseClicked
        // TODO add your handling code here:
        String answer = (String) JOptionPane.showInputDialog(null,
                (String) "Show only symbols having "
                + "\nMarket Cap Greater Then: ", "Cap > Then: ?",
                JOptionPane.OK_CANCEL_OPTION, null, null, "100000000");

        double cap = Double.parseDouble(answer);
        showCap(cap);
    }//GEN-LAST:event_cHyperLink11MouseClicked

    private void cHyperLink10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink10MouseClicked
        // TODO add your handling code here:
        String answer = (String) JOptionPane.showInputDialog(null,
                (String) "Show only symbols having "
                + "\nVolume Greater Then: ", "Volume > Then: ?",
                JOptionPane.OK_CANCEL_OPTION, null, null, "100000");
        double v = Double.parseDouble(answer);
        showVol(v);
    }//GEN-LAST:event_cHyperLink10MouseClicked

    private void cHyperLink7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink7MouseClicked
        // TODO add your handling code here:
        PnlRangeSelection prs = new PnlRangeSelection();
        int answer = JOptionPane.showConfirmDialog(null, prs);
        if (answer == JOptionPane.YES_OPTION) {
            double from = prs.getStartLevelMark();
            double to = prs.getEndLevelMark();
            showLevelRange(from, to);
        }
    }//GEN-LAST:event_cHyperLink7MouseClicked

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        createFibonacciSRLevel();
        table.setModel(new PivotDataTableModel(levelList));
        table.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cHyperLink2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink2MouseClicked
        // TODO add your handling code here:
        setSupportLevelMark("S2");
        selectedEntity ="S2";
        levelTable.setModel(new LevelMarkTableModel(lpList));
        levelTable.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_cHyperLink2MouseClicked

    private void cHyperLink3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink3MouseClicked
        // TODO add your handling code here:
        setSupportLevelMark("S3");
        selectedEntity = "S3";
        levelTable.setModel(new LevelMarkTableModel(lpList));
        levelTable.setAutoCreateRowSorter(true);
    }//GEN-LAST:event_cHyperLink3MouseClicked

    private void cHyperLink12MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink12MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_cHyperLink12MouseClicked

    private void setSupportLevelMark(String entity) {
        CDataDaemon cdd = new CDataDaemon();
        ArrayList<DailyData> ddl = new ArrayList<>();
        ddl = cdd.getQuoteList();
        TreeMap<String, Double> valMap = new TreeMap<>();
        for (SRLevel l : levelList) {
            String e = l.getSymbol();
            double price = 0;
            if (entity.compareToIgnoreCase("S1") == 0) {
                price = l.getS1();
            } else if (entity.compareToIgnoreCase("S2") == 0) {
                price = l.getS2();
            } else if (entity.compareToIgnoreCase("S3") == 0) {
                price = l.getS3();
            }

            valMap.put(e, price);
        }

        lpList = new ArrayList<>();
        for (DailyData dd : ddl) {
            String symbol = dd.getSymbol();
            double current = dd.getClose();
            double volume = dd.getVolume();
            double cap = dd.getMcap();
            if (valMap.containsKey(symbol)) {
                double support = valMap.get(symbol);

                double diff = current - support;
                LevelPresentation lp = new LevelPresentation();
                lp.setSymbol(symbol);
                lp.setCurrent(current);
                lp.setDiff(diff);
                lp.setSupport(support);
                lp.setVolume(volume);
                lp.setCap(cap);

                lpList.add(lp);
            }

        }

    }

    private void showCap(double cap) {
        ArrayList<LevelPresentation> qlist = new ArrayList<>();
        Iterator i = lpList.iterator();
        while (i.hasNext()) {
            LevelPresentation dd = (LevelPresentation) i.next();
            double c = (dd.getCap());
            if (c > cap) {
                qlist.add(dd);
            }
        }

        TableModel tableModel = new LevelMarkTableModel(qlist);
        this.levelTable.setModel(tableModel);
    }

    private void showVol(double vol) {
        ArrayList<LevelPresentation> qlist = new ArrayList<>();
        Iterator i = lpList.iterator();
        while (i.hasNext()) {
            LevelPresentation dd = (LevelPresentation) i.next();
            double v = (dd.getVolume());
            if (v > vol) {
                qlist.add(dd);
            }
        }

        TableModel tableModel = new LevelMarkTableModel(qlist);
        this.levelTable.setModel(tableModel);
    }

    private void showLevelRange(double from, double to) {
        ArrayList<LevelPresentation> qlist = new ArrayList<>();
        Iterator i = lpList.iterator();
        while (i.hasNext()) {
            LevelPresentation dd = (LevelPresentation) i.next();
            double r = (dd.getDiff());
            if (r >= from && r <= to) {
                qlist.add(dd);
            }
        }

        TableModel tableModel = new LevelMarkTableModel(qlist);
        
        this.levelTable.setModel(tableModel);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private acr.component.CHyperLink cHyperLink1;
    private acr.component.CHyperLink cHyperLink10;
    private acr.component.CHyperLink cHyperLink11;
    private acr.component.CHyperLink cHyperLink12;
    private acr.component.CHyperLink cHyperLink2;
    private acr.component.CHyperLink cHyperLink3;
    private acr.component.CHyperLink cHyperLink4;
    private acr.component.CHyperLink cHyperLink5;
    private acr.component.CHyperLink cHyperLink6;
    private acr.component.CHyperLink cHyperLink7;
    private acr.component.CTextField cTextField1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar2;
    private javax.swing.JPanel pnlLevel;
    private javax.swing.JPanel pnlTable;
    // End of variables declaration//GEN-END:variables
}

class PivotDataTableModel extends AbstractTableModel {

    private static final int COL_INDEX = 0;
    private static final int COL_SYMBOL = 1;
    private static final int COL_S1 = 2;
    private static final int COL_S2 = 3;
    private static final int COL_S3 = 4;
    private static final int COL_R1 = 5;
    private static final int COL_R2 = 6;
    private static final int COL_R3 = 7;

    private String[] columnNames = {"No", "Symbol", "S1", "S2",
        "S3", "R1", "R2", "R3"};

    private ArrayList<SRLevel> levelList;

    public PivotDataTableModel(ArrayList<SRLevel> levelList) {
        this.levelList = levelList;

        int indexCount = 1;
        for (SRLevel srl : levelList) {
            srl.setIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return levelList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (levelList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SRLevel srl = levelList.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COL_INDEX:
                returnValue = srl.getIndex();
                break;
            case COL_SYMBOL:
                returnValue = srl.getSymbol();
                break;
            case COL_S1:
                returnValue = srl.getS1();
                break;
            case COL_S2:
                returnValue = srl.getS2();
                break;
            case COL_S3:
                returnValue = srl.getS3();
                break;
            case COL_R1:
                returnValue = srl.getR1();
                break;
            case COL_R2:
                returnValue = srl.getR2();
                break;
            case COL_R3:
                returnValue = srl.getR3();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        SRLevel srl = levelList.get(rowIndex);
        if (columnIndex == COL_INDEX) {
            srl.setIndex((int) value);
        }
    }

}

class LevelMarkTableModel extends AbstractTableModel {

    private static final int COL_INDEX = 0;
    private static final int COL_SYMBOL = 1;
    private static final int COL_CURRENT = 2;
    private static final int COL_SUPPORT = 3;
    private static final int COL_DIFF = 4;
    private static final int COL_VOLUME = 5;
    private static final int COL_CAP = 6;

    private String[] columnNames = {"No", "Symbol", "Current", "Support",
        "Diff", "Volume", "Cap"};

    private ArrayList<LevelPresentation> lpList;

    public LevelMarkTableModel(ArrayList<LevelPresentation> lpList) {
        this.lpList = lpList;

        int indexCount = 1;
        for (LevelPresentation lp : lpList) {
            lp.setIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return lpList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (lpList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        LevelPresentation lp = lpList.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COL_INDEX:
                returnValue = lp.getIndex();
                break;
            case COL_SYMBOL:
                returnValue = lp.getSymbol();
                break;
            case COL_CURRENT:
                returnValue = lp.getCurrent();
                break;
            case COL_SUPPORT:
                returnValue = lp.getSupport();
                break;
            case COL_DIFF:
                returnValue = lp.getDiff();
                break;
            case COL_VOLUME:
                returnValue = lp.getVolume();
            case COL_CAP:
                returnValue = lp.getCap();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        LevelPresentation lp = lpList.get(rowIndex);
        if (columnIndex == COL_INDEX) {
            lp.setIndex((int) value);
        }
    }
    
    public ArrayList<LevelPresentation> getLpList(){
        return lpList;
    }

}

class LevelPresentation {

    private int index;
    private String symbol;
    private double current, support, diff, volume, cap;

    public LevelPresentation() {

    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getCurrent() {
        return current;
    }

    public void setCurrent(double current) {
        this.current = current;
    }

    public double getSupport() {
        return support;
    }

    public void setSupport(double support) {
        this.support = support;
    }

    public double getDiff() {
        return diff;
    }

    public void setDiff(double diff) {
        this.diff = diff;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getCap() {
        return cap;
    }

    public void setCap(double cap) {
        this.cap = cap;
    }

}
