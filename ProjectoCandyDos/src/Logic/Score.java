package Logic;

import java.util.List;
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
}
