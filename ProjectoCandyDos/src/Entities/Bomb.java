package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;

import Enums.Colour;

import Logic.Block;
import Logic.Board;

public class Bomb extends Entity {
    // private Timer timer;
    public Bomb(int rowPosition, int columnPosition) {
        super(rowPosition, columnPosition, Colour.BOMB);
        // timer = new Timer();
        // timer.start();
    }

    @Override public boolean isEquivalent(Equivalent e) { return e.isEquivalent(this); }
    @Override public boolean isEqual(Bomb b)            { return true; }
    @Override public boolean hasCollateralDamage()		{ return true; }

    @Override public int getScore() { return 150; }
    
    public String toString() { return super.setStringColor("Q");}

	@Override
	public List<Block> getDestroyables(Board b) {
		List<Block> toReturn = new LinkedList<Block>();
		toReturn.add(b.getBlock(row, column));
		//TODO donde estaria para notificar que el juego termino
		//timer.stop();
		return toReturn;
	}	
}
