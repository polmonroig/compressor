package presentation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class VariableLabel extends ContentDecorator {

    private ArrayList<JLabel> labels;

    public VariableLabel(ContentInterface content){
        super(content);
        labels = new ArrayList<>();
    }

    public int addLabel(){
        labels.add(new JLabel());
        return getLabelsSize() - 1;
    }

    public void setLabel(int id, String text){
        labels.get(id).setText(text);
    }


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
