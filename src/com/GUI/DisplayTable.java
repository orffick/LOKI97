package com.GUI;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.util.Map;

public class DisplayTable
{
    private final String[] columnNames = new String[]{"Name of file", "Size of file (bytes)", "Progress"};
    private Object[][] data;

    public DisplayTable(Map<String, Object[]> dataFiles, JTable table){

        data = new Object[dataFiles.size()][3];

        String[] keysOfDataFiles = dataFiles.keySet().toArray(new String[dataFiles.size()]);

        for(int i = 0; i < dataFiles.size(); i++){
            data[i] = dataFiles.get(keysOfDataFiles[i]);
        }

        TableModel tableModel = new TableModel(data, columnNames);

        TableRowSorter<TableModel> tableRowSorter = new TableRowSorter<>(tableModel);

        tableRowSorter.setComparator(2, (one, two) ->
                                    ((JProgressBar)one).getValue() - ((JProgressBar)two).getValue());
        table.setModel(tableModel);
        table.setAutoCreateRowSorter(true);
        table.setRowSorter(tableRowSorter);

        table.setDefaultRenderer(JProgressBar.class, new TableRenderer());
    }
}


