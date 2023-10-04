package Animations;

import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;

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
public class AnimatorStateChange implements Animator {

    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    
    protected String path_img;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver m, Drawable c) {
        manager = m;
        drawableAnimated = c;
        
        path_img = c.getLogicalEntity().getImage();
    }
    
    
    @Override
    public Drawable getDrawable() {
        return drawableAnimated;
    }

    @Override
    public void startAnimation() {
        //int size_label = drawableAnimated.getImageSize();
        //ImageIcon imgIcon = new ImageIcon(this.getClass().getResource(path_img));
        //Image imgEscalada = imgIcon.getImage().getScaledInstance(size_label, size_label, Image.SCALE_SMOOTH);
        //Icon iconoEscalado = new ImageIcon(imgEscalada);
        //mi_celda_animada.setIcon(iconoEscalado);
        manager.notifyEndAnimation(this);
    }

}