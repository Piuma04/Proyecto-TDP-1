package Interfaces;

import GUI.GraphicalEntity;

public interface VisualEntity extends LogicEntity {
    public void setGraphicalEntity(GraphicalEntity gEntity);
    public GraphicalEntity getGraphicalEntity();
}
