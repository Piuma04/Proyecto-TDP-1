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

        final int width = 833;
        final int height = 650;

        Game.setLabelSize(86);

        setTitle(gameName);
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane.setLayout(null);

        boardPanel.setLocation(0, 50);
        boardPanel.setSize(Game.getLabelSize()*Board.getRows(), Game.getLabelSize()*Board.getColumns());
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

        setUpLives();
        setUpGoals();

        cantMoves.setBounds(530, 125, 183, 38);
        contentPane.add(cantMoves);

        levelShower.setBounds(194, 20, 80,20);
        levelShower.setFont(new Font("Stencil", Font.PLAIN, 20));
        contentPane.add(levelShower);

        watch = new JLabel("Aca iria el reloj");
        watch.setForeground(new Color(0, 0, 0));
        watch.setBounds(548, 95, 200, 28);
        contentPane.add(watch);
    }

    public void updateLives(int lives) {
        live1.setVisible(!(lives < 1));
        live2.setVisible(!(lives < 2));
        live3.setVisible(!(lives < 3));
    }
    
    private void setUpLives() {
        ImageIcon imageIcon = new ImageIcon(imagesPath + "life.gif"); 
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        live1 = new JLabel(imageIcon);
        live2 = new JLabel(imageIcon);
        live3 = new JLabel(imageIcon);

        live1.setBounds(540, 24, 46, 38);
        contentPane.add(live1);
        
        live2.setBounds(584, 24, 46, 38);
        contentPane.add(live2);
        
        live3.setBounds(629, 24, 46, 38);
        contentPane.add(live3);
    }

    private void setUpGoals() {
        JLabel amountToGoLabel = new JLabel();
        JLabel typeOfCandyLabel = new JLabel();

        amountToGoLabel.setBounds(645, 223, 46, 29);
        contentPane.add(amountToGoLabel);
        amountToGo[0] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        amountToGoLabel.setBounds(667, 306, 68, 38);
        getContentPane().add(amountToGoLabel);
        amountToGo[1] = amountToGoLabel;

        amountToGoLabel = new JLabel();
        amountToGoLabel.setBounds(667, 397, 60, 38);
        getContentPane().add(amountToGoLabel);
        amountToGo[2] = amountToGoLabel;


        typeOfCandyLabel = new JLabel();
        typeOfCandyLabel.setBounds(540, 195, 80, 80);
        contentPane.add(typeOfCandyLabel);
        typeOfCandy[0] = typeOfCandyLabel;

        typeOfCandyLabel = new JLabel();
        typeOfCandyLabel.setBounds(539, 286, 91, 63);
        getContentPane().add(typeOfCandyLabel);
        typeOfCandy[1] = typeOfCandyLabel;
        
        typeOfCandyLabel = new JLabel();
        typeOfCandyLabel.setBounds(530, 386, 90, 64);
        getContentPane().add(typeOfCandyLabel);
        typeOfCandy[2] = typeOfCandyLabel;
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
            imageIconAux.setImage(imageIconAux.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
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