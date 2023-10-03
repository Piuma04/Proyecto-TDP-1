package Logic;

import java.util.Stack;

import Entities.Empty;
import Entities.Entity;
import Entities.Jelly;
import Entities.Modifiers;
import GUI.GraphicalEntity;
import Interfaces.Equivalent;
import Interfaces.Focusable;
import Interfaces.LogicEntity;

public class Block implements Focusable, LogicEntity {

    private Entity myEntity;
    private Stack<Modifiers> myModifiers;

    private boolean focused;

    private GraphicalEntity gBlock;
    private int row;
    private int column;

    private String typeOfBlock;
    private String[] images;

    public Block(int r, int c) {
        focused = false;
        row = r;
        column = c;
        typeOfBlock = "empty"; // deberia podes ser varios ( con gelatina, etc) tal vez se lo pueda pasar el
                               // modifier
    }

    public Entity getEntity() {
        return myEntity;
    }

    public void setEntity(Entity e) {
        myEntity = e;
    }

    public boolean isEmpty() {
        return myEntity.equals(new Empty()); // TODO ??

    }

    public void createWrapped() {
        myModifiers.push(new Jelly());
    }

    public void swapEntity(Block block) {
        Entity entity = block.getEntity();
        int tempRow = this.row;
        int tempColumn = this.column;
        myEntity.changePosition(entity.getRow(), entity.getColumn());
        entity.changePosition(tempRow, tempColumn);
        block.setEntity(myEntity);
        myEntity = entity;
    }

    public Equivalent popModifier() {
        return myModifiers.pop();
    }

    public Entity destroyEntity() {
        Entity e = myEntity;
        myEntity = new Empty();
        return e;
    }

    public void pushModifier(Modifiers modifier) {
        myModifiers.add(modifier);
    }

    @Override
    public void defocus() {
        focused = false;
        gBlock.notifyChangeStatus();
    }

    public void uploadRepresetnativePictures() {
        images = new String[2];

        images[0] = "vacio.png";
        images[1] = "vacio-resaltado.png";
    }

    public boolean focus() {
        focused = true;
        gBlock.notifyChangeStatus();
        return true;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getImage() {
        return images[focused ? 1 : 0];
    }

    public void setGraphicEntity(GraphicalEntity gEntity) {
        gBlock = gEntity;
    }
}
