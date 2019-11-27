package presentation;


import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;


public class MainView extends JFrame {
    private JPanel mainPanel;
    private JButton comprimirArchivoDeTextoButton;
    private JButton comprimirImagenButton;
    private JButton comprimirCarpetaButton;
    private JButton exitButton;
    private JButton mostrarEstadisticasGlobalesButton;
    private JButton welcomeButton;
    private JButton decompressButton;
    private JButton infoButton;
    private JPanel contentPanel;
    private JPanel buttonsPanel;
    private JPanel welcomePanel;
    private JTextArea aplicacionParaComprimirYTextArea;
    private CompressionView compressionView;
    private DescompressionView descompressionView;
    private PresentationCtrl presentationCtrl;
    private int activeButton;
    private Color deactivatedColor;
    private Color activeColor;


    private void initComponents(){
        activeButton = 0;
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        //setResizable(false);
        activeColor = new Color(79, 131, 249);
        deactivatedColor = new Color(53, 71, 120);

        setContentPane(mainPanel);
        this.setIconImage(img.getImage());


        //stadisticasButton.setEnabled(false);
//        compressButton.setEnabled(false);
//        decompressButton.setEnabled(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        compressionView = new CompressionView(getTitle(), this);
        descompressionView = new DescompressionView(getTitle(), this);



        setVisible(true);
    }


    private void addHoverEffect(JButton button, int index){
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(button.isEnabled())button.setBackground(activeColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(button.isEnabled() && activeButton != index) button.setBackground(deactivatedColor);
            }
        });
    }

    private void updateButtonsListeners(){
        addHoverEffect(welcomeButton, 0);
        addHoverEffect(comprimirArchivoDeTextoButton, 1);
        addHoverEffect(comprimirImagenButton, 2);
        addHoverEffect(comprimirCarpetaButton, 3);
        addHoverEffect(decompressButton, 4);
        addHoverEffect(infoButton, 5);
        addHoverEffect(mostrarEstadisticasGlobalesButton, 6);
    }


    private void initEventListeners(){

        updateButtonsListeners();






       /* comprimirButton.addActionListener(actionEvent -> {
            setVisible(false);
            compressionView.init();


            //abrir nueva ventana y cerrar esta
        });

        descomprimirButton.addActionListener(actionEvent -> {
            setVisible(false);
            descompressionView.init();


            //abrir nueva ventana y cerrar esta
        });*/




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
