package Entities;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;

import Interfaces.Equivalent;
import Interfaces.Swappable;
import Logic.Block;
import Logic.Board;

public class Bomb extends Entity {
	private Timer timer;

	public Bomb(int rowPosition ,int columnPosition) { 
		super(rowPosition, columnPosition, Colour.BOMB);
		timer = new Timer();
		//timer.start();
		}
	
	@Override public boolean isEquivalent(Equivalent e) {return e.isEquivalent(this);}
	@Override public boolean isEqual(Bomb b) {return true;}
	
	@Override public boolean isSwappable(Swappable e) { return e.canReceive(this);}
	@Override public boolean canReceive(Candy c)      { return true; }
    @Override public boolean canReceive(Stripped s)   { return true; }
    @Override public boolean canReceive(Wrapped w)    { return true; }
    @Override public boolean canReceive(MegaStripped m) { return true; }
    @Override public boolean canReceive(Bomb b) { return true; }

    @Override
    public void destroy() {
    	playGif("explosion.gif");
    	setImage(null);
    }
    
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
