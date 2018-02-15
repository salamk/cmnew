/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.util.ArrayList;

/**
 *
 * @author salam
 */
public class CompanyList {

    private static final ArrayList<Company> companiesList = new ArrayList<>();

    public CompanyList() {
        CMFile cmf = new CMFile("cat.csv");
        ArrayList<String> lines = cmf.getLines();

        for (String s : lines) {
            String[] toke = s.split(",");
            String ccode = toke[0];
            String cname = toke[1];
            String csector = toke[2];
            String ccategory = toke[3];
            String cshortName = toke[4];

            Company comp = new Company();
            comp.setCompanyCategory(ccategory);
            comp.setCompanyCode(ccode);
            comp.setCompanyName(cname);
            comp.setCompanySector(csector);
            comp.setCompanyShortName(cshortName);

            companiesList.add(comp);

        }

    }

    public static ArrayList<Company> getCompaniesList() {
        return companiesList;
    }


}
