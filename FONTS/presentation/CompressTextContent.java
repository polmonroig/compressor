package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressTextContent extends Content {
    private JComboBox algorithms;
    private JLabel instructions;
    private JTextField pathLabel;
    private CustomButton fileSelectButton;
    private CustomButton compressButton;
    private JPanel chooserPanel;
    private JPanel compressPanel;

    public CompressTextContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        String[] algs = {"auto", "lz78", "lzss", "lzw"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox(algs);
        instructions = new JLabel("Introduce el path del archivo a comprimir:");
        pathLabel = new JTextField("", 10);
        chooserPanel = new JPanel();
        compressPanel = new JPanel();
        fileSelectButton = new CustomButton("Seleccionar archivos de texto", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton = new CustomButton("Comprimir archivos", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(1, 2);
        setLayout(layout);
        add(mainText);
        add(description);
        chooserPanel.setLayout(new GridLayout(2, 1));
        add(instructions);
        add(pathLabel);
        add(chooserPanel);
        chooserPanel.add(fileSelectButton);
        chooserPanel.add(compressButton);
        add(compressPanel);
        compressPanel.setLayout(new GridLayout(1, 3));
        compressPanel.add(new JLabel());
        compressPanel.add(compressButton);
        compressPanel.add(new JLabel());
        compressButton.setEnabled(false);
    }
}
