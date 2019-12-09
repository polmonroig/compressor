package presentation;

import java.awt.*;

public class ConnectedButton extends CustomButton {


    private ButtonsPanel parent;
    private boolean selected;
    private int id;

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
                    setBackground(getDeactivatedBackgroundColor());
                    setForeground(getDeactivatedTextColor());
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
