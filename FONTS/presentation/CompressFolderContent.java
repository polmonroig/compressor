package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressFolderContent extends Content {
    private JComboBox<String> qual;
    private JComboBox<String> algorithms;
    private CustomButton compressButton;
    private JPanel chooserPanel;
    private JPanel qualityPanel;
    private JPanel algorithmsPanel;
    private JPanel compressPanel;
    private JLabel instructions2;

    public CompressFolderContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        String[] quality = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};//obtener de la capa de domini para ser independiente
        qual = new JComboBox(quality);
        String[] algs = {"auto", "lz78", "lzss", "lzw"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox(algs);
        instructions = new JLabel();
        instructions2 = new JLabel();

        chooserPanel = new JPanel();
        qualityPanel = new JPanel();
        algorithmsPanel = new JPanel();
        compressPanel = new JPanel();

        compressButton = new CustomButton("Comprimir carpeta", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        //fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(7, 1);
        setLayout(layout);
        add(mainText);
        add(description);
        layout = new GridLayout(2, 1);
        chooserPanel.setLayout(layout);
        chooserPanel.add(Box.createHorizontalStrut(5));
        add(chooserPanel);
        qualityPanel.setLayout(layout);
        instructions.setText("Escoje la calidad que deseas:");
        instructions2.setText("Escoje la calidad deseada:");
        qualityPanel.add(instructions2);
        qualityPanel.add(qual);
        chooserPanel.add(Box.createHorizontalStrut(5));
        add(qualityPanel);
        algorithmsPanel.setLayout(layout);
        instructions.setText("Escoje el algoritmo que deseas usar para comprimir los archivos de texto:");
        algorithmsPanel.add(instructions);
        algorithmsPanel.add(algorithms);
        add(algorithmsPanel);
        layout = new GridLayout(1, 1);
        compressPanel.add(compressButton);
        add(compressPanel);
        compressButton.setEnabled(false);
    }
}
