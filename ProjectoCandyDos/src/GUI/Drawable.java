package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Interfaces.LogicEntity;

@SuppressWarnings("serial")
public class Drawable extends JComponent implements GraphicalEntity {

    protected GuiAnimable agui;
    protected LogicEntity myLogicEntity;
    protected int sizeIcon;
    protected ImageIcon myIcon;

    public Drawable(GuiAnimable GUIAnimable, LogicEntity logicBlock, int sizeImg) {
        super();
        agui = GUIAnimable;
        myLogicEntity = logicBlock;
        sizeIcon = sizeImg;
        
        setImage(logicBlock.getImage());
        setLocation(myLogicEntity.getColumn() * sizeIcon, myLogicEntity.getRow() * sizeIcon);
        setSize(sizeIcon, sizeIcon);
        notifyChangeState();
        
        // setBounds(logicBlock.getColumn() * size, logicBlock.getRow() * size, size, size);
    }

    public int getImageSize() { return sizeIcon; }
    public LogicEntity getLogicalEntity() { return myLogicEntity; }

    public void setImage(String path) {
        Image scaledImage = new ImageIcon(path).getImage().getScaledInstance(sizeIcon, sizeIcon, Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(scaledImage);
    }
    public void setImage(Image im) {
        //Image scaledImage = new ImageIcon(im).getImage().getScaledInstance(sizeIcon, sizeIcon, Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(im);
    }

    @Override
    public void notifyChangeState() { agui.animateChangeState(this); }
    public void notifyChangePosition() { agui.animateMovement(this); }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw your shapes here using the Graphics object
        myIcon.paintIcon(this, g, 0, 0);
        // g.drawImage(image, 0, 0, this);
        // Draw other shapes as needed
    }
}
