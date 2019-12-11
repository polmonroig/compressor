package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OptionSelector extends ContentDecorator {


    private JComboBox<String> options;
    private JLabel label;
    private int type;
    private View view;

    public static final int ALGORITHM_SELECTOR = 0;
    public static final int QUALITY_SELECTOR = 1;

    public OptionSelector(ContentInterface content, String[] comboOptions, String text, int type, View parentView){
        super(content);
        label = new JLabel(text);
        options = new JComboBox<>(comboOptions);
        this.type = type;
        view = parentView;
    }

    @Override
    public void resetValues(){
        getInnerContent().resetValues();
        options.setSelectedIndex(0);
    }

    @Override
    public void init(){
        initComponents();
        initEventListeners();
    }

    private void initComponents(){
        getInnerContent().init();
        incrementRows();
        setLayout(new GridLayout(3, 1));
        add(getInnerContent());
        JPanel innerLabel = new JPanel();
        JPanel innerOptions = new JPanel();
        innerLabel.add(label);
        innerOptions.add(options);
        add(innerLabel);
        add(innerOptions);
    }

    private void initEventListeners(){
        options.addActionListener (e -> {
            if(type == ALGORITHM_SELECTOR){
                view.setAlgorithm(options.getSelectedIndex());
            }
            else if(type == QUALITY_SELECTOR){
                view.setQuality(options.getSelectedIndex());
            }
        });
    }


}
