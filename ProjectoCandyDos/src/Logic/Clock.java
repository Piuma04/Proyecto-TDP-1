package Logic;

import GUI.Gui;

public class Clock extends Thread {
    
	
	private long seconds, minutes;
	private Gui myGui;
	private Game myGame;
    
    public Clock(Gui mG, long timeStart, Game mGa) {
    	super();
    	myGui = mG;
    	myGame = mGa;
    	seconds = timeStart;
    }
    
   public void run() {
	   minutes = seconds/60;
	   seconds = seconds%60;
	   myGui.setTime(minutes+":"+seconds);
	   while(seconds>0||minutes>0) {
	   		try {sleep(1000);} catch (InterruptedException e) {System.out.println(e.getMessage());}
	   		seconds--;
	   		if(seconds < 0) {
	   			minutes--;
	   			seconds = 59;
	   		}
	   		myGui.setTime(minutes+":"+seconds);
	   }
	   myGame.lost();
	  
   }
}
