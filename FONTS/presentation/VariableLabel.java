package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Variable label.
 */
public class VariableLabel extends ContentDecorator {

    private ArrayList<JLabel> labels;

    /**
     * <p>Instantiates a new Variable label.</p>
     *
     * @param content the content
     */
    public VariableLabel(ContentInterface content){
        super(content);
        labels = new ArrayList<>();
    }

    /**
     * <p>Add label int.</p>
     *
     * @return the int
     */
    public int addLabel(){
        labels.add(new JLabel());
        return getLabelsSize() - 1;
    }

    /**
     * <p>Set label.</p>
     *
     * @param id   the id
     * @param text the text
     */
    public void setLabel(int id, String text){
        labels.get(id).setText(text);
    }


    /**
     * <p>Get labels size int.</p>
     *
     * @return the int
     */
    public int getLabelsSize(){
        return labels.size();
    }

    @Override
    public void init(){
        initComponents();
    }

    private void initComponents(){
        getInnerContent().init();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(10, 0, 0, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(getInnerContent(), constraints);
        constraints.gridwidth = 1;
        int i = 1;
        for(JLabel label : labels){
            constraints.gridy = i;
            add(label, constraints);
            i += 1;
        }
    }
}
