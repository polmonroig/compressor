package presentation;

import javax.swing.*;



// Class that represents an interface,
// it isn't an actual interface because we have to
// enforce the JPanel extension. This way the code
// can be joined.
// This class is the base class for the Decorator pattern
public abstract class ContentInterface extends JPanel  {

    private int rows;

    public int getRows(){
       return rows;
    }

    public void incrementRows(){
        rows += 1;
    }

    public abstract void init();

    public abstract void setVisibility(boolean b);

    public abstract void resetValues();
}
