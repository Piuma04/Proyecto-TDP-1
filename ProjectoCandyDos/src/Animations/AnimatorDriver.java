package Animations;

import GUI.Drawable;

/**
 * Define las operaciones esperables por sobre un Manejador de Animaciones.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface AnimatorDriver {
    
    /**
     * Permite notificar la finalización de una animación llevada a cabo por el animador parametrizado, que se encontraba en curso.
     * Notifica a la ventana asociada al manejador que se finalizó con una de las animaciones pendientes.
     * @param a Animador que se encontraba en progreso y finalizó su actividad
     */
    public void notifyEndAnimation(Animator a);

    public void notifyToDelete(Drawable drawable);
}