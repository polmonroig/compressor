package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileChooser extends ContentDecorator {

    private JFileChooser fileChooser;
    private CustomButton selectFileButton, functionButton;
    private JTextField label;
    private String[] fileTypes;
    private File file;
    private String filePath;
    private int selectionMode;
    private int compressionMode;
    private View view;

    public static final int COMPRESSION_MODE = 0;
    public static final int DECOMPRESSION_MODE = 1;

    public FileChooser(ContentInterface content, String[] types, String buttonFunction, View parentView, int mode, int fileMode){
        super(content);
        view = parentView;
        compressionMode = mode;
        fileChooser = new JFileChooser();
        fileTypes = types;
        selectionMode = fileMode;
        label = new JTextField("Directorio del archivo", 20);
        selectFileButton = new CustomButton("Seleccionar archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        functionButton = new CustomButton(buttonFunction, Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    @Override
    public void resetValues(){
        getInnerContent().resetValues();
        functionButton.setEnabled(false);
        label.setText("Directiorio del archivo");
    }

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
