package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Interfaces.LogicBlock;
import Logic.Game;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import java.awt.Font;
import javax.swing.SwingConstants;




public class GUI extends JFrame {
	
	private Game myGame;
	private int rows;
	private int columns;
	
	private JLabel lblMovements;
	private JLabel lblLives;
	private JPanel mainPanel;
	private int size_label = 150,i = 0;

	
	public GUI(Game j, int r, int c) {
		myGame = j;
		rows = r;
		columns = c;
		inicializar();
	}
	
	protected void inicializar() {
		
		
		setTitle("CandyCrush Villero");
		setSize(new Dimension(1500, 950));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout());
		
		lblMovements = new JLabel("Movimientos restantes: ");
		lblLives = new JLabel("Vidas restantes: ");
		
		mainPanel = new JPanel();
		mainPanel.setSize(size_label * rows, size_label * columns);
		mainPanel.setLayout(null);
		mainPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ myGame.move(Game.LEFT); break; }
					case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
					case KeyEvent.VK_UP: 	{ myGame.move(Game.UP); break; }
					case KeyEvent.VK_DOWN: 	{ myGame.move(Game.DOWN); break; }
					case KeyEvent.VK_W:		{ myGame.swap(Game.UP); break; }
					case KeyEvent.VK_S:		{ myGame.swap(Game.DOWN); break; }
					case KeyEvent.VK_A:		{ myGame.swap(Game.LEFT); break; }
					case KeyEvent.VK_D:		{ myGame.swap(Game.RIGHT); break; } 
				}
			}
		});
		
		
		getContentPane().add(lblLives, BorderLayout.SOUTH);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(lblMovements, BorderLayout.NORTH);
		mainPanel.setFocusable(true);
	}
	
	public GraphicalBlock agregar_entidad(LogicBlock e) {
		Cell Cellaux = new Cell(this, e, size_label);
		mainPanel.add(Cellaux);
		return Cellaux;
	}
	
	public void updateMoves(int moves) {
		lblMovements.setText("Movimientos restantes: "+moves);
	}
	
	public void updateLives(int lives) {
		lblLives.setText("Vidas restantes: "+lives);
	}
	public void showLostScreen() {
		JOptionPane.showMessageDialog(mainPanel, "perdio, se carga el nivel de vuelta");
		
	}
	public void gameOver() {
		JOptionPane.showMessageDialog(mainPanel, "Perdio el juego");
		this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
	}
	
	public void reset() {
		mainPanel = new JPanel();
		mainPanel.setSize(size_label * rows, size_label * columns);
		mainPanel.setLayout(null);
		mainPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ myGame.move(Game.LEFT); break; }
					case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
					case KeyEvent.VK_UP: 	{ myGame.move(Game.UP); break; }
					case KeyEvent.VK_DOWN: 	{ myGame.move(Game.DOWN); break; }
					case KeyEvent.VK_W:		{ myGame.swap(Game.UP); break; }
					case KeyEvent.VK_S:		{ myGame.swap(Game.DOWN); break; }
					case KeyEvent.VK_A:		{ myGame.swap(Game.LEFT); break; }
					case KeyEvent.VK_D:		{ myGame.swap(Game.RIGHT); break; } 
				}
			}
		});
		
		
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(lblMovements, BorderLayout.NORTH);
		mainPanel.setFocusable(true);
		
	}
	
	
	
	
}