package presentation;

import javax.swing.*;
import java.awt.*;

public class ExitButton extends ContentDecorator {

    private CustomButton button;
    private JFrame frame;

    public ExitButton(ContentInterface content, String buttonLabel, JFrame exitFrame){
        super(content);
        frame = exitFrame;
        button = new CustomButton(buttonLabel, Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    @Override
    public void init(){
        initComponents();
        initEventListeners();
    } 

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
        add(button, constraints);
    }

    @Override
    public void resetValues(){
        getInnerContent().resetValues();
    }

    private void initEventListeners(){
        button.addActionListener(actionEvent -> {
            frame.dispose();
        });
    }


}
