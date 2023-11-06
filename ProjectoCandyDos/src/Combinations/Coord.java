package Combinations;

public class Coord {

	private int row;
	private int column;
	public Coord(int row, int column)
	{
		this.row = row;
		this.column = column;
	}
	public Coord() {
		row = 0;
		column = 0;
	}
	public int getRow()
	{
		return row;
	}
	public int getColumn()
	{
		return column;
	}
	public void setRow(int row)
	{
		this.row = row;
	}
	public void setColumn(int column)
	{
		this.column = column;
	}
}
