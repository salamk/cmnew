/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tools;

import cmnew.CoolmarchConstants;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author coolmarch
 */
public class PnlReports extends javax.swing.JPanel {

    private ArrayList<Publication> publicationList;

    /**
     * Creates new form PnlReports
     */
    public PnlReports() {
        initComponents();
        publicationList = new ArrayList<>();
        tbl.setModel(new PublicationTableModel(publicationList));
        tbl.setAutoCreateRowSorter(true);
        setPublication();
        setPublicationType();
        showPublicationByType((String) this.cmbType.getSelectedItem());

    }

    private void setPublication() {
        cmbType.addItem("All");
        String str = "";
        String script = CoolmarchConstants.COOLMARCH_SERVER+"/pub/pubindex.php";
        try {
            URL url = new URL(script);
            URLConnection uc = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                str = inputLine;
                if (str.startsWith("EL>")) {
                    str = str.replaceAll("EL>", "");
                    str = str.replaceAll("<br />", "");
                    String[] toke = str.split(",");
                    if (toke.length == 7) {
                        int id = Integer.parseInt(toke[0]);
                        String pubTime = toke[1];
                        String pubType = toke[2];
                        String pubTitle = toke[3];
                        String pubOwner = toke[4];
                        String pubUrl = toke[5];
                        String filesize = toke[6];

                        if (filesize == null) {
                            filesize = "0";
                        }

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date pubDate = new Date();
                        try {
                            pubDate = sdf.parse(pubTime);
                        } catch (Exception e) {
                        }
                        Publication pub = new Publication();

                        pub.setIndex(id);
                        pub.setPubOwner(pubOwner);
                        pub.setPubTime(pubDate);
                        pub.setPubTitle(pubTitle);
                        pub.setPubType(pubType);
                        pub.setPubUrl(pubUrl);
                        pub.setFilesize(Float.parseFloat(filesize));

                        publicationList.add(pub);

                    }
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void setPublicationType() {
        Set<String> s = new HashSet();
        for (Publication pub : publicationList) {
            s.add(pub.getPubType());
        }

        for (String type : s) {
            cmbType.addItem(type);
        }
    }

    private void showPublicationByType(String type) {

        ArrayList<Publication> selectedList = new ArrayList<>();

        for (Publication pub : publicationList) {
            String ptype = pub.getPubType();

            if (type.compareToIgnoreCase(ptype) == 0 || type.compareToIgnoreCase("all") == 0) {
                selectedList.add(pub);
            }
        }

        tbl.setModel(new PublicationTableModel(selectedList));
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tbl.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else if (i == 3) {
                column.setPreferredWidth(700); //sport column is bigger
            } else {
                column.setPreferredWidth(150);
            }
        }
    }

    private void search(String word) {
        ArrayList<Publication> selectedList = new ArrayList<>();

        for (Publication pub : publicationList) {
            String str = pub.toString();

            if (str.contains(word)) {
                selectedList.add(pub);
            }
        }

        tbl.setModel(new PublicationTableModel(selectedList));
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = tbl.getColumnModel().getColumn(i);
            if (i == 0) {
                column.setPreferredWidth(50);
            } else if (i == 3) {
                column.setPreferredWidth(700); //sport column is bigger
            } else {
                column.setPreferredWidth(150);
            }
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

        jToolBar1 = new javax.swing.JToolBar();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        cmbType = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        tfWait = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        jToolBar1.setRollover(true);

        jButton1.setText("View");
        jButton1.setFocusable(false);
        jButton1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jButton1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        jToolBar1.add(jButton1);

        jPanel1.setBackground(new java.awt.Color(223, 245, 223));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        cmbType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbTypeActionPerformed(evt);
            }
        });
        jPanel1.add(cmbType);

        jLabel1.setText("         ");
        jPanel1.add(jLabel1);

