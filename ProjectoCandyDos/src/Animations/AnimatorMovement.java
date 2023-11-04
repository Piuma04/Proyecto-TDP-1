package Animations;

import GUI.Drawable;

/**
 * Modela el comportamiento de un animador que permite visualizar el cambio de posición de una entidad.
 * Cuando el animador comienza su animación, modifica la ubicación de la celda, en función de parámetros step, delay, que se asumen válidos.
 * El animador hace uso de un Thread específico para efectivizar el comportamiento.
 * Una vez finalizada la animación, el animador notificará a su manager de esta situación.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class AnimatorMovement extends Thread implements Animator {

    private static final int _id = 1;

    protected AnimatorDriver manager;
    protected Drawable drawable;
    
    protected int pos_x_destination;
    protected int pos_y_destination;
    
    protected int step;
    protected int delay;
    
    /**
     * Inicializa el estado del animador, considerando
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param step La cantidad de pixels que se desplaza la Celda en cada movimiento.
     * @param d El delay establecido entre desplazamiento y desplazamiento.
     * @param c La celda animada.
     */
    public AnimatorMovement(AnimatorDriver manager, int step, int delay, Drawable drawable) {
        this.manager = manager;
        this.drawable = drawable;
        this.step = step;
        this.delay = delay;

        int pos[] = drawable.getVisualLocation();
        pos_x_destination = pos[0];
        pos_y_destination = pos[1];
    }

    @Override
    public void run() {
        int pos_x_actual = drawable.getX();
        int pos_y_actual = drawable.getY();
        
        int paso_en_x = 0;
        int paso_en_y = 0;
        
        if (pos_x_actual != pos_x_destination)
            paso_en_x = (pos_x_actual < pos_x_destination ? 1 : -1);
        
        if (pos_y_actual != pos_y_destination)
            paso_en_y = (pos_y_actual < pos_y_destination ? 1 : -1);

        while( (pos_x_actual != pos_x_destination) || (pos_y_actual != pos_y_destination)) {
            int dx = paso_en_x * step;
            int dy = paso_en_y * step;
            pos_x_actual += dx;
            pos_y_actual += dy;

            drawable.setLocation(pos_x_actual, pos_y_actual);
            drawable.repaint();
            try { sleep(delay); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        manager.notifyEndAnimation(this);
    }

    @Override public Drawable getDrawable() { return drawable; }
    @Override public void startAnimation() { this.start(); }
    @Override public final int id() { return _id; }
    
    public String toString() { return "Movement(" + drawable.getLogicalEntity().toString() + ")"; }
}