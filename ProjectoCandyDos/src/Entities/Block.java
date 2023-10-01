package Entities;

import java.util.Stack;

import GUI.GraphicalBlock;
import Interfaces.Equivalent;
import Interfaces.Focusable;
import Interfaces.LogicBlock;

public class Block implements Focusable, LogicBlock{

    private Entity myEntity;
    private Stack<Modifiers> myModifiers;

	private boolean focused;

	private GraphicalBlock graphicalBlock;
	private int row;
	private int column;
	
	private String typeOfBlock;
	private String [] images;

    public Block(int r, int c) {
    	focused = false;
        row = r;
        column = c;
        typeOfBlock = "empty"; //deberia podes ser varios ( con gelatina, etc) tal vez se lo pueda pasar el modifier
        uploadRepresetnativePictures("/imagenes/");
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

	
	  private void uploadRepresetnativePictures(String path_img) {
		 images = new String [2]; 
		 images[0] = "/imagenes/vacio.png"; 
		 images[1] = "/imagenes/vacio-resaltado.png"; 
		 }
	



    public boolean focus() {
        focused = true;
        graphicalBlock.notifyChangeStatus();
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
		int indice = 0;
		indice += (focused ? 1 : 0);
		return images[indice];
	}
	public void setGraphicBlock(GraphicalBlock e) {
		 graphicalBlock = e;
	}

}
