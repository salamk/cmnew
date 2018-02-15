/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.daemon;

import java.util.ArrayList;
import net.coolmarch.cmnew.common.CMImporter;
import net.coolmarch.cmnew.common.ExchangeStatus;

/**
 *
 * @author salam
 */
public class ExchangeInformationDaemon implements Runnable {

    public static ExchangeStatus es;

    public void run() {
        while (true) {
            CMImporter cmi = new CMImporter("exchange.zip");
            ArrayList<String> lines = cmi.getLines();
            es = new ExchangeStatus(lines);
            try {

                Thread.sleep(50000);

            } catch (InterruptedException e) {

            }
        }
    }

}
