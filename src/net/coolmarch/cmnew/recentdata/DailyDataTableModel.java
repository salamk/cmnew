/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.recentdata;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.coolmarch.cmnew.common.DailyData;

/**
 *
 * @author coolmarch
 */
public class DailyDataTableModel extends AbstractTableModel {

    private static final int COL_INDEX = 0;
    private static final int COL_SYMBOL = 1;
    private static final int COL_OPEN = 2;
    private static final int COL_HIGH = 3;
    private static final int COL_LOW = 4;
    private static final int COL_CLOSE = 5;
    private static final int COL_VOLUME = 6;
    private static final int COL_CHANGE = 7;
    private static final int COL_PCHANGE = 8;
    private static final int COL_MCAP = 9;

    private String[] columnNames = {"No", "Symbol", "Open", "High",
        "Low", "Close", "Volume", "Change", "% Change", "Avg Cap"};

    private ArrayList<DailyData> dailyDataList;

    public DailyDataTableModel(ArrayList<DailyData> dailyDataList) {
        this.dailyDataList = dailyDataList;

        int indexCount = 1;
        for (DailyData dailyData : dailyDataList) {
            dailyData.setIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return dailyDataList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (dailyDataList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DailyData dailyData = dailyDataList.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COL_INDEX:
                returnValue = dailyData.getIndex();
                break;
            case COL_SYMBOL:
                returnValue = dailyData.getSymbol();
                break;
            case COL_OPEN:
                returnValue = dailyData.getOpen();
                break;
            case COL_HIGH:
                returnValue = dailyData.getHigh();
                break;
            case COL_LOW:
                returnValue = dailyData.getLow();
                break;
            case COL_CLOSE:
                returnValue = dailyData.getClose();
                break;
            case COL_VOLUME:
                returnValue = dailyData.getVolume();
                break;
            case COL_MCAP:
                returnValue = dailyData.getMcap();
                break;
            case COL_CHANGE:
                returnValue = dailyData.getChange();
                break;
            case COL_PCHANGE:
                returnValue = dailyData.getPchange();
                break;
            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        DailyData dailyData = dailyDataList.get(rowIndex);
        if (columnIndex == COL_INDEX) {
            dailyData.setIndex((int) value);
        }
    }

}
