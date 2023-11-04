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

    public void endAnimation(Animator animator) {
        Animator nextAnimator;
        List<Animator> drawableAnimations;
        final Drawable drawable = animator.getDrawable();

        drawableAnimations = map_drawable_animations.get(drawable);
        drawableAnimations.remove(animator);

        if (!drawableAnimations.isEmpty()) {
            nextAnimator = drawableAnimations.get(0);
            nextAnimator.startAnimation();
        } else {
            map_drawable_animations.remove(drawable);
        }
    }

    /**
     * Estima si la celda parametrizada actualmente cuenta con animaciones en progreso. 
     * @param drawable Celda que se desea considerar para el chequeo de animaciones en progreso.
     * @return True si la celda tiene animaciones actualmente en progreso; false en caso contrario.
     */
    private boolean hasAnimationInProgress(Drawable drawable) {
        final List<Animator> list = map_drawable_animations.get(drawable); 
        return list != null && !list.isEmpty();
    }
}
