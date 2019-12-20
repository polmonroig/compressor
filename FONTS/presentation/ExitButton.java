package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * Exit button is a decorator that given a JFrame
 * it closes the window
 * */
public class ExitButton extends ContentDecorator {

    /**
     * This is the button the is used to exit the frame
     * */
    private CustomButton button;
    /**
     * The target frame to close
     * */
    private JFrame frame;

    /**
     * <p>Basic constructor</p>
     * */
    public ExitButton(ContentInterface content, String buttonLabel, JFrame exitFrame){
        super(content);
        frame = exitFrame;
        button = new CustomButton(buttonLabel, Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    /**
     * <p>Initialize the components and listeners</p>
     * */
    @Override
    public void init(){
        initComponents();
        initEventListeners();
    }

    /**
     * <p>Initialize the display components of the buttons and decorators</p>
     * */
    private void initComponents() {
        // init super class
        getInnerContent().init();

        // init decorator
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        add(getInnerContent(), constraints);
        constraints.fill = GridBagConstraints.NONE;
        constraints.gridy = 1;
        constraints.insets = new Insets(15, 0, 15, 0);
        add(button, constraints);
    }

    /**
     * <p>Resets the values of the parent decorator</p>
     * */
    @Override
    public void resetValues(){
        getInnerContent().resetValues();
    }

    /**
     * <p>Adds the exit functionality</p>
     * */
    private void initEventListeners(){
        button.addActionListener(actionEvent -> {
            frame.dispose();
        });
    }


}