package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class GUI extends JFrame implements KeyListener {
	private final int numRows = 8;
	private final int numCols = 8;
	private final JLabel[][] candyButtons = new JLabel[numRows][numCols];

	public GUI() {
		setTitle("Candy Crush");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(numRows, numCols));

		// Agregar el KeyListener para la navegación
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// Crear botones con caramelos aleatorios
		Random random = new Random();
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				JLabel label = new JLabel(getRandomCandy());
				candyButtons[i][j] = label;
				getContentPane().add(label);
			}
		}

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	// Generar un caramelo aleatorio
	private String getRandomCandy() {
		String[] candyTypes = { "Caramelo1", "Caramelo2", "Caramelo3", "Caramelo4", "Caramelo5" };
		Random random = new Random();
		int index = random.nextInt(candyTypes.length);
		return candyTypes[index];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		int currentRow = 0;
		int currentCol = 0;

		// Encuentra la celda actualmente enfocada
		for (int i = 0; i < numRows; i++) {
			for (int j = 0; j < numCols; j++) {
				if (candyButtons[i][j].isFocusOwner()) {
					currentRow = i;
					currentCol = j;
				}
			}
		}

		// Moverse a través de las celdas con las teclas de flecha
		if (keyCode == KeyEvent.VK_LEFT && currentCol > 0) {
			candyButtons[currentRow][currentCol - 1].requestFocus();
		} else if (keyCode == KeyEvent.VK_RIGHT && currentCol < numCols - 1) {
			candyButtons[currentRow][currentCol + 1].requestFocus();
		} else if (keyCode == KeyEvent.VK_UP && currentRow > 0) {
			candyButtons[currentRow - 1][currentCol].requestFocus();
		} else if (keyCode == KeyEvent.VK_DOWN && currentRow < numRows - 1) {
			candyButtons[currentRow + 1][currentCol].requestFocus();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new GUI());
	}
}
