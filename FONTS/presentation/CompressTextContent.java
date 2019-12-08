package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressTextContent extends Content {
    private JComboBox<String> algorithms;
    private CustomButton compressButton;
    private JPanel chooserPanel;
    private JPanel algorithmPanel;
    private JPanel compressPanel;

    public CompressTextContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        String[] algs = {"auto", "lz78", "lzss", "lzw"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox(algs);
        instructions = new JLabel();
        pathLabel = new JTextField("Path del archivo a comprimir", 20);
        chooserPanel = new JPanel();
        algorithmPanel = new JPanel();
        compressPanel = new JPanel();
        fileSelectButton = new CustomButton("Seleccionar archivos de texto", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton = new CustomButton("Comprimir archivos", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(6, 1);
        setLayout(layout);
        add(mainText);
        add(description);
        layout = new GridLayout(1, 2);
        chooserPanel.setLayout(layout);
        chooserPanel.add(pathLabel);
        chooserPanel.add(fileSelectButton);
        add(chooserPanel);
        algorithmPanel.setLayout(layout);
        instructions.setText("Escoje el algoritmo que deseas usar:");
        algorithmPanel.add(instructions);
        algorithmPanel.add(algorithms);
        add(algorithmPanel);
        layout = new GridLayout(1, 1);
        compressPanel.add(compressButton);
        add(compressPanel);
        compressButton.setEnabled(false);
    }
}
