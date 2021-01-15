package com.company;

import com.GUI.Configuration;
import com.GUI.ProgressChecker;
import com.LOKI97TopLevel.LOKI97Cipher;

import javax.swing.*;

public class ThreadCipher extends Thread{

    private final JProgressBar jProgressBar;
    private final JTable jtable;
    private final Configuration configuration;

    public ThreadCipher(String threadName, Configuration configuration, JProgressBar progressBar, JTable table){
        super(threadName);

        this.configuration = configuration;
        jProgressBar = progressBar;
        jtable = table;

        start();
    }

    public void run(){
        try {

            byte[] keyX = configuration.getKey();
            byte[] iv   = configuration.getIV();

            String modeName = configuration.getModeName();

            byte[] data;

            switch (configuration.getCipher())
            {
                case "encrypt":
                    data = LOKI97Cipher.encrypt(FileWorker.read(getName()), keyX, iv, modeName, new ProgressChecker(jProgressBar, jtable));
                    break;
                case "decrypt":
                    data = LOKI97Cipher.decrypt(FileWorker.read(getName()), keyX, iv, modeName, new ProgressChecker(jProgressBar, jtable));
                    break;
                default:
                    throw new Exception("Failed to " + configuration.getCipher());
            }

            FileWorker.write(getName(), data); // перезапишем
        }
        catch (Exception exp) {
            System.out.println("Exeption:\n" + exp.getMessage());
        }
    }


}

