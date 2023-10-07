package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Interfaces.LogicEntity;

@SuppressWarnings("serial")
public class Drawable extends JComponent implements GraphicalEntity {

    protected GuiAnimable agui;
    protected LogicEntity myLogicBlock;
    protected int sizeIcon;
    protected ImageIcon myIcon;

    public Drawable(GuiAnimable GUIAnimable, LogicEntity logicBlock, int sizeImg) {
        super();
        agui = GUIAnimable;
        myLogicBlock = logicBlock;
        sizeIcon = sizeImg;
        setImage(logicBlock.getImage());
        setLocation(myLogicBlock.getColumn() * sizeIcon, myLogicBlock.getRow() * sizeIcon);
        setSize(sizeIcon, sizeIcon);
        notifyChangeState();
        // setBounds(logicBlock.getColumn() * size, logicBlock.getRow() * size, size, size);
    }

    public int getImageSize() { return sizeIcon; }
    public LogicEntity getLogicalEntity() { return myLogicBlock; }

    public void setImage(String path) {
        Image scaledImage = new ImageIcon(path).getImage().getScaledInstance(sizeIcon, sizeIcon, Image.SCALE_DEFAULT);
        myIcon = new ImageIcon(scaledImage);
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
