package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * The type Image panel.
 */
public class ImagePanel extends JPanel {

    private Image image;
    private int maxSize;

    /**
     * <p>Instantiates a new Image panel.</p>
     *
     * @param i    the
     * @param size the size
     */
    public ImagePanel(Image i, int size){
        image = i;
        maxSize = size;
    }


    @Override
    protected void paintComponent(Graphics g){

        g.drawImage(image, 0, 0, this);
    }

    /**
     * <p>Init.</p>
     */
    public void init(){
        initComponents();
    }

    private void initComponents(){
        resize();
    }

    private void resize(){
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        if(width > maxSize){
            image = image.getScaledInstance(maxSize, (int)(((float)height / (float)width) * maxSize), Image.SCALE_DEFAULT);
        }
        else if(height > maxSize){
            image = image.getScaledInstance((int)(((float)width / (float)height) * maxSize), maxSize, Image.SCALE_DEFAULT);
        }

    }


}
