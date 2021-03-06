/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.chartwidget;

import acr.component.CHyperLink;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import net.coolmarch.cmnew.common.CommonIdentification;
import net.coolmarch.cmnew.res.CMResource;

/**
 *
 * @author salam
 */
public final class PnlChartView extends javax.swing.JPanel {

    /**
     * Creates new form PnlChartView
     */
    
    private final String cid = "yZZeoV49fhF7jZxJUNEKax26Z0uaWT";
    public PnlChartView() {
        checkAttr();
        initComponents();
        chReload.setIcon(new CMResource().getIcon("reload.png"));
        pnl.setLayout(new GridLayout(3, 3));

        for (int i = 0; i <= 9 - 1; i++) {
            addPanel(new JPanel());
        }

    }

    private void addPanel(JPanel p) {
        p.setLayout(new BorderLayout());
        pnl.add(p);
        p.setBorder(BorderFactory.createLineBorder(Color.gray));
        CMResource cmr = new CMResource();
        JToolBar tb = new JToolBar();
        CHyperLink chChange = new CHyperLink();
        chChange.setText(" ");
        chChange.setIcon(cmr.getIcon("widget16.png"));
        CHyperLink chEnlarge = new CHyperLink();
        chEnlarge.setText(" ");
        chEnlarge.setIcon(cmr.getIcon("detach16.png"));
        JTextField tf = new JTextField();
        tf.setEnabled(false);
        tf.setEditable(false);
        tf.setDisabledTextColor(Color.blue);
        tb.add(chChange);
        tb.add(chEnlarge);
        tb.add(tf);
        p.add(tb, BorderLayout.SOUTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        p.add(contentPanel);

        chChange.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JDialog dlg = new JDialog();
                dlg.setModal(true);
                PnlAddWidgetItem pi = new PnlAddWidgetItem(contentPanel, tf, dlg);
                dlg.add(pi);
                dlg.setSize(pi.getPreferredSize());
                dlg.setLocationRelativeTo(null);
                dlg.setVisible(true);
            }
        });

        chEnlarge.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                JFrame frame = new JFrame();
                frame.getContentPane().add(contentPanel);
                frame.setSize(500, 300);
                frame.setLocationRelativeTo(null);
                p.update(p.getGraphics());
                p.validate();

                JToolBar toolBar = new JToolBar();
                JButton btnAttach = new JButton("Attach");
                JButton btnDesign = new JButton("Design-Mode");

                toolBar.add(btnAttach);
                toolBar.add(btnDesign);
                frame.add(toolBar, BorderLayout.NORTH);

                btnAttach.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        p.add(contentPanel);
                        p.update(p.getGraphics());
                        p.validate();
                        frame.setVisible(false);
                        frame.dispose();
                    }
                });

                frame.setVisible(true);
            }
        });

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnl = new javax.swing.JPanel();
        jToolBar1 = new javax.swing.JToolBar();
        chReload = new acr.component.CHyperLink();

        setLayout(new java.awt.BorderLayout());

        pnl.setLayout(new java.awt.GridLayout(4, 4, 2, 2));
        add(pnl, java.awt.BorderLayout.CENTER);

        jToolBar1.setBackground(new java.awt.Color(0, 153, 153));
        jToolBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jToolBar1.setRollover(true);

        chReload.setForeground(new java.awt.Color(255, 255, 255));
        chReload.setText("Reload");
        chReload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                chReloadMouseExited(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                chReloadMouseEntered(evt);
            }
        });
        jToolBar1.add(chReload);

        add(jToolBar1, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    private void chReloadMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chReloadMouseEntered
        // TODO add your handling code here:
        chReload.setForeground(Color.white);
    }//GEN-LAST:event_chReloadMouseEntered

    private void chReloadMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chReloadMouseExited
        // TODO add your handling code here:
        chReload.setForeground(Color.white);
    }//GEN-LAST:event_chReloadMouseExited


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private acr.component.CHyperLink chReload;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel pnl;
    // End of variables declaration//GEN-END:variables
        private void checkAttr() {
        CommonIdentification ci = new CommonIdentification();
        boolean a = ci.authenticateCode(this.getClass().getSimpleName(), cid);
        if (!a) {
            JOptionPane.showMessageDialog(null, "System failure ||| PANIC Signal");
        }
    }



}
