package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.channels.Selector;
import java.util.ArrayList;

/**
 * FileChooser is a ContentDecorator that adds the
 * functionality to the user of selecting a file or
 * directory and executing a desired action with
 * the selected file
 *
 *
 * */
public class FileChooser extends ContentDecorator {

    /**
     * This button executes the specified action
     * */
    private CustomButton functionButton;

    /**
     * The compression mode defines the functionality of the function button
     * currently only COMPRESSION_MODE and DECOMPRESSION_MODE
     * and COMPARISON_MODE are supported
     * */
    private int mode;
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

    public static final int COMPARISON_MODE = 2;

    /**
     * This mode limits the selection of the file browsing only to files
     * */
    public static final int FILES_ONLY = 0;

    /**
     * This mode limits the selection of the file browsing only to directories
     * */
    public static final int DIRECTORIES_ONLY = 1;

    private int filesSelected;


    private ArrayList<FileSelector> selectors;

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
        filesSelected = 0;
        mode = functionMode;
        int nSelectors = 1;
        if(COMPARISON_MODE  == functionMode)nSelectors = 2;
        selectors = new ArrayList<>();
        for(int i = 0; i < nSelectors; ++i){
            selectors.add(new FileSelector(types, fileMode, this));
        }
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
        for(FileSelector selector : selectors){
            selector.resetValues();
        }
        filesSelected = 0;
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

    /**
     * <p>Initializes the components and setups their values
     *    it also initializes the parent components</p>
     * */
    private void initComponents(){
        // init super class
        getInnerContent().init();

        // init decorator
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();





        // add parent Content
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(10, 0, 0, 0);
        add(getInnerContent(), constraints);

        // Add FileSelectors
        int currentIndex = 1;
        constraints.fill = GridBagConstraints.NONE;
        for(FileSelector selector : selectors){
            constraints.gridy = currentIndex;
            currentIndex += 1;
            selector.init();
            add(selector, constraints);
        }

        // add function button
        functionButton.setEnabled(false);
        functionButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));
        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.fill = GridBagConstraints.NONE;
        constraints.insets = new Insets(30, 0, 0, 0);
        functionButton.setPreferredSize(new Dimension(15 * functionButton.getText().length(), 50));
        add(functionButton, constraints);



    }

    /**
     * <p>Notifies that a file selection has been made,
     * and we should update its status</p>
     * */
    public void notifySelection(){
        filesSelected += 1;
        if(filesSelected == selectors.size()){
            functionButton.setEnabled(true);
        }
    }

    /**
     * <p>Initializes the event listeners of the components</p>
     * */
    private void initEventListeners() {

        functionButton.addActionListener(actionEvent -> {
            if(mode == COMPRESSION_MODE) view.compress(selectors.get(0).getFile());
            else if(mode == DECOMPRESSION_MODE)view.decompress(selectors.get(0).getFile());
            else if(mode == COMPARISON_MODE) view.displayImages(selectors.get(0).getFile(), selectors.get(1).getFile());

            functionButton.setEnabled(false);
            filesSelected = 0;

        });

    }




}
