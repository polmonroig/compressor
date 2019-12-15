package presentation;


import presentation.controllers.PresentationCtrl;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class View extends JFrame {



    // set reference to the controller calling it
    private PresentationCtrl presentationCtrl;

    // a view is made of a buttons panel and a content panel
    private JSplitPane separator;
    private ButtonsPanel buttonsPanel;
    private ContentPanel contentPanel;
 

    // define the layout
    private GridLayout layout;
    private static final String[] buttonNames = {"About", "Comprimir text", "Comprimir imatge",
                                                 "Comprimir carpeta", "Descomprimir", "Comparar",
                                                 "Estadístiques", "Informació"};


    private static final String[] qualityOptions = {"0", "1", "2", "3", "4", "5", "6",
                                                    "7", "8", "9", "10", "11", "12"};

    private static final String[] algorithmOptions = {"lz78", "lzss","lzw", "auto"};

    private static final int AUTO_ALGORITHM_INDEX = 1;


    // save global stats ids
    private int nFilesId;
    private int compressionTimeId;
    private int compressionDegreeId;
    private  int compressionSpeedId;
    private int originalFileSizeId;
    private int compressedFileSizeId;


    public View(String title, PresentationCtrl controller){
        setTitle(title);
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
        setInitLocation(this);

        separator.setDividerLocation(350);
        separator.setEnabled(false);
        separator.setDividerSize(0);
        separator.setFocusable(false);
        // remove undesirable border
        separator.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // finally set view to visible
        setVisible(true);
    }

    private static void setInitLocation(JFrame frame){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    public void selectView(int id) {
        contentPanel.selectView(id);
    }


    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        JFrame statsFrame = new JFrame();

        statsFrame.setTitle("Estadistiques");
        // set location at the middle of the screen
        statsFrame.setSize(new Dimension(500, 300));
        setInitLocation(statsFrame);
        ContentInterface content = new Content("Estadisticas", "Visualizacion de las estadisticas de la compresion realizada");
        content = new VariableLabel(content);
        int id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Tamaño inicial: " + originalFileSize + " bytes");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Tamaño comprimido: " + compressedFileSize + " bytes");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Grado de compresion: " + compressionDegree);
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Velocidad de compresion: " + compressionSpeed + " bytes/ms");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Tiempo de compresion: " + compressionTime + " ms");
        content.init();
        statsFrame.add(content);
        statsFrame.setVisible(true);
    }

    private ContentInterface setupAboutContent(){
        ContentInterface aboutContent = new Content("About", "Description de about");
        return aboutContent;
    }

    private ContentInterface setupCompressTextContent(){
        ContentInterface compressText = new Content("Comprimir Text",
                "dexcirpcion de comprimir un text");
        compressText = new OptionSelector(compressText, algorithmOptions,
                "Selecciona el algorisme de compressió desitjat", OptionSelector.ALGORITHM_SELECTOR, this);
        compressText = new FileChooser(compressText, new String[]{"txt"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressText;
    }

    private ContentInterface setupCompressImageContent(){
        ContentInterface compressImage = new Content("Comprimir imatge",
                "desciprion imagen");
        compressImage = new OptionSelector(compressImage, qualityOptions,
                "Selecciona la qualitat de compressió", OptionSelector.QUALITY_SELECTOR, this);
        compressImage = new FileChooser(compressImage, new String[]{"ppm"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressImage;
    }

    private ContentInterface setupFolderContent(){
        ContentInterface compressFolder = new Content("Comprimir carpeta","descripcion");
        compressFolder = new OptionSelector(compressFolder, algorithmOptions,
                "Selecciona el algorisme de compressió desitjat", OptionSelector.ALGORITHM_SELECTOR, this);
        compressFolder = new OptionSelector(compressFolder, qualityOptions,
                "Selecciona la qualitat de compressió", OptionSelector.QUALITY_SELECTOR, this);
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
        compareFile = new FileChooser(compareFile, new String[]{"ppm"}, "Comparar",
                                      this, FileChooser.COMPARISON_MODE, FileChooser.FILES_ONLY);
        return compareFile;
    }


    private ContentInterface setupGlobalStatsContent(){
        ContentInterface globalStats = new Content("Estadístiques Globals", "descriptcion");
        globalStats = new VariableLabel(globalStats);
        nFilesId = ((VariableLabel) globalStats).addLabel();
        compressionTimeId = ((VariableLabel) globalStats).addLabel();
        compressionDegreeId = ((VariableLabel) globalStats).addLabel();
        compressionSpeedId = ((VariableLabel) globalStats).addLabel();
        originalFileSizeId = ((VariableLabel) globalStats).addLabel();
        compressedFileSizeId = ((VariableLabel) globalStats).addLabel();
        return globalStats;
    }

    private ContentInterface setupInfoContent(){
        ContentInterface info = new Content("Informació", "Descripciondassdadsdasdassda ESTOY HARTO !!!!");
        return info;
    }

    public void compress(File file) {
        presentationCtrl.compress(file);
    }

    public void decompress(File file) {
        presentationCtrl.decompress(file);
    }

    public void setAlgorithm(int selectedIndex) {
        if(selectedIndex == algorithmOptions.length - 1){
            selectedIndex = AUTO_ALGORITHM_INDEX;
        }
        presentationCtrl.setAlgorithm(selectedIndex);
    }

    public void setQuality(int selectedIndex) {
        presentationCtrl.setQuality(selectedIndex);
    }

    public void resetValues() {
        presentationCtrl.resetValues();
    }



    public void displayImages(File fileA, File fileB) {

        Image imageA = presentationCtrl.getPPM(fileA);
        Image imageB = presentationCtrl.getPPM(fileB);
        if (imageA != null && imageB != null) {
            int maxSize = 500;
            JFrame imagesFrame = new JFrame();
            imagesFrame.setTitle("Comparador");
            imagesFrame.setSize(maxSize * 2, maxSize * 2);
            setInitLocation(imagesFrame);
            imagesFrame.setLayout(new GridLayout(1, 2));
            ImagePanel a = new ImagePanel(imageA, maxSize);
            a.init();
            ImagePanel b = new ImagePanel(imageB, maxSize);
            b.init();
            imagesFrame.add(a);
            imagesFrame.add(b);
            imagesFrame.setVisible(true);
        }
    }

    public void displayMessage(String title, String message) {
        JFrame errorFrame = new JFrame();

        errorFrame.setTitle(title);
        // set location at the middle of the screen
        errorFrame.setSize(new Dimension(400, 200));
        setInitLocation(errorFrame);
        ContentInterface errorContent = new Content(title, message);
        errorContent = new ExitButton(errorContent, "Aceptar", errorFrame);
        errorContent.init();
        errorContent.setVisibility(true);
        errorFrame.add(errorContent);
        errorFrame.setVisible(true);
    }

    public void setGlobalStats(float nFiles, float compressionTime, float compressedFileSize,
                               float compressionDegree, float compressionSpeed, float originalFileSize) {

        if(nFiles > 0){
            VariableLabel content = (VariableLabel)contentPanel.getContent(6);
            buttonsPanel.setEnabledButton(6, true);
            content.setLabel(nFilesId, "Numero de archivos: " + nFiles);
            content.setLabel(compressionTimeId, "Tiempo medio de compresion: " + compressionTime + " ms");
            content.setLabel(compressedFileSizeId, "Tamaño medio del archivo compromido: " + compressedFileSize + " bytes");
            content.setLabel(compressionDegreeId, "Grado medio de compresion: " + compressionDegree);
            content.setLabel(compressionSpeedId, "Velocidad media de compresion: " + compressionSpeed + " bytes/ms");
            content.setLabel(originalFileSizeId, "Tamaño medio del archivo original: " + originalFileSize + "bytes");
        }
    }
}
