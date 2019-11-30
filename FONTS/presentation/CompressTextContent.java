package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressTextContent extends Content {

    private CustomButton fileSelectButton;
    private CustomButton compressButton;
    private JPanel buttonsPanel;

    public CompressTextContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        buttonsPanel = new JPanel();
        fileSelectButton = new CustomButton("Seleccionar archivos de texto", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        compressButton = new CustomButton("Comprimir archivos", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        fileSelectButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        compressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
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
        buttonsPanel.add(compressButton);
        compressButton.setEnabled(false);
    }
}
