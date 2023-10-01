package GUI;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Interfaces.LogicBlock;



@SuppressWarnings("serial")
public class Cell extends JLabel implements GraphicalBlock {
	
	protected GUI mi_ventana;
	protected LogicBlock MyLogicBlock;
	protected int sizeLabel;
	
	public Cell(GUI v, LogicBlock e, int s) {
		super();
		mi_ventana = v;
		MyLogicBlock = e;
		sizeLabel = s;
		setBounds(e.getColumn()*sizeLabel, e.getRow()*sizeLabel, sizeLabel, sizeLabel);
		cambiar_imagen(e.getImage());	
	}
	
	
	
	public LogicBlock getLogicalBlock() {
		return MyLogicBlock;
	}
	
	protected void cambiar_imagen(String i) {
		ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
		Image imgEscalada = imgIcon.getImage().getScaledInstance(sizeLabel, sizeLabel, Image.SCALE_SMOOTH);
		Icon iconoEscalado = new ImageIcon(imgEscalada);
		setIcon(iconoEscalado);
	}


	@Override
	public void notifyChangeStatus() {
		cambiar_imagen(MyLogicBlock.getImage());
		
	}


	
	public void notifyChangePosition() {
		// implementar esto: mi_ventana.considerar_para_intercambio_posicion(this);
	}
}
