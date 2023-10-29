package Logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
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
		
		
	}
}
