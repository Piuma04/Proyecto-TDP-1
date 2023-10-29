package Animations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import GUI.Drawable;

public class DrawableAnimator {
    protected HashMap<Drawable, List<Animator>> map_drawable_animations;

    public DrawableAnimator() {
        map_drawable_animations = new HashMap<Drawable, List<Animator>>();
    }
    
    public void startAnimation(Animator animator) {
        Drawable c = animator.getDrawable();
        if (hasAnimationInProgress(c)) {
            map_drawable_animations.get(c).add(animator);
        } else {
            map_drawable_animations.put(c, new LinkedList<Animator>());
            map_drawable_animations.get(c).add(animator);
            animator.startAnimation();
        }
    }

    public void endAnimation(Animator a) {
        Animator animator;
        List<Animator> drawableAnimations;

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
        if (map_drawable_animations.get(c) != null)
            retorno = !map_drawable_animations.get(c).isEmpty();
        return retorno;
    }
}
