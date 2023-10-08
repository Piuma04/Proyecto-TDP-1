package Logic;

import GUI.Gui;

public class Timer {

    private Gui myGui;
    private Game myGame;
    private long seconds, minutes;
    private boolean stopped;
    private Runnable myTask;
    private Thread myThread;

    public Timer(Game game, Gui gui) {
        super();
        myGame = game;
        myGui = gui;
        seconds = 0;
        minutes = 0;
        stopped = true;
        
        myTask = () -> {
            myGui.setTime(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            while ((seconds > 0 || minutes > 0) && !stopped) {
                try { Thread.sleep(1000); } catch (InterruptedException e) { System.out.println(e.getMessage()); }
                seconds--;
                if (seconds < 0) {
                    minutes--;
                    seconds = 59;
                }
                myGui.setTime(String.format("%02d", minutes) + ":" + String.format("%02d", seconds));
            }
            if (!stopped)
                myGame.timerEnded();
        };
        myThread = new Thread(myTask);
    }

    public void startTimer(long time) {
        minutes = time / 60;
        seconds = time % 60;
        stopped = false;
        if (!myThread.isAlive()) { myThread = new Thread(myTask); myThread.start(); }
    }

    public void stopTimer() { stopped = true; }
}
