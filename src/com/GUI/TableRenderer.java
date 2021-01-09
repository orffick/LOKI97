package com.GUI;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;

public class TableRenderer implements TableCellRenderer
{
    @Override
    public Component getTableCellRendererComponent(JTable jTable, Object o, boolean b, boolean b1, int i, int i1) {
        return (Component) o;
    }
}
