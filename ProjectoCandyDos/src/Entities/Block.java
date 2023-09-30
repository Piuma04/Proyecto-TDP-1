package Entities;

import java.util.Stack;


import Interfaces.Equivalent;


public class Block {

	private Entity myEntity;
	private Stack<Modifiers> myModifiers;
	
	

	
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

	

	

}
