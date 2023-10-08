package Animations;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.sound.sampled.Clip;
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
    protected int size_label;
    
    protected Queue<Object[]> queue;
    protected int currentAnimatorType;
    protected Runnable myTask;
    protected Thread myThread;
    
    public CentralAnimator(Gui v) {
        gui = v;
        map_drawable_animations = new HashMap<Drawable, List<Animator>>();
        queue = new ArrayDeque<Object[]>();
        currentAnimatorType = 2;
        
        myTask = () -> {
            Object[] head = queue.poll();
            while (head != null) {
                Integer i = (Integer)head[0];
                if (currentAnimatorType != i) {
                    while (gui.getPendingAnimations() > 0) {
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } currentAnimatorType = i;
                }
                if (i == 1)
                {
                    Drawable c = (Drawable)head[1];
                    int finalRow = (Integer)head[2];
                    int finalColumn = (Integer)head[3];
                    Animator animador = new AnimatorMovement(this, 1, 2, c, finalRow, finalColumn);
                    startAnimation(c, animador);
                }
                else if (i == 2) {
                    Drawable c = (Drawable)head[1];
                    String animationPath = (String)head[2];
                    int gifFrames = (Integer)head[3];
                    Animator animador = new AnimatorStateChange(this, c, animationPath, gifFrames);
                    startAnimation(c, animador);
                } else if (i == 3) {
                    Clip sound = (Clip)head[1];
                    sound.stop();
                    sound.flush();
                    sound.setFramePosition(0);
                    try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                    sound.start();
                }
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
        Object data[] = new Object[4];
        data[0] = 1;
        data[1] = c;
        data[2] = c.getLogicalEntity().getRow();
        data[3] = c.getLogicalEntity().getColumn();
        queue.add(data);
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
        int gifFrameCount = c.getLogicalEntity().getGifFrameCount(); 
        Object data[] = new Object[4];
        data[0] = 2;
        data[1] = c;
        data[2] = imagePath;
        data[3] = gifFrameCount;
        
        boolean isBlock = imagePath != null && imagePath.contains("vacio");
        if (!isBlock)
            queue.add(data);
        else {
            Animator animador = new AnimatorStateChange(this, c, imagePath, gifFrameCount);
            startAnimation(c, animador);
        }
        
        if (!myThread.isAlive()) { myThread = new Thread(myTask); myThread.start(); }
    }

    public void playSound(Clip sound) {
        Object data[] = new Object[2];
        data[0] = 3;
        data[1] = (Clip)sound;
        queue.add(data);
    }

    public void startAnimation(Drawable c, Animator animador) {
        gui.notifyAnimationInProgress();
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