/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmlsys;

import cmnew.CoolmarchConstants;
import cmnew.InterfaceMain2;
import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;
import javax.swing.text.Document;
import net.coolmarch.cmnew.common.CheckLastUpdate;
import net.coolmarch.cmnew.common.CommonIdentification;
import net.coolmarch.cmnew.common.CommonTasks;
import static net.coolmarch.cmnew.common.CommonTasks.copy;
import net.coolmarch.cmnew.common.GeneralDB;

/**
 *
 * @author coolmarch
 */
public final class Splash extends javax.swing.JPanel {
    private final String cid = "zeSnOLFlgZo2Mw5ylb0tpOVN5GtLFn";
    
    /**
     * Creates new form Splash
     */
    private final JFrame parent;

    public Splash(JFrame parent) {
        checkAttr();
        initComponents();
        checkPwdFile();
        this.parent = parent;
        btnLogin.setEnabled(false);
        DefaultCaret caret = (DefaultCaret) taText2.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        scroller.setViewportView(taText2);

        new Thread(() -> {
            int counter = 1;
            int c = 9616;
            String s = Character.toString((char) c);
            while (counter <= 70) {
                append("|");
                try {
                    Thread.sleep(20);
                } catch (InterruptedException ie) {

                }

                counter++;
            }

            printServerMessages();
            boolean update_availaible = checkForUpdate();
            CheckLastUpdate clu = new CheckLastUpdate();

            if (update_availaible) {
                String current_directory = System.getProperty("user.dir");
                String file = "cmnew.jar";
                boolean downloaded = downloadFile(file, current_directory);
                if (!downloaded) {
                    append("\nNetwork Problem\nPlease try again later..");

                } else {
                    updateVersionString();
                    append("\nApplication updated successfully\n\t"
                            + "Please close this window and run again");
                    JOptionPane.showMessageDialog(null, "Application updated\n"
                            + "Please restart the application");
                    System.exit(0);
                }

            } else {

                updateCache();
                // checkDbUpdate();
                append("\nUpdate completed");
                btnLogin.setEnabled(true);
            }
        }).start();

    }

    private boolean checkForUpdate() {
        //append("\nLooking up...");
        String localVersion = "";
        String remoteVersion = "";
        boolean update_found = false;

        remoteVersion = getRemoteVersionString();
        localVersion = getLocalVersionString();

        if (remoteVersion.compareTo(localVersion)
                == 0) {
            update_found = false;
        } else {
            update_found = true;
        }

        return update_found;
    }

    private String getLocalVersionString() {
        String localVersion = "";
        try {

            BufferedReader br = new BufferedReader(new FileReader("jarsign.jrs"));
            String a = "";
            while ((a = br.readLine()) != null) {
                localVersion += a;
            }

            br.close();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return localVersion.trim();
    }

    private void printServerMessages() {
        ArrayList<String> s = this.getServerMessages();
        for (int i = 0; i <= s.size() - 2; i++) {
            append("\n" + (String) s.get(i));
        }
        System.setProperty("puck", s.get(s.size() - 1));
        // append("\n" + System.getProperty("puck"));
    }

    public void append(String s) {
        try {
            Document doc = taText2.getDocument();
            doc.insertString(doc.getLength(), s, null);
        } catch (BadLocationException exc) {
            exc.printStackTrace();
        }
    }

    private String getRemoteVersionString() {
        String remoteVersion = "";
        try {
            long t = new Date().getTime();
            URL oracle = new URL(CoolmarchConstants.COOLMARCH_SERVER + "/ver.php?t=" + t);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                remoteVersion += inputLine;
            }

            in.close();

        } catch (Exception e) {

        }

        return remoteVersion.trim();

    }

    private ArrayList<String> getServerMessages() {
        Date d = new Date();
        String rs = new SimpleDateFormat("hhmmss").format(d);
        ArrayList<String> serverMessages = new ArrayList<>();
        String str = "";
        try {

            URL cm = new URL(CoolmarchConstants.INFO_SERVER + "/abscripts/welcome.php?m=" + rs);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(cm.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                str += inputLine;
            }

            in.close();

        } catch (Exception e) {

        }

        String[] toke = str.split("#");
        for (int i = 0; i <= toke.length - 1; i++) {
            serverMessages.add(toke[i]);
        }

        return serverMessages;

    }

    private boolean updateVersionString() {
        boolean updated = false;
        String remoteVersion = getRemoteVersionString();
        try {
            File file = new File("jarsign.jrs");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(remoteVersion);
            bw.close();
            updated = true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());

        }

