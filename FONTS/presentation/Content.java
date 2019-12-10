package presentation;

import javax.swing.*;
import java.awt.*;

public abstract class Content extends JPanel {
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

    public final void init(){
        initComponents();
        initEventListeners();
    }

    protected abstract void initComponents();
    protected abstract void initEventListeners();


    public void notifyParent() {
        throw new UnsupportedOperationException("Operation not implemented on current derivation");
    }
}
