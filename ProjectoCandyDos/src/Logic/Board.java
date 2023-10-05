package Logic;

import java.util.List;

import java.util.LinkedList;
import java.util.Set;

import Entities.Candy;
import Entities.Colour;
import Entities.Entity;
import Entities.Stripped;
import Entities.Wrapped;
import GUI.Gui;
import GUI.GraphicalEntity;
import Interfaces.Equivalent;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Board {
	private static final int ROWS = 6;
	private static final int COLUMNS = 6;
	private int playerRow, playerColumn;
	private Block[][] matrix;
	private Game myGame;
	private Gui myGui;

	public Board(Game g, Gui gui) 
	{
		matrix = new Block[ROWS][COLUMNS];
		playerRow = 3;
		playerColumn = 3;
		myGame = g;
		myGui = gui;
		GraphicalEntity gEntity = null;
		for (int row = 0; row < ROWS; row++)
			for (int column = 0; column < COLUMNS; column++) 
			{
			    Block block =  new Block(row, column);
			    gEntity = myGui.addEntity(block);
                block.setGraphicEntity(gEntity);
                matrix[row][column] = block;
			}
	}
	
	/**
	 * returns the amount of {@code rows} the board has.
	 * @return {@code rows}
	 */
	public static int getRows() 
	{
		return ROWS;
	}

	/**
	 * returns the amount of {@code columns} the board has.
	 * @return {@code columns}
	 */
	public static int getColumns() 
	{
		return COLUMNS;
	}

	/*
	 * private Set<Integer> columnsToFill() { Set<Integer> s = new
	 * HashSet<Integer>();
	 * 
	 * for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) { for (int i =
	 * ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--) { if
	 * (matrix[i][j].isEmpty()) { s.add(j); } } } }
	 */
	/**
	 * Moves the player towards a specific direction.
	 * @param direction the direction the player will move towards to
	 */
	public void movePlayerDirection(int direction) 
	{
		switch (direction) 
		{
			case Game.DOWN: 
			{
				movePlayerPosition(playerRow + 1, playerColumn);
				break;
			}
			case Game.UP: 
			{
				movePlayerPosition(playerRow - 1, playerColumn);
				break;
			}
			case Game.LEFT: 
			{
				movePlayerPosition(playerRow, playerColumn - 1);
				break;
			}
			case Game.RIGHT: 
			{
				movePlayerPosition(playerRow, playerColumn + 1);
				break;
			}
		}
	}

	/**
	 * Swaps the element the player is on with one that is relative to a specific direction
	 * @param direction in which the element will be swapped
	 * @return a list of the elements destroyed
	 */
	public List<Equivalent> swap(int direction) 
	{
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		switch (direction) 
		{
		case Game.DOWN: {
			destroyed = swapEntities(playerRow + 1, playerColumn);
			break;
			}
		case Game.UP: {
			destroyed = swapEntities(playerRow - 1, playerColumn);
			break;
			}
		case Game.LEFT: {
			destroyed = swapEntities(playerRow, playerColumn - 1);
			break;
			}
		case Game.RIGHT: {
			destroyed = swapEntities(playerRow, playerColumn + 1);
			break;
			}
		}
		return destroyed;
	}

	/**
	 * returns a specific block from the board
	 * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @return Block from the board,in the row and column specified
	 */
	public Block getBlock(int row, int column) 
	{ 
		return matrix[row][column];
	}
	
	/**
	 * Sets the player's position
	 * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 */
	public void setPlayerPosition(int newRow, int newColumn) 
	{
		matrix[newRow][newColumn].focus();
	}
	
	/**
	 * Sets a new entity in the block specified with row and column
	 * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @param entity to be set on the block
	 */
    public void setEntity(int row, int column, Entity entity) 
    {
        Block block = getBlock(row, column);
        if (block.getEntity() != null) destroyEntity(row, column);
        block.setEntity(entity);
        entity.setGraphicEntity(myGui.addEntity(entity));
    }

    /**
     * Destroys the entity inside the block specified with {@code row} and {@code column}
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 */
    public void destroyEntity(int row, int column) {
        getBlock(row, column).destroyEntity();
    }
    
    /**
     * Swaps the entity the player is on with the one specified with {@code row} and {@code column}.
     * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @return Elements destroyed by potential combinations
     */
    private List<Equivalent> swapEntities(int newRow, int newColumn) 
    {
		Entity e1, e2;
		Set<Integer> columnsToCheck;
		List<Block> remaining;
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		boolean canExchange = false;

		if (isValidPosition(newRow, newColumn)) 
		{
			Block b1 = matrix[playerRow][playerColumn];
			Block b2 = matrix[newRow][newColumn];
			e1 = b1.getEntity();
			e2 = b2.getEntity();
			canExchange = e1.isSwappable(e2);
			System.out.println("canExchange: "+canExchange);
			if (canExchange) 
			{
				b1.swapEntity(b2);
				remaining = checkCombinations(playerRow, playerColumn);	//Get combinations for the first entity
				if(!remaining.contains(matrix[newRow][newColumn]))	    //Checks whether the second entity's combination is already checked
					remaining.addAll(checkCombinations(newRow, newColumn));
				if (!remaining.isEmpty()) 
				{
					while (!remaining.isEmpty()) //While there are remaining combinations, destroy them,fill the board, and check again
					{
						destroyed.addAll(destroyEntities(remaining));
						columnsToCheck = fillBoard();
						remaining = checkRemainingCombinations(columnsToCheck);
					}
					System.out.println("Total: "+destroyed);
				} else b1.swapEntity(b2);
			}	
		}
		return destroyed;
	}
    /**
     * Destroys the entities inside the blocks specified.
     * @param toDestroy list of blocks set to be destroyed
     * @return entities destroyed
     */
    private List<Equivalent> destroyEntities(List<Block> toDestroy)
    {
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		List<Block> destroyables = new LinkedList<Block>();
		for (Block b : toDestroy) 
		{
			for(Block bb: b.getEntity().getDestroyables(this))//raro
				if(!destroyables.contains(bb))
					destroyables.add(bb);
		}
		for (Block b : destroyables) 
		{
			destroyed.add(b.getEntity());
			destroyEntity(b.getRow(), b.getColumn());
		}
		System.out.println("destroyed" +destroyed);
		return destroyed;
	}
    /**
     * Fills the board pulling down the elements and putting random elements on the empty blocks
     * @return {@code columns} that were filled
     */
    private Set<Integer> fillBoard() 
    {
		Set<Integer> s = new HashSet<Integer>();
		boolean found = false;
		for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--)
			for (int i = ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--)
				if (matrix[i][j].isEmpty())
					s.add(j);

		for (Integer j : s) {
			for (int i = ROWS - 1; i >= 0; i--) 
			{
				if (matrix[i][j].isEmpty()) 
				{
					int nextEntity = i-1;
					while (nextEntity >= 0 && !found) 
					{
						found = !matrix[nextEntity][j].isEmpty();
						if(!found) 
							nextEntity--;
					}
					if (found)
						matrix[i][j].swapEntity(matrix[nextEntity][j]);
					else
						for (int cont = i; cont >= 0; cont--) 
						{
							Entity e = new Candy(i, j, randomColour());
							setEntity(i, j, e);
							//e.changePosition(0, j);
						}
					found = false;
				}
			}
		}
		return s;
	}
    /**
     * checks the combinations of the {@code columns} specified
     * @param columns {@code columns} to be checked
     * @return blocks that make combinations on the {@code columns} specified
     */
    private List<Block> checkRemainingCombinations(Set<Integer> columns) 
    {
		List<Block> combinations = new LinkedList<Block>();
		for (Integer j : columns) 
		{
			for (int i = 0; i < ROWS; i++) 
			{
				if(!combinations.contains(matrix[i][j]))
					combinations.addAll(checkCombinations(i,j));
			}
		}
		return combinations;
	}

    /**
     * Checks the combinations an element specified with {@code row} and {@code column} makes with the surrounding elements
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @return blocks that contain the elements that combined
     */
 	private List<Block> checkCombinations(int row, int column) 
 	{
 		List<Block> combination = new LinkedList<Block>();
 		Colour color = matrix[row][column].getEntity().getColour();
 		int cantHorizontal = checkSeguidosH(row, column, combination);
 		int cantVertical = checkSeguidosV(row, column, combination);
 		
 		if (cantHorizontal >= 2 && cantVertical >= 2) 
 		{
 			setEntity(row, column, new Wrapped(row, column, color));
 		} 
 		else if (cantHorizontal == 3 && cantVertical < 2) 
 		{
 		    setEntity(row, column, new Stripped(row, column, color, false));
 		} 
 		else if (cantHorizontal < 2 && cantVertical == 3) 
 		{
 			setEntity(row, column, new Stripped(row, column, color, true));
 		}
 		else
 			combination.add(matrix[row][column]);
 		if(combination.size()<3)
 			combination.clear();
 		if(!combination.isEmpty())
 			System.out.println("combination: "+combination);
 		System.out.println("Combination found"+combination);
 		return combination;
 	}
 	/**
 	 * Checks the horizontal combinations an element specified with row and column makes
 	 * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @param combination blocks that make combinations
 	 * @return amount of horizontal combinations
 	 */
 	private int checkSeguidosH(int row, int column, List<Block> combination) 
 	{
 		List<Block> toAdd = new LinkedList<Block>();
 		Entity comparable = matrix[row][column].getEntity();
 		boolean cumple = true;
 		for (int i = row + 1; i >= 0 && i < ROWS && cumple; i++) 
 		{
 			cumple = matrix[i][column].getEntity().getColour() == comparable.getColour();
 			if (cumple)
 				toAdd.add(matrix[i][column]);
 		}
 		cumple = true;
 		for (int i = row - 1; i >= 0 && i < ROWS && cumple; i--) 
 		{
 			cumple = matrix[i][column].getEntity().getColour() == comparable.getColour();
 			if (cumple) 
 			{
 				toAdd.add(matrix[i][column]);
 			}
 		}
 		if (toAdd.size() >= 2)
 			combination.addAll(toAdd);
 		return toAdd.size();
 	}
 	/**
 	 * Checks the vertical combinations an element specified with row and column makes
 	 * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
 	 * @param combination blocks that make combinations
 	 * @return amount of vertical combinations
 	 */
 	private int checkSeguidosV(int row, int column, List<Block> combination) 
 	{
 		List<Block> toAdd = new LinkedList<Block>();
 		Entity comparable = matrix[row][column].getEntity();
 		boolean cumple = true;
 		for (int j = column + 1; j >= 0 && j < COLUMNS && cumple; j++) 
 		{
 			cumple = matrix[row][j].getEntity().getColour() == comparable.getColour();
 			if (cumple)
 				toAdd.add(matrix[row][j]);
 		}
 		cumple = true;
 		for (int j = column - 1; j >= 0 && j < COLUMNS && cumple; j--) 
 		{
 			cumple = matrix[row][j].getEntity().getColour() == comparable.getColour();
 			if (cumple) 
 				toAdd.add(matrix[row][j]);
 		}
 		if (toAdd.size() >= 2)
 			combination.addAll(toAdd);
 		return toAdd.size();
 	}
    /**
     * 
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return {@code true} if it is a valid position inside the board.
     */
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS;
    }
    /**
     * Moves player's position to a new {@code row} and new {@code column} 
     * @param newRow valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param newColumn valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     */
	private void movePlayerPosition(int newRow, int newColumn) {
		if ((0 <= newRow) && (newRow < ROWS) && (0 <= newColumn) && (newColumn < COLUMNS)) {
			if (matrix[newRow][newColumn].focus()) {
				matrix[playerRow][playerColumn].defocus();
				playerRow = newRow;
				playerColumn = newColumn;
			}
		}
	}
	/**
	 * returns a random {@link Colour}, posibilities are RED,BLUE,PURPLE,YELLOW,GREEN
	 * @return random colour
	 */
	private Colour randomColour() {
		Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
		Random r = new Random();
		return colores[Math.abs(r.nextInt()) % 5];
	}
}