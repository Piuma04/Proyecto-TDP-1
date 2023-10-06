package Animations;

import GUI.Drawable;
import Interfaces.LogicEntity;

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

    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    protected String path_img;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver m, Drawable c, String animationPath) {
        manager = m;
        drawableAnimated = c;
        path_img = animationPath;
    }
    
    
    @Override
    public Drawable getDrawable() {
        return drawableAnimated;
    }

    @Override
    public void run() {
        drawableAnimated.setImage("src/imagenes/" + path_img);
        // DEBUG
        LogicEntity ent = drawableAnimated.getLogicalEntity();
        if (ent != null) {
            //System.out.println(drawableAnimated.getLogicalEntity().toString() + "path: " + path_img);
            //System.out.println("{" + ent.getRow() + ", " + ent.getColumn()  + "}");
            //System.out.println("{" + drawableAnimated.getY() + ", " + drawableAnimated.getX()  + "}");
        }
        // END DEBUG.
        if (path_img != null && path_img.contains(".gif")) {
            long startTime =  System.currentTimeMillis();
            long elapsedTime =  startTime;
            final double timer = 1.2; // seconds
            while (elapsedTime < startTime + timer * 1000) {
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elapsedTime = System.currentTimeMillis();
                drawableAnimated.repaint();
            }
        }
        drawableAnimated.repaint();
        manager.notifyEndAnimation(this, path_img == null);
    }

    @Override
    public void startAnimation() {
        this.start();
    }

}