        tfSearch.setColumns(15);
        jPanel1.add(tfSearch);

        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);

        jLabel2.setForeground(new java.awt.Color(0, 51, 51));
        jLabel2.setText("*Type any word to search content    ");
        jPanel1.add(jLabel2);

        tfWait.setEditable(false);
        tfWait.setColumns(15);
        tfWait.setBorder(null);
        tfWait.setOpaque(false);
        jPanel1.add(tfWait);

        jToolBar1.add(jPanel1);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        tbl.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "#", "Date", "Type", "Title", "Owner"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl.setRowHeight(20);
        tbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl);
        if (tbl.getColumnModel().getColumnCount() > 0) {
            tbl.getColumnModel().getColumn(0).setMinWidth(50);
            tbl.getColumnModel().getColumn(0).setPreferredWidth(50);
            tbl.getColumnModel().getColumn(0).setMaxWidth(50);
            tbl.getColumnModel().getColumn(1).setMinWidth(100);
            tbl.getColumnModel().getColumn(1).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(1).setMaxWidth(100);
            tbl.getColumnModel().getColumn(2).setMinWidth(100);
            tbl.getColumnModel().getColumn(2).setPreferredWidth(100);
            tbl.getColumnModel().getColumn(2).setMaxWidth(100);
            tbl.getColumnModel().getColumn(4).setMinWidth(150);
            tbl.getColumnModel().getColumn(4).setPreferredWidth(150);
            tbl.getColumnModel().getColumn(4).setMaxWidth(150);
        }

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void cmbTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbTypeActionPerformed
        // TODO add your handling code here:
        showPublicationByType((String) this.cmbType.getSelectedItem());
    }//GEN-LAST:event_cmbTypeActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        search(this.tfSearch.getText());
    }//GEN-LAST:event_jButton3ActionPerformed

    private void tblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblMouseClicked
        // TODO add your handling code here:
        int clickCount = evt.getClickCount();
        if (clickCount == 2) {
            tfWait.setText("Please wait...");
            tfWait.update(tfWait.getGraphics());
            tfWait.validate();
            int selectedRow = tbl.getSelectedRow();
            int id = (int) tbl.getValueAt(selectedRow, 0);
            for (Publication pub : publicationList) {
                int index = pub.getIndex();
                if (id == index) {
                    String source = pub.getPubUrl();
                    try {
                        URL url = new URL(source);
                        Desktop.getDesktop().browse(url.toURI());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            tfWait.setText("");
        }
    }//GEN-LAST:event_tblMouseClicked

    private void downloadAndOpenFile(String source, String fileName) {
        URL url = null;
        String savePath = System.getProperty("user.home") + System.getProperty("file.separator");
        File file = new File(savePath + fileName);
        if (file.exists()) {
            System.out.println("File already exists");
        } else {
            try {
                System.out.println("opening connection");
                url = new URL(source);
                InputStream in = url.openStream();
                FileOutputStream fos = new FileOutputStream(new File(savePath + fileName));

                System.out.println("reading from resource and writing to file...");
                int length = -1;
                byte[] buffer = new byte[1024];// buffer for portion of data from connection
                while ((length = in.read(buffer)) > -1) {
                    fos.write(buffer, 0, length);
                }
                fos.close();
                in.close();
                System.out.println("File downloaded...........");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "There was an error in downloading\nPlease try again later");
                System.out.println(e.getMessage());
            } finally {
                tfWait.setText("");
            }
        }

        if (Desktop.isDesktopSupported()) {
            try {

                String path = savePath + fileName;
                File myFile = new File(path);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cmbType;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTable tbl;
    private javax.swing.JTextField tfSearch;
    private javax.swing.JTextField tfWait;
    // End of variables declaration//GEN-END:variables
}

class Publication {

    private int index;
    private Date pubTime;
    private String pubType;
    private String pubTitle;
    private String pubOwner;
    private String pubUrl;
    private float filesize;

    public Publication() {

    }

    public float getFilesize() {
        return filesize;
    }

    public void setFilesize(float filesize) {
        this.filesize = filesize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Date getPubTime() {
        return pubTime;
    }

    public void setPubTime(Date pubTime) {
        this.pubTime = pubTime;
    }

    public String getPubType() {
        return pubType;
    }

    public void setPubType(String pubType) {
        this.pubType = pubType;
    }

    public String getPubTitle() {
        return pubTitle;
    }

    public void setPubTitle(String pubTitle) {
        this.pubTitle = pubTitle;
    }

    public String getPubOwner() {
        return pubOwner;
    }

    public void setPubOwner(String pubOwner) {
        this.pubOwner = pubOwner;
    }

    public String getPubUrl() {
        return pubUrl;
    }

    public void setPubUrl(String pubUrl) {
        this.pubUrl = pubUrl;
    }

    public String toString() {
        String str = "";
        str += this.getPubTime() + " , " + this.getPubOwner() + " , " + this.getPubTitle() + " , " + this.getPubUrl();
        return str;
    }

}

class PublicationTableModel extends AbstractTableModel {

    private static final int COL_INDEX = 0;
    private static final int COL_DATE = 1;
    private static final int COL_TYPE = 2;
    private static final int COL_TITLE = 3;
    private static final int COL_OWNER = 4;
    private static final int COL_SIZE = 5;

    private String[] columnNames = {"No", "Date", "Type", "Title",
        "Owner", "size(MB)"};

    private ArrayList<Publication> publicationList;

    public PublicationTableModel(ArrayList<Publication> publicationList) {
        this.publicationList = publicationList;

        int indexCount = 1;
        for (Publication pub : publicationList) {
            pub.setIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return publicationList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (publicationList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Publication pub = publicationList.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COL_INDEX:
                returnValue = pub.getIndex();
                break;
            case COL_DATE:
                returnValue = pub.getPubTime();
                break;
            case COL_TYPE:
                returnValue = pub.getPubType();
                break;
            case COL_TITLE:
                returnValue = pub.getPubTitle();
                break;
            case COL_OWNER:
                returnValue = pub.getPubOwner();
                break;
            case COL_SIZE:
                returnValue = pub.getFilesize();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        Publication pub = publicationList.get(rowIndex);
        if (columnIndex == COL_INDEX) {
            pub.setIndex((int) value);
        }
    }
}
