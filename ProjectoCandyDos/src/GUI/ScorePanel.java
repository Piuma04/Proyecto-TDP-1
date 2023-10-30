package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.util.regex.PatternSyntaxException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Logic.LevelGenerator;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {

    protected JPanel scoreBox;
    
    protected JLabel[] scores;

    public ScorePanel() {
        super();
        setLayout(new BorderLayout());
        setOpaque(false);

        scoreBox = new JPanel();
        scoreBox.setLayout(new BoxLayout(scoreBox, BoxLayout.Y_AXIS));
        
        int padding = 30;
        Border borderPadding = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        scoreBox.setBorder(borderPadding);
        
        scoreBox.setOpaque(false);
        add(scoreBox, BorderLayout.EAST);

        scores = new JLabel[5];
        for (int i = 0; i < scores.length; i++) {
            JLabel currentScore = new JLabel(); 
            scores[i] = currentScore;
            currentScore.setForeground(Color.GREEN);
            scoreBox.add(currentScore);
        }

        updateScore();
    }

    public void updateScore() {
        List<String> maxScores = getMaxScores();
        String[] playerAndScore = null;
        for (int i = 0; i < scores.length; i++) {
        	
           try {
        	   playerAndScore = maxScores.get(i).split(",");
        	   if(Integer.valueOf(playerAndScore[1])!=-1)
        		scores[i].setText(playerAndScore[0]+": "+ playerAndScore[1]);
           }catch(ArrayIndexOutOfBoundsException | PatternSyntaxException e) {System.out.println(e.getMessage());}
        }
    }

    private List<String> getMaxScores() {
        List<String> lines = LevelGenerator.readFileLines(Resources.getScorePath());
        return lines;
    }
    
    /*@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // You can add custom painting code here if needed
    }*/
}
