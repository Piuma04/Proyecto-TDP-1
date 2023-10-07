package Logic;

import GUI.GraphicalEntity;
import Interfaces.VisualEntity;

public abstract class VisualEntityDummy implements VisualEntity {

    protected static final int picSize = 70;
    protected int row;
    protected int column;
    protected String imagePath;
    protected int gifFrames;

    protected GraphicalEntity gEntity;

    public void setGif(String gifPath, int gifFrameCount) {
        imagePath = gifPath;
        gifFrames = gifFrameCount;
        gEntity.notifyChangeState();
    }
    public void setImage(String imagePath) {
        this.imagePath = imagePath;
        gifFrames = 0;
        gEntity.notifyChangeState();
    }
    @Override public int getRow() { return row; }
    @Override public int getColumn() { return column; }
    @Override public String getImage() { return imagePath; }
    @Override public int getPicSize() { return picSize; }
    @Override public int getGifFrameCount() { return gifFrames; }

    @Override public void setGraphicalEntity(GraphicalEntity gEntity) { this.gEntity = gEntity; }
    @Override public GraphicalEntity getGraphicalEntity() { return gEntity; }
    
    public void notifyChangeState() {  }
    
    public void changePosition(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
        if (gEntity != null)
            gEntity.notifyChangePosition();
    }
}
