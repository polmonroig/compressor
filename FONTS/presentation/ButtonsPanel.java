package presentation;

import org.hamcrest.core.Is;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ButtonsPanel extends JPanel {


    private ArrayList<ConnectedButton> buttons;
    private JButton exitButton;
    private JPanel innerPanel;
    private int currentSelected;
    private View parent;


    // define color constants
    private static final Color DEACTIVATED_COLOR = new Color(53, 71, 120);
    private static final Color ACTIVE_COLOR = new Color(79, 131, 249);
    private static final Color ACTIVE_TEXT = new Color(255, 255, 255);
    private static final Color DEACTIVATED_TEXT = new Color(150, 178, 221);
    private static final Color EXIT_COLOR = new Color(242, 143, 104);

    public ButtonsPanel(String[] names, View view){
        // init layout
        innerPanel = new JPanel();
        innerPanel.setLayout(new GridLayout(names.length + 1, 1));
        innerPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        add(innerPanel);
        currentSelected = 0;
        parent = view;



        setBackground(DEACTIVATED_COLOR);
        exitButton = new JButton("Salir");

        buttons = new ArrayList<>();
        for(int i = 0 ; i < names.length; ++i){
            buttons.add(new ConnectedButton(names[i], DEACTIVATED_COLOR, ACTIVE_COLOR, DEACTIVATED_TEXT, ACTIVE_TEXT, this, i));
            innerPanel.add(buttons.get(i));
        }
        innerPanel.add(exitButton);
    }

    public void init() {
        initComponents();
        initEventListeners();

    }

    private void initEventListeners() {
        // exit app
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void initComponents() {

        // init inner panel
        innerPanel.setBackground(DEACTIVATED_COLOR);

        // init exit button
        exitButton.setBackground(EXIT_COLOR);
        exitButton.setForeground(Color.WHITE);
        exitButton.setMaximumSize(new Dimension(50, -1));
        exitButton.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        exitButton.setFocusable(false);


        // init buttons
        buttons.get(currentSelected).setActive();
    }

    public void deactivateCurrent(int id) {
        buttons.get(currentSelected).setInactive();
        currentSelected = id;
        parent.selectView(id);
    }
}
