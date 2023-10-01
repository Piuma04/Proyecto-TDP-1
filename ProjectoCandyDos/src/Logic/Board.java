package Logic;

import java.util.List;

import java.util.LinkedList;
import java.util.Set;

import Entities.Block;
import Entities.Candy;
import Entities.Colour;
import Entities.Entity;
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

	public Set<Integer> fillBoard() {
		Set<Integer> s = new HashSet<Integer>();
		boolean canNext = false;
		for (int j = COLUMNS - 1; j >= 0 && s.size() < COLUMNS; j--) {
			for (int i = ROWS - 1; i >= 0 && s.size() < COLUMNS; i--) {
				if (matrix[i][j].isEmpty() && !s.contains(j)) {
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

	public Block getBlock(int row, int column) {
		return matrix[row][column];
	}

	public List<Equivalent> destroyEntities(List<Block> l) {
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		for (Block b : l) {
			destroyed.addAll(b.getEntity().getDestroyables(this));
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
		Block b1 = matrix[row][column];
		Block b2 = matrix[newRow][newColumn];

		if (newRow>=0 && newRow < ROWS && newColumn>=0 && newColumn < COLUMNS) {
			e1 = b1.getEntity();
			e2 = b2.getEntity();
			canExchange = e1.isSwappable(e2);
			if (canExchange) {
				b1.swapEntity(b2);
				l1 = checkCombinations(row, column);
				l2 = checkCombinations(newRow, newColumn);
				l1.addAll(l2);
				if(!l1.isEmpty())
				{
					destroyed = destroyEntities(l1);
					columnsToCheck = fillBoard();
					remaining = checkRemainingCombinations(columnsToCheck);
					while (!remaining.isEmpty()) {
						destroyed.addAll(destroyEntities(remaining));
						columnsToCheck = fillBoard();
						remaining = checkRemainingCombinations(columnsToCheck);
					}
				}
				else
					b1.swapEntity(b2);
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

	/**
	 * Metodo privado que chequea las combinaciones de la entidad del bloque dado la
	 * fila y la columna
	 * 
	 * @param row    fila
	 * @param column columna
	 * @return lista de bloques destruibles
	 */
	private List<Block> checkCombinations(int row, int column) {
		List<Block> toReturn = new LinkedList<Block>();
		if (match3(row, column, toReturn)) {
		} else if (match4(row, column, toReturn)) { // TODO falta verificar si es horizontal o vertical
		} else if (matchT(row, column, toReturn)) {
			matrix[row][column].createWrapped();
		} else if (matchL(row, column, toReturn)) {
			matrix[row][column].createWrapped();
		} else {
		} // no hay combinacion
		return toReturn;
	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean match3(int row, int column, List<Block> list) {
		if (check3Medio(row, column, list)) // se fija vertical y horizontalmente
			return true;
		if (check3Arriba(row, column, list))
			return true;
		else if (check3Abajo(row, column, list))
			return true;
		else if (check3Der(row, column, list))
			return true;
		else if (check3Izq(row, column, list))
			return true;
		return false;

	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3Medio(int row, int column, List<Block> list) {
		return check3MedioH(row, column, list) || check3MedioV(row, column, list);

	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3MedioV(int row, int column, List<Block> list) {
		if (row - 1 >= 0 && row + 1 < matrix.length) {
			if (matrix[row - 1][column].getEntity().getColour() == matrix[row][column].getEntity().getColour()
					&& matrix[row][column].getEntity().getColour() == matrix[row + 1][column].getEntity().getColour()) {
				list.add(matrix[row - 1][column]);
				list.add(matrix[row][column]);
				list.add(matrix[row + 1][column]);
				return true;
			}
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3MedioH(int row, int column, List<Block> list) {
		if (column - 1 >= 0 && column + 1 < matrix[0].length) {
			if (matrix[row][column - 1].getEntity().getColour() == matrix[row][column].getEntity().getColour()
					&& matrix[row][column].getEntity().getColour() == matrix[row][column + 1].getEntity().getColour()) {
				list.add(matrix[row][column - 1]);
				list.add(matrix[row][column]);
				list.add(matrix[row][column + 1]);
				return true;
			}
		}
		return false;

	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3Arriba(int row, int column, List<Block> list) {
		// ver arriba
		List<Block> toAdd = new LinkedList<Block>();
		boolean toRet = false;
		Entity ent = matrix[row][column].getEntity();
		int cont = 0;
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int r = row; r >= 0 && !toRet; r--) {
			if (matrix[r][column].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[r][column]);
				if (cont == 3)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3Abajo(int row, int column, List<Block> list) {
		List<Block> toAdd = new LinkedList<Block>();
		boolean toRet = false;
		Entity ent = matrix[row][column].getEntity();
		int cont = 0;
		// ver abajo
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int r = row; row < matrix.length && !toRet; r++) {
			if (matrix[r][column].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[r][column]);
				if (cont == 3)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3Der(int row, int column, List<Block> list) {
		List<Block> toAdd = new LinkedList<Block>();
		boolean toRet = false;
		Entity ent = matrix[row][column].getEntity();
		int cont = 0;
		// hoz derecha
		for (int c = column; c < matrix[0].length && !toRet; c++) {
			if (matrix[row][c].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[row][c]);
				if (cont == 3)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 3 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check3Izq(int row, int column, List<Block> list) {
		List<Block> toAdd = new LinkedList<Block>();
		boolean toRet = false;
		Entity ent = matrix[row][column].getEntity();
		int cont = 0;
		// hoz izq
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int c = column; c >= 0 && !toRet; c--) {
			if (matrix[row][c].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[row][c]);
				if (cont == 3)
					toRet = true;
			} else
				break;
		}

		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 4 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean match4(int row, int column, List<Block> list) {
		List<Block> toAdd = new LinkedList<Block>();
		boolean toRet = false;
		Entity ent = matrix[row][column].getEntity();
		int cont = 0;

		if (check4Medio(row, column, list)) // se fija vertical y horizontalmente
			return true;

		if (check4Medio(row, column, list)) // se fija vertical y horizontalmente
			return true;

		// hoz derecha
		for (int c = column; c < matrix[0].length && !toRet; c++) {
			if (matrix[row][c].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[row][c]);
				if (cont == 4)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}

		// ver abajo
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int r = row; row < matrix.length && !toRet; r++) {
			if (matrix[r][column].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[r][column]);
				if (cont == 4)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}

		// ver arriba
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int r = row; r >= 0 && !toRet; r--) {
			if (matrix[r][column].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[r][column]);
				if (cont == 4)
					toRet = true;
			} else
				break;
		}
		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}

		// hoz izq
		toRet = false;
		cont = 0;
		toAdd = new LinkedList<Block>();
		for (int c = column; c >= 0 && !toRet; c--) {
			if (matrix[row][c].getEntity().getColour() == ent.getColour()) {
				cont++;
				toAdd.add(matrix[row][c]);
				if (cont == 4)
					toRet = true;
			} else
				break;
		}

		if (toRet) {
			list.addAll(toAdd);
			list.add(matrix[row][column]);
			return toRet;
		}

		return false;
	}

	// TODO FALTA VER COMO DIFERENCIAR 4 HORIZONTAL Y VERTICAL

	/**
	 * Metodo privado que chequea que haya match 4 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check4Medio(int row, int column, List<Block> list) {
		return check4MedioH(row, column, list) || check4MedioV(row, column, list);
	}

	/**
	 * Metodo privado que chequea que haya match 4 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check4MedioH(int row, int column, List<Block> list) {
		if (row - 2 >= 0 && row + 1 < matrix.length) {
			// if(matrix[row-2][column].get)
			// {}
			if (row - 2 >= 0 && row + 1 < matrix.length)
				if (matrix[row - 2][column].getEntity().getColour() == matrix[row][column].getEntity().getColour()
						&& matrix[row - 1][column].getEntity().getColour() == matrix[row][column].getEntity()
								.getColour()
						&& matrix[row][column].getEntity().getColour() == matrix[row + 1][column].getEntity()
								.getColour()) {
					list.add(matrix[row - 2][column]);
					list.add(matrix[row - 1][column]);
					list.add(matrix[row][column]);
					list.add(matrix[row + 1][column]);
					return true;
				}
		}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match 4 dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean check4MedioV(int row, int column, List<Block> list) {
		if (column - 2 >= 0 && column + 1 < matrix.length)
			if (matrix[row][column - 2].getEntity().getColour() == matrix[row][column].getEntity().getColour()
					&& matrix[row][column - 1].getEntity().getColour() == matrix[row][column].getEntity().getColour()
					&& matrix[row][column].getEntity().getColour() == matrix[row][column + 1].getEntity().getColour()) {
				list.add(matrix[row][column - 2]);
				list.add(matrix[row][column - 1]);
				list.add(matrix[row][column]);
				list.add(matrix[row][column + 1]);
				return true;
			}
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match T dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean matchT(int row, int column, List<Block> list) {
		// T
		if (check3MedioH(row, column, list) && (check3Arriba(row, column, list) || check3Abajo(row, column, list)))
			return true;

		// T "apaisada"
		if (check3MedioV(row, column, list) && (check3Der(row, column, list) || check3Izq(row, column, list)))
			return false;
		return false;
	}

	/**
	 * Metodo privado que chequea que haya match L dada la posicion de la matriz
	 * dada
	 * 
	 * @param row    fila
	 * @param column columna
	 * @param list   lista de destruibles
	 * @return lista de destruibles a retornar
	 */
	private boolean matchL(int row, int column, List<Block> list) {
		if (check3Arriba(row, column, list) && (check3Der(row, column, list) || check3Izq(row, column, list)))
			return true;
		else if (check3Abajo(row, column, list) && (check3Der(row, column, list) || check3Izq(row, column, list)))
			return true;
		return false;
	}

	// DEBUG
	public void showMatrix() {
		for (Block[] row : matrix) {
			for (Block block : row) {
				System.out.print(block.getEntity().toString() + "   ");
			}
			System.out.println();
		}
	}
}
