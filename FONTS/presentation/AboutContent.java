package presentation;


import java.awt.*;

public class AboutContent extends Content {

    public AboutContent(String title, String contentDescription, int i){
        super(title, contentDescription, i);
    }

    protected void initComponents(){
        layout = new GridLayout(2, 1);
        setLayout(layout);
        add(mainText);
        add(description);
    }

    protected void initEventListeners() {}

}
