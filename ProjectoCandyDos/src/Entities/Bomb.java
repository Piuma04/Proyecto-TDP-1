package Entities;

import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import Interfaces.Equivalent;
import Interfaces.GameOverOnly;
import Enums.Colour;

import Logic.Block;
import Logic.Board;

public class Bomb extends Entity {
    
	protected GameOverOnly finalized;
	protected Runnable myTask;
	protected Thread myThread;
	protected int seconds;
	
    public Bomb(int rowPosition, int columnPosition, GameOverOnly game) {
        super(rowPosition, columnPosition, Colour.BOMB);
        finalized = game;
        seconds = (int)Math.floor(Math.random() *(50 - 25 + 1) + 25) ;
        
        myTask = () -> {
            while (seconds > 0 ) {
            	
            	if(gEntity!=null) gEntity.setText(String.valueOf(seconds));
                try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
                seconds--;
                if(gEntity!=null) gEntity.setText(String.valueOf(seconds));
            }
            finalized.finalLost();
        };

        myThread = new Thread(myTask);
      
        myThread.start();
    }
    
    public void destroy() {
    	
       gEntity.setText("");
        myThread.stop();
        playGif(explosionGif);
        setImage(null); 
        
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
		return toReturn;
	}	
}
