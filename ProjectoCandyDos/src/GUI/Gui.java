package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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




@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    protected Game myGame;
    private Container contentPane;
    protected JPanel boardPanel;
    
    private int LABEL_SIZE = 80;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;
    
    private JLabel live1, live2, live3;
    private JLabel cantMoves, typeOfCandy, amountToGo, levelShower, watch;

    public Gui(Game game) {
        myGame = game;
        contentPane = getContentPane();
        boardPanel = new JPanel();
        animator = new CentralAnimator(this);
        pendingAnimations = 0;
        stopInterchanges = false;

        inicializar();
    }

    protected void inicializar() {
        setTitle("CandyCrush Humilde");
        final int width = LABEL_SIZE*Board.getRows() + 350;
        final int height = LABEL_SIZE*Board.getColumns() + 175;
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        boardPanel.setLocation(0, 50);
        boardPanel.setSize(LABEL_SIZE*Board.getRows(), LABEL_SIZE*Board.getColumns());
        boardPanel.setLayout(null);
        boardPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  { myGame.move(Game.LEFT); break; }
                    case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
                    case KeyEvent.VK_UP:    { myGame.move(Game.UP); break; }
                    case KeyEvent.VK_DOWN:  { myGame.move(Game.DOWN); break; }
                    case KeyEvent.VK_W:     { if (!stopInterchanges) myGame.swap(Game.UP); break; }
                    case KeyEvent.VK_S:     { if (!stopInterchanges) myGame.swap(Game.DOWN); break; }
                    case KeyEvent.VK_A:     { if (!stopInterchanges) myGame.swap(Game.LEFT); break; }
                    case KeyEvent.VK_D:     { if (!stopInterchanges) myGame.swap(Game.RIGHT); break; }
                    case KeyEvent.VK_J:     { System.out.println(boardPanel.getComponents().length); }
                }
            }
        });
        contentPane.setLayout(null);
        contentPane.add(boardPanel);
        boardPanel.setFocusable(true);

        setUpLives();

        cantMoves = new JLabel();
        cantMoves.setBounds(530, 125, 183, 38);
        contentPane.add(cantMoves);
        
        amountToGo = new JLabel();
        amountToGo.setBounds(645, 223, 46, 29);
        contentPane.add(amountToGo);
        
        typeOfCandy = new JLabel();
        typeOfCandy.setBounds(540, 195, 80, 80);
        contentPane.add(typeOfCandy);
        
        levelShower = new JLabel();
        levelShower.setBounds(194, 20, 80,20);
        levelShower.setFont(new Font("Stencil", Font.PLAIN, 20));
        contentPane.add(levelShower);
        
        watch = new JLabel("Aca iria el reloj");
        watch.setBounds(548, 95, 200, 14);
        contentPane.add(watch);
    }

    
    public void updateLives(int lives) {
        live1.setVisible(!(lives < 1));
        live2.setVisible(!(lives < 2));
        live3.setVisible(!(lives < 3));
    }
    
    private void setUpLives() {
        ImageIcon imageIcon = new ImageIcon("src/imagenes/heart-gif-1.gif"); 
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));

        live1 = new JLabel(imageIcon);
        live1.setBounds(540, 24, 46, 38);
        contentPane.add(live1);
        
        live2 = new JLabel(imageIcon);
        live2.setBounds(584, 24, 46, 38);
        contentPane.add(live2);
        
        live3 = new JLabel(imageIcon);
        live3.setBounds(629, 24, 46, 38);
        contentPane.add(live3);
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

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(contentPane, message);
    }

    public void executeAfterAnimation(Runnable r) {

        new Thread(() -> {
                    while (animator.isActive()) { try { Thread.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); } }
                    new Thread(r).start();
                    }).start();
    }

    public void showObjective(String typeOfEntity, int amountMissing) {
        ImageIcon imageIconAux = new ImageIcon("src/imagenes/" + typeOfEntity);
        imageIconAux.setImage(imageIconAux.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        typeOfCandy.setIcon(imageIconAux);

        amountToGo.setText(amountMissing + "");
    }

    public void updateGraphicObjective(int amountMissing) {
        amountToGo.setText((amountMissing <0 ? 0 : amountMissing) + "");
    }
    public void setCurrentLevel(String level) { levelShower.setText(level); }
    public void setTime(String timeString) { watch.setText("Tiempo restante: " + timeString); }
    
    @Override
    public void notifyAnimationInProgress() {
        synchronized(this){
            pendingAnimations++;
            stopInterchanges = true;
        }
    }
    
    @Override
    public void notifyAnimationEnd() {
        synchronized(this){
            pendingAnimations--;
            stopInterchanges = pendingAnimations > 0;
        }
    }

    @Override
    public void animateMovement(Drawable c) {
        animator.animateChangePosition(c);
    }
    
    @Override
    public void animateChangeState(Drawable c) {
        animator.animateChangeState(c);
    }
    
    @Override
    public void playSound(SoundPlayer sound) {
        animator.playSound(sound);
    }
    
    public int getPendingAnimations() { return pendingAnimations; }

    public void reset() {
        boardPanel.removeAll();
    }
}