package GUI;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Interfaces.LogicEntity;
import Logic.Game;

@SuppressWarnings("serial")
public class Drawable extends JComponent implements GraphicalEntity {

    protected GuiAnimable agui;
    protected LogicEntity myLogicEntity;
    protected int sizeIcon;
    protected ImageIcon myIcon;
    
    protected boolean bSkipQueue;

    public Drawable(GuiAnimable GUIAnimable, LogicEntity logicBlock, int sizeImg) {
        super();
        agui = GUIAnimable;
        myLogicEntity = logicBlock;
        sizeIcon = sizeImg;
        bSkipQueue = false;
        
        int pos[] = getVisualLocation();
        setLocation(pos[0], pos[1]);
        setImage(logicBlock.getImage());
        setSize(sizeIcon, sizeIcon);
        notifyChangeState();
    }

    public int getImageSize() { return sizeIcon; }
    public LogicEntity getLogicalEntity() { return myLogicEntity; }

    public void setImage(String path) {
        Image scaledImage = new ImageIcon(path).getImage().getScaledInstance(sizeIcon, sizeIcon, Image.SCALE_SMOOTH);
        myIcon = new ImageIcon(scaledImage);
    }

    public void setImage(Image im) { myIcon = new ImageIcon(im); }
    
    @Override public void notifyChangeState()    { agui.animateChangeState(this); }
    @Override public void notifyChangePosition() { agui.animateMovement(this); }
    @Override public void setSkipQueue(boolean skipQueue) { bSkipQueue = skipQueue; }
    public boolean getSkipQueue() { return bSkipQueue; }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        myIcon.paintIcon(this, g, 0, 0);
    }

    public int[] getVisualLocation() {
        int boardLabelSize = Game.getLabelSize();
        int x = myLogicEntity.getColumn() * boardLabelSize + (boardLabelSize - sizeIcon) / 2;
        int y = myLogicEntity.getRow() * boardLabelSize + (boardLabelSize - sizeIcon) / 2;
        return new int[]{x, y};
    }
}
