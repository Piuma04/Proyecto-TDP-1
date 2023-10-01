package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Interfaces.LogicBlock;
import Logic.Game;




public class GUI extends JFrame {
	
	protected Game myGame;
	protected int rows;
	protected int columns;
	
	protected Cell celda_1_pendiente_animacion;
	protected Cell celda_2_pendiente_animacion;
	
	protected JLabel upperText;
	protected JPanel mainPanel;
	private int size_label = 60;
	
	public GUI(Game j, int r, int c) {
		myGame = j;
		rows = r;
		columns = c;
		inicializar();
	}
	
	protected void inicializar() {
		setTitle("CandyCrush Villero");
		setSize(new Dimension(500, 500));
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		upperText = new JLabel("( ͡°( ͡° ͜ʖ( ͡° ͜ʖ ͡°)ʖ ͡°) ͡°)");
		
		mainPanel = new JPanel();
		mainPanel.setSize(size_label * rows, size_label * columns);
		mainPanel.setLayout(null);
		mainPanel.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {	
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT: 	{ myGame.move(Game.LEFT); break; }
					case KeyEvent.VK_RIGHT: { myGame.move(Game.RIGHT); break; }
					case KeyEvent.VK_UP: 	{ myGame.move(Game.UP);break; }
					case KeyEvent.VK_DOWN: 	{ myGame.move(Game.DOWN); break; }
					case KeyEvent.VK_W:		{ myGame.swap(Game.UP); break; }
					case KeyEvent.VK_S:		{ myGame.swap(Game.DOWN); break; }
					case KeyEvent.VK_A:		{ myGame.swap(Game.LEFT); break; }
					case KeyEvent.VK_D:		{ myGame.swap(Game.RIGHT); break; } 
				}
			}
		});
		
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		getContentPane().add(upperText, BorderLayout.NORTH);
		
		mainPanel.setFocusable(true);
	}
	
	public GraphicalBlock agregar_entidad(LogicBlock e) {
		Cell Cellaux = new Cell(this, e, size_label);
		mainPanel.add(Cellaux);
		return Cellaux;
	}
	
	/*public void considerForSwapPosition(Cell c) {
		if (celda_1_pendiente_animacion == null) {
			celda_1_pendiente_animacion = c;
		}else {
			celda_2_pendiente_animacion = c;
			AnimadorIntercambio mi_animador_intercambio = new AnimadorIntercambio(size_label, 10, 50, celda_1_pendiente_animacion, celda_2_pendiente_animacion);
			celda_1_pendiente_animacion = null;
			celda_2_pendiente_animacion = null;
			mi_animador_intercambio.start();
		}
	}*/
}