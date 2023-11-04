package Animations;

import GUI.Drawable;
import Interfaces.LogicEntity;
import VisualPlayers.ImageStorage;
import VisualPlayers.GifPlayer;

/**
 * Modela el comportamiento de un animador que permite visualizar el cambio de estado de una entidad.
 * Cuando el animador comienza su animación, modifica la imagen asociada a la celda animada.
 * La imagen que se considerará para efectivizar el cambio de estado, será la que se encontraba asociada a la celda lógica al momento
 * de crear el animador.
 * Una vez finalizada la animación, el animador notificará a su manager de esta situación.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class AnimatorStateChange extends Thread implements Animator {

    private static ImageStorage imageStorage = new ImageStorage();
    private static GifPlayer gifPlayer = new GifPlayer();

    protected AnimatorDriver manager;
    protected Drawable drawable;
    protected String animationPath;
    protected boolean bGif;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver manager, Drawable drawable) {
        this.manager = manager;
        this.drawable = drawable;
        animationPath = drawable.getLogicalEntity().getImage();
        bGif = isGif(animationPath);
        if (bGif)
            gifPlayer.add(animationPath, drawable.getImageSize());
        else
            imageStorage.add(animationPath, drawable.getImageSize());
    }

    @Override
    public void run() {
        if (animationPath == null)
            drawable.delete();
        else if (bGif)
            gifPlayer.play(animationPath, drawable);
        else
            drawable.setIcon(imageStorage.get(animationPath));
        drawable.repaint();
        manager.notifyEndAnimation(this);
    }

    @Override public Drawable getDrawable() { return drawable; }
    @Override public void startAnimation() { this.start(); }
    @Override public int id() { return 2; }
    private static boolean isGif(String imagePath) { return imagePath != null && imagePath.endsWith(".gif"); }
    
    //public String toString() { return "State(" + drawable.getLogicalEntity().toString() + ")"; }
    public String toString() {
        LogicEntity log = drawable.getLogicalEntity();
        String s = "State(" + "("+ log.getRow() + ", " + log.getColumn() + ")) (" + log.toString() + ")";
        return  s;
    }
}