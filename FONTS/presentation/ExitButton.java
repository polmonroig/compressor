package presentation;

import java.awt.*;

public class ExitButton extends ContentDecorator {

    private CustomButton button;

    public ExitButton(ContentInterface content, String buttonLabel){
        super(content);
        button = new CustomButton(buttonLabel, Color.DARK_GRAY, Color.WHITE, Color.WHITE, Color.DARK_GRAY);
    }

    @Override
    public void init(){
        initEventListeners();
    }

    @Override
    public void resetValues(){
        getInnerContent().resetValues();
    }

    public void initEventListeners(){
        button.addActionListener(actionEvent -> {
            System.exit(0);
        });
    }


}
