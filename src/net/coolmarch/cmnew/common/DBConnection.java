/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author salamk
 */
public class DBConnection {

    String url = "jdbc:derby:";
    String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    String user = "cmcache";
    String pwd = "SagDGEE80DXK9uz";

    public DBConnection() {

    }

    public Connection getConnection() {

        Connection con = null;
        String cd = System.getProperty("user.dir");
        String sep = System.getProperty("file.separator");
        url = url + cd + sep + "cmcache";

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException nfe) {
            System.out.println("\t>" + nfe.getMessage());
        }

        try {

            con = DriverManager.getConnection(url, user, pwd);

        } catch (SQLException sqle) {

        } catch (Exception e) {

        }

        return con;
    }

    public void closeConnection(Connection con) {

        try {
            con.close();
        } catch (Exception e) {

        }
    }

}
