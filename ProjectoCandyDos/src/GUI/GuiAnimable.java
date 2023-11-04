package GUI;

import VisualPlayers.SoundPlayer;

public interface GuiAnimable {

    public void animateMovement(Drawable drawable);
    public void animateChangeState(Drawable drawable);
    public void playSound(SoundPlayer sound);
    public void deleteDrawable(Drawable drawable);
}