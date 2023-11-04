package Animations;

import java.util.Queue;

import javax.swing.SwingUtilities;

import java.util.ArrayDeque;

import GUI.Drawable;
import GUI.Gui;
import VisualPlayers.SoundPlayer;

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
    protected DrawableAnimator drawableAnimator;

    protected Queue<Runnable> extraTasks;
    protected Queue<Animator> queue;
    protected int currentAnimatorType;

    public CentralAnimator(Gui v) {
        gui = v;
        drawableAnimator = new DrawableAnimator();
        queue = new ArrayDeque<Animator>();
        extraTasks = new ArrayDeque<Runnable>();
        currentAnimatorType = 2;
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
            animator.startAnimation();
    }

    private void enqueueAnimator(Animator animator) {
        if (gui.getPendingAnimations() == 0)
            currentAnimatorType = animator.id();

        if (queue.isEmpty() && currentAnimatorType == animator.id())
            startDrawableAnimation(animator);
        else
            queue.add(animator);
    }

    @Override
    synchronized public void notifyEndAnimation(Animator animator) {
        if (animator.getDrawable() != null)
            drawableAnimator.endAnimation(animator);
        gui.notifyAnimationEnd();

        if (gui.getPendingAnimations() == 0 && !queue.isEmpty()) {
            SwingUtilities.invokeLater( () -> {
                Animator head = queue.peek();
                while (head != null) {
                    int id = head.id();
                    if (currentAnimatorType != id) {
                        if (gui.getPendingAnimations() > 0)
                            break;
                        currentAnimatorType = id;
                    }
                    startDrawableAnimation(head);
                    queue.poll();
                    head = queue.peek();
                }
            });
        }

        if (!isActive())
            SwingUtilities.invokeLater( () -> {
                while (!extraTasks.isEmpty())
                    extraTasks.poll().run();
            });
    }

    public void executeAfterAnimation(Runnable task) {
        if (isActive())
            extraTasks.add(task);
        else
            task.run();
    }

    public boolean isActive() { return gui.getPendingAnimations() > 0 || !queue.isEmpty(); }
    
}