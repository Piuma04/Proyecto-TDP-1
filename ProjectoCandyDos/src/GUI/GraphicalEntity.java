package GUI;

public interface GraphicalEntity {

    public void notifyChangeState();
    public void notifyChangePosition();
    public void setSkipQueue(boolean skipQueue);
    public void setText(String textToSet);
}
