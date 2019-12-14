package presentation;

import javax.swing.*;
import java.awt.*;

public  class CustomButton extends JButton {




    private Color disabledBackgroundColor;
    private Color activeBackgroundColor;
    private Color disabledTextColor;
    private Color activeTextColor;
 

    private int borderRadius;
    private static final int DEFAULT_RADIUS = 15;







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

    protected void addOnClickEffect() {
        addActionListener(actionEvent -> { setInactive(); });
    }


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



    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(getBackground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRoundRect(0, 0, getSize().width-1,getSize().height-1, borderRadius, borderRadius);

        super.paintComponent(g);
    }





    public void setActive(){
        setBackground(activeBackgroundColor);
        setForeground(activeTextColor);
    }

    public void setInactive() {
        setBackground(disabledBackgroundColor);
        setForeground(disabledTextColor);
    }


    public Color getDisabledBackgroundColor() {
        return disabledBackgroundColor;
    }


    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }


    public Color getActiveBackgroundColor() {
        return activeBackgroundColor;
    }



    public Color getDisabledTextColor() {
        return disabledTextColor;
    }


    public Color getActiveTextColor() {
        return activeTextColor;
    }


}
