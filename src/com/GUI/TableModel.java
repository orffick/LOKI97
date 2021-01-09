package com.GUI;

import javax.swing.table.DefaultTableModel;

public class TableModel extends DefaultTableModel
{
    public TableModel (Object[][] data, String[] columnNames) {
        super (data, columnNames);
    }

    public Class <?> getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    public boolean isCellEditable(int row, int column){
        return false;
    }

}
