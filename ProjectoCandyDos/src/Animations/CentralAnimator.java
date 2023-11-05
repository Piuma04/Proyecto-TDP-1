package Animations;

import java.util.Queue;

import javax.swing.SwingUtilities;

import java.util.ArrayDeque;
import java.util.List;

import GUI.Drawable;
import GUI.Gui;
import VisualPlayers.SoundPlayer;

/**
 * Modela el manager de animaciones requerido para efectivizar las mismas en
 * función al orden en el que fueron solicitadas desde la ventana. Ante cada
 * nueva animación solicitada por ventana y que se debe realizar por sobre una
 * celda, se encarga de efectivizar estas animaciones en el orden que
 * corresponda a su arriba (FIFO). Permite que dos o más animaciones por sobre
 * una misma celda se realicen efectivamente en orden secuencial, sin
 * solapamientos. Las animaciones entre entidades diferentes se resolverán,
 * recurrentemente, considerando que algunos animadores se efectivizarán
 * mediante Threads.
 * 
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class CentralAnimator implements AnimatorDriver {
    protected Gui gui;
    protected DrawableAnimator drawableAnimator;
    protected SyncList<Animator> animatorList;
    protected Queue<Runnable> extraTasks;
    public static int counter = 0;

    public CentralAnimator(Gui v) {
        gui = v;
        drawableAnimator = new DrawableAnimator();
        animatorList = new SyncList<Animator>();
        extraTasks = new ArrayDeque<Runnable>();
    }

    public void animateChangePosition(Drawable drawable) {
        Animator animator = new AnimatorMovement(this, 1, 2, drawable);
        enqueueAnimator(animator);
    }

    public void animateChangeState(Drawable drawable) {
        Animator animator = new AnimatorStateChange(this, drawable);

        if (drawable.getSkipQueue())
            startDrawableAnimation(animator);
        else
            enqueueAnimator(animator);
    }

    public void playSound(SoundPlayer sound) {
        Animator animator = new AnimatorSound(this, sound);
        enqueueAnimator(animator);
    }

    private void startDrawableAnimation(Animator animator) {
        gui.notifyAnimationInProgress();

        if (animator.getDrawable() != null)
            drawableAnimator.startAnimation(animator);
        else
            animator.startAnimation(); // ANIMATION NOT ASOCIATED TO A DRAWABLE. FOR EXAMPLE SOUND.
    }

    private void enqueueAnimator(Animator animator) {
        if (animatorList.add(animator)) {
            startDrawableAnimation(animator);
        }
    }

    @Override
    public void notifyEndAnimation(Animator animator) {

        if (animator.getDrawable() != null)
            drawableAnimator.endAnimation(animator);

        gui.notifyAnimationEnd();
        animatorList.remove(animator);

        if (gui.getPendingAnimations() == 0) {
            SwingUtilities.invokeLater(() -> {
                if (!animatorList.isEmpty()) {
                    int lastType = animatorList.getCurrentType();
                    animatorList.updateType();
                    if (lastType != animatorList.getCurrentType()) {
                        List<Animator> sameTypes = animatorList.peekSameTypes();
                        for (Animator a : sameTypes)
                            startDrawableAnimation(a);  
                    }
                } else {
                    while (!extraTasks.isEmpty())
                        extraTasks.poll().run();
                }
            });
        }
    }

    public void executeAfterAnimation(Runnable task) {
        if (isActive())
            extraTasks.add(task);
        else
            task.run();
    }

    public boolean isActive() { return gui.getPendingAnimations() > 0 || !animatorList.isEmpty(); }
}