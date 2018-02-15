/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import cmnew.CoolmarchConstants;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author salam
 */
public final class CommonTasks {

    private static final String sep = ":HXY:";
    private static final String server_main_url = CoolmarchConstants.COOLMARCH_SERVER + "/dorequest.php?cstr=";
    private static final String last_update_url = CoolmarchConstants.COOLMARCH_SERVER + "/lup.php";

    public CommonTasks() {

    }

    public static String getLast_update_url() {
        return last_update_url;
    }

    public String getSeparator() {
        return sep;
    }

    public String getServerMainUrl() {
        return server_main_url;
    }

    public String getMd5(String string) {
        String str = "";
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(string.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            str = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return str;
    }

    public String decodeBase64(String str) {
        byte[] decodedValue = Base64.getDecoder().decode(str);
        String originalString = "";
        for (int i = 0; i < decodedValue.length; i++) {
            originalString += (char) decodedValue[i];
        }
        return originalString;
    }

    public String encodeBase64(String str) {
        String encoded = Base64.getEncoder().encodeToString(str.getBytes());
        return encoded;
    }

    public String getBase64UrlSafe(String encodedString) {
        encodedString = encodedString.replaceAll("\\+", "-");
        encodedString = encodedString.replaceAll("/", "_");
        encodedString = encodedString.replaceAll("=", ".");
        return encodedString;
    }

    public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
        byte[] buf = new byte[bufferSize];
        int n = input.read(buf);
        while (n >= 0) {
            output.write(buf, 0, n);
            n = input.read(buf);
        }

        output.flush();
    }

    public static ArrayList<String> getLinesFromFile(String file) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            String line = "";
            while ((line = br.readLine()) != null) {
                lines.add(line);
            }

            br.close();

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
            JOptionPane.showMessageDialog(null, ioe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        return lines;
    }

    public static boolean isAlreadyFound(String fileName) {
        boolean found = false;
        File file = new File(fileName);
        Path p = file.toPath();

        if (file.exists()) {
            try {
                BasicFileAttributes attr = Files.readAttributes(p, BasicFileAttributes.class);
                FileTime ft = attr.lastModifiedTime();
                Long milli = ft.toMillis();
                // System.out.println(milli);

                String t = System.getProperty("LAST_UPDATE_TIME");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    Date d = sdf.parse("2016-06-02");
                    long millis = d.getTime();
                    //   System.out.println(millis);
                    if (milli > millis) {
                        found = true;
                    }
                } catch (ParseException pe) {

                }

            } catch (IOException ioe) {
                //  System.out.println(ioe.getMessage());
            }
        }

        return found;
    }

    public String getPulse(String item) {
        String str = "";
        String script = CoolmarchConstants.COOLMARCH_SERVER + "/dorequest.php?cstr=SPLUSytdsk&symbolcode=" + item;
        try {
            URL url = new URL(script);
            URLConnection uc = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                str = inputLine;
            }
            br.close();

        } catch (Exception e) {
            //  System.out.println(e.getMessage());
        }

        return str;
    }

    public String getPulses(String item) {
        String str = "";
        String script = CoolmarchConstants.COOLMARCH_SERVER + "/dorequest.php?cstr=MPLUSytdsk&symbolcode=" + item;
        try {
            URL url = new URL(script);
            URLConnection uc = url.openConnection();

            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String inputLine;
            while ((inputLine = br.readLine()) != null) {
                str = inputLine;
            }
            br.close();

        } catch (Exception e) {
            //  System.out.println(e.getMessage());
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public ArrayList<String> getPortfolioItems() {
        ArrayList<String> al = new ArrayList<>();
        File file = new File("portfolio.pfs");
        if (!file.exists()) {
            try {

                file.createNewFile();
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line = "";
                while ((line = br.readLine()) != null) {
                    if (line.length() < 5) {
                        ;
                    } else {
                        String[] toke = line.split("::");
                        String symbol = toke[0];
                        al.add(symbol);
                        //  System.out.println(symbol);
                    }
                }

                br.close();

            } catch (Exception e) {
                //  System.out.println(e.getMessage());
            }
        }

        return al;
    }

    public static void main(String[] args) {
        CommonTasks ct = new CommonTasks();
        String val = ct.getPulses("BYCO:PTC:ENGRO:KEL");
        // System.out.println(val);
    }

}
