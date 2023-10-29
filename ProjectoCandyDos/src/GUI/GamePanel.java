package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
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

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private static final String font = "Stencil";
    //private static final String font = "DejaVu";
    //private static final String font = "MonoLisa";

    private static final String[] resources = { "background.gif", "life.gif" };

    protected JPanel contentPanel;

    protected JPanel boardPanel;
    protected JPanel gameDataPanel;
    
    private JLabel live1;
    private JLabel live2;
    private JLabel live3;
    private JLabel typeOfCandy[];
    private JLabel amountToGo[];

    private JLabel cantMoves, levelShower, watch;

    private ImageIcon backgroundGif = new ImageIcon(Resources.getImagesFolderPath() + resources[0]);

    public GamePanel(Dimension windowSize) {
        setPreferredSize(windowSize);

        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setPreferredSize(windowSize);
        add(contentPanel);

        Game.setLabelSize(getPreferredSize().height / Board.getRows());
        initializeContentPanel();
    }

    protected void initializeContentPanel() {
        initializeBoardPanel();
        initializeGameDataPanel();
        contentPanel.add(boardPanel, BorderLayout.WEST);
        contentPanel.add(gameDataPanel);
    }

    private void initializeBoardPanel() {
        boardPanel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                backgroundGif.paintIcon(this, g, 0, 0);
            }
        };

        int boardWidth  = Game.getLabelSize()*Board.getRows();
        int boardHeight = Game.getLabelSize()*Board.getColumns();

        backgroundGif.setImage(backgroundGif.getImage().getScaledInstance(boardWidth, boardHeight, 0));

        boardPanel.setLocation(0, 0);
        boardPanel.setPreferredSize(new Dimension(boardWidth, boardHeight));
        boardPanel.setSize(boardPanel.getPreferredSize());
        boardPanel.setLayout(null);
    }

    private void initializeGameDataPanel() {
        gameDataPanel = new JPanel();
        gameDataPanel.setLayout(new BoxLayout(gameDataPanel, BoxLayout.Y_AXIS));

        levelShower = new JLabel();
        typeOfCandy = new JLabel[3];
        amountToGo = new JLabel[3];
        cantMoves = new JLabel();
        watch = new JLabel();
        
        int padding = 10;
        Border borderPadding = BorderFactory.createEmptyBorder(padding, padding, padding, padding);
        

        JPanel levelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        levelShower.setFont(new Font(font, Font.PLAIN, 20));
        levelPanel.add(levelShower, BorderLayout.CENTER);
        

        // START SET UP LIVES.
        ImageIcon imageIcon = new ImageIcon(Resources.getImagesFolderPath() + resources[1]);
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        live1 = new JLabel(imageIcon);
        live2 = new JLabel(imageIcon);
        live3 = new JLabel(imageIcon);

        JPanel livesPanel = new JPanel(new BorderLayout());
        livesPanel.add(live1, BorderLayout.WEST);
        livesPanel.add(live2, BorderLayout.CENTER);
        livesPanel.add(live3, BorderLayout.EAST);
        
        // END SET UP LIVES.

        // START SET UP WATCH.
        JPanel watchPanel = new JPanel(new BorderLayout());
        watchPanel.setBorder(borderPadding);
        watchPanel.add(watch);
        // END SET UP WATCH.

        // START SET UP MOVIMIENTOS RESTANTES.
        JPanel movesPanel = new JPanel(new BorderLayout());
        movesPanel.setBorder(borderPadding);
        movesPanel.add(cantMoves);
        // END SET UP MOVIMIENTOS RESTANTES.

        gameDataPanel.add(levelPanel);
        gameDataPanel.add(livesPanel);
        gameDataPanel.add(watchPanel);
        gameDataPanel.add(movesPanel);

        // START SET UP GOALS.
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
        }
        // END SET UP GOALS.
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

    public void setCurrentLevel(String level) { levelShower.setText(level); }
    public void setTime(String timeString) { watch.setText("Tiempo restante: " + timeString); }

    public void reset() { boardPanel.removeAll(); }
}
