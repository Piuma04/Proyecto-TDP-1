package Animations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.swing.SwingUtilities;

import java.util.ArrayDeque;

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
    
    protected Queue<Animator> queue;
    protected Runnable myTask;
    protected Thread myThread;

    protected boolean bReset = false;

    public CentralAnimator(Gui v) {
        gui = v;
        map_drawable_animations = new HashMap<Drawable, List<Animator>>();
        queue = new ArrayDeque<Animator>();

        myTask = () -> {
            int currentAnimatorType = 0;
            bReset = false;
            Animator head = queue.poll();
            while (head != null) {
                Integer id = head.id();
                if (currentAnimatorType != id) {
                    while (gui.getPendingAnimations() > 0 && !bReset) {
                        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace();}
                    }
                    currentAnimatorType = id;
                }
                startAnimation(head);
                head = queue.poll();
            }
        };
        myThread = new Thread(myTask);
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
        int finalRow = c.getLogicalEntity().getRow();
        int finalColumn = c.getLogicalEntity().getColumn();
        Animator animador = new AnimatorMovement(this, 1, 2, c, finalRow, finalColumn);
        queue.add(animador);

        if (!myThread.isAlive()) { myThread = new Thread(myTask); myThread.start(); }
    }
    
    /**
     * Indica que la celda parametrizada debe ser animada a partir de un cambio de estado.
     * La animación será lanzada de inmediato, siempre que no existan animaciones en progreso sobre c.
     * La animación será encolada para efectivizarse en el futuro, a la espera de que las animaciones solicitadas previamente sobre c
     * se realicen primero.
     * @param c Celda que debe animarse, en relación a la imagen actual que la representa.
     */
    public void animateChangeState(Drawable c) {
        String imagePath = c.getLogicalEntity().getImage();
        Animator animador = new AnimatorStateChange(this, c, imagePath);
        
        //boolean isBlock = imagePath != null && imagePath.contains("vacio");
        if (c.getSkipQueue())
            startAnimation(animador);
        else {
            queue.add(animador);
            if (!myThread.isAlive()) { myThread = new Thread(myTask); myThread.start(); }
        }
    }

    public void playSound(SoundPlayer sound) {
        Animator animador = new AnimatorSound(this, sound);
        queue.add(animador);
    }

    public void startAnimation(Animator animador) {
        gui.notifyAnimationInProgress();
        Drawable c = animador.getDrawable();
        if (hasAnimationInProgress(c) ) {
            map_drawable_animations.get(c).add(animador);
        } else {
            map_drawable_animations.put(c, new LinkedList<Animator>());
            map_drawable_animations.get(c).add(animador);
            animador.startAnimation();
        }
    }

    @Override
    public void notifyEndAnimation(Animator a, boolean bDestroy) {
        Animator animator;
        List<Animator> drawableAnimations;

        gui.notifyAnimationEnd();

        drawableAnimations = map_drawable_animations.get(a.getDrawable());
        drawableAnimations.remove(a);
        
        if (!drawableAnimations.isEmpty()) {
            animator = drawableAnimations.get(0);
            animator.startAnimation();
        }
        if (bDestroy)
            SwingUtilities.invokeLater(() -> { gui.removeEntity(a.getDrawable()); });
    }
    
    public boolean isActive() { return !queue.isEmpty() || gui.getPendingAnimations() > 0; }

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