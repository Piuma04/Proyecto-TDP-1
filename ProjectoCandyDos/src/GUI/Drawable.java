package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Entities.Empty;
import Entities.Entity;
import Interfaces.LogicEntity;

@SuppressWarnings("serial")

public class Drawable extends JComponent implements GraphicalEntity {

    protected Image image;
    protected GuiAnimable agui;
    protected LogicEntity myLogicBlock;
    protected int sizeImage;


    public Drawable(GuiAnimable GUIAnimable, LogicEntity logicBlock, int si) {
        super();
        agui = GUIAnimable;
        sizeImage = si; // NOT HARDCODED!
        myLogicBlock = logicBlock;
        notifyChangeState();
        setLocation(myLogicBlock.getColumn() * sizeImage, myLogicBlock.getRow() * sizeImage);
        setSize(sizeImage, sizeImage);
        //setBounds(logicBlock.getColumn() * size, logicBlock.getRow() * size, size, size);
    }
    
    public int getImageSize() { return sizeImage; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw your shapes here using the Graphics object
        g.drawImage(image, 0, 0, null);
        // Draw other shapes as needed
    }

    public LogicEntity getLogicalEntity() {
        return myLogicBlock;
    }

    public void setImage(String path) {
        Image scaledImage = new ImageIcon(path).getImage().getScaledInstance(sizeImage, sizeImage, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        image = scaledIcon.getImage();
    }

    @Override
    public void notifyChangeState() {
        agui.animateChangeState(this);
    }

    public void notifyChangePosition() {
        agui.animateMovement(this);
    }
}
