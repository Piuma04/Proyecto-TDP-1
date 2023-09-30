package Logic;

import java.util.List;

public class Board {
	private int posX, posY, rows, columns;
	private Block[][] matriz;
	private Game myGame;
	private void setPlayerPosition(int newRow, int newColumn)
	{
		posY = newRow;
		posX = newColumn;
	}
	private List<Equivalent> swapEntities()
	{
		
	}
	private Block getBlock(int row, int column)
	{
		return matriz[row][column];
	}
	private void checkRemainingCombinations()
	{
		
	}
	private List<Block> checkCombinations(int row, int column)
	{
		
	}
}
