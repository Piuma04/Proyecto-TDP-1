package Entities;

import java.util.LinkedList;
import java.util.List;

import Interfaces.Equivalent;
import Interfaces.GameOverOnly;
import Interfaces.PausableObserver;
import Enums.Colour;
import GUI.GraphicalEntity;
import Logic.Block;
import Logic.Board;

public class Bomb extends Entity implements PausableObserver{
    
	protected GameOverOnly finalized;
	protected Runnable myTask;
	protected Thread myThread;
	protected int seconds;
	
	protected boolean stopped;
	
    public Bomb(int rowPosition, int columnPosition, GameOverOnly game, int sec) {
        super(rowPosition, columnPosition, Colour.BOMB);
        finalized = game;
        seconds = sec ;
        stopped = false;
    
        myTask = () -> {
            while (seconds >= 0 && !stopped) {
            	
            	if(gEntity!=null) gEntity.setText(String.valueOf(seconds));
                try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
                 seconds--;
                
            }
            
            if(!stopped) finalized.finalLost();
        };

        myThread = new Thread(myTask);
      
        myThread.start();
    }
    
    public void destroy() {
    	
        gEntity.setText("");
        finalized.removePausableObserver(this);
        stopped = true;
        playGif(explosionGif);
        setImage(null); 
        
    }
    
    public void setGraphicalEntity(GraphicalEntity graphicalEntity) { 
    	gEntity = graphicalEntity;
    	gEntity.setText(String.valueOf(seconds));
    }
    
    @Override public boolean isEquivalent(Equivalent e) { return e.isEquivalent(this); }
    @Override public boolean isEqual(Bomb b)            { return true; }
    @Override public boolean hasCollateralDamage()		{ return true; }
    @Override public void update(boolean isPaused) { 
    	stopped = isPaused;
    	if(!stopped &&  !myThread.isAlive()){ 
			myThread = new Thread(myTask); 
			myThread.start(); 
		}
    }
    
    @Override public int getScore() { return 150; }
    
    
    public String toString() { return super.setStringColor("Q");}
    

	@Override
	public List<Block> getDestroyables(Board b) {
		List<Block> toReturn = new LinkedList<Block>();
		toReturn.add(b.getBlock(row, column));
		return toReturn;
	}	
}
