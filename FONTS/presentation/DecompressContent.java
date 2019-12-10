package presentation;

import javax.swing.*;
import java.awt.*;

public class DecompressContent extends Content {
    private CustomButton decompressButton;
    private JPanel chooserPanel;
    private JPanel decompressPanel;

    public DecompressContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        instructions = new JLabel();
        chooserPanel = new JPanel();
        decompressPanel = new JPanel();
        decompressButton = new CustomButton("Descomprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        decompressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(6, 1);
        setLayout(layout);
        add(mainText);
        add(description);
        layout = new GridLayout(2, 1);
        chooserPanel.setLayout(layout);
        chooserPanel.add(Box.createHorizontalStrut(5));
        add(chooserPanel);
        layout = new GridLayout(1, 1);
        decompressPanel.add(decompressButton);
        add(decompressPanel);
        decompressButton.setEnabled(false);
    }
}
