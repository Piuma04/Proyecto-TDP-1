package Animations;

import GUI.Drawable;

/**
 * Define las operaciones esperables por sobre un elemento Animador.
 * Un animador podrá llevar adelante la animación de movimiento o de cambio de estado, de una entidad.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public interface Animator {
    /**
     * Obtiene la celda que se animará.
     * @return Retorna la celda obtenida.
     */
    public Drawable getDrawable();
    
    /**
     * Inicia el comportamiento asociado con la animación.
     */
    public void startAnimation();
    
    public int id();
}