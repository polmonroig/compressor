package presentation;


import presentation.controllers.PresentationCtrl;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


/**
 * A View is the main window of the application,
 * its job is to connect all the components of the application
 * to enable their interaction and update all the necessary attributes.
 *
 * The view of the application is mainly composed of two things,
 * a ButtonsPanel and a ContentPanel. The buttons panel is  at the left
 * side of the window and its made of a series of buttons, only one
 * button can be activated at a time, and each button corresponds to a
 * specific Content. The ContentPanel is where the user mainly interacts
 * its at the right side of the window and it is made of a series of Contents
 * each content is associated with a button from the buttons panel, it is
 * required that the number of buttons is equal to the number of contents
 * otherwise there would be a mismatch.
 *
 * The View has also the ability to create a new Frame with a
 * an arbitrary message, it is also responsible of creating an
 * image comparison Frame. It is required that the view keeps track
 * of the global stats. Finally the View is required to keep track of
 * the relationship between every button-content pair.
 *
 * */
public class View extends JFrame {



    /**
     * This is a reference to the presentation controller
     * */
    private PresentationCtrl presentationCtrl;

    /**
     * The separator is just a component that
     * separates the buttonsPanel from the contentPanel
     * */
    private JSplitPane separator;
    /**
     * The buttonsPanel is at the left side of the window and
     * it contains information of every button.
     * */
    private ButtonsPanel buttonsPanel;
    /**
     * The contentPanel is at the right side of the window
     * and contains information of every panel.
     * */
    private ContentPanel contentPanel;
    /**
     * This defines the layout of the view
     * */
    private GridLayout layout;
    /**
     * This contains the names of each button
     * */
    private static final String[] buttonNames = {"About", "Comprimir text", "Comprimir imatge",
                                                 "Comprimir carpeta", "Descomprimir", "Comparar",
                                                 "Estadístiques", "Ajuda"};
    /**
     * This contains the quality options selections required by the JPEG algorithm
     * */
    private static final String[] qualityOptions = {"0", "1", "2", "3", "4", "5", "6",
                                                    "7", "8", "9", "10", "11", "12"};
    /**
     * This contains the algorithm options for text compression
     * */
    private static final String[] algorithmOptions = {"lz78", "lzss","lzw", "auto"};
    /**
     * This is the index of the automatic algorithm referenced at algorithmOptions
     * */
    private static final int AUTO_ALGORITHM_INDEX = 1;
    /**
     * This is the id of the number of files label,
     * it is used to determine the order of the
     * labels in the global stats panel
     * */
    private int nFilesId;
    /**
     * This is the id of the compression time
     * */
    private int compressionTimeId;
    /**
     * This is the id of the compression degree
     * */
    private int compressionDegreeId;
    /**
     * This is the id of the compression speed
     * */
    private  int compressionSpeedId;
    /**
     * This is the id for the size of the original file
     * */
    private int originalFileSizeId;
    /**
     * This is the id for the size of the compressed file
     * */
    private int compressedFileSizeId;


    /**
     * <p>Basic constructor</p>
     * @param title is the title of the window
     * @param controller is a reference to the caller
     * */
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


    /**
     * <p>setupContents configures each of the components</p>
     * @return a list of configured components
     * */
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


    /**
     * <p>This initialized every component in the window
     *    its function is critical and required for the
     *    correct functionality of the program</p>
     * */
    public void init(){
        initComponents();
    }

    /**
     * <p>This initialized every component of the view</p>
     * */
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

        //Resize
        setResizable(false);

