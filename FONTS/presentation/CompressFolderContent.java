package presentation;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class CompressFolderContent extends Content {
    private JComboBox<String> qual;
    private JComboBox<String> algorithms;
    private CustomButton compressButton;
    private JPanel compressPanel;
    private JLabel instructions2;
    private FileChooser fileChooser;
    private View parent;

    public CompressFolderContent(String title, String contentDescription, int i, View pointer){
        super(title, contentDescription, i);
        String[] quality = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};//obtener de la capa de domini para ser independiente
        qual = new JComboBox<>(quality);
        String[] algs = {"auto", "lz78", "lzss", "lzw"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox<>(algs);
        instructions = new JLabel("Escoje el algoritmo que deseas usar para comprimir los archivos de texto:");
        instructions2 = new JLabel("Escoje la calidad deseada:");
        compressPanel = new JPanel();
        fileChooser =  new FileChooser(new String[]{"txt", "ppm"}, this);
        compressButton = new CustomButton("Comprimir carpeta", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        parent = pointer;
    }


    @Override
    protected void initComponents(){
        layout = new GridLayout(8, 1);
        setLayout(layout);

        // add element 1 and 2
        add(mainText);
        add(description);

        // add element 3
        fileChooser.setFolderSelection(true);
        fileChooser.init();
        add(fileChooser);

        // add element 4 and 5
        add(instructions2);
        add(qual);

        // add element 6 and 7
        add(instructions);
        add(algorithms);


        // add element 8
        compressPanel.add(compressButton);
        compressButton.setEnabled(false);
        add(compressPanel);
    }

    @Override
    protected void initEventListeners(){
        compressButton.addActionListener(actionEvent -> {
            try {
                parent.compressFiles(qual.getSelectedIndex(), algorithms.getSelectedIndex());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void notifyParent(){
        parent.setFiles(fileChooser.getFiles());
        compressButton.setEnabled(true);
    }
}
