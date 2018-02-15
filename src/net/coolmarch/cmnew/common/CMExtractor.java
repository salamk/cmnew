/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

/**
 *
 * @author salam
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class CMExtractor {

    private ArrayList<String> lines;
    private static final String zip_password = "TpbWvajk@Fy07L?o7GRkyQ&I323jxmds32$3#dsb";

    public CMExtractor(ZipFile zipFile, String fileName) {

        lines = new ArrayList<>();

        try {
            // If zip file is password protected then set the password
            if (zipFile.isEncrypted()) {
                zipFile.setPassword(getCode());
                // System.out.println("Passord applied");
                String cdir = System.getProperty("user.dir");
                String sep = System.getProperty("file.separator");
                String path = cdir + sep + "cache";

                zipFile.extractFile(fileName, path);

                File file = new File(path+sep + fileName);
                FileInputStream fis = new FileInputStream(file);
                BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                String line = "";
                while ((line = br.readLine()) != null) {
                    lines.add(line);
                    //System.out.println(line);
                }
                br.close();
                fis.close();
                file.delete();

                //  zipFile.getFile().delete();
            }
        } catch (ZipException ze) {
            // System.out.println(ze.getMessage());
        } catch (IOException ioe) {

        }

    }

    private String getCode() {
        String password = "";
        CommonTasks ct = new CommonTasks();
        String b64p = ct.encodeBase64(zip_password);
        password = ct.getMd5(b64p);
//        System.out.println("The P-Hash is: "+password);
        return password;
    }

    public ArrayList<String> getLines() {
        return lines;
    }

}
