package GUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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


@SuppressWarnings("serial")
public class Gui extends JFrame implements GuiAnimable, GuiNotifiable {

    private static final String imagesPath = "src/resources/images/";
    private static final String gameName = "PlayCrush";

    private int LABEL_SIZE = Board.getBoardLabelSize();

    protected Game myGame;
    private Container contentPane;
    protected JPanel boardPanel;

    protected CentralAnimator animator;
    protected int pendingAnimations;
    protected boolean stopInterchanges;
    
    private JLabel live1, live2, live3;
    private JLabel cantMoves, typeOfCandy0, amountToGo0, levelShower, watch;
    private JLabel amountToGo1, typeOfCandy1;
    private JLabel amountToGo2, typeOfCandy2;

    private ImageIcon backgroundGif = new ImageIcon(imagesPath + "stars.gif");

    public Gui(Game game) {
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

        inicializar();
    }

    protected void inicializar() {
        setTitle(gameName);
        //final int width = LABEL_SIZE*Board.getRows() + 350;
        //final int height = LABEL_SIZE*Board.getColumns() + 175;
        setSize(new Dimension(833, 592));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        contentPane.setLayout(null);

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

        /*JLabel backgroundLabel = new JLabel();
        backgroundLabel.setIcon(backgroundGif);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        contentPane.add(backgroundLabel);*/

        boardPanel.setFocusable(true);

        setUpLives();

        cantMoves = new JLabel();
        cantMoves.setBounds(530, 125, 183, 38);
        contentPane.add(cantMoves);
        
        amountToGo0 = new JLabel();
        amountToGo0.setBounds(645, 223, 46, 29);
        contentPane.add(amountToGo0);
        
        typeOfCandy0 = new JLabel();
        typeOfCandy0.setBounds(540, 195, 80, 80);
        contentPane.add(typeOfCandy0);
        
        levelShower = new JLabel();
        levelShower.setBounds(194, 20, 80,20);
        levelShower.setFont(new Font("Stencil", Font.PLAIN, 20));
        contentPane.add(levelShower);
        
        watch = new JLabel("Aca iria el reloj");
        watch.setBounds(548, 95, 200, 14);
        contentPane.add(watch);
        
         typeOfCandy1 = new JLabel("");
        typeOfCandy1.setBounds(539, 286, 91, 63);
        getContentPane().add(typeOfCandy1);
        
         typeOfCandy2 = new JLabel("");
        typeOfCandy2.setBounds(530, 386, 90, 64);
        getContentPane().add(typeOfCandy2);
        
         amountToGo2 = new JLabel("\r\n");
        amountToGo2.setBounds(667, 397, 60, 38);
        getContentPane().add(amountToGo2);
        
         amountToGo1 = new JLabel("\r\n");
        amountToGo1.setBounds(667, 306, 68, 38);
        getContentPane().add(amountToGo1);

    }

    
    public void updateLives(int lives) {
        live1.setVisible(!(lives < 1));
        live2.setVisible(!(lives < 2));
        live3.setVisible(!(lives < 3));
    }
    
    private void setUpLives() {
        ImageIcon imageIcon = new ImageIcon(imagesPath + "heart-gif-1.gif"); 
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

    public void showMessage(String message) { JOptionPane.showMessageDialog(contentPane, message); }

    public void executeAfterAnimation(Runnable r) {
        new Thread(() -> {
                    while (animator.isActive()) { try { Thread.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); } }
                    new Thread(r).start();
        }).start();
    }

    public void showObjective(List<String> entities, List<Integer> remaining) {
        int i = 0;
    	for(String s: entities) {
    		setEntity(s,i);
    		setRemaining(remaining.get(i),i);
    		i++;
    	}
    }

	private void setRemaining(Integer remaining, int reference) {
		switch(reference) {
		case 0:{
			amountToGo0.setText(remaining+"");
			break;
		}
		case 1:{
			amountToGo1.setText(remaining+"");
			break;
		}
		case 2:{
			amountToGo2.setText(remaining+"");
			break;
		}
	}

		
	}

	private void setEntity(String s, int reference) {
		ImageIcon imageIconAux = new ImageIcon(imagesPath + s);
		imageIconAux.setImage(imageIconAux.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT));
		switch(reference) {
			case 0:{
				typeOfCandy0.setIcon(imageIconAux);
				break;
			}
			case 1:{
				typeOfCandy1.setIcon(imageIconAux);
				break;
			}
			case 2:{
				typeOfCandy2.setIcon(imageIconAux);
				break;
			}
		}
		
	}

	public void updateGraphicObjective(List<Integer> list) {
        int i = 0;
		for(Integer j : list) {
			setRemaining(j, i);
			i++;
		}
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
}