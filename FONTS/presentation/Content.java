package presentation;

import javax.swing.*;
import java.awt.*;

public class Content extends JPanel {
    protected GridLayout layout;
    protected JLabel mainText;
    protected JLabel instructions;
    protected JTextField pathLabel;
    protected CustomButton fileSelectButton;
    protected JTextArea description;
    private int id;

    public Content(String title, String contentDescription, int i){
        id = i;
        mainText = new JLabel(title);
        mainText.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));
        description = new JTextArea(contentDescription);
        description.setBackground(new Color(238, 238, 238));
        description.setEditable(false);
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        setVisible(false);
    }

    public void init(){
        layout = new GridLayout(2, 1);
        setLayout(layout);
        add(mainText);
        add(description);

    }
}
