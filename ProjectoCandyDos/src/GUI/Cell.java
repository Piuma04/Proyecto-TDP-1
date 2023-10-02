package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import Interfaces.LogicBlock;

@SuppressWarnings("serial")
public class Cell extends JComponent implements GraphicalBlock {

    protected LogicBlock MyLogicBlock;
    protected Image image;
    protected int sizeLabel;

    public Cell(LogicBlock e, int s) {
        super();
        //image = new ImageIcon("src/imagenes/vacio.png").getImage().getScaledInstance(s, s, Image.SCALE_SMOOTH);
        image = new ImageIcon("src/imagenes/vacio.png").getImage();
        MyLogicBlock = e;
        sizeLabel = s;
        setBounds(e.getColumn() * sizeLabel, e.getRow() * 10, sizeLabel, sizeLabel);
        //cambiar_imagen(e.getImage());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw your shapes here using the Graphics object
        g.drawImage(image, 0, 0, null);
        // Draw other shapes as needed
    }

    public LogicBlock getLogicalBlock() {
        return MyLogicBlock;
    }

    protected void cambiar_imagen(String i) {
        ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
        Image imgEscalada = imgIcon.getImage().getScaledInstance(sizeLabel, sizeLabel, Image.SCALE_SMOOTH);
        Icon iconoEscalado = new ImageIcon(imgEscalada);
        //setIcon(iconoEscalado);
    }

    @Override
    public void notifyChangeStatus() {
        cambiar_imagen(MyLogicBlock.getImage());

    }

    public void notifyChangePosition() {
        // implementar esto: mi_ventana.considerar_para_intercambio_posicion(this);
    }
}
