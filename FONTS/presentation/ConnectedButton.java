package presentation;

import java.awt.*;

/**
 * ConnectedButton is a CustomButton which uses functions that happens when some events are activated
 *
 * */
public class ConnectedButton extends CustomButton {


    private ButtonsPanel parent;
    private boolean selected;
    private int id;
    /**
     * <p>Constructor for a ConnectedButton</p>
     * @param colorBA Color for the background when the button is actvated
     * @param colorBD Color for the background when the button is disabled
     * @param colorFA Color for the font when the button is actvated
     * @param colorFD Color for the font when the button is disabled
     * @param i id of the button
     * @param panel ButtonsPannel which contains this buttion
     * @param text Text the button will contain
     * */
    public ConnectedButton(String text, Color colorBD, Color colorBA, Color colorFD, Color colorFA, ButtonsPanel panel, int i) {
        super(text, colorBD, colorBA, colorFD, colorFA);

        parent = panel;
        id = i;
        selected = false;
 
    }
    /**
     * <p>Adds an effect when the button is clicked</p>
     * */
    @Override
    protected void addOnClickEffect() {
        addActionListener(actionEvent -> {
            if(!selected){
                selected = true;
                setActive();
                parent.disableCurrent(id);
            }

        });
    }
    /**
     * <p>Adds an effect when the button is hoved</p>
     * */
    @Override
    protected void addOnHoverEffect(){
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled() ){
                    setBackground(getActiveBackgroundColor());
                    setForeground(getActiveTextColor());
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(isEnabled() && !selected){
                    setBackground(getDisabledBackgroundColor());
                    setForeground(getDisabledTextColor());
                }
            }
        });
    }
    /**
     * <p>Activates the effects of this button and goes into selected state</p>
     * */
    public void setActive(){
        super.setActive();
        selected = true;
    }
    /**
     * <p>Disactivates the effects of this buttos and returns to unselected state</p>
     * */
    public void setInactive() {
        super.setInactive();
        selected = false;
    }
}
