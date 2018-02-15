/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.symboltree;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import net.coolmarch.cmnew.res.CMResource;

/**
 *
 * @author salam
 */
public class MyCellRenderer extends DefaultTreeCellRenderer {

    @Override
    public Color getBackgroundNonSelectionColor() {
        return (null);
    }

    @Override
    public Color getBackgroundSelectionColor() {
        return Color.GREEN;
    }

    @Override
    public Color getBackground() {
        return null;
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree,
            Object value, boolean selected, boolean expanded,
            boolean isLeaf, int row, boolean focused) {
        CMResource cmr = new CMResource();
        Component c = super.getTreeCellRendererComponent(tree, value,
                selected, expanded, isLeaf, row, focused);
        if (isLeaf) {
            setIcon(cmr.getIcon("simple16.png"));
        } else {
            setIcon(cmr.getIcon("pie16.png"));
        }
        return c;
    }
}
