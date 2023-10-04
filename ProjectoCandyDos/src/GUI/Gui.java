package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import Animations.CentralAnimator;
import Interfaces.LogicEntity;
import Logic.Game;

@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    private Container contentPane;
    protected JPanel boardPanel, auxPanel;
    protected Game myGame;
    protected int rows;
    protected int columns;
    
    private int LABEL_SIZE = 80;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;
    
    private JLabel live1, live2, live3;
    private JLabel cantMoves, typeOfCandy, amountToGo;

    public Gui(Game game, int r, int c) {
        myGame = game;
        rows = r; columns = c;
        contentPane = getContentPane();
        boardPanel = new JPanel();
        auxPanel = new JPanel();

        animator = new CentralAnimator(this);
        pendingAnimations = 0;
        stopInterchanges = false;

        inicializar();
    }

    protected void inicializar() {
        setTitle("CandyCrush Villero");
        final int width = LABEL_SIZE*rows + 200;
        final int height = LABEL_SIZE*columns + 200;
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
       
        boardPanel.setSize(486, 445);
        boardPanel.setLayout(null);
        boardPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:  { myGame.move(Game.LEFT); break; }
                    case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
                    case KeyEvent.VK_UP:    { myGame.move(Game.UP); break; }
                    case KeyEvent.VK_DOWN:  { myGame.move(Game.DOWN); break; }
                    case KeyEvent.VK_W:     { myGame.swap(Game.UP); break; }
                    case KeyEvent.VK_S:     { myGame.swap(Game.DOWN); break; }
                    case KeyEvent.VK_A:     { myGame.swap(Game.LEFT); break; }
                    case KeyEvent.VK_D:     { myGame.swap(Game.RIGHT); break; }
                }
            }
        });
        contentPane.setLayout(null);
        contentPane.add(boardPanel);
        boardPanel.setFocusable(true);
        
        setUpLives();
        
        cantMoves = new JLabel();
        cantMoves.setBounds(527, 125, 183, 38);
        contentPane.add(cantMoves);
        
       
		
		amountToGo = new JLabel();
		amountToGo.setBounds(643, 223, 46, 29);
		getContentPane().add(amountToGo);
		
		typeOfCandy = new JLabel();
		typeOfCandy.setBounds(540, 210, 64, 58);
		getContentPane().add(typeOfCandy);
    }

    
    public void updateLives(int lives) {
		
    	if (lives < 1) live1.setVisible(false);
    	else live1.setVisible(true);
    		
    	if (lives < 2) live2.setVisible(false); 
    	else live2.setVisible(true);
    		
    	if (lives < 3) live3.setVisible(false);
    	else live3.setVisible(true);

	}
    
    private void setUpLives() {
    	ImageIcon imageIcon = new ImageIcon("src/imagenes/heart-gif-1.gif"); 
        imageIcon.setImage(imageIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
       
        ImageIcon imageIcon2 = new ImageIcon("src/imagenes/heart-gif-1.gif"); 
        imageIcon2.setImage(imageIcon2.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        
        ImageIcon imageIcon3 = new ImageIcon("src/imagenes/heart-gif-1.gif"); 
        imageIcon3.setImage(imageIcon3.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
        
        live1 = new JLabel(imageIcon);
        live1.setBounds(540, 24, 46, 38);
        contentPane.add(live1);
        
        live3 = new JLabel(imageIcon3);
        live3.setBounds(584, 24, 46, 38);
        contentPane.add(live3);
        
        live2 = new JLabel(imageIcon2);
        live2.setBounds(629, 24, 46, 38);
        contentPane.add(live2);

    }
    
    public void updateMoves(int i) {
    	cantMoves.setText("movimientos restantes: "+i);
    }
    
    public GraphicalEntity addEntity(LogicEntity e) {
        Drawable drawable = new Drawable(this, e, e.getPicSize());
        boardPanel.add(drawable);
        return drawable;
    }

    
    public void removeEntity(GraphicalEntity gentity) {
        boardPanel.remove((Drawable)gentity);
        boardPanel.repaint();
    }

    
    public int chooseLevel() {
		int h = Integer.valueOf(JOptionPane.showInputDialog(contentPane, "Ingrese el nivel"));
		return h;
	}

	public void gameOver() {
		JOptionPane.showMessageDialog(contentPane, "Perdio el juego");
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		
	}

	public void ending() {
		JOptionPane.showMessageDialog(contentPane, "Felicitaciones! Ha ganado el juego");
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		
	}

	public void showObjective(String typeOfEntity, int amountMissing) {
		ImageIcon imageIconAux = new ImageIcon("src/imagenes/"+typeOfEntity); 
		System.out.println("src/imagenes/"+typeOfEntity);
		imageIconAux.setImage(imageIconAux.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
	    typeOfCandy.setIcon(imageIconAux);
	  
		amountToGo.setText(amountMissing+"");
		
		
	}
	public void updateGraphicObjective(int amountMissing) {
		
		int i = amountMissing <0 ? 0 : amountMissing;
		amountToGo.setText(i+"");
	}
    
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
    
    /*@Override
    public void animateChangeState(Drawable c) {
        animator.animateChangeState(c);
    }*/
}