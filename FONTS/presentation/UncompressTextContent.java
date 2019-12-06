package presentation;

import javax.swing.*;
import java.awt.*;

public class UncompressTextContent extends Content{
    private CustomButton fileSelectButton;
    private CustomButton uncompressButton;
    private JPanel buttonsPanel;

    public UncompressTextContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        buttonsPanel = new JPanel();
        fileSelectButton = new CustomButton("Seleccionar archivos de texto", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        uncompressButton = new CustomButton("Descoomprimir archivos", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        uncompressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
    }

    @Override
    public void init(){
        layout = new GridLayout(3, 1);
        setLayout(layout);
        add(mainText);
        add(description);
        buttonsPanel.setLayout(new GridLayout(1, 2));
        add(buttonsPanel);
        buttonsPanel.add(fileSelectButton);
        buttonsPanel.add(uncompressButton);
        uncompressButton.setEnabled(false);
    }
}
