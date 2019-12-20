package presentation;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The type Content panel.
 */
public class ContentPanel extends JPanel {

    private ArrayList<ContentInterface> contents;
    private int selectedView;
    private View parent;

    /**
     * <p>Instantiates a new Content panel.</p>
     *
     * @param contentPanels the content panels
     * @param view          the view
     */
    public ContentPanel(ArrayList<ContentInterface> contentPanels, View view){
        parent = view;
        selectedView = 0;
        contents = contentPanels;

    }

    /**
     * <p>Init.</p>
     */
    public void init() {
        for (ContentInterface content : contents) {
            content.init();
            content.setVisibility(false);
            add(content);
        }
        contents.get(selectedView).setVisibility(true);
    }

    /**
     * <p>Get content content interface.</p>
     *
     * @param id the id
     * @return the content interface
     */
    public ContentInterface getContent(int id){
        return contents.get(id);
    }

    /**
     * <p>Select view.</p>
     *
     * @param id the id
     */
    public void selectView(int id) {
        // every time we change of panel we must reset all values
        parent.resetValues();
        contents.get(selectedView).setVisibility(false);
        contents.get(selectedView).resetValues();
        selectedView = id;
        contents.get(id).setVisibility(true);
    }

}
