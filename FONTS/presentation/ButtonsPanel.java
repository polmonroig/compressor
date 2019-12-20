package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Buttons panel.
 */
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
     * <p>Instantiates a new Buttons panel.</p>
     *
     * @param names the names
     * @param view  the view
     */
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
     * <p>Init.</p>
     */
    public void init() {
        initComponents();
    }


    private void initComponents() {

        // init inner panel
        innerPanel.setBackground(DISABLED_COLOR);

        // init buttons
        buttons.get(currentSelected).setActive();
    }

    /**
     * <p>Disable current.</p>
     *
     * @param id the id
     */
    public void disableCurrent(int id) {
        buttons.get(currentSelected).setInactive();
        currentSelected = id;
        parent.selectView(id);
    }

    /**
     * <p>Sets enabled button.</p>
     *
     * @param i the
     * @param b the b
     */
    public void setEnabledButton(int i, boolean b) {
        buttons.get(i).setEnabled(b);
    }
}
