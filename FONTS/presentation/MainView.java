package presentation;


import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainView extends JFrame {
    private JButton selectFilesButton;
    private JPanel mainPanel;
    private JButton exitButton;
    private JButton compressButton;
    private JButton decompressButton;
    private JButton selectCompressedFile;
    private PresentationCtrl presentationCtrl;
    private JFileChooser jfc;

    private void initComponents(){
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setContentPane(mainPanel);
        this.setIconImage(img.getImage());
        jfc = new JFileChooser();
        compressButton.setEnabled(false);
        decompressButton.setEnabled(false);
        setVisible(true);
    }

    private void initEventListeners(){

        // select files button
        selectFilesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jfc.setMultiSelectionEnabled(true);
                jfc.setAcceptAllFileFilterUsed(false);

                FileNameExtensionFilter filter = new FileNameExtensionFilter("PPM or TXT, ","ppm", "txt" );
                jfc.addChoosableFileFilter(filter);
                jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                int returnValue = jfc.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    presentationCtrl.setFiles(jfc.getSelectedFiles());
                    compressButton.setEnabled(true);
                }
            }
        });


        selectCompressedFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jfc.setAcceptAllFileFilterUsed(false);

                FileNameExtensionFilter filter = new FileNameExtensionFilter("MC",  "mc" );
                jfc.addChoosableFileFilter(filter);
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int returnValue = jfc.showOpenDialog(null);
                if(returnValue == JFileChooser.APPROVE_OPTION){
                    presentationCtrl.setFile(jfc.getSelectedFile());
                    decompressButton.setEnabled(true);
                }
            }
        });

        compressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    compressButton.setEnabled(false);
                    presentationCtrl.compressFiles();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        decompressButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                decompressButton.setEnabled(false);
                presentationCtrl.decompressFile();
            }
        });


        // exit app button
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }


    public void init(){
        initComponents();
        initEventListeners();


    }


    public MainView(String title, PresentationCtrl controller){
        setTitle(title);
        presentationCtrl = controller;
    }
}
