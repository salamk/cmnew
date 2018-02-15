/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author salam
 */
public class CMDLImporter {

    String url = "jdbc:derby:";
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String user = "cmcache";
    String pwd = "SagDGEE80DXK9uz";

    private ArrayList<Quote> quoteList = new ArrayList();

    public CMDLImporter(String symbol) {
        Connection con = null;
        Statement stmt = null;

        try {
            String cd = System.getProperty("user.dir");
            String sep = System.getProperty("file.separator");
            url = url + cd + sep + "cmcache";
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pwd);
            stmt = con.createStatement();
            String sql = "select ddate, dopen, dhigh, dlow, dclose, dvolume from "
                    + "quote where dsymbol like '" + symbol + "' order by ddate desc";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Date dt = rs.getDate("ddate");
                double open = rs.getDouble("dopen");
                double high = rs.getDouble("dhigh");
                double low = rs.getDouble("dlow");
                double close = rs.getDouble("dclose");
                double volume = rs.getDouble("dvolume");

                Quote q = new Quote();
                q.setClose(close);
                q.setDate(dt);
                q.setHigh(high);
                q.setLow(low);
                q.setOpen(open);
                q.setSymbol(symbol);
                q.setVolume(volume);

                quoteList.add(q);
            }
            
            con.close();
            stmt.close();

        } catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            sqle.printStackTrace();
        } catch (ClassNotFoundException cnf) {
            System.out.println(cnf.getMessage());
            cnf.printStackTrace();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
            }// do nothing
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try

        }

    }

    public ArrayList<Quote> getQuoteList() {
        return quoteList;
    }

}
