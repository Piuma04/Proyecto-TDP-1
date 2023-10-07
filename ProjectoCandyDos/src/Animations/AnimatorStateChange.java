package Animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.swing.Timer;

import GUI.Drawable;
/**
 * Modela el comportamiento de un animador que permite visualizar el cambio de estado de una entidad.
 * Cuando el animador comienza su animación, modifica la imagen asociada a la celda animada.
 * La imagen que se considerará para efectivizar el cambio de estado, será la que se encontraba asociada a la celda lógica al momento
 * de crear el animador.
 * Una vez finalizada la animación, el animador notificará a su manager de esta situación.
 * @author FJoaquin (federico.joaquin@cs.uns.edu.ar)
 *
 */
public class AnimatorStateChange extends Thread implements Animator {

    private static Map<String, Image[]> gifImages = new HashMap<String, Image[]>();
    private static final int gifDelayMillis = 80;

    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    protected String path_img;
    protected int gifFrames;
    private int currentFrame;
    protected Timer timer;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver m, Drawable c, String animationPath, int gifFrameCount) {
        manager = m;
        drawableAnimated = c;
        path_img = animationPath;
        gifFrames = gifFrameCount;
        currentFrame = 0;
        
        //int gifFrameAmount = path.endsWith(".gif") ? 12 : 0;
        if (gifFrames > 0) {
            if (!gifImages.containsKey(animationPath)) {
                BufferedImage gifFrameImages[] = new BufferedImage[gifFrames];
                try {
                    File gifFile = new File("src/imagenes/" + animationPath);
                    ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
                    reader.setInput(ImageIO.createImageInputStream(gifFile));
                    for (int i = 0; i < gifFrames; i++)
                        gifFrameImages[i] = reader.read(i);
                    reader.dispose();
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                gifImages.put(animationPath, resizeGifImages(gifFrameImages, drawableAnimated.getImageSize()));
            }
        }
    }
    
    
    @Override
    public Drawable getDrawable() { return drawableAnimated; }

    @Override
    public void run() {
        drawableAnimated.setImage("src/imagenes/" + path_img);
        if (gifFrames > 0) {
            /*long startTime =  System.currentTimeMillis();
            long elapsedTime =  startTime;
            final double timer = 1.2; // seconds
            while (elapsedTime < startTime + timer * 1000) {
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                elapsedTime = System.currentTimeMillis();
                drawableAnimated.repaint();
            }*/
            while (currentFrame < gifFrames) {
                    drawableAnimated.setImage(gifImages.get(path_img)[currentFrame]);
                    drawableAnimated.repaint();
                    currentFrame++;
                    try { sleep(gifDelayMillis); } catch (InterruptedException e) {  e.printStackTrace(); }
                }
            /*for (Image im : gifImages.get(path_img)) {
                drawableAnimated.setImage(im);
                drawableAnimated.repaint();
            }
            try { Thread.sleep(gifDelayMillis * gifFrames); } catch (InterruptedException e) { }
            */
            /*try {
                SwingUtilities.invokeAndWait(() -> { drawableAnimated.startFrameCount(); });
            } catch (InvocationTargetException | InterruptedException e) {
                e.printStackTrace();
            }
            
            while ( drawableAnimated.getFrameCount() <= gifFrames ) {
                try { Thread.sleep(gifDelayMillis); } catch (InterruptedException e) { }
                //drawableAnimated.repaint();
            }*/

            // SwingUtilities.invokeLater(() -> { gui.removeEntity(a.getDrawable()); });
        }
        drawableAnimated.repaint();
        manager.notifyEndAnimation(this, path_img == null);
    }

    @Override
    public void startAnimation() {
        this.start();
    }
    
    /**
     * function NOT checked.
     * @author ChatGPT3
     */
    public static Image[] resizeGifImages(BufferedImage[] frames, int targetSize) {
        // Find the largest frame
        BufferedImage largestFrame = frames[0];
        for (BufferedImage frame : frames) {
            if (frame.getWidth() * frame.getHeight() > largestFrame.getWidth() * largestFrame.getHeight()) {
                largestFrame = frame;
            }
        }

        // Calculate the scaling factor based on the largest frame to maintain proportions
        double scaleFactor = (double) targetSize / Math.max(largestFrame.getWidth(), largestFrame.getHeight());

        // Resize all frames proportionally and center them with padding
        Image[] resizedFrames = new Image[frames.length];
        for (int i = 0; i < frames.length; i++) {
            int width = (int) (frames[i].getWidth() * scaleFactor);
            int height = (int) (frames[i].getHeight() * scaleFactor);

            BufferedImage resizedImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();

            // Calculate padding to center the image
            int xOffset = (targetSize - width) / 2;
            int yOffset = (targetSize - height) / 2;

            g2d.drawImage(frames[i], xOffset, yOffset, width, height, null);
            g2d.dispose();

            resizedFrames[i] = resizedImage;
        }

        return resizedFrames;
    }
}