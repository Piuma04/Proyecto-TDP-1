package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.LogicEntity;
import Logic.Game;

@SuppressWarnings("serial")
public class GUI extends JFrame {

    private Container contentPane;
    protected JPanel boardPanel, auxPanel;
    private JLabel cantLives, cantMoves;
    protected Game myGame;
    protected int rows;
    protected int columns;
    
    private int LABEL_SIZE = 80;

    
    
    //protected Drawable celda_1_pendiente_animacion;
    //protected Drawable celda_2_pendiente_animacion;

    public GUI(Game game, int r, int c) {
        myGame = game;
        rows = r; columns = c;
        contentPane = getContentPane();
        boardPanel = new JPanel();
        auxPanel = new JPanel();
        inicializar();
    }

    protected void inicializar() {
        setTitle("CandyCrush Villero");
        final int width = LABEL_SIZE*rows + 200;
        final int height = LABEL_SIZE*columns + 200;
        setSize(new Dimension(width, height));
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        contentPane.setLayout(new BorderLayout());
        
        
        
        boardPanel.setSize(LABEL_SIZE * rows, LABEL_SIZE * columns);
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
        
        cantLives = new JLabel();
        cantLives.setText("Vidas: ");
       
        
        cantMoves = new JLabel();
        cantMoves.setText("Movimientos: ");
        
        auxPanel.setSize(250, 250);
        auxPanel.setBackground(Color.WHITE);
        auxPanel.setLayout(new BoxLayout(auxPanel,BoxLayout.PAGE_AXIS));
        auxPanel.add(cantLives);
        auxPanel.add(cantMoves);
        
        contentPane.add(auxPanel,BorderLayout.NORTH);
        contentPane.add(boardPanel, BorderLayout.CENTER);
        boardPanel.setFocusable(true);
    }

    public GraphicalEntity addEntity(LogicEntity e) {
        Drawable drawable = new Drawable(e, e.getPicSize());
        boardPanel.add(drawable);
        return drawable;
    }
}