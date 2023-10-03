package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Interfaces.LogicEntity;

@SuppressWarnings("serial")

public class Drawable extends JComponent implements GraphicalEntity {

    protected Image image;
    protected LogicEntity myLogicBlock;
    protected int sizeImage;


    public Drawable(LogicEntity logicBlock) {
        super();
        
        //image = new ImageIcon("src/imagenes/vacio.png").getImage().getScaledInstance(s, s, Image.SCALE_SMOOTH);
        sizeImage = 80; // NOT HARDCODED! // TODO
        myLogicBlock = logicBlock;
        openImage("src/imagenes/" + logicBlock.getImage());
        setSize(sizeImage, sizeImage);
        setLocation(myLogicBlock.getColumn() * sizeImage, myLogicBlock.getRow() * sizeImage);
        //setBounds(logicBlock.getColumn() * size, logicBlock.getRow() * size, size, size);
        //cambiar_imagen(e.getImage());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw your shapes here using the Graphics object
        g.drawImage(image, 0, 0, null);
        // Draw other shapes as needed
    }

    public LogicEntity getLogicalBlock() {
        return myLogicBlock;
    }

    protected void cambiar_imagen(String i) {
        //ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(i));
        //Image imgEscalada = imgIcon.getImage().getScaledInstance(sizeImage, sizeImage, Image.SCALE_SMOOTH);
        //Icon iconoEscalado = new ImageIcon(imgEscalada);
        //setIcon(iconoEscalado);
    }

    protected void openImage(String path) {
        Image scaledImage = new ImageIcon(path).getImage().getScaledInstance(sizeImage, sizeImage, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        image = scaledIcon.getImage();
    }
    
    @Override
    public void notifyChangeStatus() {
        //cambiar_imagen(myLogicBlock.getImage());

    }

    public void notifyChangePosition() {
        // implementar esto: mi_ventana.considerar_para_intercambio_posicion(this);
    }
}
