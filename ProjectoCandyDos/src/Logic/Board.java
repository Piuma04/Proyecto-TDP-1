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
	private int row, column;
	private Block[][] matrix;
	private Game myGame;

	public Board(Game g) {
		matrix = new Block[ROWS][COLUMNS];
		myGame = g;
		row = 3;
		column = 3;
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				matrix[i][j] = new Block(i, j);
	}

	public int getRows() {
		return ROWS;
	}

	public int getColumns() {
		return COLUMNS;
	}

	/*private Set<Integer> columnsToFill()
	{
		Set<Integer> s = new HashSet<Integer>();
		
		for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) {
			for (int i = ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--) {
				if (matrix[i][j].isEmpty()) {
					s.add(j);
				}
			}
		}
	}*/
	public Set<Integer> fillBoard() {
		Set<Integer> s = new HashSet<Integer>();
		boolean canNext = false;
		for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) {
			for (int i = ROWS - 1; i >= 0 && s.size() < COLUMNS && !s.contains(j); i--) {
				if (matrix[i][j].isEmpty()) {
					s.add(j);
				}
			}
		}
		for (Integer j : s) {
			for (int i = ROWS - 1; i >= 0; i--) {
				if (matrix[i][j].isEmpty()) {
					int nextEntity = i;
					while (nextEntity >= 0 && !canNext) {
						nextEntity--;
						canNext = !matrix[i][j].isEmpty();
					}
					if (canNext)
						matrix[i][j].swapEntity(matrix[nextEntity][j]);
					else
						// TODO
						// New
						for (int cont = i; cont >= 0; cont--) {
							Entity e = new Candy(cont, j, Colour.YELLOW);
							matrix[i][j].setEntity(e);
						}
					canNext = false;//
				}
			}
		}
		return s;
	}

	public void movePlayerDirection(int direction) {
		switch (direction) {
		case Game.DOWN: {
			movePlayerPosition(row + 1, column);
			break;
		}
		case Game.UP: {
			movePlayerPosition(row - 1, column);
			break;
		}
		case Game.LEFT: {
			movePlayerPosition(row, column - 1);
			break;
		}
		case Game.RIGHT: {
			movePlayerPosition(row, column + 1);
			break;
		}
		}
	}

	public List<Equivalent> swap(int direction) {
		switch (direction) {
		case Game.DOWN: {
			swapEntities(row + 1, column);
			break;
		}
		case Game.UP: {
			swapEntities(row - 1, column);
			break;
		}
		case Game.LEFT: {
			swapEntities(row, column - 1);
			break;
		}
		case Game.RIGHT: {
			swapEntities(row, column + 1);
			break;
		}
		}
		return null; // deber retornar bien
	}

	public Block getBlock(int row, int column) { //Requiere row y column validos
		return matrix[row][column];
	}

	public List<Equivalent> destroyEntities(List<Block> l) {
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		List<Block> toDestroy = new LinkedList<Block>();
		for (Block b : l) {
			toDestroy.addAll(b.getEntity().getDestroyables(this));
		}
		for(Block b : toDestroy)
		{
			destroyed.add(b.getEntity());
			b.destroyEntity();
		}
		return destroyed;
	}

	private void movePlayerPosition(int newRow, int newColumn) {
		if ((0 <= newRow) && (newRow < ROWS) && (0 <= newColumn) && (newColumn < COLUMNS)) {
			if (matrix[newRow][newColumn].focus()) {
				matrix[row][column].defocus();
				row = newRow;
				column = newColumn;
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

		if (newRow>=0 && newRow < ROWS && newColumn>=0 && newColumn < COLUMNS) {
			Block b1 = matrix[row][column];
			Block b2 = matrix[newRow][newColumn];
			e1 = b1.getEntity();
			e2 = b2.getEntity();
			canExchange = e1.isSwappable(e2);
			if (canExchange) {
				b1.swapEntity(b2);
				l1 = checkCombinations(row, column);
				l2 = checkCombinations(newRow, newColumn);
				l1.addAll(l2);
				System.out.println(l1.size());
				if(!l1.isEmpty())
				{
					System.out.println("Entro en el bucle");
					destroyed = destroyEntities(l1);
					columnsToCheck = fillBoard();
					remaining = checkRemainingCombinations(columnsToCheck);
					while (!remaining.isEmpty()) {
						destroyed.addAll(destroyEntities(remaining));
						columnsToCheck = fillBoard();
						remaining = checkRemainingCombinations(columnsToCheck);
					}
				}
				//else
					//b1.swapEntity(b2);
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
	//METODO BALTASAR
	private List<Block> checkCombinations(int row,int column)
	{
		List<Block> combination = new LinkedList<Block>();
		Colour color = matrix[row][column].getEntity().getColour();
		int cantHorizontal =checkSeguidosH(row,column,combination);
		int cantVertical = checkSeguidosV(row,column,combination);
		if(cantHorizontal >=3 && cantVertical>=3)
			matrix[row][column].setEntity(new Wrapped(row,column,color));
		else if(cantHorizontal ==4 && cantVertical<3)
			matrix[row][column].setEntity(new Stripped(row,column,color,true));
		else if(cantHorizontal <3 && cantVertical==4)
			matrix[row][column].setEntity(new Stripped(row,column,color,false));
		else if(cantHorizontal==3 || cantVertical==3)
			combination.add(matrix[row][column]);
		return combination;
	}
	private int checkSeguidosH(int row, int column, List<Block> combination)
	{
		List<Block> toAdd = new LinkedList<Block>();
		Entity comparable = matrix[row][column].getEntity();
		boolean cumple = true;
		for(int i = row+1 ;i>=0 && i<ROWS && cumple;i++)aaaassa
		{
			cumple = matrix[i][column].getEntity().getColour()==comparable.getColour();
			if(cumple)
				toAdd.add(matrix[i][column]);
		}
		cumple = true;
		for(int i = row-1;i>=0 && i<ROWS && cumple;i--)
		{
			cumple = matrix[i][column].getEntity().getColour()==comparable.getColour();
			if(cumple) 
			{
				toAdd.add(matrix[i][column]);
			}
		}
		if(toAdd.size()>=2)
			combination.addAll(toAdd);
		return toAdd.size();
	}
	private int checkSeguidosV(int row, int column, List<Block> combination)
	{
		List<Block> toAdd = new LinkedList<Block>();
		Entity comparable = matrix[row][column].getEntity();
		boolean cumple = true;
		for(int j = column+1 ;j>=0 && j<COLUMNS && cumple;j++)
		{
			cumple = matrix[row][j].getEntity().getColour()==comparable.getColour();
			if(cumple)
				toAdd.add(matrix[row][j]);
		}
		cumple = true;
		for(int j = column-1;j>=0 && j<COLUMNS && cumple;j--)
		{
			cumple = matrix[row][j].getEntity().getColour()==comparable.getColour();
			if(cumple) 
			{
				toAdd.add(matrix[row][j]);
			}
		}
		if(toAdd.size()>=2)
			combination.addAll(toAdd);
		return toAdd.size();
	}//END Problema en checkRemaining ya que puede destruirse el rayado creado
}
