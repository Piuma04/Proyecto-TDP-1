package GUI;

import java.awt.Graphics;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

import Interfaces.LogicEntity;
import Logic.Game;

@SuppressWarnings("serial")
public class Drawable extends JComponent implements GraphicalEntity {

    protected static Icon EMPTY_ICON = new ImageIcon();
    protected GuiAnimable agui;
    protected LogicEntity myLogicEntity;
    protected Icon myIcon;
    protected int sizeIcon;
    
    protected boolean bSkipQueue;

    public Drawable(GuiAnimable GUIAnimable, LogicEntity logicBlock, int sizeImg) {
        super();
        agui = GUIAnimable;
        myLogicEntity = logicBlock;
        sizeIcon = sizeImg;
        bSkipQueue = false;
        
        final int pos[] = getVisualLocation();
        setLocation(pos[0], pos[1]);
        setSize(sizeIcon, sizeIcon);
        myIcon = EMPTY_ICON;
        notifyChangeState();
    }

    public int getImageSize() { return sizeIcon; }
    public LogicEntity getLogicalEntity() { return myLogicEntity; }

    public void setIcon(Icon icon) { myIcon = icon; }
    
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
        final int boardLabelSize = Game.getLabelSize();
        final int x = myLogicEntity.getColumn() * boardLabelSize + (boardLabelSize - sizeIcon) / 2;
        final int y = myLogicEntity.getRow() * boardLabelSize + (boardLabelSize - sizeIcon) / 2;
        return new int[]{x, y};
    }
}
