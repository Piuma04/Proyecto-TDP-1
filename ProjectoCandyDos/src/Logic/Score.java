package Logic;


import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import GUI.Resources;
import Interfaces.Equivalent;
public class Score {
	private int score;
	
	public Score()
	{
		score = 0;
	}
	public int update(List<Equivalent> values)
	{
		for(Equivalent e: values)
			score += e.getScore();
		return score;
	}
	public void setNewScores() {
		//here the new score would be put in the maxScores.txt file
    	List<String> maxScores = LevelGenerator.readFileLines(Resources.getScorePath());
    	boolean foundPos = false;
    	int i = 0;
    	FileWriter fileWriter;  PrintWriter printWriter;
    	while(i<5 && !foundPos) {
    		if(Integer.parseInt(maxScores.get(i)) < score ) {
    			foundPos = true;
    			maxScores.add(i, String.valueOf(score));
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
