package Logic;

import java.util.Stack;

import Entities.Empty;
import Entities.Entity;
import Entities.Modifier;
import Interfaces.Equivalent;
import Interfaces.Focusable;

/**
 * Represents a block with an Entity
 */
public class Block extends VisualEntityDummy implements Focusable {

    protected static final int picSize = Board.getBoardLabelSize();
    protected static final Empty empty = new Empty();
    protected static final String[] images = { "BLOCK/vacio.png", "BLOCK/vacio-resaltado.png" };

    protected Entity myEntity;
    protected Stack<Modifier> myModifiers;

    
    public Block(int r, int c) {
        row = r;
        column = c;
        myModifiers = new Stack<Modifier>();
        imagePath = images[0];
    }

    public Entity getEntity() { return myEntity; }
    public void   setEntity(Entity e) { myEntity = e; }

    public boolean isEmpty() { return myEntity.isEqual(empty); }

    public void       pushModifier(Modifier modifier) { myModifiers.add(modifier); }
    public Equivalent popModifier() { Modifier m = myModifiers.pop(); m.destroy(); return m; }
    public boolean    hasModifiers() { return !myModifiers.isEmpty(); }

    public void swapEntity(Block block) {
        Entity entity = block.getEntity();
        int tempRow = row;
        int tempColumn = column;
        myEntity.changePosition(entity.getRow(), entity.getColumn());
        entity.changePosition(tempRow, tempColumn);
        block.setEntity(myEntity);
        myEntity = entity;
    }

    public Entity destroyEntity() {
        Entity e = myEntity;
        myEntity.destroy();
        myEntity = empty;
        return e;
    }

    @Override public void         focus() { setImage(images[1]); }
    @Override public void         defocus() { setImage(images[0]); }
    
    public String toString() {
        String s = getEntity() != null ? getEntity().toString() : "";
        return "{" + s + "}";
    }
}
