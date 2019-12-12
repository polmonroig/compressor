package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * FileChooser is a ContentDecorator that adds the
 * functionality to the user of selecting a file or
 * directory and executing a desired action with
 * the selected file
 *
 * @author Pol Monroig Company
 *
 * */
public class FileChooser extends ContentDecorator {

    /**
     * This is the component that enables the file browsing
     * */
    private JFileChooser fileChooser;
    /**
     * This button opens the file browser
     * */
    private CustomButton selectFileButton;
    /**
     * This button executes the specified action
     * */
    private CustomButton functionButton;
    /**
     * This label cotains the path to the selected file
     * */
    private JTextField label;
    /**
     * This container has the list of file extensions
     * permitted during the file browsing.
     * */
    private String[] fileTypes;
    /**
     * This is the selected file
     * */
    private File file;
    /**
     * This is the path to the selected file
     * */
    private String filePath;
    /**
     * This defines what type of selection is available
     * currently only FILES_ONLY and DIRECTORIES_ONLY are supported
     * */
    private int selectionMode;
    /**
     * The compression mode defines the functionality of the function button
     * currently only COMPRESSION_MODE and DECOMPRESSION_MODE are supported
     * */
    private int compressionMode;
    /**
     * This is a reference to the main view since we need to tell it
     * when the function button has been activated in order to activate the desired functionality
     * */
    private View view;

    /**
     * This mode enables the compression of a directory or a file
     * */
    public static final int COMPRESSION_MODE = 0;
    /**
     * This mode enables the decompression of a file,
     * thus requiring the FILES_ONLY flag
     * */
    public static final int DECOMPRESSION_MODE = 1;

    /**
     * This mode limits the selection of the file browsing only to files
     * */
    public static final int FILES_ONLY = 0;

    /**
     * This mode limits the selection of the file browsing only to directories
     * */
    public static final int DIRECTORIES_ONLY = 1;


    /**
     * <p>Base FileChooser constructor</p>
     * @param content reference to the base content, or parent content for the decoration
     * @param types list of permitted extensions
     * @param buttonFunction is the name displayed in the function button
     * @param parentView is the reference to the view
     * @param functionMode is the selected function mode
     * @param fileMode is the selected file selection mode
     * */
    public FileChooser(ContentInterface content, String[] types, String buttonFunction, View parentView, int functionMode, int fileMode){
        super(content);
        view = parentView;
        compressionMode = functionMode;
        fileChooser = new JFileChooser();
        fileTypes = types;
        selectionMode = fileMode;
        label = new JTextField("Directorio del archivo", 20);
        selectFileButton = new CustomButton("Seleccionar archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        functionButton = new CustomButton(buttonFunction, Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    /**
     * <p>This overridden method resets the values of the parent
     *    Content and of the components of the FileChooser</p>
     * */
    @Override
    public void resetValues(){
        getInnerContent().resetValues();
        functionButton.setEnabled(false);
        label.setText("Directiorio del archivo");
    }

    /**
     * <p>Initializes the components of the decorator and
     *    its functionality </p>
     * */
    @Override
    public void init(){
        initComponents();
        initEventListeners();
    }

    private void initComponents(){
        // init super class
        getInnerContent().init();

        incrementRows();
        // init decorator
        GridLayout layout = new GridLayout(3, 1);
        setLayout(layout);
        add(getInnerContent());
        JPanel upperPanel = new JPanel();
        JPanel lowerPanel = new JPanel();
        upperPanel.setLayout(new GridLayout(1, 2));
        JPanel upperInner = new JPanel();
        upperPanel.add(upperInner);
        upperInner.add(label);
        upperInner.add(selectFileButton);

        label.setEditable(false);
        selectFileButton.setBorderRadius(0);
        selectFileButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));

        lowerPanel.add(functionButton);
        add(upperPanel);
        add(lowerPanel);
        functionButton.setEnabled(false);

    }


    private void initEventListeners() {
        selectFileButton.addActionListener(actionEvent -> {
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("", fileTypes);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileSelectionMode(selectionMode);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                setArguments();
                label.setText(filePath);
                functionButton.setEnabled(true);
            }
        });

        functionButton.addActionListener(actionEvent -> {
            if(compressionMode == COMPRESSION_MODE) view.compress(file);
            else if(compressionMode == DECOMPRESSION_MODE)view.decompress(file);
            functionButton.resetColor();
            functionButton.setEnabled(false);

        });

    }



    private void setArguments(){
        file = fileChooser.getSelectedFile();
        filePath = file.getAbsolutePath();
    }


}
