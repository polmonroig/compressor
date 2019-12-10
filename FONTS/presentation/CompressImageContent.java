package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressImageContent extends Content {
    private JComboBox<String> qual;
    private CustomButton compressButton;
    private JPanel compressPanel;
    private FileChooser fileChooser;


    public CompressImageContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        String[] quality = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};//obtener de la capa de domini para ser independiente
        qual = new JComboBox<>(quality);
        instructions = new JLabel("Escoge la calidad que deseas:");
        compressPanel = new JPanel();
        compressButton = new CustomButton("Comprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fileChooser = new FileChooser(new String[]{"ppm"}, this);
    }




    @Override
    protected void initEventListeners(){

    }

    @Override
    protected void initComponents(){
        layout = new GridLayout(6, 1);
        setLayout(layout);


        add(mainText);
        add(description);

        fileChooser.init();
        add(fileChooser);

        add(instructions);
        add(qual);


        compressPanel.add(compressButton);
        compressButton.setEnabled(false);
        add(compressPanel);
    }
}
