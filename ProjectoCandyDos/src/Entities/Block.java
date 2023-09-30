package Entities;

import java.util.Stack;

import GUI.GraphicalBlock;
import Interfaces.Equivalent;
import Interfaces.Focusable;

public class Block implements Focusable {

    private Entity myEntity;
    private Stack<Modifiers> myModifiers;

	private boolean focused;

	private GraphicalBlock graphicalBlock;


    public Block() {
        // TODO Auto-generated constructor stub
    }

    public Entity getEntity() {
        return myEntity;
    }

    public void setEntity(Entity e) {
        myEntity = e;

    }

    public boolean isEmpty() {
        return myEntity.equals(new Empty());

    }

    public void createWrapped() {
        myModifiers.push(new Jelly());
    }

    public void swapEntity(Block b) {
        Entity e = b.getEntity();
        b.setEntity(myEntity);
        myEntity = e;
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
		graphicalBlock.notifyChangeStatus();
	}

	/*
	 * private void uploadRepresetnativePictures(String path_img) {
	 * imagenes_representativas = new String [2]; imagenes_representativas[0] =
	 * path_img + color +".png"; imagenes_representativas[1] = path_img + color
	 * +"-resaltado.png"; }
	 */
//TODO Hay que implementar este metodo, sirve para poner las imagenes a las entidades


    public boolean focus() {
        focused = true;
        graphicalBlock.notifyChangeStatus();
        return true;
    }

}