        // finally set view to visible
        setVisible(true);
    }


    /**
     * <p>Given a frame it sets it in the center of the screen</p>
     * */
    private static void setInitLocation(JFrame frame){
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);
    }

    /**
     * <p>Displays the given content</p>
     * @param id is the id of the content to be displayed
     * */
    public void selectView(int id) {
        contentPanel.selectView(id);
    }

    /**
     *<p>Sets and visualizes the local stats in a new window</p>
     * @param compressionTime is the compression time
     * @param compressedFileSize is the compressed file size
     * @param compressionDegree is the compression degree
     * @param compressionSpeed is the compression speed
     * @param originalFileSize is the original file size
     * */
    public void setLocalStats(float compressedFileSize, float compressionDegree, float compressionSpeed, float compressionTime, float originalFileSize) {
        JFrame statsFrame = new JFrame();

        statsFrame.setTitle("Estadistiques");
        // set location at the middle of the screen
        statsFrame.setSize(new Dimension(500, 300));
        setInitLocation(statsFrame);
        ContentInterface content = new Content("Estadístiques compressió", "Estadístiques de la compressió realitzada.");
        content = new VariableLabel(content);
        int id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Tamany inicial: " + originalFileSize + " bytes");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Tamany comprimit: " + compressedFileSize + " bytes");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Grau de compressió: " + compressionDegree + "%");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Velocitat de compressió: " + compressionSpeed + " bytes/ms");
        id = ((VariableLabel)content).addLabel();
        ((VariableLabel) content).setLabel(id, "Temps de compressió: " + compressionTime + " ms");
        content = new ExitButton(content, "Aceptar", statsFrame);
        content.init();
        statsFrame.add(content);
        statsFrame.setVisible(true);
    }

    /**
     * <p>This sets up the about content</p>
     * @return a content with about characteristics
     * */
    private ContentInterface setupAboutContent(){
        ContentInterface aboutContent = new Content("About", "\nPrograma de compressió de text, imatges i carpetes creat per a la assignatura de PROP" +
                                                                                        "\nde la FIB per l'any 2019/2020. Sóm el grup 6.1 integrat per:\n-Adrián Álvarez\n-Jaume Bernaus\n-Pol Monroig\n-David Santos");
        return aboutContent;
    }

    /**
     * <p>This sets up the compress text content</p>
     * @return a content with the text compression qualities
     * */
    private ContentInterface setupCompressTextContent(){
        ContentInterface compressText = new Content("Comprimir Text","");
        compressText = new OptionSelector(compressText, algorithmOptions,
                "Selecciona el algorisme de compressió desitjat", OptionSelector.ALGORITHM_SELECTOR, this);
        ((OptionSelector) compressText).setDefaultIndex(algorithmOptions.length-1);//auto (la ultima) por defecto
        compressText = new FileChooser(compressText, new String[]{"txt"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressText;
    }

    /**
     * <p>This sets up the compress image content</p>
     * @return a content with the image compression qualities
     * */
    private ContentInterface setupCompressImageContent(){
        ContentInterface compressImage = new Content("Comprimir imatge","");
        compressImage = new OptionSelector(compressImage, qualityOptions,
                "Selecciona la qualitat de compressió", OptionSelector.QUALITY_SELECTOR, this);
        compressImage = new FileChooser(compressImage, new String[]{"ppm"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.FILES_ONLY);
        return compressImage;
    }

    /**
     * <p>This sets up the compress folder content</p>
     * @return a content with the folder compression qualities
     * */
    private ContentInterface setupFolderContent(){
        ContentInterface compressFolder = new Content("Comprimir carpeta","");
        compressFolder = new OptionSelector(compressFolder, algorithmOptions,
                "Selecciona el algorisme de compressió desitjat", OptionSelector.ALGORITHM_SELECTOR, this);
        compressFolder = new OptionSelector(compressFolder, qualityOptions,
                "Selecciona la qualitat de compressió", OptionSelector.QUALITY_SELECTOR, this);
        compressFolder = new FileChooser(compressFolder, new String[]{"ppm", "txt"},
                "Comprimir", this,
                FileChooser.COMPRESSION_MODE, FileChooser.DIRECTORIES_ONLY);
        return compressFolder;
    }

    /**
     * <p>This sets up the decompression content</p>
     * @return a content with the decompression qualities
     * */
    private ContentInterface setupDecompressContent(){
        ContentInterface decompressFile = new Content("Descomprimir", "");
        decompressFile = new FileChooser(decompressFile, new String[]{"jpeg", "lz78", "lzw", "lzss", "auto"},
                "Descomprimir", this,
                FileChooser.DECOMPRESSION_MODE, FileChooser.FILES_ONLY);
        return decompressFile;
    }

    /**
     * <p>This sets up the compare images content</p>
     * @return a content with the comparison qualities
     * */
    private ContentInterface setupCompareContent(){
        ContentInterface compareFile = new Content("Comparar archivos", "");
        compareFile = new FileChooser(compareFile, new String[]{"ppm"}, "Comparar",
                                      this, FileChooser.COMPARISON_MODE, FileChooser.FILES_ONLY);
        return compareFile;
    }


    /**
     * <p>This sets up the content for the global stats/p>
     * @return a content with the global stats of the compression qualities
     * */
    private ContentInterface setupGlobalStatsContent(){
        ContentInterface globalStats = new Content("Estadístiques Globals", "");
        globalStats = new VariableLabel(globalStats);
        nFilesId = ((VariableLabel) globalStats).addLabel();
        compressionTimeId = ((VariableLabel) globalStats).addLabel();
        compressionDegreeId = ((VariableLabel) globalStats).addLabel();
        compressionSpeedId = ((VariableLabel) globalStats).addLabel();
        originalFileSizeId = ((VariableLabel) globalStats).addLabel();
        compressedFileSizeId = ((VariableLabel) globalStats).addLabel();
        return globalStats;
    }

    /**
     * <p>This sets up the info content</p>
     * @return a content with help information
     * */
    private ContentInterface setupInfoContent(){
        ContentInterface info = new Content("Ajuda", "Comprimir text: Selecciona l’algorisme de compressió desitjat, selecciona \n " +
                                                                                "el arxiu i fes clic a comprimir. Es mostraran les estadístiques de compressió\n" +
                                                                                "i el path de l’arxiu generat.\n\n" +
                                                                                "Comprimir imatge: Selecciona la qualitat de compressió desitjada, selecciona el\n" +
                                                                                "arxiu i fes clic a comprimir. Es mostraran les estadístiques de compressió i \n" +
                                                                                "el path de l’arxiu generat.\n\n" +
                                                                                "Comprimir carpeta: Selecciona l’algorisme de compressió desitjat per als arxius \n" +
                                                                                "de text, selecciona la qualitat de la compressió de les imatges, selecciona l’arxiu\n" +
                                                                                " i fes clic a comprimir. Es mostraran les estadístiques de compressió i el path de \n" +
                                                                                "l’arxiu generat.\n\n" +
                                                                                "Descomprimir: Selecciona l’arxiu i fes clic a descomprimir. Es mostrara el path de\n l’arxiu descomprimit.\n\n" +
                                                                                "Comparar: Selecciona dos imatges i fes clic a comparar. Es mostrara una finestra\n amb les dos imatges.\n\n" +
                                                                                "Estadístiques: Es mostren les estadístiques conjuntes de totes les compressions \nrealitzades. \n\n");
        return info;
    }

    /**
     * <p>This function sends a file to the presentation for
     * with the intention of compressing it, from the controller
     * it is then send to the domain controller</p>
     * */
    public void compress(File file) {
        presentationCtrl.compress(file);
    }

    /**
     * <p>This function sends a file to the presentation for
     * with the intention of decompressing it, from the controller
     * it is then send to the domain controller</p>
     * */
    public void decompress(File file) {
        presentationCtrl.decompress(file);
    }

    /**
     * <p>Given an index (usually set by an OptionSelector)
     * an algorithm is selected and set in the domain controller
     * The indexes available are displayed in the AlgorithmSet class</p>
     * @param selectedIndex selected algorithm
     * */
    public void setAlgorithm(int selectedIndex) {
        if(selectedIndex == algorithmOptions.length - 1){
            selectedIndex = AUTO_ALGORITHM_INDEX;
        }
        presentationCtrl.setAlgorithm(selectedIndex);
    }

    /**
     * <p>Given an index of quality, it is send to the
     * domain controller to set it to the JPEG algorithm</p>
     * @param selectedIndex selected quality
     * */
    public void setQuality(int selectedIndex) {
        presentationCtrl.setQuality(selectedIndex);
    }

    /**
     * <p>Resets the values to their original state</p>
     * */
    public void resetValues() {
        presentationCtrl.resetValues();
    }


    /**
     * <p>Given to images it displays them side by side</p>
     * @param fileA is a file to a given image
     * @param fileB is a file to another image
     * */
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

    /**
     * <p>Displays in a new window and informative message</p>
     * @param title is the main title of the window
     * @param message is the informative message
     * */
    public void displayMessage(String title, String message) {
        JFrame errorFrame = new JFrame();

        errorFrame.setTitle(title);
        // set location at the middle of the screen
        errorFrame.setSize(new Dimension(500, 200));
        setInitLocation(errorFrame);
        ContentInterface errorContent = new Content(title, message);
        errorContent = new ExitButton(errorContent, "Aceptar", errorFrame);
        errorContent.init();
        errorContent.setVisibility(true);
        errorFrame.add(errorContent);
        errorFrame.setVisible(true);
    }

    /**
     * <p>Updates the global stats panel given different arguments</p>
     * @param nFiles is the number of files
     * @param compressionTime is the compression time
     * @param compressedFileSize is the compressed file size
     * @param compressionDegree is the compression degree
     * @param compressionSpeed is the compression speed
     * @param originalFileSize is the original file size
     * */
    public void setGlobalStats(float nFiles, float compressionTime, float compressedFileSize,
                               float compressionDegree, float compressionSpeed, float originalFileSize) {

        if(nFiles > 0){
            VariableLabel content = (VariableLabel)contentPanel.getContent(6);
            buttonsPanel.setEnabledButton(6, true);
            content.setLabel(nFilesId, "Numero de compressions: " + nFiles);
            content.setLabel(compressionTimeId, "Temps mitja de compressions: " + compressionTime + " ms");
            content.setLabel(compressedFileSizeId, "Mida mitjana dels arxius comprimits: " + compressedFileSize + " bytes");
            content.setLabel(compressionDegreeId, "Grau mitja de compressió: " + compressionDegree + "%");
            content.setLabel(compressionSpeedId, "Velocitat mitjana de compressió: " + compressionSpeed + " bytes/ms");
            content.setLabel(originalFileSizeId, "Tamany mitja dels arxius originals: " + originalFileSize + " bytes");
        }
    }
}
