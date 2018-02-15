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
import acr.component.CHyperLink;
import cmnew.InterfaceMain2;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.util.TreeMap;

/**
 * @author 6dc
 *
 * A class which creates a JTabbedPane and auto sets a close button when you add
 * a tab
 */
public class JTabbedPaneCloseButton extends JTabbedPane {

    private CHyperLink button;
    private static TreeMap<String, Integer> elementMap;

    public JTabbedPaneCloseButton() {
        super();
        elementMap = new TreeMap<>();
    }

    /* Override Addtab in order to add the close Button everytime */
    @Override
    public void addTab(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
        int count = this.getTabCount() - 1;
        setTabComponentAt(count, new CloseButtonTab(component, title, icon));
    }

    @Override
    public void addTab(String title, Icon icon, Component component) {
        addTab(title, icon, component, null);
    }

    @Override
    public void addTab(String title, Component component) {
        boolean alreadyOpened = checkAlreadyOpened(title);
        if (alreadyOpened) {
            return;
        } else {
            addTab(title, null, component);
            setSelectedComponent(component);
            int tabPosition = this.getSelectedIndex();
            elementMap.put(title, tabPosition);
        }
    }

    /* addTabNoExit */
    public void addTabNoExit(String title, Icon icon, Component component, String tip) {
        super.addTab(title, icon, component, tip);
    }

    public void addTabNoExit(String title, Icon icon, Component component) {
        addTabNoExit(title, icon, component, null);
    }

    public void addTabNoExit(String title, Component component) {
        addTabNoExit(title, null, component);
    }

    private boolean checkAlreadyOpened(String title) {
        boolean already_exists = false;
        for (String key : elementMap.keySet()) {
            if (key.compareTo(title) == 0) {
                already_exists = true;
                int index = elementMap.get(key);
                this.setSelectedIndex(index);
            }
        }

        return already_exists;

    }

    /* Button */
    public class CloseButtonTab extends JPanel {

        private Component tab;

        public CloseButtonTab(final Component tab, String title, Icon icon) {
            this.tab = tab;
            setOpaque(false);
            FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 3, 3);
            setLayout(flowLayout);
            JLabel jLabel = new JLabel(title);
            jLabel.setIcon(icon);
            add(jLabel);
            button = new CHyperLink();
            button.setText("x");
            // button.setMargin(new Insets(0, 0, 0, 0));
            button.addMouseListener(new CloseListener(tab, title));
            add(button);
        }

    }

    /* ClickListener */
    public class CloseListener implements MouseListener {

        private Component tab;
        private String title;

        public CloseListener(Component tab, String title) {
            this.tab = tab;
            this.title = title;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource() instanceof JLabel) {
                JLabel clickedButton = (JLabel) e.getSource();
                JTabbedPane tabbedPane = (JTabbedPane) clickedButton.getParent().getParent().getParent();
                tabbedPane.remove(tab);
                elementMap.remove(title);
                if (title.startsWith("T:")) {
                    String t = title.replaceAll("T:", "");
                    String regId = t+"-iddq";
                    InterfaceMain2.unregisterChart(regId);
                } else if(title.startsWith("D:")){
                    String t = title.replaceAll("D:", "");
                    String regId1 = t + "-cmain";
                    String regId2 = t + "-cpv";
                    String regId3 = t + "-cp10";
                    InterfaceMain2.unregisterChart(regId1);
                    InterfaceMain2.unregisterChart(regId2);
                    InterfaceMain2.unregisterChart(regId3);
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
//            if(e.getSource() instanceof JButton){
//                JButton clickedButton = (JButton) e.getSource();
//             //   clickedButton.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3));
//            }
            /// button.setBorder(BorderFactory.createLineBorder(Color.gray));
        }

        @Override
        public void mouseExited(MouseEvent e) {
//            if(e.getSource() instanceof JButton){
//                JButton clickedButton = (JButton) e.getSource();
//             //   clickedButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY,3));
//            }
            button.setBorder(null);
        }
    }
}
