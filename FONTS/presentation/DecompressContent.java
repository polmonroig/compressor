package presentation;

import javax.swing.*;
import java.awt.*;

public class DecompressContent extends Content {
    private CustomButton decompressButton;
    private JPanel decompressPanel;
    private FileChooser fileChooser;

    public DecompressContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        decompressPanel = new JPanel();
        decompressButton = new CustomButton("Descomprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        decompressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fileChooser = new FileChooser(new String[]{"lz78, lzss, lzw, auto, jpeg"}, this);
    }


    protected void initComponents(){
        layout = new GridLayout(6, 1);
        setLayout(layout);
        add(mainText);
        add(description);

        fileChooser.init();
        add(fileChooser);

        decompressPanel.add(decompressButton);
        decompressButton.setEnabled(false);
        add(decompressPanel);
    }


    protected void initEventListeners(){}
}
