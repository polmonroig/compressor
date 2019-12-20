package presentation;

import java.awt.*;

/**
 * The type Connected button.
 */
public class ConnectedButton extends CustomButton {


    private ButtonsPanel parent;
    private boolean selected;
    private int id;

    /**
     * <p>Instantiates a new Connected button.</p>
     *
     * @param text    the text
     * @param colorBD the color bd
     * @param colorBA the color ba
     * @param colorFD the color fd
     * @param colorFA the color fa
     * @param panel   the panel
     * @param i       the
     */
    public ConnectedButton(String text, Color colorBD, Color colorBA, Color colorFD, Color colorFA, ButtonsPanel panel, int i) {
        super(text, colorBD, colorBA, colorFD, colorFA);

        parent = panel;
        id = i;
        selected = false;
 
    }

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

    public void setActive(){
        super.setActive();
        selected = true;
    }

    public void setInactive() {
        super.setInactive();
        selected = false;
    }
}
