package Logic;

import java.util.List;

import java.util.LinkedList;
import java.util.Set;

import Entities.Candy;
import Entities.Colour;
import Entities.Entity;
import Entities.Stripped;
import Entities.Wrapped;
import Interfaces.Equivalent;

import java.util.HashSet;
import java.util.Random;

public class Board {
	private static final int ROWS = 6;
	private static final int COLUMNS = 6;
	private int playerRow, playerColumn;
	private Block[][] matrix;
	private Game myGame;

	public Board(Game g) {
		matrix = new Block[ROWS][COLUMNS];
		myGame = g;
		playerRow = 3;
		playerColumn = 3;
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				matrix[i][j] = new Block(i, j);
	}

	private Colour randomColour() {
		Colour[] colores = { Colour.BLUE, Colour.GREEN, Colour.PURPLE, Colour.RED, Colour.YELLOW };
		Random r = new Random();
		return colores[Math.abs(r.nextInt()) % 5];
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
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
	public Set<Integer> fillBoard() {
		Set<Integer> s = new HashSet<Integer>();
	
		boolean found = false;
		for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) {
			for (int i = ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--) {
				if (matrix[i][j].isEmpty()) {
					System.out.println("w");
					s.add(j);
				}
			}
		}
		for (Integer j : s) {
			for (int i = ROWS - 1; i >= 0; i--) {
				if (matrix[i][j].isEmpty()) {
					int nextEntity = i-1;
					while (nextEntity >= 0 && !found) {
						found = !matrix[nextEntity][j].isEmpty();
						if(!found) 
							nextEntity--;
					}
					if (found)
						matrix[i][j].swapEntity(matrix[nextEntity][j]);
					else
						// TODO
						// New
						for (int cont = i; cont >= 0; cont--) {
							Entity e = new Candy(i, j, randomColour());
							matrix[i][j].setEntity(e);
						}
					found = false;//
				}
			}
		}
		return s;
	}

	public void movePlayerDirection(int direction) {
		switch (direction) {
		case Game.DOWN: {
			movePlayerPosition(playerRow + 1, playerColumn);
			break;
		}
		case Game.UP: {
			movePlayerPosition(playerRow - 1, playerColumn);
			break;
		}
		case Game.LEFT: {
			movePlayerPosition(playerRow, playerColumn - 1);
			break;
		}
		case Game.RIGHT: {
			movePlayerPosition(playerRow, playerColumn + 1);
			break;
		}
		}
	}

	public List<Equivalent> swap(int direction) {
		switch (direction) {
		case Game.DOWN: {
			swapEntities(playerRow + 1, playerColumn);
			break;
		}
		case Game.UP: {
			swapEntities(playerRow - 1, playerColumn);
			break;
		}
		case Game.LEFT: {
			swapEntities(playerRow, playerColumn - 1);
			break;
		}
		case Game.RIGHT: {
			swapEntities(playerRow, playerColumn + 1);
			break;
		}
		}
		return null; // deber retornar bien
	}

	public Block getBlock(int row, int column) { // Requiere row y column validos
		return matrix[row][column];
	}

	public List<Equivalent> destroyEntities(List<Block> l) {
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		List<Block> toDestroy = new LinkedList<Block>();
		for (Block b : l) {
			toDestroy.addAll(b.getEntity().getDestroyables(this));
		}
		for (Block b : toDestroy) {
			destroyed.add(b.getEntity());
			b.destroyEntity();
		}
		return destroyed;
	}

	private void movePlayerPosition(int newRow, int newColumn) {
		if ((0 <= newRow) && (newRow < ROWS) && (0 <= newColumn) && (newColumn < COLUMNS)) {
			if (matrix[newRow][newColumn].focus()) {
				matrix[playerRow][playerColumn].defocus();
				playerRow = newRow;
				playerColumn = newColumn;
			}
		}
	}

	public void setPlayerPosition(int newRow, int newColumn) {

		matrix[newRow][newColumn].focus();

	}

