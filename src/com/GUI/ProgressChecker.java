package com.GUI;

import javax.swing.*;

public class ProgressChecker
{
    private JProgressBar progressBar;
    private JTable table;

    public ProgressChecker(JProgressBar progressBar, JTable table){
        this.progressBar = progressBar;
        this.table = table;
    }

    public void setValue(int currentValue){
        progressBar.setValue(currentValue);

        update();
    }

    public void setSize(int size){
        progressBar.setMaximum(size);

    }

    private void update(){
        table.repaint();
    }
}
