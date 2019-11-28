package presentation;


import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MainView extends JFrame {
    // define view components
    private JPanel mainPanel;
    private JButton compressTextButton;
    private JButton compressImageButton;
    private JButton compressFolderButton;
    private JButton exitButton;
    private JButton showStatsButton;
    private JButton welcomeButton;
    private JButton decompressButton;
    private JButton infoButton;
    private JPanel contentPanel;
    private JPanel buttonsPanel;
    private JPanel welcomePanel;
    private JTextArea aboutText;
    private JPanel compressTextPanel;
    private JButton seleccionarArchivoButton;
    private JButton comprimirButton;
    private JPanel compressImagePanel;
    private JButton seleccionarArchivosButton;
    private JButton comprimirButton1;
    private JButton button1;
    private JPanel compressFolderPanel;
    private PresentationCtrl presentationCtrl;
    private int activeButton;

    // define color constants
    private static final Color DEACTIVATED_COLOR = new Color(53, 71, 120);
    private static final Color ACTIVE_COLOR = new Color(79, 131, 249);
    private static final Color ACTIVE_TEXT = new Color(255, 255, 255);
    private static final Color DEACTIVATED_TEXT = new Color(150, 178, 221);

    // define panel constants
    private static final int WELCOME_PI = 0;
    private static final int COMPRESS_TEXT_PI = 1;
    private static final int COMPRESS_IMAGE_PI = 2;
    private static final int COMPRESS_FOLDER_PI = 3; // where PI = panel index
    private static final int DECOMPRESS_PI = 4;
    private static final int INFO_PI = 5;
    private static final int STATS_PI = 6;




    private void initComponents(){
        activeButton = WELCOME_PI;
        ImageIcon img = new ImageIcon("/home/adrian/Escritorio/compressor/DOCS/compresion.png");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 500);
        //setResizable(false);


        setContentPane(mainPanel);
        this.setIconImage(img.getImage());


        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);



        // finally set view to visible
        setVisible(true);
    }


    private void addHoverEffect(JButton button, int index){
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(button.isEnabled())button.setBackground(ACTIVE_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(button.isEnabled() && activeButton != index) button.setBackground(DEACTIVATED_COLOR);
            }
        });
    }

    private void addOnClickEffect(JButton button, int panelIndex) {
        button.addActionListener(actionEvent -> {
            if(activeButton != panelIndex){
                hideCurrentPanel();
                activeButton = panelIndex;
                showCurrentPanel();
            }

        });
    }


    private void updateButtonsListeners(){

        // add hover effects
        addHoverEffect(welcomeButton, WELCOME_PI);
        addHoverEffect(compressTextButton, COMPRESS_TEXT_PI);
        addHoverEffect(compressImageButton, COMPRESS_IMAGE_PI);
        addHoverEffect(compressFolderButton, COMPRESS_FOLDER_PI);
        addHoverEffect(decompressButton, DECOMPRESS_PI);
        addHoverEffect(infoButton, INFO_PI);
        addHoverEffect(showStatsButton, STATS_PI);


        // add on click effects
        addOnClickEffect(welcomeButton, WELCOME_PI);
        addOnClickEffect(compressTextButton, COMPRESS_TEXT_PI);
        addOnClickEffect(compressImageButton, COMPRESS_IMAGE_PI);
    }

    private void showCurrentPanel() {
        if(activeButton == WELCOME_PI){
            welcomePanel.setVisible(true);
            activateButton(welcomeButton);
        }
        else if(activeButton == COMPRESS_TEXT_PI){
            compressTextPanel.setVisible(true);
            activateButton(compressTextButton);
        }
        else if(activeButton == COMPRESS_IMAGE_PI){
            compressImagePanel.setVisible(true);
            activateButton(compressImageButton);
        }
    }

    private void activateButton(JButton button) {
        button.setForeground(ACTIVE_TEXT);
    }


    private void hideCurrentPanel() {
        if(activeButton == WELCOME_PI){
            welcomePanel.setVisible(false);
            deactivateButton(welcomeButton);
        }
        else if(activeButton == COMPRESS_TEXT_PI){
            compressTextPanel.setVisible(false);
            deactivateButton(compressTextButton);
        }
        else if(activeButton == COMPRESS_IMAGE_PI){
            compressImagePanel.setVisible(false);
            deactivateButton(compressImageButton);
        }

    }

    private void deactivateButton(JButton button) {
        button.setForeground(DEACTIVATED_TEXT);
        button.setBackground(DEACTIVATED_COLOR);
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
