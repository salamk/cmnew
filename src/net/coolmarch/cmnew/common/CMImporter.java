/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import cmnew.CoolmarchConstants;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import static net.coolmarch.cmnew.common.CommonTasks.copy;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

/**
 *
 * @author salam
 */
public class CMImporter {

    private ArrayList<String> lines = new ArrayList();

    public CMImporter(String fname) {
        String sep = System.getProperty("file.separator");
        lines.clear();
        String cdir = System.getProperty("user.dir");
        String path = cdir + sep + "cache";

        String fileName = path + sep + fname;

        boolean isAlreadyFound = isAlreadyFound(fileName);
        if (fname.startsWith("current")
                || fname.startsWith("exchange") || fname.startsWith("ticker")
                || fname.startsWith("rc") || fname.startsWith("fldata")) {
            isAlreadyFound = false;
        }
        if (isAlreadyFound) {
            try {
                CMExtractor cme = new CMExtractor(new ZipFile(fileName),
                        getContainingFileName(fname));

                lines = cme.getLines();

            } catch (ZipException ze) {

            }
        } else {

            boolean downloaded = downloadFile(fname);
            int retry = 1;
            while (downloaded == false) {
                downloaded = downloadFile(fname);
                if (retry % 3 == 0) {
                    break;
                }
            }
        }
    }

    private boolean downloadFile(String fileName) {
        boolean downloaded = false;
        // int counter = 1;
        String sep = System.getProperty("file.separator");
        CommonTasks ct = new CommonTasks();
        String url = "";
        String puck = System.getProperty("puck");
        long tt = new Date().getTime();
        url = CoolmarchConstants.COOLMARCH_SERVER+"/abscripts/getfile.php?puck=" + puck + "&fname=" + fileName + "&tt=" + tt;
        try {
            URL lc = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) lc.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(50000);
            InputStream in = connection.getInputStream();
            String cdir = System.getProperty("user.dir");
            String path = cdir + sep + "cache";

            FileOutputStream out = new FileOutputStream(path + sep + fileName);
            copy(in, out, 1024);
            out.close();
            // System.out.println("Download OK -/");
            downloaded = true;

            CMExtractor cme = new CMExtractor(new ZipFile(path + sep + fileName),
                    getContainingFileName(fileName));

            lines = cme.getLines();

        } catch (Exception e) {
            //  System.out.println(e.getMessage());
            e.printStackTrace();
            downloaded = false;
        }

        return downloaded;

    }

    public ArrayList<String> getLines() {
        return lines;
    }

    private String getContainingFileName(String zipFileName) {
        String containingFile = "";
        if (zipFileName.startsWith("P_")) {
            containingFile = zipFileName.replaceAll("P_", "");
            containingFile = containingFile.replaceAll(".zip", ".csv");
        } else if (zipFileName.startsWith("PV_")) {
            containingFile = zipFileName.replaceAll("PV_", "");
            containingFile = containingFile.replaceAll(".zip", ".csv");
        } else if (zipFileName.startsWith("D_")) {
            containingFile = zipFileName.replaceAll(".zip", ".csv");
        } else if (zipFileName.startsWith("static")) {
            containingFile = zipFileName.replaceAll(".zip", ".csv");
        } else if (zipFileName.startsWith("catscript")) {
            containingFile = "cat.csv";
        } else if (zipFileName.startsWith("ticker")) {
            containingFile = "ticker.txt";
        } else if (zipFileName.startsWith("pivotanalysis")) {
            containingFile = "pivotanalysis.csv";
        } else if (zipFileName.startsWith("rc")) {
            containingFile = "rc.txt";
        } else {
            containingFile = zipFileName.replaceAll(".zip", ".csv");
        }

        return containingFile;
    }

    private boolean isAlreadyFound(String fileName) {
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
                    Date d = sdf.parse(t);
                    long millis = d.getTime();
                    if (milli > millis) {
                        found = true;
                    }
                } catch (ParseException pe) {
                }

            } catch (IOException ioe) {
                // System.out.println(ioe.getMessage());
            }
        }

        return found;
    }

}
