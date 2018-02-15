/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

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
import java.util.Date;
import static net.coolmarch.cmnew.common.CommonTasks.copy;

/**
 *
 * @author salam
 */
public class CMDownloader {

    private static final String clientId = "ytkH43ewFBcTexQazmn98HCDswel07l1";

    public CMDownloader() {
    }

    public boolean downloadFile(String fileName) {
        boolean ok = false;

        if (isAlreadyFound(fileName)) {
            return true;
        }

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

            FileOutputStream out = new FileOutputStream(path + sep + fileName);
            copy(in, out, 1024);
            out.close();
            //   System.out.println("Download OK -/");
            ok = true;

        } catch (Exception e) {
            // System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return ok;
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
        } else {
            containingFile = zipFileName.replaceAll(".zip", ".csv");
        }

        return containingFile;
    }

    private boolean isAlreadyFound(String fileName) {
        boolean found = false;
        String sep = System.getProperty("file.separator");
        String cdir = System.getProperty("user.dir");
        String path = cdir + sep + "cache";

        File file = new File(path + sep + fileName);
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
                    // System.out.println(millis);
                    if (milli > millis) {
                        found = true;
                    }
                } catch (ParseException pe) {
                    // System.out.println(pe.getMessage());
                }

            } catch (IOException ioe) {
                //  System.out.println(ioe.getMessage());
            }
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

}