        return updated;
    }

    private boolean downloadFile(String fileName, String destination) {
        boolean downloaded = false;
        String sep = System.getProperty("file.separator");
        CommonTasks ct = new CommonTasks();

        long t = new Date().getTime();
        String url = "";
        String puck = System.getProperty("puck");
        url = CoolmarchConstants.COOLMARCH_SERVER + "/abscripts/getfile.php?puck=" + puck + "&fname=" + fileName + "&t=" + t;
        try {
            URL lc = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) lc.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            FileOutputStream out = new FileOutputStream(destination + sep + fileName);
            copy(in, out, 1024);
            out.close();
            downloaded = true;
            append("\n Download OK -/");
        } catch (Exception e) {

        }

        return downloaded;
    }

    private void updateCache() {
        ArrayList<String> fileOnServer = new ArrayList<>();
        try {
            long t = new Date().getTime();
            URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER + "/pvcachelist.php?t=" + t);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("<br />", "");
                if (line.length() > 3) {
                    fileOnServer.add(line);
                }
            }

            br.close();
        } catch (Exception e) {
            // //System.out.println(e.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> flist = new ArrayList<>();
        String sep = System.getProperty("file.separator");
        String cdir = System.getProperty("user.dir");
        File folder = new File(cdir + sep + "cache" + sep + "pv");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String n = listOfFiles[i].getName();
                flist.add(n);
            } else if (listOfFiles[i].isDirectory()) {
            }
        }

        Set<String> localSet = new HashSet();
        Set<String> serverSet = new HashSet();

        for (String line : fileOnServer) {
            serverSet.add(line);
        }

        for (String line : flist) {
            localSet.add(line);
        }

        serverSet.removeAll(localSet);
        if (serverSet.isEmpty()) {
        } else {
            int size = serverSet.size();
            append("\n Total " + size + " files remaining");
            int counter = 1;
            for (String s : serverSet) {
                append("\n Downloading File: " + counter + " of " + size);
                downloadFile(s, cdir + sep + "cache" + sep + "pv");
                counter++;
            }
        }
    }

    private String createConfigString(String clientIdMd5,
            String strCurrentDate, String scriptCode) {
        String configString = "";
        CommonTasks ct = new CommonTasks();
        String str = clientIdMd5 + ct.getSeparator() + strCurrentDate;
        String strEncoded = ct.encodeBase64(str);
        String urlSafe = ct.getBase64UrlSafe(strEncoded);
        configString = scriptCode + urlSafe;
        return configString;
    }

    private void login() {

        CommonTasks ct = new CommonTasks();
        String lid = tfLid.getText();
        String lpd = String.valueOf(tfLpd.getPassword());
        lpd = ct.getMd5(lpd);
        lid = ct.encodeBase64(lid);
        lpd = ct.encodeBase64(lpd);
        String ttime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        String url = CoolmarchConstants.COOLMARCH_SERVER + "/abscripts/auth.php?"
                + "lid=" + lid + "&"
                + "lpd=" + lpd + "&"
                + "ttime=" + ttime;
        String result = "";
        String line = "";

        try {
            URL lc = new URL(url);
            URLConnection urlc = lc.openConnection();
            urlc.setConnectTimeout(5000);
            InputStream is = urlc.getInputStream();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(is));

            while ((line = rd.readLine()) != null) {
                result = line;
            }

            is.close();
            rd.close();

        } catch (IOException ioe) {

        }

        if (result.compareTo("ok") == 0) {
            append("\n\t" + result + "\tLogin Successfull");
            parent.setVisible(false);
            parent.dispose();
            InterfaceMain2 im = new InterfaceMain2();
            im.setExtendedState(JFrame.MAXIMIZED_BOTH);
            im.setVisible(true);
            writePwdFile();

        } else {
            append("\n\t" + result);
        }

    }

    private void checkDbUpdate() {
        String q = "select max(ddate) from quote";
        String ddateMax = new GeneralDB().getSingleColumnData(q);
        System.out.println(ddateMax);
        long t = new Date().getTime();
        String surl = CoolmarchConstants.COOLMARCH_SERVER + "/abscripts/getqforupdate.php?dt=" + ddateMax + "&t=" + t;
        System.out.println(surl);
        ArrayList<String> rs = new ArrayList();
        try {
            File file = new File("rs.txt");
            URL url = new URL(surl);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#00")) {
                    append("\n\t..OK");
                    break;
                } else {
                    line = line.replaceAll("<br />", "");
                    bw.write(line);
                    bw.newLine();
                }
            }

            bw.close();
            br.close();

            updateDb(file.getAbsolutePath());

        } catch (Exception e) {

        }
    }

    private void updateDb(String filePath) {
        String query = "SYSCS_UTIL.SYSCS_IMPORT_TABLE (null, "
                + "'quote','" + filePath + "', ',' ,'%',null,0)";
        GeneralDB gdb = new GeneralDB();
        String msg = gdb.execute(query);
        if (msg.compareToIgnoreCase("ok") == 0) {
            append("\n\t..OK");
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

        scroller = new javax.swing.JScrollPane();
        taText2 = new javax.swing.JEditorPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        tfLid = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tfLpd = new javax.swing.JPasswordField();
        pnlLoginControl = new javax.swing.JPanel();
        btnLogin = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jCheckBox1 = new javax.swing.JCheckBox();
        cHyperLink1 = new acr.component.CHyperLink();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();

        scroller.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        scroller.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        taText2.setEditable(false);
        taText2.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        taText2.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        taText2.setForeground(new java.awt.Color(0, 0, 51));
        taText2.setDisabledTextColor(new java.awt.Color(0, 0, 51));
        taText2.setEnabled(false);
        scroller.setViewportView(taText2);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        jLabel1.setText("User ID");

        tfLid.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfLid.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfLid.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setText("Password");

        tfLpd.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tfLpd.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        tfLpd.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        pnlLoginControl.setBackground(new java.awt.Color(255, 242, 242));

        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        pnlLoginControl.add(btnLogin);

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        pnlLoginControl.add(jButton3);

        jCheckBox1.setSelected(true);
        jCheckBox1.setText("Save");
        jCheckBox1.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlLoginControl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jCheckBox1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(tfLid)
                            .addComponent(tfLpd, javax.swing.GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE))))
                .addContainerGap(52, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfLid, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(tfLpd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(pnlLoginControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        cHyperLink1.setBackground(new java.awt.Color(204, 204, 255));
        cHyperLink1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/facebook32.png"))); // NOI18N
        cHyperLink1.setText("<html><font face=arial size=3>Contact us on Facebook<br> (Click to find us)</font>");
        cHyperLink1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cHyperLink1MouseClicked(evt);
            }
        });

        jLabel3.setBackground(new java.awt.Color(204, 204, 255));
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/whatsapp24.png"))); // NOI18N
        jLabel3.setText("<html><font color=green size=3>Whatsapp</font><br>\n<font color=green size=3>+923411283456</html>");

        jLabel4.setBackground(new java.awt.Color(204, 204, 255));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/net/coolmarch/cmnew/res/email24.png"))); // NOI18N
        jLabel4.setText("<html><font color=pink size=3>Email</font><br>\n<font color=green size=3>coolmarch.net@gmail.com</html>");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cHyperLink1, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(scroller, javax.swing.GroupLayout.PREFERRED_SIZE, 616, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scroller, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cHyperLink1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(22, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cHyperLink1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cHyperLink1MouseClicked
        // TODO add your handling code here:
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new URI("http://www.facebook.com/coolmarch.net"));
            } catch (Exception e) {
                //System.out.println(e.getMessage());
            }
        }
    }//GEN-LAST:event_cHyperLink1MouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        // TODO add your handling code here:
        new Thread(() -> {
            login();
        }).start();

    }//GEN-LAST:event_btnLoginActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private acr.component.CHyperLink cHyperLink1;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel pnlLoginControl;
    private javax.swing.JScrollPane scroller;
    private javax.swing.JEditorPane taText2;
    private javax.swing.JTextField tfLid;
    private javax.swing.JPasswordField tfLpd;
    // End of variables declaration//GEN-END:variables

    private void checkPwdFile() {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String userDir = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");

        File file = new File(userDir + sep + "user.cms");
        if (!file.exists()) {

        } else {
            ArrayList<String> lineList = CommonTasks.getLinesFromFile(file.getAbsolutePath());
            if (lineList.isEmpty()) {

            } else {
                String uid = (String) lineList.get(0);
                String pwd = (String) lineList.get(1);
                this.tfLid.setText(uid);
                this.tfLpd.setText(pwd);
            }
        }

    }

    private void writePwdFile() {
        String userDir = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");
        File file = new File(userDir + sep + "user.cms");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            String id = this.tfLid.getText();
            String pwd = String.copyValueOf(this.tfLpd.getPassword());
            bw.write(id);
            bw.newLine();
            bw.write(pwd);
           bw.close();
        }catch(Exception e){
        
        }

    }
    
    private void checkAttr() {
        CommonIdentification ci = new CommonIdentification();
        boolean a = ci.authenticateCode(this.getClass().getSimpleName(), cid);
        if (!a) {
            JOptionPane.showMessageDialog(null, "System failure ||| PANIC Signal");
        }
    }
}
