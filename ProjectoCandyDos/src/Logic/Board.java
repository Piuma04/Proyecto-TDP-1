package Logic;

import java.util.List;

public class Board {
	private int posX, posY, rows, columns;
	private Block[][] matriz;
	private Game myGame;
	
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public void fillBoard() {
		
	}
	public void movePlayerDirection(int direction) {
		switch(direction) {
		case Juego.ABAJO:{
			mover_jugador_auxiliar(posY + 1, posX);
			break;
		}
		case Juego.ARRIBA:{
			mover_jugador_auxiliar(posY - 1, posX);
			break;
		}
		case Juego.IZQUIERDA:{
			mover_jugador_auxiliar(posY, posX - 1);
			break;
		}
		case Juego.DERECHA:{
			mover_jugador_auxiliar(posY, posX + 1);
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
