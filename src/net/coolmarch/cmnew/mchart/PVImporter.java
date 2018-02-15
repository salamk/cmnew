/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.mchart;

import cmnew.CoolmarchConstants;
import java.io.BufferedReader;
import net.coolmarch.cmnew.common.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import static net.coolmarch.cmnew.common.CommonTasks.copy;
import net.lingala.zip4j.core.ZipFile;

/**
 *
 * @author salam
 */
public class PVImporter {

    private static final String clientId = "ytkH43ewFBcTexQazmn98HCDswel07l1";
    private ArrayList<String> lines;

    public PVImporter() {

    }

    public PVImporter(String symbol, int days) {
        updateCache();
    }

    public ArrayList<TickData> getData(String symbol, int days) {
        String sep = System.getProperty("file.separator");
        ArrayList<TickData> al = new ArrayList<>();
        ArrayList<Date> flist = this.getAvailaibleDates();
        Collections.sort(flist);
        Collections.reverse(flist);
        // System.out.println("Size availiable" + flist.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String[] arch = new String[days];
        for (int i = 0; i <= days - 1; i++) {
            Date d = flist.get(i);
            String str = sdf.format(d);

            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            String archName = path + sep + "pv" + sep + "pv_" + str + ".zip";
            arch[i] = archName;
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> alines = getLines(arch, symbol);
        // System.out.println("Size of list is: " + alines.size());

        for (String line : alines) {
            TickData td = new TickData();
            String[] toke = line.split(",");
            if (toke.length == 3) {
                String dt = toke[0];
                Date d = new Date();
                try {
                    d = sdf.parse(dt);
                } catch (Exception e) {
                    //  System.out.println(e.getMessage());
                }

                String pr = toke[1];
                String vl = toke[2];
                double price = Double.parseDouble(pr);
                int volume = Integer.parseInt(vl);
                td.setDailyQuotePrice(price);
                td.setDailyQuoteVolume(volume);
                td.setQuoteDate(d);
                al.add(td);
            }
        }

        sort(al);

        return al;
    }

    public ArrayList<TickData> getStaticData(int days) {
        String symbol = "static";
        String sep = System.getProperty("file.separator");
        ArrayList<TickData> al = new ArrayList<>();
        ArrayList<Date> flist = this.getAvailaibleDates();
        Collections.sort(flist);
        Collections.reverse(flist);
        // System.out.println("Size availiable" + flist.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String[] arch = new String[days];
        for (int i = 0; i <= days - 1; i++) {
            Date d = flist.get(i);
            String str = sdf.format(d);
            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            String archName = path + sep + "pv" + sep + "pv_" + str + ".zip";
            arch[i] = archName;
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> alines = getLines(arch, symbol);
        System.out.println("tttSize of list is: " + alines.size());

        for (String line : alines) {
            TickData td = new TickData();
            String[] toke = line.split(",");
            if (toke.length == 3) {
                String dt = toke[0];
                Date d = new Date();
                try {
                    d = sdf.parse(dt);
                } catch (Exception e) {
                    //  System.out.println(e.getMessage());
                }

                String pr = toke[1];
                //String vl = toke[2];
                double price = Double.parseDouble(pr);
                //int volume = Integer.parseInt(vl);
                td.setDailyQuotePrice(price);
                td.setDailyQuoteVolume(0);
                td.setQuoteDate(d);
                al.add(td);
            }
        }

        sort(al);
        System.out.println("The allsize is: " + al.size());

        return al;
    }

    private void sort(ArrayList<TickData> al) {
        Collections.sort(al, new Comparator<TickData>() {
            public int compare(TickData o1, TickData o2) {
                return o1.getQuoteDate().compareTo(o2.getQuoteDate());
            }
        });
    }

    public ArrayList<TickData> getPVStaticData(int days) {
        String sep = System.getProperty("file.separator");
        ArrayList<TickData> al = new ArrayList<>();
        ArrayList<Date> flist = this.getAvailaibleDates();
        Collections.sort(flist);
        Collections.reverse(flist);
        //  System.out.println("Size availiable" + flist.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String[] arch = new String[days];
        for (int i = 0; i <= days - 1; i++) {
            Date d = flist.get(i);
            String str = sdf.format(d);
            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            String archName = path + sep + "pv" + sep + "pv_" + str + ".zip";
            arch[i] = archName;
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> alines = getLines(arch, "static");
        // System.out.println("Size of list is: " + alines.size());

        for (String line : alines) {
            TickData td = new TickData();
            String[] toke = line.split(",");
            String dt = toke[0];
            Date d = new Date();
            try {
                d = sdf.parse(dt);
                String pr = toke[1];
                String vl = toke[2];
                // System.out.println(pr + "-" + vl);
                double price = Double.parseDouble(pr);
                int volume = (int) Double.parseDouble(vl);
                td.setDailyQuotePrice(price);
                td.setDailyQuoteVolume(0);
                td.setQuoteDate(d);

                al.add(td);

            } catch (Exception e) {
                //  System.out.println(e.getMessage());
            }

        }
        sort(al);
        return al;
    }

    public ArrayList<TickData> getPData(String symbol, int days) {
        String sep = System.getProperty("file.separator");
        ArrayList<TickData> al = new ArrayList<>();
        ArrayList<Date> flist = this.getAvailaibleDates();
        Collections.sort(flist);
        Collections.reverse(flist);
        // System.out.println("Size availiable" + flist.size());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String[] arch = new String[days];
        for (int i = 0; i <= days - 1; i++) {
            Date d = flist.get(i);
            String str = sdf.format(d);
            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            String archName = path + sep + "pv" + sep + "pv_" + str + ".zip";
            arch[i] = archName;
        }

        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ArrayList<String> alines = getLines(arch, symbol);
        System.out.println("Size of list is: " + alines.size());
        double tp = 0;
        int vol = 0;
        for (String line : alines) {
            TickData td = new TickData();
            String[] toke = line.split(",");
            String dt = toke[0];
            Date d = new Date();
            try {
                d = sdf.parse(dt);
            } catch (Exception e) {
                //   System.out.println(e.getMessage());
            }

            String pr = toke[1];
            String vl = toke[2];
            int volume = Integer.parseInt(vl);
            double price = Double.parseDouble(pr);
            if (price == tp) {
                ;
            } else {
                td.setDailyQuotePrice(price);
                td.setDailyQuoteVolume(volume);
                td.setQuoteDate(d);
                al.add(td);
                tp = price;
            }

        }
        sort(al);
        return al;
    }

    private void downloadFile(String fileName) {
        String sep = System.getProperty("file.separator");
        CommonTasks ct = new CommonTasks();
        String clientIdMd5 = ct.getMd5(clientId);

        SimpleDateFormat sdfhms = new SimpleDateFormat("yyyyMMddHHmmss");
        String strCurrentDate = sdfhms.format(new Date());
        String scriptCode = "";
        scriptCode = "DTD";
        String configString = createConfigString(clientIdMd5, strCurrentDate, scriptCode);
        String url = "";
        url = ct.getServerMainUrl() + configString + "&fname=" + fileName;
        try {
            URL lc = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) lc.openConnection();
            connection.setRequestMethod("GET");
            InputStream in = connection.getInputStream();
            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            FileOutputStream out = new FileOutputStream(path + sep + "pv" + sep + fileName);
            copy(in, out, 1024);
            out.close();
            //  System.out.println("Download OK -/");
        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public ArrayList<String> getLines() {
        return lines;
    }

    private boolean isAlreadyFound(String fileName) {
        boolean found = false;
        File file = new File(fileName);
        Path p = file.toPath();

        if (file.exists()) {
            found = true;
        }

        return found;
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

    private ArrayList<String> getLines(String[] archive, String symbol) {
        ArrayList<String> allLines = new ArrayList<String>();
        for (int i = 0; i <= archive.length - 1; i++) {
            try {
                CMExtractor cme = new CMExtractor(new ZipFile(archive[i]), symbol + ".csv");
                ArrayList<String> lineList = cme.getLines();
                for (String line : lineList) {
                    allLines.add(line);
                }
            } catch (Exception e) {
                //   System.out.println(e.getMessage());
            }
        }

        return allLines;
    }

    private ArrayList<Date> getAvailaibleDates() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        ArrayList<Date> flist = new ArrayList<>();
        String sep = System.getProperty("file.separator");
        String cdir = System.getProperty("user.dir");
        String path = cdir + sep + "cache";

        File folder = new File(path + sep + "pv");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String n = listOfFiles[i].getName();
                n = n.replaceAll(".zip", "");
                n = n.replaceAll("pv_", "");
                //  System.out.println("n is " + n);
                try {
                    Date d = sdf.parse(n);
                    flist.add(d);
                } catch (Exception e) {

                }

            } else if (listOfFiles[i].isDirectory()) {
                // System.out.println("Directory " + listOfFiles[i].getName());
            }
        }

        return flist;
    }

    public void updateCache() {
        ArrayList<String> fileOnServer = new ArrayList<>();
        try {
            URL url = new URL(CoolmarchConstants.COOLMARCH_SERVER+"/pvcachelist.php");
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
            // System.out.println(e.getMessage());
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<String> flist = new ArrayList<>();
        String sep = System.getProperty("file.separator");
        String cdir = System.getProperty("user.dir");
        String path = cdir + sep + "cache";

        File folder = new File(path + sep + "pv");
        if (!folder.exists()) {
            folder.mkdirs();
        }

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                String n = listOfFiles[i].getName();
                flist.add(n);
            } else if (listOfFiles[i].isDirectory()) {
                //  System.out.println("Directory " + listOfFiles[i].getName());
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
            // System.out.println("All data is uptodate");
        } else {
            for (String s : serverSet) {
                downloadFile(s);
            }
        }
    }

}
