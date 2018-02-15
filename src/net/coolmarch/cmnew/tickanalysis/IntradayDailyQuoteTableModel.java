/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.coolmarch.cmnew.tickanalysis;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.coolmarch.cmnew.common.IntradayDailyQuote;

/**
 *
 * @author sania
 */
public class IntradayDailyQuoteTableModel extends AbstractTableModel {

    private static final int COLUMN_NO = 0;
    private static final int COLUMN_PRICE = 1;
    private static final int COLUMN_VOLUME = 2;


    private String[] columnNames = {"No", "Price", "Volume"};
    private ArrayList<IntradayDailyQuote> intradayQuoteList;

    public IntradayDailyQuoteTableModel(ArrayList<IntradayDailyQuote> intradayQuoteList) {
        this.intradayQuoteList = intradayQuoteList;

        int indexCount = 1;
        for (IntradayDailyQuote intradayQuote : intradayQuoteList) {
            intradayQuote.setIndex(indexCount++);
        }
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public int getRowCount() {
        return intradayQuoteList.size();
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (intradayQuoteList.isEmpty()) {
            return Object.class;
        }
        return getValueAt(0, columnIndex).getClass();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        IntradayDailyQuote dailyQuote = intradayQuoteList.get(rowIndex);
        Object returnValue = null;

        switch (columnIndex) {
            case COLUMN_NO:
                returnValue = dailyQuote.getIndex();
                break;
            case COLUMN_PRICE:
                returnValue = dailyQuote.getDailyQuotePrice();
                break;
            case COLUMN_VOLUME:
                returnValue = dailyQuote.getDailyQuoteVolume();
                break;

            default:
                throw new IllegalArgumentException("Invalid column index");
        }

        return returnValue;
    }

    @Override
    public void setValueAt(Object value, int rowIndex, int columnIndex) {
        IntradayDailyQuote dailyQuote = intradayQuoteList.get(rowIndex);
        if (columnIndex == COLUMN_NO) {
            dailyQuote.setIndex((int) value);
        }
    }

}
