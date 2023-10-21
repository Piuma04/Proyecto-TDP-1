package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;

import Logic.Board;
import Logic.Game;
import Interfaces.LogicEntity;

import Animations.CentralAnimator;
import Animations.SoundPlayer;
import java.awt.Color;


@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    private static final String imagesPath = "src/resources/images/";
    private static final String gameName = "PlayCrush";

    protected Game myGame;
    private Container contentPane;
    protected JPanel boardPanel;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;
    
    private JLabel live1;
    private JLabel live2;
    private JLabel live3;
    private JLabel typeOfCandy[];
    private JLabel amountToGo[];
    
    private JLabel cantMoves, levelShower, watch;

    private ImageIcon backgroundGif = new ImageIcon(imagesPath + "stars.gif");

    public Gui(Game game) {
    	getContentPane().setForeground(Color.BLACK);
        myGame = game;
        contentPane = getContentPane();
        boardPanel =  new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                backgroundGif.paintIcon(this, g, 0, 0);
            }
        };

        animator = new CentralAnimator(this);
        pendingAnimations = 0;
        stopInterchanges = false;

        typeOfCandy = new JLabel[3];
        amountToGo = new JLabel[3];
        cantMoves = new JLabel();
        levelShower = new JLabel();
        watch = new JLabel();

        inicializar();
    }

    protected void inicializar() {

        final int width = 800;
        final int height = 800;

        int x = 0;
        int y = 0;
        int w = 0;
        int h = 0;


        setTitle(gameName);
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        int topBorderFrameHeight = 10;
        Game.setLabelSize(width / 8);
        int boardWidth = Game.getLabelSize()*Board.getRows();
        int boardHeight = Game.getLabelSize()*Board.getColumns();
        backgroundGif.setImage(backgroundGif.getImage().getScaledInstance(boardWidth, boardHeight, 0));

        contentPane.setLayout(null);
        w = 80;
        h = 20;
        x = boardWidth / 2 - w / 2;
        y = topBorderFrameHeight;
        levelShower.setBounds(x, y, w, h);
        levelShower.setFont(new Font("Stencil", Font.PLAIN, 20));
        contentPane.add(levelShower);

        x = 0;
        y = levelShower.getHeight()+topBorderFrameHeight;
        w = boardWidth;
        h = boardHeight;
        boardPanel.setLocation(x, y);
        boardPanel.setSize(w, h);

        setSize(new Dimension(width, y + h + 37));

        boardPanel.setLayout(null);
        boardPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  { myGame.move(Game.LEFT); break; }
                    case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
                    case KeyEvent.VK_UP:    { myGame.move(Game.UP); break; }
                    case KeyEvent.VK_DOWN:  { myGame.move(Game.DOWN); break; }
                    case KeyEvent.VK_W:     { if (!animator.isActive() && !myGame.isAnimating()) myGame.swap(Game.UP); break; }
                    case KeyEvent.VK_S:     { if (!animator.isActive() && !myGame.isAnimating()) myGame.swap(Game.DOWN); break; }
                    case KeyEvent.VK_A:     { if (!animator.isActive() && !myGame.isAnimating()) myGame.swap(Game.LEFT); break; }
                    case KeyEvent.VK_D:     { if (!animator.isActive() && !myGame.isAnimating()) myGame.swap(Game.RIGHT); break; }
                    case KeyEvent.VK_O:     { if (!animator.isActive() && !myGame.isAnimating()) myGame.lost(); break; }
                    case KeyEvent.VK_J:     { System.out.println(boardPanel.getComponents().length); }
                }
            }
        });

        contentPane.add(boardPanel);
        boardPanel.setFocusable(true);

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
        contentPane.add(live1);
        
        x += live1.getWidth();
        live2.setBounds(x, y, w, h);
        contentPane.add(live2);
        
        x += live2.getWidth();
        live3.setBounds(x, y, w, h);
        contentPane.add(live3);
        // END SET UP LIVES.

        // START SET UP WATCH.
        x = boardWidth + widthPadding;
        y = live1.getY() + live1.getHeight() + heightPadding;
        w = 175;
        h = 30;
        watch = new JLabel();
        watch.setForeground(new Color(0, 0, 0));
        watch.setBounds(x, y, w, h);
        contentPane.add(watch);
        // END SET UP WATCH.

        // START SET UP MOVIMIENTOS RESTANTES.
        x = boardWidth + widthPadding;
        y = watch.getY() + watch.getHeight();
        cantMoves.setBounds(x, y, w, h);
        contentPane.add(cantMoves);
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
        contentPane.add(typeOfCandyLabel);
        typeOfCandy[0] = typeOfCandyLabel;

        typeOfCandyLabel = new JLabel();
        y = typeOfCandy[0].getY() + typeOfCandy[0].getHeight() + heightPadding;
        typeOfCandyLabel.setBounds(x, y, w, h);
        getContentPane().add(typeOfCandyLabel);
        typeOfCandy[1] = typeOfCandyLabel;
        
        typeOfCandyLabel = new JLabel();
        y = typeOfCandy[1].getY() + typeOfCandy[1].getHeight() + heightPadding;
        typeOfCandyLabel.setBounds(x, y, w, h);
        getContentPane().add(typeOfCandyLabel);
        typeOfCandy[2] = typeOfCandyLabel;

        amountToGoLabel = new JLabel();
        w = 50;
        h = 30;
        x = typeOfCandyLabel.getX() + typeOfCandyLabel.getWidth() + widthPadding;
        y = typeOfCandy[0].getY() + typeOfCandy[0].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        contentPane.add(amountToGoLabel);
        amountToGo[0] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        y = typeOfCandy[1].getY() + typeOfCandy[1].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        getContentPane().add(amountToGoLabel);
        amountToGo[1] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        y = typeOfCandy[2].getY() + typeOfCandy[2].getHeight()/2 - h/2;
        amountToGoLabel.setBounds(x, y, w, h);
        getContentPane().add(amountToGoLabel);
        amountToGo[2] = amountToGoLabel;
        // END SET UP GOALS.

        setLocationRelativeTo(null);
    }

    public void updateLives(int lives) {
        live1.setVisible(!(lives < 1));
        live2.setVisible(!(lives < 2));
        live3.setVisible(!(lives < 3));
    }

    public void updateMoves(int i) { cantMoves.setText("Movimientos Restantes: " + i); }
    
    public GraphicalEntity addLogicEntity(LogicEntity e) {
        Drawable drawable = new Drawable(this, e, e.getPicSize());
        boardPanel.add(drawable);
        return drawable;
    }

    
    public void removeEntity(Drawable gentity) {
        boardPanel.remove(gentity);
        boardPanel.repaint();
    }

    
    public int chooseLevel() { return Integer.valueOf(JOptionPane.showInputDialog(contentPane, "Ingrese el nivel")); }

    public void showMessage(String message) { JOptionPane.showMessageDialog(contentPane, message); }

    public void executeAfterAnimation(Runnable r) {
        new Thread(() -> {
                    while (animator.isActive()) { try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); } }
                    new Thread(r).start();
        }).start();
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
    
    @Override
    public void notifyAnimationInProgress() {
        synchronized(this) {
            pendingAnimations++;
            stopInterchanges = true;
        }
    }
    
    @Override
    public void notifyAnimationEnd() {
        synchronized(this) {
            pendingAnimations--;
            stopInterchanges = pendingAnimations > 0;
        }
    }

    @Override public void animateMovement(Drawable c) { animator.animateChangePosition(c); }
    @Override public void animateChangeState(Drawable c) { animator.animateChangeState(c); }
    @Override public void playSound(SoundPlayer sound) { animator.playSound(sound); }

    public int getPendingAnimations() { return pendingAnimations; }
    public void reset() { boardPanel.removeAll(); }
    public void close() {
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		
	}
}