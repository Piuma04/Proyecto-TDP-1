package Threads;

import GUI.Drawable;
import Interfaces.LogicEntity;

/**
 * Clase que permite animar el intercambio entre dos entidades.
 * Se asume que las entidades se encuentran en posiciones adyacentes.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class AnimadorIntercambio extends Thread {

	protected int size_label;
	protected int step;
	protected int delay;
	protected Drawable Drwbl_1;
	protected Drawable Drwbl_2;
	
	public AnimadorIntercambio(int size, int step, int d, Drawable c1, Drawable c2) {
		size_label = size;
		this.step = step;
		delay = d;
		Drwbl_1 = c1;
		Drwbl_2 = c2;
	}
		
	public void run() {
		LogicEntity el1 = Drwbl_1.getLogicalBlock();
		LogicEntity el2 = Drwbl_2.getLogicalBlock();
		
		int pos_x_c1 = Drwbl_1.getX();
		int pos_y_c1 = Drwbl_1.getY();
		int pos_x_c2 = Drwbl_2.getX();
		int pos_y_c2 = Drwbl_2.getY();
		
		int pos_x_c1_futura = el1.getColumn() * size_label;
		int pos_y_c1_futura = el1.getRow() * size_label;
		int pos_x_c2_futura = el2.getColumn() * size_label;
		int pos_y_c2_futura = el2.getRow() * size_label;
		
		int paso_c1_en_x = 0;
		int paso_c1_en_y = 0;
		int paso_c2_en_x = 0;
		int paso_c2_en_y = 0;
		
		boolean seguir_c1 = true;
		boolean seguir_c2 = true;		
		
		if (pos_x_c1 != pos_x_c1_futura) {
			paso_c1_en_x = (pos_x_c1 < pos_x_c1_futura ? 1 : -1);
		}
		
		if (pos_y_c1 != pos_y_c1_futura) {
			paso_c1_en_y = (pos_y_c1 < pos_y_c1_futura ? 1 : -1);
		}
		
		if (pos_x_c2 != pos_x_c2_futura) {
			paso_c2_en_x = (pos_x_c2 < pos_x_c2_futura ? 1 : -1);
		}
		
		if (pos_y_c2 != pos_y_c2_futura) {
			paso_c2_en_y = (pos_y_c2 < pos_y_c2_futura ? 1 : -1);
		}
		
		while(seguir_c1 || seguir_c2) {
			seguir_c1 = seguir_c1 && (pos_x_c1 != pos_x_c1_futura || pos_y_c1 != pos_y_c1_futura);
			if (seguir_c1) {
				pos_x_c1 += paso_c1_en_x * step;
				pos_y_c1 += paso_c1_en_y * step;
				Drwbl_1.setBounds(pos_x_c1, pos_y_c1, size_label, size_label);
			}
			
			seguir_c2 = seguir_c2 && (pos_x_c2 != pos_x_c2_futura || pos_y_c2 != pos_y_c2_futura);
			if (seguir_c2) {
				pos_x_c2 += paso_c2_en_x * step;
				pos_y_c2 += paso_c2_en_y * step;
				Drwbl_2.setBounds(pos_x_c2, pos_y_c2, size_label, size_label);
			}
			
			try {
				sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		Drwbl_1 = null;
		Drwbl_2 = null;
		
	}
	
}