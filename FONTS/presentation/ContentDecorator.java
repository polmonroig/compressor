package presentation;


public class ContentDecorator extends ContentInterface {

    private ContentInterface innerContent;

    public ContentDecorator(ContentInterface content) {
        innerContent = content;
    }

    public ContentInterface getInnerContent(){
        return innerContent;
    }

    @Override
    public void resetValues(){
        innerContent.resetValues();
    }

    @Override
    public void setVisibility(boolean b){
        innerContent.setVisibility(b);
        setVisible(b);
    }

    @Override
    public void init(){
        innerContent.init();
    }
}