	private List<Equivalent> swapEntities(int newRow, int newColumn) {
		Entity e1, e2;
		Set<Integer> columnsToCheck;
		List<Block> l1, l2, remaining;
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		boolean canExchange = false;

		if (isValidPosition(newRow, newColumn)) {
			Block b1 = matrix[playerRow][playerColumn];
			Block b2 = matrix[newRow][newColumn];
			e1 = b1.getEntity();
			e2 = b2.getEntity();
			canExchange = e1.isSwappable(e2);
			System.out.println(canExchange);
			if (canExchange) {
				b1.swapEntity(b2);
				l1 = checkCombinations(playerRow, playerColumn);
				l2 = checkCombinations(newRow, newColumn);
				l1.addAll(l2);
				System.out.println(l1.size());
				if (!l1.isEmpty()) {
					destroyed = destroyEntities(l1);
					System.out.println(destroyed);
					columnsToCheck = fillBoard();
					remaining = checkRemainingCombinations(columnsToCheck);
					while (!remaining.isEmpty()) {
						destroyed.addAll(destroyEntities(remaining));
						columnsToCheck = fillBoard();
						remaining = checkRemainingCombinations(columnsToCheck);
					}
				} else b1.swapEntity(b2);
			}	
		}
		
		
		return destroyed;
	}

	private List<Block> checkRemainingCombinations(Set<Integer> s) {
		List<Block> combinations = new LinkedList<Block>();
		for (Integer j : s) {
			for (int i = 0; i < ROWS; i++) {
				combinations.addAll(checkCombinations(i, j));
			}
		}
		return combinations;
	}

	// METODO BALTASAR
	private List<Block> checkCombinations(int row, int column) {
		List<Block> combination = new LinkedList<Block>();
		Colour color = matrix[row][column].getEntity().getColour();
		int cantHorizontal = checkSeguidosH(row, column, combination);
		int cantVertical = checkSeguidosV(row, column, combination);
		if (cantHorizontal >= 3 && cantVertical >= 3) {
			matrix[row][column].setEntity(new Wrapped(row, column, color));
			combination.remove(matrix[row][column]);
		} else if (cantHorizontal == 4 && cantVertical < 3) {
			matrix[row][column].setEntity(new Stripped(row, column, color, false));
		} else if (cantHorizontal < 3 && cantVertical == 4) {
			matrix[row][column].setEntity(new Stripped(row, column, color, true));
		}
		return combination;
	}

	private int checkSeguidosH(int row, int column, List<Block> combination) {
		List<Block> toAdd = new LinkedList<Block>();
		Entity comparable = matrix[row][column].getEntity();
		boolean cumple = true;
		toAdd.add(matrix[row][column]);
		for (int i = row + 1; i >= 0 && i < ROWS && cumple; i++) {
			cumple = matrix[i][column].getEntity().getColour() == comparable.getColour();
			if (cumple)
				toAdd.add(matrix[i][column]);
		}
		cumple = true;
		for (int i = row - 1; i >= 0 && i < ROWS && cumple; i--) {
			cumple = matrix[i][column].getEntity().getColour() == comparable.getColour();
			if (cumple) {
				toAdd.add(matrix[i][column]);
			}
		}
		if (toAdd.size() >= 3)
			combination.addAll(toAdd);
		return toAdd.size();
	}

	private int checkSeguidosV(int row, int column, List<Block> combination) {
		List<Block> toAdd = new LinkedList<Block>();
		Entity comparable = matrix[row][column].getEntity();
		boolean cumple = true;
		toAdd.add(matrix[row][column]);
		for (int j = column + 1; j >= 0 && j < COLUMNS && cumple; j++) {
			cumple = matrix[row][j].getEntity().getColour() == comparable.getColour();
			if (cumple)
				toAdd.add(matrix[row][j]);
		}
		cumple = true;
		for (int j = column - 1; j >= 0 && j < COLUMNS && cumple; j--) {
			cumple = matrix[row][j].getEntity().getColour() == comparable.getColour();
			if (cumple) {
				toAdd.add(matrix[row][j]);
			}
		}
		if (toAdd.size() >= 3)
			combination.addAll(toAdd);
		return toAdd.size();
	}// END Problema en checkRemaining ya que puede destruirse el rayado creado
	
    
    /**
     * 
     * @param row valid {@code row} values are ({@code row >= 0}) && ({@code row < }{@link Board#ROWS})
     * @param column valid {@code column} values are ({@code column >= 0}) && ({@code column < }{@link Board#COLUMNS}}
     * @return {@code true} if it is a valid position inside the board.
     */
    private boolean isValidPosition(int row, int column) {
        return row >= 0 && row < ROWS && column >= 0 && column < COLUMNS;
    }
}
