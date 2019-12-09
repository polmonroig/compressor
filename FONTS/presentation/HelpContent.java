package presentation;

import javax.swing.*;
import java.awt.*;

public class HelpContent extends Content {
    private JLabel help;
    private JFrame helpcontent;

    public HelpContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
        help = new JLabel();
        helpcontent = new JFrame();
    }

    @Override
    public void init(){
        layout = new GridLayout(5, 1);
        setLayout(layout);
        add(mainText);
        add(description);

    }
}
