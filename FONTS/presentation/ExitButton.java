package presentation;

import java.awt.*;

public class ExitButton extends CustomButton {

    public ExitButton(String text, Color colorBD, Color colorBA, Color colorFD, Color colorFA) {
        super(text, colorBD, colorBA, colorFD, colorFA);

        setMaximumSize(new Dimension(50, -1));
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 26));
        setFocusable(false);

    }


}
