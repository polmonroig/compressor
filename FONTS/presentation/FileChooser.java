package presentation;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class FileChooser extends JPanel {

    private JFileChooser fileChooser;
    private CustomButton button;
    private JTextField label;
    private String[] fileTypes;
    private File[] files;
    private Content parent;
    private String filePath;

    public FileChooser(String[] types, Content content){
        fileChooser = new JFileChooser();
        parent = content;
        fileTypes = types;
        label = new JTextField("Path del archivo a comprimir", 20);
        button = new CustomButton("Seleccionar archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    public void init(){
        initComponents();
        initEventListeners();
    }

    private void initComponents(){
        GridLayout layout = new GridLayout(1, 2);
        setLayout(layout);
        label.setEditable(false);
        button.setBorderRadius(0);
        button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        add(label);
        add(button);

        setVisible(true);
    }


    private void initEventListeners() {
        button.addActionListener(actionEvent -> {
            fileChooser.setMultiSelectionEnabled(false);
            fileChooser.setAcceptAllFileFilterUsed(false);
            FileNameExtensionFilter filter = new FileNameExtensionFilter("TXT", fileTypes);
            fileChooser.addChoosableFileFilter(filter);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                setArguments();
                label.setText(filePath);
                parent.notifyParent();
            }
        });

    }

    private void setArguments(){
        files = fileChooser.getSelectedFiles();
        if(files.length == 0){
            files = new File[]{fileChooser.getSelectedFile()};
            filePath = files[0].getAbsolutePath();
        }
        else{
            filePath = files[0].getParentFile().getAbsolutePath();
        }
    }

    public File[] getFiles(){
        return files;
    }

}
