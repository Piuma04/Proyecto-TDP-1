package Logic;

import GUI.GraphicalEntity;

import Interfaces.VisualEntity;

public abstract class VisualEntityDummy implements VisualEntity {

    protected int picSize = Game.getLabelSize();
    protected int row;
    protected int column;
    protected String imagePath;

    protected GraphicalEntity gEntity;

    
    /**
     * Must not be called if there is no {@link GraphicalEntity} initialized
     * 
     * @param newRow
     * @param newColumn
     * @see {@link #setGraphicalEntity(GraphicalEntity)}
     */
    public void setImage(String imageName) {
        imagePath = imageName;
        gEntity.notifyChangeState();
    }

    public void playGif(String gifPath) { setImage(gifPath); }

    @Override public int getRow()      { return row; }
    @Override public int getColumn()   { return column; }
    @Override public String getImage() { return imagePath; }
    @Override public int getPicSize()  { return picSize; }
    public void setPicSize(int pictureSize) { picSize = pictureSize; };

    @Override public void setGraphicalEntity(GraphicalEntity graphicalEntity) { gEntity = graphicalEntity; }
    @Override public GraphicalEntity getGraphicalEntity() { return gEntity; }
    
    /**
     * Must not be called if there is no {@link GraphicalEntity} initialized
     * @param newRow
     * @param newColumn
     * @see {@link #setGraphicalEntity(GraphicalEntity)}
     */
    public void changePosition(int newRow, int newColumn) {
        row = newRow;
        column = newColumn;
        gEntity.notifyChangePosition();
    }
}
