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
    protected static final int picSize = 70;

    protected Entity myEntity;
    protected Stack<Modifier> myModifiers;

    protected boolean focused;

    protected static String[] images = { "vacio.png", "vacio-resaltado.png" };
    protected static Empty empty = new Empty();

    public Block(int r, int c) {
        focused = false;
        row = r;
        column = c;
        myModifiers = new Stack<Modifier>();
    }

    public Entity getEntity() { return myEntity; }
    public void   setEntity(Entity e) { myEntity = e; }

    public boolean isEmpty() { return myEntity.equals(empty); }
    public void createWrapped() { }; //myModifiers.push(new Jelly()); }

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

    @Override public boolean         focus() { focused = true; setImage(images[1]); return true; }
    @Override public void            defocus() { focused = false; setImage(images[0]); }
    @Override public String          getImage() { return images[focused ? 1 : 0]; }
    
    public String toString() {
        String s = getEntity() != null ? getEntity().toString() : "";
        return "{" + s + "}";
    }
}
