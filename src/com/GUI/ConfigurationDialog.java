package com.GUI;

import com.LOKI97TopLevel.Modes.CBCMode;
import com.LOKI97TopLevel.Modes.CFBMode;
import com.LOKI97TopLevel.Modes.ECBMode;
import com.LOKI97TopLevel.Modes.OFBMode;
import com.company.FileWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextLayout;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ConfigurationDialog extends JDialog {

    private final String keyError = "KEY ERROR";
    private final String ivError  = "IV ERROR";

    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField textField1;
    private JTextField textField2;
    private JRadioButton encryptRadioButton;
    private JRadioButton decryptRadioButton;
    private JRadioButton ECBRadioButton;
    private JRadioButton CBCRadioButton;
    private JRadioButton CFBRadioButton;
    private JRadioButton OFBRadioButton;
    private JButton downloadButton;
    private JButton downloadButton1;

    private Configuration configuration;

    public ConfigurationDialog(Configuration configuration) {
        this.configuration = configuration;

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        setTitle("Configuration");
        setMinimumSize(new Dimension(350, 300));
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width - getWidth()) / 2, (screenSize.height - getHeight()) / 2);

        setKey(new String(configuration.getKey()));
        setIV(new String(configuration.getIV()));
        setSelectCipher(configuration.getCipher());
        setSelectMode(configuration.getModeName());

        downloadButton.addActionListener(new DownloadKeyActionListener());
        downloadButton1.addActionListener(new DownloadIVActionListener());

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //==========================================================================================//


        pack();
        setVisible(true);
    }

    public class DownloadKeyActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir") + "/resources/keys/");
            jFileChooser.showOpenDialog(null);

            var temp = jFileChooser.getSelectedFile();
            byte[] key = FileWorker.read(temp.getPath());

            if(!checkKey(key)){
                ShowMessageError(key.length, keyError);

                return;
            }

            textField1.setText(new String(FileWorker.read(temp.getPath())));
        }
    }

    public class DownloadIVActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser(System.getProperty("user.dir") + "/resources/");
            jFileChooser.showOpenDialog(null);

            var temp = jFileChooser.getSelectedFile();
            byte[] iv = FileWorker.read(temp.getPath());

            if (!checkIV(iv)){
                ShowMessageError(iv.length, ivError);

                return;
            }

            textField2.setText(new String(iv));
        }
    }

    private void ShowMessageError(int count, String typeError){

        switch (typeError){
            case keyError:
                JOptionPane.showMessageDialog(contentPane, "The key can only be 16/24/32 bytes.\n" +
                        "Was introduced:" + count + " bytes.", typeError, JOptionPane.ERROR_MESSAGE); break;
            case ivError:
                JOptionPane.showMessageDialog(contentPane, "The initialization vector can only be 16 bytes.\n" +
                        "Was introduced:" + count + " bytes.", typeError, JOptionPane.ERROR_MESSAGE); break;
        }
    }

    private boolean checkIV(byte[] iv){

        return (iv.length == 16);
    }

    private boolean checkKey(byte[] key){

        return ((key.length == 16) || (key.length == 24) || (key.length == 32));
    }

    private void onOK() {

        byte[] key = textField1.getText().getBytes();

        if(!checkKey(key)){
            ShowMessageError(key.length, keyError);

            return;
        }

        byte[] iv = textField2.getText().getBytes();

        if (!checkIV(iv)){
            ShowMessageError(iv.length, ivError);

            return;
        }

        configuration.setKey(key);
        configuration.setIV(iv);
        configuration.setCipher(getCipher());
        configuration.setModName(getModeName());

        dispose();
    }

    private void onCancel() {
        dispose();
    }

    public void setKey(String key){
        textField1.setText(key);
    }

    public void setIV(String iv){
        textField2.setText(iv);
    }

    public String getCipher(){

        if(encryptRadioButton.isSelected())
            return "encrypt";

        if(decryptRadioButton.isSelected())
            return "decrypt";

        return "";
    }

    public String getModeName(){

        if (ECBRadioButton.isSelected())
            return "ECB";

        if (CBCRadioButton.isSelected())
            return "CBC";

        if (CFBRadioButton.isSelected())
            return "CFB";

        if (OFBRadioButton.isSelected())
            return "OFB";

        return "";
    }

    private void setSelectCipher(String cipher){

        switch (cipher) {
            case "encrypt": encryptRadioButton.setSelected(true); break;
            case "decrypt": decryptRadioButton.setSelected(true); break;
        }
    }

    private void setSelectMode(String cipher){

        switch (cipher) {
            case "ECB": ECBRadioButton.setSelected(true); break;
            case "CBC": CBCRadioButton.setSelected(true); break;
            case "CFB": CFBRadioButton.setSelected(true); break;
            case "OFB": OFBRadioButton.setSelected(true); break;
        }
    }
}
