package GUI;

/**
 * Define las operaciones esperables por sobre una ventana que permite notificarse sobre animaciones en progreso o finalizadas.
 * Las notificaciones se esperan sean realizadas desde la central de animaciones.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface GuiNotifiable {
    /**
     * Notifica que una nueva animación comenzó a ejecutarse.
     */
    public void notifyAnimationInProgress();
    
    /**
     * Notifica que una animación en progreso, ha finalizado.
     */
    public void notifyAnimationEnd();
}