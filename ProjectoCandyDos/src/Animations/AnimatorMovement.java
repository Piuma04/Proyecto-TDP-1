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

    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    
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
    public AnimatorMovement(AnimatorDriver m, int step, int d, Drawable c, int finalRow, int finalColumn) {
        manager = m;
        drawableAnimated = c;
        
        this.step = step;
        delay = d;

        int pos[] = c.getVisualLocation(finalRow, finalColumn);
        pos_x_destination = pos[0];
        pos_y_destination = pos[1];
    }
    
    @Override
    public void run() {
        int pos_x_actual = drawableAnimated.getX();
        int pos_y_actual = drawableAnimated.getY();
        
        int paso_en_x = 0;
        int paso_en_y = 0;
        
        if (pos_x_actual != pos_x_destination)
            paso_en_x = (pos_x_actual < pos_x_destination ? 1 : -1);
        
        if (pos_y_actual != pos_y_destination)
            paso_en_y = (pos_y_actual < pos_y_destination ? 1 : -1);

        
        while( (pos_x_actual != pos_x_destination) || (pos_y_actual != pos_y_destination)) {
            pos_x_actual += paso_en_x * step;
            pos_y_actual += paso_en_y * step;

            drawableAnimated.setLocation(pos_x_actual, pos_y_actual);
            drawableAnimated.repaint();
            try { sleep(delay); } catch (InterruptedException e) { e.printStackTrace(); }
        }
        manager.notifyEndAnimation(this, false);
    }

    @Override public Drawable getDrawable() { return drawableAnimated; }
    @Override public void startAnimation() { this.start(); }
    @Override public int id() { return 1; }
}