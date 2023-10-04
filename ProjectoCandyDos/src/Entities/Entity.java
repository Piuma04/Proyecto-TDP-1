package Entities;

import java.util.List;

import GUI.GraphicalEntity;
import Interfaces.Equivalent;

import Interfaces.LogicEntity;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

/**
 * Abstract class representing an entity
 */
public abstract class Entity implements Equivalent, Swappable, LogicEntity {

	protected static final int picSize = 70;
	protected Colour colour;
	protected int row;
	protected int column;

	private GraphicalEntity gEntity;

	public Entity(int rowPosition, int columnPosition, Colour colour) {
		row = rowPosition;
		column = columnPosition;
		this.colour = colour;
	}

	/**
	 * Retrieves the color of the entity.
	 *
	 * @return The color of the entity.
	 */
	public Colour getColour() {
		return colour;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	/**
	 * Changes the position of the entity to the specified row and column.
	 *
	 * @param newRow    The new row position.
	 * @param newColumn The new column position.
	 */
	public void changePosition(int newRow, int newColumn) {
		row = newRow;
		column = newColumn;
		if (gEntity != null)
			gEntity.notifyChangePosition();
	}

	/**
	 * Sets the graphical entity for this object.
	 *
	 * @param gEntity The graphical entity to set.
	 */
	public void setGraphicEntity(GraphicalEntity gEntity) {
		this.gEntity = gEntity;
	}

	/**
	 * Retrieves the graphical entity associated with this object.
	 *
	 * @return The graphical entity.
	 */
	public GraphicalEntity getGraphicEntity() {
		return gEntity;
	}

	/**
	 * Retrieves a list of destroyable blocks on the given board.
	 *
	 * @param b The board on which to find destroyable blocks.
	 * @return A list of destroyable blocks.
	 */
	public abstract List<Block> getDestroyables(Board b);

	/**
	 * Removes the entity from the game.
	 */
	public void destroy() {
		// changePosition(7, 6);
		if (gEntity != null)
			gEntity.notifyChangeState();
	}

	public String getImage() {
		return colour.toString() + ".png";
	}

	public int getPicSize() {
		return picSize;
	}

	/**
	 * Sets the color of the input string.
	 *
	 * @param str The string to set the color for.
	 * @return The colored string.
	 */
	protected String setStringColor(String str) {
		final String ANSI_RESET = "\u001B[0m";
		final String ANSI_RED = "\u001B[31m";
		final String ANSI_YELLOW = "\u001B[33m";
		final String ANSI_GREEN = "\u001B[32m";
		final String ANSI_PURPLE = "\u001B[35m";
		final String ANSI_BLUE = "\u001B[34m";
		final String ANSI_BLACK = "\u001B[30m";
		final String ANSI_CYAN = "\u001B[36m";
		// final String ANSI_WHITE = "\u001B[37m";

		String colourStr = null;
		switch (this.colour) {
		case RED:
			colourStr = ANSI_RED;
			break;
		case YELLOW:
			colourStr = ANSI_YELLOW;
			break;
		case GREEN:
			colourStr = ANSI_GREEN;
			break;
		case PURPLE:
			colourStr = ANSI_PURPLE;
			break;
		case BLUE:
			colourStr = ANSI_BLUE;
			break;
		case NONE:
			colourStr = ANSI_BLACK;
			break;
		case GLAZED:
			colourStr = ANSI_CYAN;
			break;
		}

		return colourStr + str + ANSI_RESET;
	}
}
