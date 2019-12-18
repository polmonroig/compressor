package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileSelector extends JPanel {

    private JTextField label;
    private CustomButton button;
    private JFileChooser fileChooser;
    private String[] fileTypes;
    private File file;
    private String filePath;
    private int selectionMode;
    private FileChooser chooser;

    public FileSelector(String[] types, int mode, FileChooser fileSelector){
        chooser = fileSelector;
        selectionMode = mode;
        label = new JTextField("Directori", 20);
        button = new CustomButton("Selecciona", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileChooser = new JFileChooser();
        fileTypes = types;
    }

    public void init(){
        initComponents();
        initEventListeners();
    }

    private void initComponents(){
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        label.setEditable(false);
        label.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 15));

        button.setBorderRadius(0);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        constraints.gridwidth = 1;
        constraints.gridy = 1;
        add(label, constraints);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(button, constraints);
    }

    private void initEventListeners(){
        button.addActionListener(actionEvent -> {
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("", fileTypes);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileSelectionMode(selectionMode);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                setArguments();
                label.setText(filePath);
                chooser.notifySelection();
            }
        });
    }

    public File getFile(){
        return file;
    }

    private void setArguments(){
        file = fileChooser.getSelectedFile();
        filePath = file.getAbsolutePath();
    }

    public void resetValues() {
        label.setText("Directori");
    }
}
