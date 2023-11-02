package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import Logic.Board;
import Logic.Game;
import VisualPlayers.Resources;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private static final String font = "Stencil";
    //private static final String font = "DejaVu";
    //private static final String font = "MonoLisa";

    private static final String[] resources = { "bg_board.gif", "life.gif", "bg_gamedata.png" };

    protected JPanel contentPanel;

    protected JPanel boardPanel;
    protected JPanel gameDataPanel;

    private JLabel live1;
    private JLabel live2;
    private JLabel live3;
    private JLabel typeOfCandy[];
    private JLabel amountToGo[];

    private JLabel cantMoves, levelShower, watch;

    private JLabel score;
    
    protected Color stringColor;
    private ImageIcon boardBackground;
    private ImageIcon gameDataBackground;

    public GamePanel(Dimension windowSize) {
        super();
        setPreferredSize(windowSize);

        contentPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                gameDataBackground.paintIcon(this, g, 0, 0);
            }
        };
        contentPanel.setBackground(Color.BLACK);

        contentPanel.setPreferredSize(windowSize);
        contentPanel.setSize(windowSize);
        add(contentPanel);
        Game.setLabelSize(getPreferredSize().height / Board.getRows());
        
        stringColor = new Color(255, 255, 255);

        initializeContentPanel();
    }

    protected void initializeContentPanel() {
        initializeBoardPanel();
        initializeGameDataPanel();
        updateResources();
        contentPanel.add(boardPanel, BorderLayout.WEST);
        contentPanel.add(gameDataPanel);
    }

    private void initializeBoardPanel() {
        boardPanel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                boardBackground.paintIcon(this, g, 0, 0);
            }
        };

        int boardWidth  = Game.getLabelSize()*Board.getRows();
        int boardHeight = Game.getLabelSize()*Board.getColumns();

        boardPanel.setLocation(0, 0);
        boardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));
        //boardPanel.setSize(boardPanel.getPreferredSize());
        boardPanel.setLayout(null);

        boardPanel.setOpaque(false);
    }

    private void initializeGameDataPanel() {
        gameDataPanel =  new JPanel();
        gameDataPanel.setLayout(new BoxLayout(gameDataPanel, BoxLayout.Y_AXIS));
        

        live1 = new JLabel();
        live2 = new JLabel();
        live3 = new JLabel();
        levelShower = new JLabel();
        typeOfCandy = new JLabel[3];
        amountToGo = new JLabel[3];
        cantMoves = new JLabel();
        watch = new JLabel();
        score = new JLabel();
        
        int padding = 10;
        Border borderPadding = BorderFactory.createEmptyBorder(padding, padding, padding, padding);

        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        levelPanel.setOpaque(false);
        levelShower.setFont(new Font(font, Font.PLAIN, 20));
        levelPanel.add(levelShower, BorderLayout.CENTER);
        

        // START SET UP LIVES.
        JPanel livesPanel = new JPanel(new GridLayout(1,0));
        livesPanel.setBorder(borderPadding);
        livesPanel.add(live1, BorderLayout.WEST);
        livesPanel.add(live2, BorderLayout.CENTER);
        livesPanel.add(live3, BorderLayout.EAST);
        // END SET UP LIVES.

        // START SET UP WATCH.
        JPanel watchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        watchPanel.setOpaque(false);
        watchPanel.setBorder(borderPadding);
        watchPanel.add(watch);
        // END SET UP WATCH.

        // START SET UP MOVIMIENTOS RESTANTES.
        JPanel movesPanel = new JPanel(new FlowLayout());
        movesPanel.setBorder(borderPadding);
        movesPanel.add(cantMoves);
        // END SET UP MOVIMIENTOS RESTANTES.

        
        score.setText("Puntaje: 0");
        JPanel scorePanel = new JPanel(new FlowLayout());
        scorePanel.setBorder(borderPadding);
        scorePanel.add(score);
        
        gameDataPanel.add(levelPanel);
        gameDataPanel.add(livesPanel);
        gameDataPanel.add(watchPanel);
        gameDataPanel.add(movesPanel);
        gameDataPanel.add(scorePanel);

        // START SET UP GOALS.
        JPanel[] goalsPanels = new JPanel[typeOfCandy.length]; 

        JPanel goalPanel = null;
        JLabel typeOfCandyLabel = null;
        JLabel amountToGoLabel = null;

        
        for (int i = 0; i < typeOfCandy.length; i++) {
            goalPanel = new JPanel(new BorderLayout());
            typeOfCandyLabel = new JLabel();
            amountToGoLabel = new JLabel();
            typeOfCandy[i] = typeOfCandyLabel;
            amountToGo[i] = amountToGoLabel;
            typeOfCandyLabel.setBorder(borderPadding);
            goalPanel.add(typeOfCandyLabel, BorderLayout.WEST);
            goalPanel.add(amountToGoLabel);
            gameDataPanel.add(goalPanel);

            goalsPanels[i] = goalPanel;
        }
        // END SET UP GOALS.

        // START SET LABEL COLORS.
        levelShower.setForeground(stringColor);
        watch.setForeground(stringColor);
        cantMoves.setForeground(stringColor);
        score.setForeground(stringColor);
        for (JLabel typeCandy : typeOfCandy)
            typeCandy.setForeground(stringColor);
        for (JLabel amount : amountToGo)
            amount.setForeground(stringColor);
        // END SET LABEL COLORS.

        // START SET OPACITY TO SEE BACKGROUND.

        gameDataPanel.setOpaque(false);

        // LABELS.
        /*live1.setOpaque(false);
        live2.setOpaque(false);
        live3.setOpaque(false);
        levelShower.setOpaque(false);
        for (JLabel typeCandy : typeOfCandy)
            typeCandy.setOpaque(false);
        for (JLabel amount : amountToGo)
            amount.setOpaque(false);
        cantMoves.setOpaque(false);
        watch.setOpaque(false);*/
        
        // PANELS.
        levelPanel.setOpaque(false);
        livesPanel.setOpaque(false);
        watchPanel.setOpaque(false);
        movesPanel.setOpaque(false);
        scorePanel.setOpaque(false);
        for (JPanel goal : goalsPanels)
            goal.setOpaque(false);
        // END SET OPACITY TO SEE BACKGROUND.
    }

    public void updateLives(int lives) {
        live1.setVisible(!(lives < 1));
        live2.setVisible(!(lives < 2));
        live3.setVisible(!(lives < 3));
    }

    public void updateMoves(int i) { cantMoves.setText("Movimientos Restantes: " + i); }
    
    public void addEntity(Drawable gentity) { boardPanel.add(gentity); }

    public void removeEntity(Drawable gentity) {
        boardPanel.remove(gentity);
        boardPanel.repaint();
    }

    public void showObjective(List<String> entities, List<Integer> remaining) {
        for (int i = 0; i < typeOfCandy.length; i++) {
            typeOfCandy[i].setVisible(false);
            amountToGo[i].setVisible(false);
        }
        for (int i = 0; i < entities.size(); i++) {
            typeOfCandy[i].setVisible(true);
            amountToGo[i].setVisible(true);

            // set entity image.
            ImageIcon imageIconAux = new ImageIcon(Resources.getImagesFolderPath() + entities.get(i));
            imageIconAux.setImage(imageIconAux.getImage().getScaledInstance(Game.getLabelSize(), Game.getLabelSize(), Image.SCALE_DEFAULT));
            typeOfCandy[i].setIcon(imageIconAux);

            amountToGo[i].setText(remaining.get(i).toString());
        }
    }

    public void updateGraphicObjective(List<Integer> list) {
        for (int i = 0; i < list.size(); i++)
            amountToGo[i].setText(list.get(i).toString());
    }
    
    public void updateScore(int i) {
    	score.setText("Puntaje: "+i);
    }

    public void setCurrentLevel(String level) { levelShower.setText(level); }
    public void setTime(String timeString) { watch.setText("Tiempo restante: " + timeString); }

    public void reset() { boardPanel.removeAll(); }

    public void updateResources() {
        Dimension s = boardPanel.getPreferredSize();
        boardBackground = new ImageIcon(Resources.getImagesFolderPath() + resources[0]);
        boardBackground.setImage(boardBackground.getImage().getScaledInstance(s.width, s.height, 0));
        
        s = contentPanel.getPreferredSize();
        gameDataBackground = new ImageIcon(Resources.getImagesFolderPath() + resources[2]);
        gameDataBackground.setImage(gameDataBackground.getImage().getScaledInstance(s.width, s.height, 0));

        int liveIconSize = (int)((contentPanel.getPreferredSize().getWidth() - boardPanel.getPreferredSize().getWidth()) / 4.0);
        ImageIcon liveIcon = new ImageIcon(Resources.getImagesFolderPath() + resources[1]);
        liveIcon.setImage(liveIcon.getImage().getScaledInstance(liveIconSize, liveIconSize, Image.SCALE_DEFAULT));
        live1.setIcon(liveIcon);
        live2.setIcon(liveIcon);
        live3.setIcon(liveIcon);
    }
    
}
