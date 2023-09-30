package Logic;

import java.util.List;
import Entities.Interfaces.Equivalent;

import Entities.Block;

public class Board {
	private int row, column, cantR, cantC;
	private Block[][] matriz;
	private Game myGame;
	
	
	public int getRows() {
		return cantR;
	}
	
	public int getColumns() {
		return cantC;
	}
	
	
	public void fillBoard() {
		
	}
	
	public void movePlayerDirection(int direction) {
		switch(direction) {
		case Juego.ABAJO:{
			setPlayerPosition(row + 1, column);
			break;
		}
		case Juego.ARRIBA:{
			setPlayerPosition(row - 1, column);
			break;
		}
		case Juego.IZQUIERDA:{
			setPlayerPosition(row, column - 1);
			break;
		}
		case Juego.DERECHA:{
			setPlayerPosition(row, column + 1);
			break;
		}
		}
	}
	
	public PositionList<Equivalent> swap(int direction){
		
	}
	
	public Block getBlock(int row, int column) {
		
	}
	
	public PositionList<Equivalent> destroyEntities(PositionList<Block> l){
		
	}
	
	
	private void setPlayerPosition(int newRow, int newColumn)
	{
		if ( (0 <= newRow) && (newRow < cantR) && (0 <= newColumn) && (newColumn < cantC)) {
			if (matriz[newRow][newRow].focus()) {
				matriz[row][column].defocus();
				row = newRow;
				column = newColumn;
			}
		}
	}
	private List<Equivalent> swapEntities()
	{
		
	}
	private void checkRemainingCombinations()
	{
		
	}
	private List<Block> checkCombinations(int row, int column)
	{
		
	}
}
