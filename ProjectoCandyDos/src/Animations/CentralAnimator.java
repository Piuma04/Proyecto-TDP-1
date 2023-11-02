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

    /**
     * Indica que la celda parametrizada debe ser animada a partir de un cambio de posición.
     * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
     * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
     * se realicen primero.
     * @param c Celda que debe animarse, en relación a la posición que ubica la JLabel y la ubicación indicada por la entidad lógica
     * referenciada por c.
     */
    public void animateChangePosition(Drawable c) {
        Animator animator = new AnimatorMovement(this, 1, 2, c);
        enqueueAnimator(animator);
    }
    
    /**
     * Indica que la celda parametrizada debe ser animada a partir de un cambio de estado.
     * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
     * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
     * se realicen primero.
     * @param c Celda que debe animarse, en relación a la imagen actual que la representa.
     */
    public void animateChangeState(Drawable c) {
        Animator animator = new AnimatorStateChange(this, c);

        if (c.getSkipQueue())
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
        drawableAnimator.startAnimation(animator);
    }

    private void enqueueAnimator(Animator animator) {
        if (gui.getPendingAnimations() == 0)
            currentAnimatorType = animator.id();
        if (queue.isEmpty() && currentAnimatorType == animator.id()) {
            startDrawableAnimation(animator);
        }
        else
            queue.add(animator);
    }

    @Override
    public void notifyEndAnimation(Animator a, boolean bDestroy) {

        gui.notifyAnimationEnd();

        drawableAnimator.endAnimation(a);

        if (gui.getPendingAnimations() == 0)
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

                if (!isActive())
                    while (!extraTasks.isEmpty())
                        extraTasks.poll().run();
            });

        if (bDestroy)
            SwingUtilities.invokeLater(() -> { gui.removeEntity(a.getDrawable()); });
    }

    public void executeAfterAnimation(Runnable r) {
        if (isActive())
            extraTasks.add(r);
        else
            r.run();
    }

    public boolean isActive() { return !queue.isEmpty() || gui.getPendingAnimations() > 0; }
}