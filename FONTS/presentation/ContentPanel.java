package presentation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ContentPanel extends JPanel {

    private ArrayList<ContentInterface> contents;
    private int selectedView;
    private View parent;

    public ContentPanel(ArrayList<ContentInterface> contentPanels, View view){
        parent = view;
        selectedView = 0;
        contents = contentPanels;

    }

    public void init() {
        for (ContentInterface content : contents) {
            content.init();
            content.setVisibility(false);
            add(content);
        }
        contents.get(selectedView).setVisibility(true);
    }

    public void selectView(int id) {
        // every time we change of panel we must reset all values
        parent.resetValues();
        contents.get(selectedView).setVisibility(false);
        contents.get(selectedView).resetValues();
        selectedView = id;
        contents.get(id).setVisibility(true);
    }

}
