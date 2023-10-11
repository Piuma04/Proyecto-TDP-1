package Animations;

import GUI.Drawable;

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

    private static final String imagesPath = "src/Resources/images/";
    private static GifPlayer gifPlayer = new GifPlayer();

    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    protected String imagePat;
    protected boolean bGif;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver m, Drawable c, String animationPath) {
        manager = m;
        drawableAnimated = c;
        imagePat = animationPath;
        bGif = isGif(animationPath);
        if (bGif) gifPlayer.add(animationPath, drawableAnimated.getImageSize());
    }

    @Override
    public void run() {
        drawableAnimated.setImage(imagesPath + imagePat);
        if (bGif) gifPlayer.play(imagePat, drawableAnimated);
        drawableAnimated.repaint();
        manager.notifyEndAnimation(this, imagePat == null);
    }

    @Override public Drawable getDrawable() { return drawableAnimated; }
    @Override public void startAnimation() { this.start(); }
    @Override public int id() { return 2; }
    private static boolean isGif(String imagePath) { return imagePath != null && imagePath.endsWith(".gif"); }
}