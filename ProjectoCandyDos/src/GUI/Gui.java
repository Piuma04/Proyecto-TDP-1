package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;

import Logic.Game;
import Logic.LevelGenerator;
import Interfaces.LogicEntity;

import Animations.CentralAnimator;
import Animations.SoundPlayer;


@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    private static final String gameName = "PlayCrush";

    protected Game myGame;
    
    protected GamePanel gamePanel;
    protected JPanel menuPanel;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;

    public Gui(Game game) {

        // minimum width: 700
        final int width = 800;
        final int height = width - 150;
        final Dimension dim = new Dimension(width, height);

        setTitle(gameName);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setPreferredSize(dim);
        pack();
        setLocationRelativeTo(null);

        myGame = game;

        menuPanel = new JPanel();
        gamePanel = new GamePanel(getContentPane().getSize());
        menuPanel.setPreferredSize(getContentPane().getSize());

        animator = new CentralAnimator(this);
        pendingAnimations = 0;
        stopInterchanges = false;

        openPanel(menuPanel);
        //openPanel(gamePanel);

        initializeMenuPanel();
        initializeGamePanel();

        setVisible(true);

    }

    protected void initializeMenuPanel() {
        menuPanel.setLayout(new BorderLayout());
        menuPanel.setFocusable(true);
        
        JLabel menu = new JLabel("ESTE ES EL MENU");
        menu.setHorizontalAlignment(JLabel.CENTER);
        menuPanel.add(menu, BorderLayout.NORTH);
        JCheckBox checkBox = new JCheckBox("MENU ANTIGUO", false);
        checkBox.setHorizontalAlignment(JCheckBox.CENTER);
        
        checkBox.addItemListener( (ItemEvent ev) -> {
            SwingUtilities.invokeLater( () -> {
                setTheme(ev.getStateChange() == ItemEvent.SELECTED);
                myGame.reloadLevel();
            });
        });
        
        menuPanel.add(checkBox, BorderLayout.CENTER);

        JButton startGame = new JButton("Continue Game");
        startGame.setPreferredSize(new Dimension(30, 20));
        startGame.setHorizontalAlignment(JButton.CENTER);
        startGame.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { openGame(); }); });
        menuPanel.add(startGame, BorderLayout.SOUTH);
        
        JButton maxScores = new JButton("Consultar maximas puntuaciones");
        maxScores.addActionListener((ActionEvent ev) -> { showMaxScores(); });
        menuPanel.add(maxScores, BorderLayout.EAST);

        JPanel levels = new JPanel(new GridLayout(0, 1));
        levels.setBackground(Color.BLACK);
        JButton level_1 = new JButton("Level 1");
        JButton level_2 = new JButton("Level 2");
        JButton level_3 = new JButton("Level 3");
        JButton level_4 = new JButton("Level 4");
        JButton level_5 = new JButton("Level 5");

        level_1.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { myGame.loadLevel(1); openGame(); resetScore();}); });
        level_2.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { myGame.loadLevel(2); openGame(); resetScore();}); });
        level_3.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { myGame.loadLevel(3); openGame(); resetScore();}); });
        level_4.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { myGame.loadLevel(4); openGame(); resetScore();}); });
        level_5.addActionListener( (ActionEvent ev) -> { SwingUtilities.invokeLater( () -> { myGame.loadLevel(5); openGame(); resetScore();}); });

        levels.add(level_1);
        levels.add(level_2);
        levels.add(level_3);
        levels.add(level_4);
        levels.add(level_5);
        menuPanel.add(levels, BorderLayout.WEST);
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
                    case KeyEvent.VK_ESCAPE:
                    case KeyEvent.VK_P:     {  if (!animator.isActive() && !myGame.isAnimating()) openMenu(); break; }
                    //case KeyEvent.VK_P:     { executeAfterAnimation(() -> { openMenu(); }); break; }
                }
            }
        });
    }

    public void openPanel(JPanel panel) { setContentPane(panel); panel.requestFocus(true); }

    public void openGame() {
        openPanel(gamePanel);
        myGame.startBackgroundMusic();
        myGame.unpauseTimer();
       }
    
    private void resetScore() {
    	myGame.resetScore();
    	updateScore(0);
    }
    public void openMenu() {
        openPanel(menuPanel);
        myGame.stopBackgroundMusic();
        myGame.pauseTimer();
    }

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
    public void updateScore(int i)            { gamePanel.updateScore(i); }
    public void setCurrentLevel(String level) { gamePanel.setCurrentLevel(level); }
    public void setTime(String timeString)    { gamePanel.setTime(timeString); }
    public void reset()                       { gamePanel.reset(); }

    public int chooseLevel() { return Integer.valueOf(JOptionPane.showInputDialog(getContentPane(), "Ingrese el nivel")); }
    public void showMessage(String message) { JOptionPane.showMessageDialog(getContentPane(), message); }

    public void executeAfterAnimation(Runnable r) { animator.executeAfterAnimation(r); }

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

    public void setTheme(boolean old) {
        Resources.setTheme(old ? 1 : 0);
        gamePanel.updateResources();
    }
    private void showMaxScores() {
    	List<String> lines = LevelGenerator.readFileLines(Resources.getScorePath());
    	String toDisplay = "";
    	for(int i = 0; i<lines.size(); i++) {
    		toDisplay += lines.get(i)+"\n";
    	}
    	JOptionPane.showMessageDialog(this, toDisplay, "Score maximo", 1);
    }

    public void close() { this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING)); }
}