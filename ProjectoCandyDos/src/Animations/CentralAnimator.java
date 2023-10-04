package Animations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import GUI.Drawable;
import GUI.Gui;

/**
 * Modela el manager de animaciones requerido para efectivizar las mismas en función al orden en el que fueron solicitadas desde la ventana.
 * Ante cada nueva animación solicitada por ventana y que se debe realizar por sobre una celda, se encarga de efectivizar estas animaciones 
 * en el orden que corresponda a su arriba (FIFO).
 * Permite que dos o más animaciones por sobre una misma celda se realicen efectivamente en orden secuencial, sin solapamientos.
 * Las animaciones entre entidades diferentes se resolverán, recurrentemente, considerando que algunos animadores se efectivizarán mediante Threads.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class CentralAnimator implements AnimatorDriver {
    
    protected Gui gui;
    protected HashMap<Drawable, List<Animator>> map_drawable_animations;
    protected List<Thread> animationThreads;
    protected int size_label;
    
    public CentralAnimator(Gui v) {
        gui = v;
        map_drawable_animations = new HashMap<Drawable, List<Animator>>();
        animationThreads = new LinkedList<Thread>();
    }
    
    /**
     * Indica que la celda parametrizada debe ser animada a partir de un cambio de posición.
     * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
     * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
     * se realicen primero.
     * @param c Celda que debe animarse, en relación a la posición que ubica la JLabel y la ubicación indicada por la entidad lógica
     * referenciada por c.
     */
    public void animateChangePosition(Drawable c) {
        Animator animador = new AnimatorMovement(this, 1,5, c);
        gui.notifyAnimationInProgress();
        
        if (hasAnimationInProgress(c) ) {
            map_drawable_animations.get(c).add(animador);
        }else {
            map_drawable_animations.put(c, new LinkedList<Animator>());
            map_drawable_animations.get(c).add(animador);
            animador.startAnimation();
        }
    }
    
    /**
     * Indica que la celda parametrizada debe ser animada a partir de un cambio de estado.
     * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
     * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
     * se realicen primero.
     * @param c Celda que debe animarse, en relación a la imagen actual que la representa.
     */
    /*public void animateChangeState(Drawable c) {
        Animator animador = new AnimatorStateChange(this, c);
        gui.notifyAnimationInProgress();
        
        if (hasAnimationInProgress (c) ) {
            mapeo_celda_animaciones.get(c).add(animador);
        }else {
            mapeo_celda_animaciones.put(c, new LinkedList<Animator>());
            mapeo_celda_animaciones.get(c).add(animador);
            animador.startAnimation();
        }
        
    }*/

    @Override
    public void notifyEndAnimation(Animator a) {
        Animator animator;
        List<Animator> drawableAnimations;
        
        gui.notifyAnimationEnd();
        
        drawableAnimations = map_drawable_animations.get(a.getDrawable());
        drawableAnimations.remove(a);
        
        if (!drawableAnimations.isEmpty()) {
            animator = drawableAnimations.get(0);
            animator.startAnimation();
        }
    }
    
    /**
     * Estima si la celda parametrizada actualmente cuenta con animaciones en progreso. 
     * @param c Celda que se desea considerar para el chequeo de animaciones en progreso.
     * @return True si la celda tiene animaciones actualmente en progreso; false en caso contrario.
     */
    private boolean hasAnimationInProgress(Drawable c) {
        boolean retorno = false;
        if (map_drawable_animations.get(c) != null) {
            retorno = !map_drawable_animations.get(c).isEmpty();
        }
        return retorno;
    }
}