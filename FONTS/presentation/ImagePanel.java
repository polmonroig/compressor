package presentation;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {

    private Image image;
    private int maxSize;

    public ImagePanel(Image i, int size){
        image = i;
        maxSize = size;
    }


    @Override
    protected void paintComponent(Graphics g){

        g.drawImage(image, 0, 0, this);
    }

    public void init(){
        initComponents();
    }

    private void initComponents(){
        resize();
    }

    private void resize(){
        int width = image.getWidth(this);
        int height = image.getHeight(this);
        System.out.println("Image width: " +  width);
        System.out.println("Image height: " + height);
        if(width > maxSize){
            image = image.getScaledInstance(maxSize, (height / width) * maxSize, Image.SCALE_DEFAULT);
        }
        else if(height > maxSize){
            image = image.getScaledInstance((width / height) * maxSize, maxSize, Image.SCALE_DEFAULT);
        }

    }


}
