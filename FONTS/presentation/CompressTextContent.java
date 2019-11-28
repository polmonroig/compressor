package presentation;

import javax.swing.*;
import java.awt.*;

public class CompressTextContent extends Content {

    private JButton fileSelectButton;
    private JButton compressButton;
    private JPanel buttonsPanel;

    public CompressTextContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        buttonsPanel = new JPanel();
        fileSelectButton = new JButton("Seleccionar archivos de texto");
        compressButton = new JButton("Comprimir archivos");
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
