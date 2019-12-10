package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressTextContent extends Content {
    private JComboBox<String> algorithms;
    private CustomButton compressButton;
    private JPanel compressPanel;
    private FileChooser fileChooser;
    private View parent;

    public CompressTextContent(String title, String contentDescription, int i, View pointer){
        super(title, contentDescription, i);
        String[] algs = {"lz78", "lzss", "lzw", "auto"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox<>(algs);
        instructions = new JLabel("Escoge el algoritmo que deseas usar:");
        compressPanel = new JPanel();

        compressButton = new CustomButton("Comprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fileChooser = new FileChooser(new String[]{"txt"}, this);
        parent = pointer;
    }

    @Override
    public void init(){
        initComponents();
        initEventListeners();
    }

    private void initComponents(){
        // init


        layout = new GridLayout(6, 1);
        setLayout(layout);
        // add element 1
        add(mainText);
        // add element 2
        add(description);

        fileChooser.init();
        // add element 3
        add(fileChooser);

        // add element 4
        add(instructions);
        // add element 5
        add(algorithms);


        // add compression button 6
        compressPanel.add(compressButton);
        compressButton.setEnabled(false);
        add(compressPanel);
    }

    private void initEventListeners(){

        compressButton.addActionListener(actionEvent -> {
            parent.compressTextFile(algorithms.getSelectedIndex());
        });
    }


    @Override
    public void notifyParent(){
        parent.setFile(fileChooser.getFiles()[0]);
        compressButton.setEnabled(true);
    }
}
