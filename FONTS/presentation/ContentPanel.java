package presentation;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class ContentPanel extends JPanel {

    private ArrayList<Content> contents;
    private int selectedView;
    private View parent;

    public ContentPanel(ArrayList<Content> contentPanels, View view){
        parent = view;
        selectedView = 0;
        contents = contentPanels;

    }

    public void init() {
        for (Content content : contents) {
            content.init();
            add(content);
        }
        contents.get(selectedView).setVisible(true);
    }

    public void selectView(int id) {
        contents.get(selectedView).setVisible(false);
        selectedView = id;
        contents.get(id).setVisible(true);
    }

}
