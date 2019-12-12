package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Content is the base content for the ContentInterface and
 * its decorators, is it a simple interface with a title and a description
 *
 * */
public class Content extends ContentInterface {

    /**
     * The title of the Content
     * */
    private JLabel title;
    /**
     * The description of the desired content
     * */
    private JTextArea description;

    /**
     * <p>Base constructor of the base content</p>
     * @param contentTitle the text desired to be set as title
     * @param contentDescription the text desired to be set as description
     * */
    public Content(String contentTitle, String contentDescription){
        title = new JLabel(contentTitle);
        description = new JTextArea(contentDescription);
    }


    /**
     * <p>Initializes the components</p>
     * */
    @Override
    public void init(){
        initComponents();
    }

    /**
     * <p>Passes since the base component is always at the same state
     *    and is unchangeable</p>
     * */
    @Override
    public void resetValues(){

    }

    /**
     * <p>Set the base component visibility</p>
     * @param b desired visibility
     * */
    @Override
    public void setVisibility(boolean b){
        setVisible(b);
    }

    /**
     * <p>Initializes the components values and the layout of the
     *    Component</p>
     * */
    private void initComponents() {
        setLayout(new GridBagLayout());

        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 28));

        description.setEditable(false);
        description.setBackground(new Color(238, 238, 238));
        setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(title, constraints);
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(description, constraints);

    }
}
