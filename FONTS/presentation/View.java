package presentation;

import presentation.controllers.PresentationCtrl;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class View extends JFrame {
    File file = new File("folder_icon.png");
    String path = file.getAbsolutePath();
    ImageIcon img = new ImageIcon(path);
    LocalStats localStats = new LocalStats();

    // set reference to the controller calling it
    private PresentationCtrl presentationCtrl;

    // a view is made of a buttons panel and a content panel
    private JSplitPane separator;
    private ButtonsPanel buttonsPanel;
    private ContentPanel contentPanel;

    // Error message panel
    private ContentInterface errorContent;

    // define the layout
    private GridLayout layout;
    private static final String[] buttonNames = {"About", "Comprimir texto", "Comprimir imagen",
                                                 "Comprimir carpeta", "Descomprimir", "Comparar",
                                                 "Estadisticas", "Informacion"};




    public View(String title, PresentationCtrl controller){
        setTitle(title);
        setIconImage(img.getImage());
        presentationCtrl = controller;
        layout = new GridLayout(1, 1);
        buttonsPanel = new ButtonsPanel(buttonNames, this);



        contentPanel = new ContentPanel(setupContents(), this);
        // init separator

        separator = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                buttonsPanel, contentPanel);

    }



    private ArrayList<ContentInterface> setupContents() {
        ArrayList<ContentInterface> contents = new ArrayList<>();
        // about content
        contents.add(setupAboutContent());
        // compress text content
        contents.add(setupCompressTextContent());
        // compress image content
        contents.add(setupCompressImageContent());
        // compress folder content
        contents.add(setupFolderContent());
        // decompress file
        contents.add(setupDecompressContent());
        // compare files
        contents.add(setupCompareContent());
        // global stats
        contents.add(setupGlobalStatsContent());
        // Info
        contents.add(setupInfoContent());
        return contents;
    }

    public void displayError(String message){
        JFrame errorFrame = new JFrame();
        errorFrame.setSize(new Dimension(200, 200));
        errorFrame.setVisible(true);
        errorContent = new Content("Error", message);
        errorContent = new ExitButton(errorContent, "Aceptar");
        errorContent.setVisibility(true);
        errorFrame.add(errorContent);


    }


    public void init(){
        initComponents();
    }

    private void initComponents() {
        // setup layout variables
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 425);
        setLayout(layout);

        // init components
        buttonsPanel.init();
        buttonsPanel.setEnabledButton(6, false);
        contentPanel.init();
        add(separator);


        // set location at the middle of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        separator.setDividerLocation(350);
        separator.setEnabled(false);
        separator.setDividerSize(0);
        separator.setFocusable(false);
        // remove undesirable border
        separator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // finally set view to visible
        setVisible(true);
    }

    public void selectView(int id) {
        contentPanel.selectView(id);
    }


    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        localStats.setLocalStats(compressedFileSize,compressionDegree, compressionSpeed, compressionTime, originalFileSize);
        localStats.init();
        localStats.setVisible(true);
    }

    private ContentInterface setupAboutContent(){
        ContentInterface aboutContent = new Content("About", "Description de about");
        return aboutContent;
    }

    private ContentInterface setupCompressTextContent(){
        ContentInterface compressText = new Content("Comprimir Texto",
                "dexcirpcion de comprimir un text");
        compressText = new OptionSelector(compressText, new String[]{"lz78", "lzss","lzw"},
                "Selecciona el algoritmo de compresion deseado", OptionSelector.ALGORITHM_SELECTOR, this);
        compressText = new FileChooser(compressText, new String[]{"txt"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressText;
    }

    private ContentInterface setupCompressImageContent(){
        ContentInterface compressImage = new Content("Comprimir imagen",
                "desciprion imagen");
        compressImage = new OptionSelector(compressImage, new String[]{"0", "1", "2", "3", "4", "5", "6", "7"},
                "Selecciona la calidad de compresion", OptionSelector.QUALITY_SELECTOR, this);
        compressImage = new FileChooser(compressImage, new String[]{"ppm"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressImage;
    }

    private ContentInterface setupFolderContent(){
        ContentInterface compressFolder = new Content("Comprimir carpeta","descripcion");
        compressFolder = new OptionSelector(compressFolder, new String[]{"lz78", "lzss","lzw"},
                "Selecciona el algoritmo de compresion deseado", OptionSelector.ALGORITHM_SELECTOR, this);
        compressFolder = new OptionSelector(compressFolder, new String[]{"0", "1", "2", "3", "4", "5", "6", "7"},
                "Selecciona la calidad de compresion", OptionSelector.QUALITY_SELECTOR, this);
        compressFolder = new FileChooser(compressFolder, new String[]{"ppm", "txt"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.DIRECTORIES_ONLY);
        return compressFolder;
    }

    private ContentInterface setupDecompressContent(){
        ContentInterface decompressFile = new Content("Descomprimir", "Descripcion");
        decompressFile = new FileChooser(decompressFile, new String[]{"jpeg", "lz78", "lzw", "lzss", "auto"},
                "Descomprimir", this,
                FileChooser.DECOMPRESSION_MODE, FileChooser.FILES_ONLY);
        return decompressFile;
    }

    private ContentInterface setupCompareContent(){
        ContentInterface compareFile = new Content("Comparar archivos", "Descripcion");
        return compareFile;
    }


    private ContentInterface setupGlobalStatsContent(){
        ContentInterface globalStats = new Content("Estadisticas Globales", "descriptcion");
        return globalStats;
    }

    private ContentInterface setupInfoContent(){
        ContentInterface info = new Content("Informacion", "Descripciondassdadsdasdassda ESTOY HARTO !!!!");
        return info;
    }

    public void compress(File file) {
        presentationCtrl.compress(file);
    }

    public void decompress(File file) {
        presentationCtrl.decompress(file);
    }

    public void setAlgorithm(int selectedIndex) {
        presentationCtrl.setAlgorithm(selectedIndex);
    }

    public void setQuality(int selectedIndex) {
        presentationCtrl.setQuality(selectedIndex);
    }

    public void resetValues() {
        presentationCtrl.resetValues();
    }
}
