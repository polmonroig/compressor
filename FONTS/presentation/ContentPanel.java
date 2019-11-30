package presentation;

import javax.swing.*;
import java.util.ArrayList;

public class ContentPanel extends JPanel {

    private ArrayList<Content> contents;
    private int selectedView;

    public ContentPanel(ArrayList<Content> contentPanels){

        selectedView = 0;
        contents = contentPanels;
        for (Content content : contents) {
            content.init();
            add(content);
        }
        contents.get(selectedView).setVisible(true);
    }

    public void init() {
    }

    public void selectView(int id) {
        contents.get(selectedView).setVisible(false);
        selectedView = id;
        contents.get(id).setVisible(true);
    }
}
