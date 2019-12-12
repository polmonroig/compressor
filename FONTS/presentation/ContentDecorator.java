package presentation;


/**
 * ContentDecorator is the class that increases the functionality
 * of a base content each decorator has its own independent functionality
 * Some of the specialised decorators have reference to the main view due
 * because they need to activate a function implemented in it and return
 * a value to the user
 *
 * */
public class ContentDecorator extends ContentInterface {

    /**
     * This is a reference to the previous Content
     * */
    private ContentInterface innerContent;
 

    /**
     * <p>Base constructor</p>
     * @param content is the previous Content that we want to enhance
     * */
    public ContentDecorator(ContentInterface content) {
        innerContent = content;
    }

    /**
     * <p>Returns the previous container</p>
     * @return the decorator base component
     * */
    public ContentInterface getInnerContent(){
        return innerContent;
    }

    /**
     * <p>Reset the values to their initial state,
     *    as a default function it resets the values of the
     *    previous Content</p>
     * */
    @Override
    public void resetValues(){
        innerContent.resetValues();
    }

    /**
     * <p>Sets the visibility of the previous Content and this
     *    ContentInterface</p>
     * @param b the desired visibility
     * */
    @Override
    public void setVisibility(boolean b){
        innerContent.setVisibility(b);
        setVisible(b);
    }

    /**
     * <p>Initializes the decorator by initializing its parent</p>
     * */
    @Override
    public void init(){
        innerContent.init();
    }
}
