package presentation;

import javax.swing.*;
import java.awt.*;

public class CustomButton extends JButton {

    private int id;
    private Color deactivatedBackgroundColor;
    private Color activeBackgroundColor;
    private Color deactivatedTextColor;
    private Color activeTextColor;
    private ButtonsPanel parent;
    private boolean selected;

    public CustomButton(String text, int i, Color colorBD, Color colorBA, Color colorFD, Color colorFA, ButtonsPanel panel){
        super(text);
        // save variables
        id = i;
        setFocusable(false);
        deactivatedBackgroundColor = colorBD;
        activeBackgroundColor = colorBA;
        deactivatedTextColor = colorFD;
        activeTextColor = colorFA;
        parent = panel;
        selected = false;

        // set attributes
        setBackground(deactivatedBackgroundColor);
        setForeground(deactivatedTextColor);
        setBorderPainted(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 22));
        addHoverEffect();
        addOnClickEffect();
    }

    private void addHoverEffect(){
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled() )setBackground(activeBackgroundColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(isEnabled() && !selected) setBackground(deactivatedBackgroundColor);
            }
        });
    }

    private void addOnClickEffect() {
        addActionListener(actionEvent -> {
            if(!selected){
                selected = true;
                setActive();
                parent.deactivateCurrent(id);
            }

        });
    }


    public void setActive(){
        setBackground(activeBackgroundColor);
        setForeground(activeTextColor);
        selected = true;
    }

    public void setInactive() {
        setBackground(deactivatedBackgroundColor);
        setForeground(deactivatedTextColor);
        selected = false;
    }
}
