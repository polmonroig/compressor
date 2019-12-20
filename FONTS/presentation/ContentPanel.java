package presentation;

import javax.swing.*;
import java.util.ArrayList;

public class ContentPanel extends JPanel {
    /**
     * Content is a JPanel which stores the contents you want to show
     *
     * */
    private ArrayList<ContentInterface> contents;
    private int selectedView;
    private View parent;
    /**
     * <p>Base constructor of the base content panel</p>
     * @param contentPanels An array of the contents you want to show
     * @param view the view you want to show the contents
     * */
    public ContentPanel(ArrayList<ContentInterface> contentPanels, View view){
        parent = view;
        selectedView = 0;
        contents = contentPanels;

    }
    /**
     * <p>Initializes the components</p>
     * */
    public void init() {
        for (ContentInterface content : contents) {
            content.init();
            content.setVisibility(false);
            add(content);
        }
        contents.get(selectedView).setVisibility(true);
    }

    /**
     * <p>Getter for the content you want to obtain</p>
     * @param id The id for the content you want to obtain
     * @return The content you requested
     * */
    public ContentInterface getContent(int id){
        return contents.get(id);
    }

    /**
     * <p>Visualize the view you want to show</p>
     * @param id The id of the view you want to visualize
     * */
    public void selectView(int id) {
        // every time we change of panel we must reset all values
        parent.resetValues();
        contents.get(selectedView).setVisibility(false);
        contents.get(selectedView).resetValues();
        selectedView = id;
        contents.get(id).setVisibility(true);
    }

}
