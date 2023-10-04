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

/**
 * Represents a block with an Entity
 */
public class Block implements Focusable, LogicEntity {

	private Entity myEntity;
	private Stack<Modifiers> myModifiers;

	private boolean focused;
	private static final int picSize = 70;
	private GraphicalEntity gBlock;
	private int row;
	private int column;

	// private String typeOfBlock;
	private static String[] images = { "vacio.png", "vacio-resaltado.png" };
	private static Empty empty = new Empty();

	public Block(int r, int c) {
		focused = false;
		row = r;
		column = c;
		// typeOfBlock = "empty"; // deberia podes ser varios ( con gelatina, etc) tal
		// vez se lo pueda pasar el
		// modifier
	}

	/**
	 * Gets the entity associated with this block.
	 *
	 * @return The entity associated with the block.
	 */
	public Entity getEntity() {
		return myEntity;
	}

	/**
	 * Sets the entity associated with this block.
	 *
	 * @param e The entity to associate with the block.
	 */
	public void setEntity(Entity e) {
		myEntity = e;
	}

	/**
	 * Checks if the block is empty.
	 *
	 * @return true if the block is empty, false otherwise.
	 */
	public boolean isEmpty() {
		return myEntity.equals(empty);
	}

	/**
	 * Creates a wrapped block
	 */
	public void createWrapped() {
		myModifiers.push(new Jelly()); // TODO
	}

	public boolean hasModifiers()
	{
		return !myModifiers.isEmpty();
	}
	/**
	 * Swaps the entity of this block with the entity of another block.
	 *
	 * @param block The block to swap entities with.
	 */
	public void swapEntity(Block block) {
		Entity entity = block.getEntity();
		int tempRow = row;
		int tempColumn = column;
		myEntity.changePosition(entity.getRow(), entity.getColumn());
		entity.changePosition(tempRow, tempColumn);
		block.setEntity(myEntity);
		myEntity = entity;
	}

	/**
	 * Removes and returns the top modifier from this block.
	 *
	 * @return The top modifier removed from the block.
	 */
	public Equivalent popModifier() {
		return myModifiers.pop();
	}

	/**
	 * Destroys the current entity associated with this block and replaces it with
	 * an empty entity.
	 *
	 * @return The destroyed entity.
	 */
	public Entity destroyEntity() {
		Entity e = myEntity;
		myEntity.destroy();
		myEntity = empty;
		return e;
	}

	/**
	 * Adds a modifier to the list of modifiers associated with this block.
	 *
	 * @param modifier The modifier to add.
	 */
	public void pushModifier(Modifiers modifier) {
		myModifiers.add(modifier);
	}

	@Override
	public void defocus() {
		focused = false;
		gBlock.notifyChangeState();
	}

	public boolean focus() {
		focused = true;
		gBlock.notifyChangeState();
		return true;
	}

	@Override
	/**
	 * Gets the row position of this block on a board.
	 *
	 * @return The row position.
	 */
	public int getRow() {
		return row;
	}

	@Override
	/**
	 * Gets the column position of this block on a board.
	 *
	 * @return The column position.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public String getImage() {
		return images[focused ? 1 : 0];
	}

	/**
	 * Sets the graphical entity associated with this block.
	 *
	 * @param gEntity The graphical entity to associate with the block.
	 */
	public void setGraphicEntity(GraphicalEntity gEntity) {
		gBlock = gEntity;
	}

	public int getPicSize() {
		return picSize;
	}

	public String toString() {
		return getEntity().toString();
	}
}
