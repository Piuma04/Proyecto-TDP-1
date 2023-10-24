package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import Logic.Board;
import Logic.Game;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
    private static final String imagesPath = "src/resources/images/";

    protected JPanel boardPanel;
    
    private JLabel live1;
    private JLabel live2;
    private JLabel live3;
    private JLabel typeOfCandy[];
    private JLabel amountToGo[];

    private JLabel cantMoves, levelShower, watch;

    private ImageIcon backgroundGif = new ImageIcon(imagesPath + "stars.gif");

    public GamePanel() {
        setForeground(Color.BLACK);
        
        boardPanel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                backgroundGif.paintIcon(this, g, 0, 0);
            }
        };
        initializeGamePanel();
    }

    protected void initializeGamePanel() {
        typeOfCandy = new JLabel[3];
        amountToGo = new JLabel[3];
        cantMoves = new JLabel();
        levelShower = new JLabel();
        watch = new JLabel();

        int x = 0;
        int y = 0;
        int w = 0;
        int h = 0;

        int topBorderFrameHeight = 10;
        int boardWidth = Game.getLabelSize()*Board.getRows();
        int boardHeight = Game.getLabelSize()*Board.getColumns();
        backgroundGif.setImage(backgroundGif.getImage().getScaledInstance(boardWidth, boardHeight, 0));

        setLayout(null);
        w = 80;
        h = 20;
        x = boardWidth / 2 - w / 2;
        y = topBorderFrameHeight;
        levelShower.setBounds(x, y, w, h);
        levelShower.setFont(new Font("Stencil", Font.PLAIN, 20));
        add(levelShower);

        x = 0;
        y = levelShower.getHeight()+topBorderFrameHeight;
        w = boardWidth;
        h = boardHeight;
        boardPanel.setLocation(x, y);
        boardPanel.setSize(w, h);
        
        boardPanel.setLayout(null);
        add(boardPanel);

        int widthPadding = 20;
        int heightPadding = 25;

        // START SET UP LIVES.
        ImageIcon imageIcon = new ImageIcon(imagesPath + "life.gif");
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        live1 = new JLabel(imageIcon);
        live2 = new JLabel(imageIcon);
        live3 = new JLabel(imageIcon);

        x = boardWidth + widthPadding;
        y = topBorderFrameHeight;
        w = imageIcon.getIconWidth();
        h = imageIcon.getIconWidth();
        live1.setBounds(x, y, w, h);
        add(live1);
        
        x += live1.getWidth();
        live2.setBounds(x, y, w, h);
        add(live2);
        
        x += live2.getWidth();
        live3.setBounds(x, y, w, h);
        add(live3);
        // END SET UP LIVES.

        // START SET UP WATCH.
        x = boardWidth + widthPadding;
        y = live1.getY() + live1.getHeight() + heightPadding;
        w = 175;
        h = 30;
        watch = new JLabel();
        watch.setForeground(new Color(0, 0, 0));
        watch.setBounds(x, y, w, h);
        add(watch);
        // END SET UP WATCH.

        // START SET UP MOVIMIENTOS RESTANTES.
        x = boardWidth + widthPadding;
        y = watch.getY() + watch.getHeight();
        cantMoves.setBounds(x, y, w, h);
        add(cantMoves);
        // END SET UP MOVIMIENTOS RESTANTES.

        // START SET UP GOALS.
        JLabel amountToGoLabel = null;
        JLabel typeOfCandyLabel = null;

        typeOfCandyLabel = new JLabel();
        x = boardWidth + widthPadding;
        y = cantMoves.getY() + cantMoves.getHeight() + heightPadding;
        w = Game.getLabelSize();
        h = Game.getLabelSize();
        typeOfCandyLabel.setBounds(x, y, w, h);
        add(typeOfCandyLabel);
        typeOfCandy[0] = typeOfCandyLabel;

        typeOfCandyLabel = new JLabel();
        y = typeOfCandy[0].getY() + typeOfCandy[0].getHeight() + heightPadding;
        typeOfCandyLabel.setBounds(x, y, w, h);
        add(typeOfCandyLabel);
        typeOfCandy[1] = typeOfCandyLabel;
        
        typeOfCandyLabel = new JLabel();
        y = typeOfCandy[1].getY() + typeOfCandy[1].getHeight() + heightPadding;
        typeOfCandyLabel.setBounds(x, y, w, h);
        add(typeOfCandyLabel);
        typeOfCandy[2] = typeOfCandyLabel;

        amountToGoLabel = new JLabel();
        w = 50;
        h = 30;
        x = typeOfCandyLabel.getX() + typeOfCandyLabel.getWidth() + widthPadding;
        y = typeOfCandy[0].getY() + typeOfCandy[0].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        add(amountToGoLabel);
        amountToGo[0] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        y = typeOfCandy[1].getY() + typeOfCandy[1].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        add(amountToGoLabel);
        amountToGo[1] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        y = typeOfCandy[2].getY() + typeOfCandy[2].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        add(amountToGoLabel);
        amountToGo[2] = amountToGoLabel;
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
            ImageIcon imageIconAux = new ImageIcon(imagesPath + entities.get(i));
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
