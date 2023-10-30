package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
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
    protected JLabel[] usernames;
    protected JLabel[] scores;

    protected Color stringColor;

    public ScorePanel() {
        super();
        setLayout(new BorderLayout());
        setOpaque(false);

        stringColor = Color.GREEN;
        int padding = 30;
        Border borderPadding = BorderFactory.createEmptyBorder(padding, padding, padding, padding);

        usernames = new JLabel[5];
        scores = new JLabel[5];

        scoreBox = new JPanel();
        scoreBox.setLayout(new BoxLayout(scoreBox, BoxLayout.Y_AXIS));        
        scoreBox.setBorder(borderPadding);
        
        JLabel scoreTitle = new JLabel("MAXIMUM SCORES");
        scoreBox.add(scoreTitle);
        
        add(scoreBox, BorderLayout.EAST);

        JPanel[] users_scores_panel = new JPanel[5];

        JPanel currentUserScore = null;
        JLabel currentUsername = null;
        JLabel currentScore = null;

        for (int i = 0; i < scores.length; i++) {
            currentUserScore = new JPanel(new GridLayout(1, 2));

            currentUsername = new JLabel();
            currentScore = new JLabel();

            usernames[i] = currentUsername;
            scores[i] = currentScore;

            currentUserScore.add(currentUsername);
            currentUserScore.add(currentScore);

            users_scores_panel[i] = currentUserScore;

            scoreBox.add(currentUserScore);
        }

        // START SET JLABEL COLOR
        scoreTitle.setForeground(stringColor);
        for (JLabel s : scores)
            s.setForeground(stringColor);
        for (JLabel u : usernames)
            u.setForeground(stringColor);
        // END SET JLABEL COLOR

        // START SET JPANEL TRANSPARENCY
        scoreBox.setOpaque(false);
        for (JPanel user_score : users_scores_panel)
            user_score.setOpaque(false);
        // END SET JPANEL TRANSPARENCY

        updateScore();
    }

    public void updateScore() {
        List<String> maxScores = getMaxScores();
        String[] playerAndScore = null;
        for (int i = 0; i < scores.length; i++) {
            try {
                playerAndScore = maxScores.get(i).split(",");
                if (Integer.valueOf(playerAndScore[1]) != -1) {
                    usernames[i].setText(playerAndScore[0]);
                    scores[i].setText(playerAndScore[1]);
                }
            }
            catch (ArrayIndexOutOfBoundsException | PatternSyntaxException e) { e.printStackTrace(); }
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
