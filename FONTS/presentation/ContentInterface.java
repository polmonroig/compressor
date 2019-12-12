package presentation;

import javax.swing.*;



/**
 * ContentInterface represents an interface,
 * it isn't an actual interface because we have to
 * enforce the JPanel extension. This way the code
 * can be joined.
 * This class is the base class for the Decorator pattern
 *
 */
public abstract class ContentInterface extends JPanel  {


    /**
     * <p>This method initializes the Content inner components and each
     *    of its actions</p>
     * */
    public abstract void init();

    /**
     * <p>This method recursively sets the visibility of the base content
     *    and its decorators</p>
     * @param b is the desired visibility
     * */
    public abstract void setVisibility(boolean b);

    /**
     * <p>This method resets the values and positions of the content
     *    to their initial value</p>
     * */
    public abstract void resetValues();
}
