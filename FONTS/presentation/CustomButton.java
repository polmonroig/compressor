package presentation;

import javax.swing.*;
import java.awt.*;

/**
 * A CustomButton is an JButton with extended
 * and customizable functionality, such as a rounded border
 * hover, and click interactions and different colors
 * depending on the action made and the state of the
 * button
 * */
public  class CustomButton extends JButton {



    /**
     * <p>Color set in the background when the button is disabled</p>
     * */
    private Color disabledBackgroundColor;
    /**
     * <p>Color set in the background when the button is enabled</p>
     * */
    private Color activeBackgroundColor;
    /**
     * <p>Color set in the text when the button is disabled</p>
     * */
    private Color disabledTextColor;
    /**
     * <p>Color set in the text when the button is enabled</p>
     * */
    private Color activeTextColor;
 
    /**
     * Border radius of the button determines
     * how round the button should be
     * */
    private int borderRadius;
    /**
     * This is the default border radius
     * */
    private static final int DEFAULT_RADIUS = 15;


    /**
     * <p>Basic constructor</p>
     * @param text is the text of the button
     * @param colorBD is the disabled background color
     * @param colorBA is the enabled background color
     * @param colorFD is the disabled text color
     * @param colorFA is the enabled text color
     * */
    public CustomButton(String text,  Color colorBD, Color colorBA, Color colorFD, Color colorFA){
        super(text);
        // save variables

        disabledBackgroundColor = colorBD;
        activeBackgroundColor = colorBA;
        disabledTextColor = colorFD;
        activeTextColor = colorFA;
        borderRadius = DEFAULT_RADIUS;

        // set attributes
        setFocusable(false);
        setContentAreaFilled(false);
        setInactive();
        setBorderPainted(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        addOnClickEffect();
        addOnHoverEffect();
    }

    /**
     * <p>Adds an effect when the user clicks the button
     * and can be overridden by any descendent</p>
     * */
    protected void addOnClickEffect() {
        addActionListener(actionEvent -> { setInactive(); });
    }

    /**
     * <p>Adds an effect when the user hovers the button,
     * and can be overridden by any descendent</p>
     * */
    protected void addOnHoverEffect() {
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled()){
                    setBackground(getActiveBackgroundColor());
                    setForeground(getActiveTextColor());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(getDisabledBackgroundColor());
                    setForeground(getDisabledTextColor());
                }

            }
        });
    }


    /**
     * <p>Paints the component and its rounded corners,
     * it can be overridden but it is not advisable</p>
     * */
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRoundRect(0, 0, getSize().width-1,getSize().height-1, borderRadius, borderRadius);

        super.paintComponent(g);
    }


    /**
     * <p>Activates the button and updates its state and colors</p>
     * */
    public void setActive(){
        setBackground(activeBackgroundColor);
        setForeground(activeTextColor);
    }
    /**
     * <p>Deactivates the button and updates its state and colors</p>
     * */
    public void setInactive() {
        setBackground(disabledBackgroundColor);
        setForeground(disabledTextColor);
    }

    /**
     * <p>Getter for the disabled background color</p>
     * @return disabled background color
     * */
    public Color getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }

    /**
     * <p>Setter for the border radius</p>
     * */
    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }

    /**
     * <p>Getter for the enabled background color</p>
     * @return enabled background color
     * */
    public Color getActiveBackgroundColor() {
        return activeBackgroundColor;
    }


    /**
     * <p>Getter for the disabled text color</p>
     * @return disabled text color
     * */
    public Color getDisabledTextColor() {
        return disabledTextColor;
    }
    /**
     * <p>Getter for the enabled text color</p>
     * @return enabled text color
     * */

    public Color getActiveTextColor() {
        return activeTextColor;
    }


}
