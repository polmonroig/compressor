package presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The type Option selector.
 */
public class OptionSelector extends ContentDecorator {


    private JComboBox<String> options;
    private JLabel label;
    private int type;
    private View view;


    /**
     * The constant ALGORITHM_SELECTOR.
     */
    public static final int ALGORITHM_SELECTOR = 0;
    /**
     * The constant QUALITY_SELECTOR.
     */
    public static final int QUALITY_SELECTOR = 1;

    /**
     * <p>Instantiates a new Option selector.</p>
     *
     * @param content      the content
     * @param comboOptions the combo options
     * @param text         the text
     * @param type         the type
     * @param parentView   the parent view
     */
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
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(getInnerContent(), constraints);
        constraints.gridy = 1;
        add(label, constraints);
        constraints.gridy = 2;
        add(options, constraints);

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


    /**
     * <p>Set default index.</p>
     *
     * @param i the
     */
    public void setDefaultIndex(int i){
        options.setSelectedIndex(i);
    }

}
