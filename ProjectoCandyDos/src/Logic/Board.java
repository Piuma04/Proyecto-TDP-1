package Logic;

import java.util.List;
<<<<<<< HEAD
import java.util.LinkedList;
import java.util.Set;
import java.util.HashSet;
=======
import Entities.Interfaces.Equivalent;
>>>>>>> branch 'master' of https://github.com/Piuma04/Proyecto-TDP-1.git

import Entities.Block;
import Entities.Entity;
import Entities.Interfaces.Equivalent;

public class Board {
	private int row, column,cantR,cantC;
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
		case myGame.ABAJO:{
			setPlayerPosition(row + 1, column);
			break;
		}
		case myGame.ARRIBA:{
			setPlayerPosition(row - 1, column);
			break;
		}
		case myGame.IZQUIERDA:{
			setPlayerPosition(row, column - 1);
			break;
		}
		case myGame.DERECHA:{
			setPlayerPosition(row, column + 1);
			break;
		}
		}
	}
	
	public List<Equivalent> swap(int direction){
		switch(direction) {
		case myGame.ABAJO:{
			swapEntities(row+1, column);
			break;
		}
		case myGame.ARRIBA:{
			swapEntities(row - 1, column);
			break;
		}
		case myGame.IZQUIERDA:{
			swapEntities(row, column - 1);
			break;
		}
		case myGame.DERECHA:{
			swapEntities(row, column + 1);
			break;
		}
	}
	}
	
	public Block getBlock(int row, int column) {
		return matriz[row][column];
	}
	
	public List<Equivalent> destroyEntities(List<Block> l){
		return null;
		
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
	private List<Equivalent> swapEntities(int newRow, int newColumn)
	{
		Entity e1,e2;
		List<Block> l1,l2;
		List<Equivalent> destroyed = new LinkedList<Equivalent>();
		boolean canExchange = false;
		Block b1 = matriz[row][column];
		Block b2 = matriz[newRow][newColumn];
		
		if(newRow<matriz.length && newColumn<matriz[0].length)
		{
			e1 = b1.getEntity();
			e2 = b2.getEntity();
			canExchange = e1.isSwappable(e2);
			if(canExchange)
			{
				b1.swapEntity(b2);
				l1 = checkCombinations(row,column);
				l2 = checkCombinations(newRow,newColumn);
				l1.addAll(l2);
				destroyed = destroyEntities(l1);
				//checkRemainingCombinations(); ver Detalles
			}
		}
		return destroyed;
	}
	private List<Block> checkRemainingCombinations() 
	{
		Set<Integer> s = new HashSet<Integer>();
		List<Block> temp = new LinkedList<Block>();
		for(int j =cantC; j>0 && s.size()<cantC;j--)
		{
			for(int i =cantR; i>0 && s.size()<cantC;i--)
			{
				if(matriz[i][j].isEmpty() && !s.contains(j))
				{
					s.add(j);
				}
			}
		}
		fillBoard();
		for(Integer j : s)
		{
			for(int i = 0;i<cantR;i++)
			{
				temp.addAll(checkCombinations(i,j));
			}
		}
		return temp;
	}
	private List<Block> checkCombinations(int row, int column)
	{
		return null;
		
	}
}
