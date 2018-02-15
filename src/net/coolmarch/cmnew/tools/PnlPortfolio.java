/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import net.coolmarch.cmnew.common.CommonTasks;
import net.coolmarch.cmnew.fiplip.PieRenderer;
import net.coolmarch.daemon.DataFetchingDaemon;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author coolmarch
 */
public class PnlPortfolio extends javax.swing.JPanel {

    private ArrayList<PFData> list;
    private PnlPortfolioItemDetails pid;

    /**
     * Creates new form PnlPortfolio
     */
    public PnlPortfolio() {
        initComponents();
        list = new ArrayList<>();
        File file = new File("portfolio.pfs");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException ioe) {
            }
        }

        showData();
        pid = new PnlPortfolioItemDetails();
        pnlDetailsTab1.add(pid);

    }

    private void showData() {
        list.clear();
        openPortfolioFile();
        Set<String> set = new HashSet();
        for (PFData pfd : list) {
            set.add(pfd.getSymbol());
        }
        DecimalFormat df = new DecimalFormat("#.##");
        DefaultTableModel model2 = ((DefaultTableModel) this.tblAvgRates.getModel());
        model2.setRowCount(0);
        double total = getTotalAmount();
        int grand_total_qty = 0;
        double grand_total_cap = 0;

        TreeMap<String, Double> map = new TreeMap<>();
        for (String symbol : set) {
            Vector v = getSum(symbol);
            double amount = (Double) v.get(3);
            map.put(symbol, amount);
            double percent = (amount / total) * 100;
            v.add(df.format(percent));
            model2.addRow(v);

            grand_total_qty += (int) v.get(1);
            grand_total_cap += percent;

        }

        double r = total / grand_total_qty;
        Vector v = new Vector();
        v.add("All");
        v.add(grand_total_qty);
        v.add(df.format(r));
        v.add(total);
        v.add(df.format(grand_total_cap));

        model2.addRow(v);

        createChart(map);

    }

    private double getTotalAmount() {
        double sum = 0;
        for (PFData pfd : list) {
            sum += pfd.getAmount();
        }
        return sum;
    }

    private Vector getSum(String symbol) {
        double sum = 0;
        int qty = 0;

        for (PFData pfd : list) {
            String sym = pfd.getSymbol();
            if (symbol.compareToIgnoreCase(sym) == 0) {
                sum += pfd.getAmount();
                qty += pfd.getQty();
            }
        }

        double r = sum / qty;
        DecimalFormat df = new DecimalFormat("#.##");

        Vector v = new Vector();
        v.add(symbol);
        v.add(qty);
        v.add(df.format(r));
        v.add(sum);

        return v;
    }

    private void createChart(TreeMap<String, Double> pfmap) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (String symbol : pfmap.keySet()) {
            dataset.setValue(symbol, pfmap.get(symbol));
        }

        JFreeChart chart = ChartFactory.createPieChart3D(
                "Portfolio",
                dataset,
                true,
                true,
                false);

        chart.removeLegend();
        chart.setTitle("");
        chart.getPlot().setOutlineVisible(false);
        chart.getPlot().setNoDataMessage("--No Data--");
        chart.getPlot().setBackgroundPaint(pnlChart.getBackground());
        chart.setBackgroundPaint(pnlChart.getBackground());

        PiePlot plot = (PiePlot) chart.getPlot();
        PieRenderer renderer = new PieRenderer();
        renderer.setColor(plot, dataset);

        pnlChart.removeAll();
        ChartPanel cp = new ChartPanel(chart);
        cp.setBackground(pnlChart.getBackground());
        pnlChart.add(cp);
        pnlChart.update(pnlChart.getGraphics());
        pnlChart.validate();

    }

    private void openPortfolioFile() {
        DefaultTableModel model = (DefaultTableModel) this.tblFile.getModel();
        model.setRowCount(0);
        File file = new File("portfolio.pfs");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.length() < 5) {
                    ;
                } else {
                    String[] toke = line.split("::");
                    String symbol = toke[0];
                    String rate = toke[1];
                    String qty = toke[2];
                    String amount = toke[3];

                    double r = Double.parseDouble(rate);
                    int q = Integer.parseInt(qty);
                    double a = r * q;

                    Vector v = new Vector();
                    v.add(symbol);
                    v.add(r);
                    v.add(q);
                    v.add(a);
                    model.addRow(v);

                    PFData pfd = new PFData(symbol, r, q);
                    list.add(pfd);

                }
            }
            
            br.close();
        } catch (IOException ioe) {

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

        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel3 = new javax.swing.JPanel();
        jSplitPane3 = new javax.swing.JSplitPane();
        pnlChart = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblAvgRates = new javax.swing.JTable();
        jLabel9 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        tfCode = new acr.component.CTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfAmount = new acr.component.CTextField();
        jLabel3 = new javax.swing.JLabel();
        tfRate = new acr.component.CTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFile = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        tfQty = new acr.component.CIntegerField();
        jButton3 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        pnlDetails = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlDetailsTab1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jSplitPane2.setDividerLocation(360);
        jSplitPane2.setDividerSize(5);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        jSplitPane1.setDividerLocation(400);
        jSplitPane1.setDividerSize(5);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jSplitPane3.setDividerLocation(175);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        pnlChart.setLayout(new java.awt.BorderLayout());
        jSplitPane3.setTopComponent(pnlChart);

        jPanel6.setLayout(new java.awt.BorderLayout());

        tblAvgRates.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Symbol", "Volume", "Rate", "Amount", "%Cap"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblAvgRates.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblAvgRatesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblAvgRates);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jLabel9.setBackground(new java.awt.Color(204, 204, 255));
        jLabel9.setText("*Double click on table row to view details below..");
        jLabel9.setOpaque(true);
        jPanel6.add(jLabel9, java.awt.BorderLayout.PAGE_START);

        jSplitPane3.setRightComponent(jPanel6);

        jPanel3.add(jSplitPane3, java.awt.BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel3);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jLabel7.setText("Amount");
        jLabel7.setEnabled(false);

        jLabel2.setText("* eg. PTC, DGKC");

        tfAmount.setEnabled(false);

        jLabel3.setText("Buy Rate");

        tblFile.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Symbol", "Rate", "Volume", "Amount"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFile);

        jLabel4.setText("* eg. 12, 12.5 etc");

        jLabel5.setText("Buy Quantity");

        jButton2.setText("Add");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        tfQty.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                tfQtyFocusLost(evt);
            }
        });

        jButton3.setText("Remove Selected");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel8.setText("#Add as you buy- the system will keep average show in summary");

        jLabel6.setText("* eg. 100, 5000 etc");

        jLabel1.setText("Company Code");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(tfCode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfRate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfQty, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(tfAmount, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton2)))
                            .addComponent(jLabel8))
                        .addGap(143, 143, 143))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 538, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(tfRate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(tfQty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(tfAmount, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGap(34, 34, 34)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel7, java.awt.BorderLayout.CENTER);

        jSplitPane1.setRightComponent(jPanel4);

        jSplitPane2.setTopComponent(jSplitPane1);

        pnlDetails.setLayout(new java.awt.BorderLayout());

        pnlDetailsTab1.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("tab1", pnlDetailsTab1);

        pnlDetails.add(jTabbedPane1, java.awt.BorderLayout.CENTER);

        jSplitPane2.setRightComponent(pnlDetails);

        jLabel10.setBackground(new java.awt.Color(204, 204, 255));
        jLabel10.setText("Your portfolio related record is saved locally on your computer drive with name \"portfolio.pfs\". Due to our policy Coolmarch does not store/save user data, so please take backup of it.");
        jLabel10.setOpaque(true);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSplitPane2)
                .addContainerGap())
            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addContainerGap())
        );

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        appendToFile();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void tfQtyFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_tfQtyFocusLost
        // TODO add your handling code here:
        calculateAmount();
    }//GEN-LAST:event_tfQtyFocusLost

    private void tblAvgRatesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblAvgRatesMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 2) {
            pid.clear();
            setSymbolInformation();
        }
    }//GEN-LAST:event_tblAvgRatesMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        remove();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void setSymbolInformation() {
        int selectedRow = this.tblAvgRates.getSelectedRow();
        String symbol = (String) tblAvgRates.getValueAt(selectedRow, 0);
        if (symbol.compareToIgnoreCase("all") == 0) {

        } else {
            int qty = (Integer) tblAvgRates.getValueAt(selectedRow, 1);
            double rate = Double.parseDouble((String) tblAvgRates.getValueAt(selectedRow, 2));
            double amount = ((Double) tblAvgRates.getValueAt(selectedRow, 3));

            CommonTasks ct = new CommonTasks();
            double currentRate = 0;
            try{
                currentRate = DataFetchingDaemon.vmMap.get(symbol);
            }catch(Exception e){
            
            }

            double camount = qty * currentRate;
            double pl = camount - amount;

            SymbolInformation si = pid.getSymbolInformation(symbol);
            pid.set52WHigh(si.getFiftyTwoWeekHigh() * qty - amount);
            pid.set52WLow(si.getFiftyTwoWeekLow() * qty - amount);
//            pid.setWCase(si.getWcRate() * qty - amount);
            pid.setQty(qty);
            pid.setRate(rate);
            pid.setCurrentRate(currentRate);
            pid.setPl(pl);
            //pid.setChart(symbol);
        }
    }

    private void appendToFile() {
        String line = "";
        String symbol = this.tfCode.getText();
        String rate = this.tfRate.getText();
        String qty = this.tfQty.getText();
        String amount = this.tfAmount.getText();
        line = symbol + "::" + rate + "::" + qty + "::" + amount + "\n";
        try {
            Files.write(Paths.get("portfolio.pfs"), line.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }

        this.showData();
    }

    private void remove() {
        int selected_row = this.tblFile.getSelectedRow();
        DefaultTableModel model = (DefaultTableModel) tblFile.getModel();
        model.removeRow(selected_row);
        File file = new File("portfolio.pfs");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            int r_count = tblFile.getRowCount();
            for (int i = 0; i <= r_count - 1; i++) {
                String symbol = (String) tblFile.getValueAt(i, 0);
                double rate = (Double) tblFile.getValueAt(i, 1);
                int qty = (Integer) tblFile.getValueAt(i, 2);
                double amount = (Double) tblFile.getValueAt(i, 3);
                String line = symbol + "::" + rate + "::" + qty + "::" + amount;
                System.out.println(line);
                bw.write(line);
                bw.newLine();
            }
            
            bw.close();
            showData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void calculateAmount() {
        int qty = Integer.parseInt(this.tfQty.getText());
        double rate = Double.parseDouble(tfRate.getText());
        double amount = qty * rate;
        DecimalFormat df = new DecimalFormat("#.##");
        this.tfAmount.setText(df.format(amount));

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlDetailsTab1;
    private javax.swing.JTable tblAvgRates;
    private javax.swing.JTable tblFile;
    private acr.component.CTextField tfAmount;
    private acr.component.CTextField tfCode;
    private acr.component.CIntegerField tfQty;
    private acr.component.CTextField tfRate;
    // End of variables declaration//GEN-END:variables
}

class PFData {

    private double rate;
    private int qty;
    private double amount;
    private String symbol;

    public PFData(String symbol, double rate, int qty) {
        this.rate = rate;
        this.qty = qty;
        this.symbol = symbol;
        this.amount = rate * qty;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
