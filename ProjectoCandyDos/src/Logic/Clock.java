package Logic;

import java.util.concurrent.TimeUnit;

import GUI.Gui;

public class Clock extends Thread{
    
	
	private long startTime;
	private Gui myGui;
	private Game myGame;
    
    
    
    public Clock(Gui mG, long timeStart, Game mGa) {
    	super();
    	myGui = mG;
    	myGame = mGa;
    	startTime = timeStart;
    }
    
   public void run() {
	   myGui.setTime(startTime);
	   while(startTime>0) {
	   		try {sleep(1000);} catch (InterruptedException e) {System.out.println(e.getMessage());}
	   		startTime--;
	   		myGui.setTime(startTime);
	   }
	   myGame.lost();
	  
   }
}
