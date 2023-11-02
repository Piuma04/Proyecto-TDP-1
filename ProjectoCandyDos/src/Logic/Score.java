package Logic;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import GUI.Gui;
import GUI.Resources;
import Interfaces.Equivalent;
import Interfaces.EventListener;
public class Score implements EventListener{
	private int score;
	private String playerName;
	private Gui myGui;
	public Score(Gui gui)
	{
		myGui = gui;
		playerName = gui.requestPlayerName();
		score = 0;
	}
	public void update(List<Equivalent> values)
	{
		for(Equivalent e: values)
			score += e.getScore();
		myGui.executeAfterAnimation(() -> {
			myGui.updateScore(score);
        });
	}
	public void setNewScores() {
		//here the new score would be put in the maxScores.txt file
    	List<String> maxScores = LevelGenerator.readFileLines(Resources.getScorePath());
    	 String[] playerAndScore = null;
    	boolean foundPos = false;
    	int i = 0;
    	FileWriter fileWriter;  PrintWriter printWriter;
    	while(i<5 && !foundPos) {
    		
    		 playerAndScore = maxScores.get(i).split(",");
    		if(Integer.parseInt(playerAndScore[1]) < score ) {
    			foundPos = true;
    			maxScores.add(i, playerName+","+String.valueOf(score));
    		}
    		else i++;
    	}
    	i = 0;
		try {
			fileWriter = new FileWriter(Resources.getScorePath());
    	    printWriter = new PrintWriter(fileWriter);
    	    printWriter.print(maxScores.get(i)+"\n");
    	    i++;
    	    while(i<maxScores.size()-1) {printWriter.printf(maxScores.get(i)+"\n"); i++;}
    	    printWriter.close();
		} catch (IOException e) {System.out.println(e.getMessage());}
	
		
	}
	public void resetScore() {
		score=0;
		
	}
}
