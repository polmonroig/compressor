package presentation;

import javax.swing.*;
import java.awt.*;

public  class CustomButton extends JButton {




    private Color deactivatedBackgroundColor;
    private Color activeBackgroundColor;
    private Color deactivatedTextColor;



    private Color activeTextColor;


    private int borderRadius;
    private static final int DEFAULT_RADIUS = 15;







    public CustomButton(String text,  Color colorBD, Color colorBA, Color colorFD, Color colorFA){
        super(text);
        // save variables

        deactivatedBackgroundColor = colorBD;
        activeBackgroundColor = colorBA;
        deactivatedTextColor = colorFD;
        activeTextColor = colorFA;
        borderRadius = DEFAULT_RADIUS;

        // set attributes
        setFocusable(false);
        setContentAreaFilled(false);
        setBackground(deactivatedBackgroundColor);
        setForeground(deactivatedTextColor);
        setBorderPainted(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        addOnClickEffect();
        addOnHoverEffect();
    }

    protected void addOnClickEffect() {
        addActionListener(actionEvent -> setActive());
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
                    setBackground(getDeactivatedBackgroundColor());
                    setForeground(getDeactivatedTextColor());
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
        setBackground(deactivatedBackgroundColor);
        setForeground(deactivatedTextColor);
    }


    public Color getDeactivatedBackgroundColor() {
        return deactivatedBackgroundColor;
    }

    public void setDeactivatedBackgroundColor(Color deactivatedBackgroundColor) {
        this.deactivatedBackgroundColor = deactivatedBackgroundColor;
    }


    public int getBorderRadius() {
        return borderRadius;
    }

    public void setBorderRadius(int borderRadius) {
        this.borderRadius = borderRadius;
    }


    public Color getActiveBackgroundColor() {
        return activeBackgroundColor;
    }

    public void setActiveBackgroundColor(Color activeBackgroundColor) {
        this.activeBackgroundColor = activeBackgroundColor;
    }

    public Color getDeactivatedTextColor() {
        return deactivatedTextColor;
    }

    public void setDeactivatedTextColor(Color deactivatedTextColor) {
        this.deactivatedTextColor = deactivatedTextColor;
    }

    public Color getActiveTextColor() {
        return activeTextColor;
    }

    public void setActiveTextColor(Color activeTextColor) {
        this.activeTextColor = activeTextColor;
    }

    public void resetColor() {
        setBackground(activeBackgroundColor);
        setForeground(activeTextColor);
    }
}
