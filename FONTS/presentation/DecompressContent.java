package presentation;

import javax.swing.*;
import java.awt.*;

public class DecompressContent extends Content {
    private CustomButton decompressButton;
    private JPanel decompressPanel;
    private FileChooser fileChooser;
    private View parent;

    public DecompressContent(String title, String contentDescription, int i, View pointer){
        super(title, contentDescription, i);
        decompressPanel = new JPanel();
        decompressButton = new CustomButton("Descomprimir archivo", Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
        decompressButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        fileChooser = new FileChooser(new String[]{"lz78", "lzss", "lzw", "auto", "jpeg"}, this);
        parent = pointer;
    }

    @Override
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


    @Override
    protected void initEventListeners(){
        decompressButton.addActionListener(actionEvent -> {
            parent.descompressFile();
        });
    }

    @Override
    public void notifyParent(){
        parent.setFile(fileChooser.getFiles()[0]);
        decompressButton.setEnabled(true);
    }
}
