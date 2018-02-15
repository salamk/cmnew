/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.jfree.data.xy.OHLCDataItem;

/**
 *
 * @author coolmarch
 */
public class CImporter {

    private String symbol;
    private ArrayList<OHLCDataItem> ohlcList;
    public static final int CACHE_SERVER_SUPPLIMENT = 0;
    private String password = "TpbWvajk@Fy07L?o7GRkyQ&I323jxmds32$3#dsb";

    public CImporter(String symbol, int method) {
        symbol = symbol.toUpperCase();
        ohlcList = new ArrayList<>();
        this.symbol = symbol;
        if (method == CACHE_SERVER_SUPPLIMENT) {
            try {
                doCacheServerSupplimentAction();
            } catch (Exception e) {
            }
        }

    }

    private void doCacheServerSupplimentAction() throws Exception {
        ohlcList.clear();
        symbol = symbol.toUpperCase();
        CMFile cmf = new CMFile(symbol+".txt");
        ArrayList<String> lines = cmf.getLines();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (String line : lines) {
            line = line.replaceAll("<br />", "");
            String[] toke = line.split(",");
            Date d = sdf.parse(toke[0]);
            double open = Double.parseDouble(toke[1]);
            double high = Double.parseDouble(toke[2]);
            double low = Double.parseDouble(toke[3]);
            double close = Double.parseDouble(toke[4]);
            double volume = Double.parseDouble(toke[5]);

            OHLCDataItem item = new OHLCDataItem(d, open, high, low, close, volume);
            ohlcList.add(item);
        }

        String cdir = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");
        String path_01 = cdir+sep+"cache"+sep;
        System.out.println("INSIDE ---00001");
        ZipFile zipFile = new ZipFile(path_01 + symbol + ".cms.zip");
        String fileName = symbol + ".cms";
        try {
            // If zip file is password protected then set the password
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(password);
                // System.out.println("Passord applied");
                
                String path = cdir+sep+"cache";
                zipFile.extractFile(fileName, path);

                File file = new File(path + sep + fileName);
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = "";
                while ((line = br.readLine()) != null) {
                    String[] toke = line.split(",");
                    String dt = toke[0];
                    double open = Double.parseDouble(toke[1]);
                    double high = Double.parseDouble(toke[2]);
                    double low = Double.parseDouble(toke[3]);
                    double close = Double.parseDouble(toke[4]);
                    double volume = Double.parseDouble(toke[5]);
                    Date d = sdf.parse(dt);

                    OHLCDataItem item = new OHLCDataItem(d, open, high, low, close, volume);
                    ohlcList.add(item);

                }
                br.close();
                fis.close();
                file.delete();

                //  zipFile.getFile().delete();
            }
        } catch (ZipException ze) {
            System.out.println(ze.getMessage());
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        Collections.sort(ohlcList, new Comparator<OHLCDataItem>() {
            public int compare(OHLCDataItem o1, OHLCDataItem o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });
        
        Collections.reverse(ohlcList);

    }

    public ArrayList<OHLCDataItem> getOhlcCollection() {
        return ohlcList;
    }

}
