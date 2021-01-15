package com.GUI;

import com.company.ThreadCipher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class Window extends JFrame {

    private JPanel mainPanel;
    private JMenuBar menuBar;

    private JTable table;

    private Map<String, Object[]> data;

    private Configuration configuration;

    private void createMenu(){

        menuBar = new JMenuBar();

        //File
        JMenu file = new JMenu("File");
        JMenuItem fileInput = new JMenuItem("Input");
        file.add(fileInput);
        fileInput.addActionListener(new InputActionListener());
        menuBar.add(file);

        //Configuration
        JMenu configuration = new JMenu("Configuration");
        JMenuItem edit = new JMenuItem("Edit");
        edit.addActionListener(new EditActionListener());
        configuration.add(edit);
        menuBar.add(configuration);

        //Run
        JMenu run = new JMenu("Run");
        JMenuItem start = new JMenuItem("Start");
        start.addActionListener(new StartActionListener());
        run.add(start);
        menuBar.add(run);

        setJMenuBar(menuBar);
    }

    public class EditActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

          new ConfigurationDialog(configuration);
        }
    }

    public class StartActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (data == null)
                return;

             for(String filePath : data.keySet()){

                    JProgressBar jp = (JProgressBar) data.get(filePath)[2];
                    jp.setValue(0);
                    new ThreadCipher(filePath, configuration, jp, table); // запускаем новый поток, в котором будет шифроваться файл
                }
        }
    }

    public class InputActionListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            JFileChooser jFileChooser = new JFileChooser(Configuration.getLastPath());
            jFileChooser.setMultiSelectionEnabled(true);
            jFileChooser.showOpenDialog(null);

            var temp = jFileChooser.getSelectedFiles();

            if (temp.length == 0)
                return;

            try {
                Configuration.setLastPathPath(temp[0].getPath());
            }
            catch (IOException ioException) {
                ioException.printStackTrace();
            }


            data = new HashMap<>();

            for (File file : temp) {
                data.put(file.getPath(), new Object[]{
                        file.getName(),
                        file.length(),
                        new JProgressBar()});
            }

            updateTable();
        }
    }

    private void updateTable(){
        new DisplayTable(data, table);
    }

    private void createWindow(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = 720;
        int height = 580;
        setSize(width, height);
        setLocation((screenSize.width - width) / 2, (screenSize.height - height) / 2);
        setTitle("LOKI97 Algorithm");
        setVisible(true);

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Window() {

        configuration = new Configuration();

        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table);

        add(scrollPane);

        createMenu();
        createWindow();
    }

}
