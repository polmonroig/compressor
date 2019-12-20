package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * ButtonsPanel is a JPanel used for using buttons
 *
 * */
public class ButtonsPanel extends JPanel {


    private ArrayList<ConnectedButton> buttons;
    private JPanel innerPanel;
    private int currentSelected;
    private View parent;

 
    // define color constants
    private static final Color DISABLED_COLOR = new Color(53, 71, 120);
    private static final Color ACTIVE_COLOR = new Color(79, 131, 249);
    private static final Color ACTIVE_TEXT = new Color(255, 255, 255);
    private static final Color DISABLED_TEXT = new Color(150, 178, 221);

    /**
     * <p>Base constructor of the ButtonsPanel</p>
     * @param names The names you want the buttons give
     * @param view The place you want to visualize the buttons
     * */
    public ButtonsPanel(String[] names, View view){
        // init layout
        innerPanel = new JPanel();
        GridLayout layout = new GridLayout(names.length, 1);
        layout.setVgap(5);
        innerPanel.setLayout(layout);
        innerPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 50, 0));
        add(innerPanel);
        currentSelected = 0;
        parent = view;



        setBackground(DISABLED_COLOR);
        buttons = new ArrayList<>();
        for(int i = 0 ; i < names.length; ++i){
            buttons.add(new ConnectedButton(names[i], DISABLED_COLOR, ACTIVE_COLOR, DISABLED_TEXT, ACTIVE_TEXT, this, i));
            innerPanel.add(buttons.get(i));
        }
    }

    /**
     * <p>Initializes the components</p>
     * */
    public void init() {
        initComponents();
    }

    /**
     * <p>Initializes the components</p>
     * */
    private void initComponents() {

        // init inner panel
        innerPanel.setBackground(DISABLED_COLOR);

        // init buttons
        buttons.get(currentSelected).setActive();
    }
    /**
     * <p>Disables a button</p>
     * @param id The id of the button you want to disable
     * */
    public void disableCurrent(int id) {
        buttons.get(currentSelected).setInactive();
        currentSelected = id;
        parent.selectView(id);
    }
    /**
     * <p>Initializes the components</p>
     * @param i The id of the button
     * @param b True for enabling the button, false for not enabling it
     * */
    public void setEnabledButton(int i, boolean b) {
        buttons.get(i).setEnabled(b);
    }
}
