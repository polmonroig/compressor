package presentation;

import javax.swing.*;



/**
 * ContentInterface represents an interface,
 * it isn't an actual interface because we have to
 * enforce the JPanel extension. This way the code
 * can be joined.
 * This class is the base class for the Decorator pattern
 *
 * @author Pol Monroig Company
 */
public abstract class ContentInterface extends JPanel  {

    /**
     * The rows in a Content represent the number of elements in
     * the content, every time a new decorator is added the
     * number of rows increments
     * */
    private int rows;

    /**
     * <p>This is the getter of the number of rows in the content </p>
     * @return returns the number of rows
     * */
    public int getRows(){
       return rows;
    }

    /**
     * <p>This function needs to be called in every decorator
     *    since it represents that a new element has been added,
     *    it increments the number of rows</p>
     * */
    public void incrementRows(){
        rows += 1;
    }

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
