package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressImageContent extends Content {
    private JComboBox<String> algorithms;
    private CustomButton compressButton;
    private JPanel chooserPanel;
    private JPanel algorithmPanel;
    private JPanel compressPanel;

    public CompressImageContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        String[] quality = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};//obtener de la capa de domini para ser independiente
        algorithms = new JComboBox(quality);
        instructions = new JLabel();
        pathLabel = new JTextField("Path del archivo a comprimir", 20);
        chooserPanel = new JPanel();
        algorithmPanel = new JPanel();
        compressPanel = new JPanel();
        fileSelectButton = new CustomButton("Seleccionar archivo de imagen", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton = new CustomButton("Comprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(6, 1);
        setLayout(layout);
        add(mainText);
        add(description);
        layout = new GridLayout(2, 1);
        chooserPanel.setLayout(layout);
        chooserPanel.add(pathLabel);
        chooserPanel.add(fileSelectButton);
        chooserPanel.add(Box.createHorizontalStrut(5));
        add(chooserPanel);
        algorithmPanel.setLayout(layout);
        instructions.setText("Escoje la calidad que deseas:");
        algorithmPanel.add(instructions);
        algorithmPanel.add(algorithms);
        add(algorithmPanel);
        layout = new GridLayout(1, 1);
        compressPanel.add(compressButton);
        add(compressPanel);
        compressButton.setEnabled(false);
    }
}
