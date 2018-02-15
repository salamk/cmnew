/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.symboltree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import net.coolmarch.cmnew.common.Company;
import net.coolmarch.cmnew.common.CompanyList;
import net.coolmarch.cmnew.common.StringStringPair;

/**
 *
 * @author salam
 */
public class SectorInformation {

    private CompanyList clist;

    public SectorInformation() {
        clist = new CompanyList();
    }

    public ArrayList<String> getCategoryList() {
        ArrayList<String> categoryList = new ArrayList<>();
        //CompanyList clist = new CompanyList();
        ArrayList<Company> companiesList = clist.getCompaniesList();
        Set<String> set = new HashSet();
        for (int i = 0; i <= companiesList.size() - 1; i++) {
            Company company = (Company) companiesList.get(i);
            String sector = company.getCompanyCategory();
            set.add(sector);
        }

        for (String c : set) {
            categoryList.add(c);
        }

        return categoryList;
    }

    public ArrayList<StringStringPair> getSymbolList(String sector) {
        ArrayList<StringStringPair> sspList = new ArrayList<>();
        //CompanyList clist = new CompanyList();
        ArrayList<Company> companiesList = clist.getCompaniesList();
        for (int i = 0; i <= companiesList.size() - 1; i++) {
            Company company = (Company) companiesList.get(i);
            String sec = company.getCompanySector();
            if (sec.compareToIgnoreCase(sector) == 0) {
                String code = company.getCompanyCode();
                String cname = company.getCompanyName();
                StringStringPair ssp = new StringStringPair(code, cname);
                sspList.add(ssp);
            }
        }
        
        return sspList;

    }

    public ArrayList<String> getSectorList(String category) {
        ArrayList<String> sectorList = new ArrayList<>();
        ArrayList<Company> companiesList = clist.getCompaniesList();
        Set<String> set = new HashSet();
        for (int i = 0; i <= companiesList.size() - 1; i++) {
            Company company = (Company) companiesList.get(i);
            String cat = company.getCompanyCategory();
            if (cat.compareToIgnoreCase(category) == 0) {
                String sector = company.getCompanySector();
                set.add(sector);
            }
        }

        for (String c : set) {
            sectorList.add(c);
        }

        return sectorList;

    }

    public ArrayList<String> getSymbolList() {
        ArrayList<String> slist = new ArrayList<String>();
        //CompanyList clist = new CompanyList();
        ArrayList<Company> companiesList = clist.getCompaniesList();
        for (int i = 0; i <= companiesList.size() - 1; i++) {
            Company company = (Company) companiesList.get(i);
            String code = company.getCompanyCode();
            slist.add(code);
        }

        return slist;

    }

}
