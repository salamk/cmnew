/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.common;

import java.util.TreeMap;
import javax.swing.JOptionPane;

/**
 *
 * @author coolmarch
 */
public final class CommonIdentification {

    private static final String cid = "feJWPc7ha5eyZGNlUCOBe6muO2ZqRX";
    private static final TreeMap<String, String> cmap = new TreeMap<>();

    public CommonIdentification() {
        cmap.put("InterfaceMain", "yUP1pxC3y0sv4nkCrXHZCTWVqz4fZ6");
        cmap.put("InterfaceMain2", "yUP1pxC3y0sv4nkCrXHZCTWVqz4fZ6");
        cmap.put("PnlMainCharting", "ZrsZ7LlgjWBH1tnqDn9AKLjNq7sBNe");
        cmap.put("Splash", "pSSq3aeMD9BgbW43IPz8FYN7IFLhaT");
        cmap.put("LoginDialog", "w73RYmTR3j7XL24hJZcFFbZKot1sRc");
        cmap.put("CMResource", "yUP1pxC3y0UJDsv4nkCrXHZCqz4fZ6");
        cmap.put("Splash", "zeSnOLFlgZo2Mw5ylb0tpOVN5GtLFn");
        cmap.put("PnlAddWidgetItem", "yZZeoV49fhF7jZxJUNEKax26Z0uaWT");
        cmap.put("CMFile", "zeSnOLFlgZo2Mw5ylb0tpOVN5GtLFn");
        cmap.put("PnlChartView", "yZZeoV49fhF7jZxJUNEKax26Z0uaWT");
        cmap.put("CommonIdentification", "feJWPc7ha5eyZGNlUCOBe6muO2ZqRX");
    }

    public boolean authenticateCode(String cls, String cid) {
        boolean authenticate = false;
        String registeredCid = cmap.get(cls);
        if (registeredCid.compareTo(cid) == 0) {
            authenticate = true;
        }

        return authenticate;

    }

    private void checkAttr() {
        CommonIdentification ci = new CommonIdentification();
        boolean a = ci.authenticateCode(this.getClass().getSimpleName(), cid);
        if (!a) {
            JOptionPane.showMessageDialog(null, "System failure ||| PANIC Signal");
        }
    }

}
