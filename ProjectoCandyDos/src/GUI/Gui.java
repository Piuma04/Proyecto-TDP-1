package GUI;

import java.awt.Color;
import java.awt.Dimension;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;

import Logic.Game;
import Interfaces.LogicEntity;

import Animations.CentralAnimator;
import Animations.SoundPlayer;


@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    //private static final String imagesPath = "src/resources/images/";
    private static final String gameName = "PlayCrush";

    protected Game myGame;
    
    protected GamePanel gamePanel;
    protected JPanel menuPanel;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;

    public Gui(Game game) {

        final int width = 800;
        final int height = 800;
        final Dimension dim = new Dimension(width, height);

        myGame = game;
        Game.setLabelSize(width / 8);

        menuPanel = new JPanel();
        gamePanel = new GamePanel();

        menuPanel.setSize(dim);
        gamePanel.setSize(dim);

        animator = new CentralAnimator(this);
        pendingAnimations = 0;
        stopInterchanges = false;

        setTitle(gameName);
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

       openPanel(menuPanel);
        openPanel(gamePanel);

        initializeMenuPanel();
        initializeGamePanel();

        setVisible(true);
    }

    protected void initializeMenuPanel() {
        menuPanel.setFocusable(true);
        menuPanel.add(new JLabel("ESTE ES EL MENU"));
        menuPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_L:  {
                        openGame();
                        break; }
                }
            }
        });
    }

    protected void initializeGamePanel() {
        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(new KeyAdapter() {
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
                    case KeyEvent.VK_J:     { System.out.println(gamePanel.boardPanel.getComponents().length); break; }
                    case KeyEvent.VK_P:     { openMenu(); break; }
                }
            }
        });
    }

    public void openPanel(JPanel panel) { setContentPane(panel); panel.requestFocus(true); }
    public void openGame() { myGame.startBackgroundMusic(); openPanel(gamePanel); }
    public void openMenu() { myGame.stopBackgroundMusic(); openPanel(menuPanel); }

    public GraphicalEntity addLogicEntity(LogicEntity e) {
        Drawable drawable = new Drawable(this, e, e.getPicSize());
        gamePanel.addEntity(drawable);
        return drawable;
    }
    public void removeEntity(Drawable gentity) { gamePanel.removeEntity(gentity); }

    public void showObjective(List<String> entities, List<Integer> remaining) { gamePanel.showObjective(entities, remaining); }
    public void updateGraphicObjective(List<Integer> list) { gamePanel.updateGraphicObjective(list); }
    public void updateLives(int lives)        { gamePanel.updateLives(lives); }
    public void updateMoves(int i)            { gamePanel.updateMoves(i); }
    public void setCurrentLevel(String level) { gamePanel.setCurrentLevel(level); }
    public void setTime(String timeString)    { gamePanel.setTime(timeString); }
    public void reset()                       { gamePanel.reset(); }

    public int chooseLevel() { return Integer.valueOf(JOptionPane.showInputDialog(getContentPane(), "Ingrese el nivel")); }
    public void showMessage(String message) { JOptionPane.showMessageDialog(getContentPane(), message); }

    public void executeAfterAnimation(Runnable r) {
        new Thread(() -> {
            while (animator.isActive()) { try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); } }
            new Thread(r).start();
        }).start();
    }

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

    public void close() { this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); }
}