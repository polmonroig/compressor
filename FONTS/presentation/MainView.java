package presentation;


import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainView extends JFrame {
    private JPanel mainPanel;
    private JButton exitButton;
    private JButton comprimirButton;
    private JButton descomprimirButton;
    private JButton estadisticasButton;
    private CompressionView compressionView;
    private DescompressionView descompressionView;
    private PresentationCtrl presentationCtrl;


    private void initComponents(){
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(375, 150);
        setResizable(false);
        setContentPane(mainPanel);
        this.setIconImage(img.getImage());

        estadisticasButton.setEnabled(false);
//        compressButton.setEnabled(false);
//        decompressButton.setEnabled(false);
        setVisible(true);
        compressionView = new CompressionView(getTitle(), this);
        descompressionView = new DescompressionView(getTitle(), this);

    }

    private void initEventListeners(){


        comprimirButton.addActionListener(actionEvent -> {
            setVisible(false);
            compressionView.init();


            //abrir nueva ventana y cerrar esta
        });

        descomprimirButton.addActionListener(actionEvent -> {
            setVisible(false);
            descompressionView.init();


            //abrir nueva ventana y cerrar esta
        });




        // select files button
/*        selectFilesButton.addActionListener(new ActionListener() {
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
*/

        // exit app button
        exitButton.addActionListener(e -> System.exit(0));
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
