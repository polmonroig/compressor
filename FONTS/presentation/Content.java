package presentation;

import javax.swing.*;
import java.awt.*;

public class Content extends ContentInterface {

    private JLabel title;
    private JTextArea description;

    public Content(String contentTitle, String contentDescription){
        title = new JLabel(contentTitle);
        description = new JTextArea(contentDescription);
        incrementRows();
        incrementRows();
    }


    @Override
    public void init(){
        initComponents();
    }


    @Override
    public void resetValues(){

    }

    @Override
    public void setVisibility(boolean b){
        setVisible(b);
    }

    private void initComponents() {
        setLayout(new GridLayout(getRows(), 1));
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

        description.setEditable(false);
        description.setBackground(new Color(238, 238, 238));
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));

        add(title);
        add(description);

    }
}
