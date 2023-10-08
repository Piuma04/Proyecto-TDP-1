package Animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

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

    private static Map<String, List<Image>> gifImages = new HashMap<String, List<Image>>();
    private static Map<String, List<Integer>> gifDelay = new HashMap<String, List<Integer>>();
    protected AnimatorDriver manager;
    protected Drawable drawableAnimated;
    protected String path_img;
    
    /**
     * Inicializa el estado interno del animador, considerando:
     * @param m El manejador de animaciones al que le notificará el fin de la animación, cuando corresponda.
     * @param c La celda animada.
     */
    public AnimatorStateChange(AnimatorDriver m, Drawable c, String animationPath) {
        manager = m;
        drawableAnimated = c;
        path_img = animationPath;
        
        if (animationPath != null && path_img.endsWith(".gif")) {
            if (!gifImages.containsKey(animationPath)) {
                List<BufferedImage> gifFrameImages = new LinkedList<BufferedImage>();
                List<Integer> gifDelayFrames = new LinkedList<Integer>();
                try {
                    File gifFile = new File("src/imagenes/" + animationPath);
                    ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
                    reader.setInput(ImageIO.createImageInputStream(gifFile));
                    int i = reader.getMinIndex();
                    int gifAmountFrames = reader.getNumImages(true);

                    while (i < gifAmountFrames)
                    {
                        // GET GIF FRAME DELAY.
                        IIOMetadata imageMetaData =  reader.getImageMetadata(i);
                        String metaFormatName = imageMetaData.getNativeMetadataFormatName();
                        IIOMetadataNode root = (IIOMetadataNode)imageMetaData.getAsTree(metaFormatName);
                        IIOMetadataNode graphicsControlExtensionNode = getNode(root, "GraphicControlExtension");
                        int frameDelay = 10 * Integer.valueOf(graphicsControlExtensionNode.getAttribute("delayTime"));

                        gifDelayFrames.add(frameDelay);
                        gifFrameImages.add(reader.read(i++));
                    }
                    reader.dispose();
                } catch (Exception e) { System.out.println("ERROR: Gif (" + animationPath + ") may not exist!"); }
                gifDelay.put(animationPath, gifDelayFrames);
                gifImages.put(animationPath, resizeGifImages(gifFrameImages, drawableAnimated.getImageSize()));
            }
        }
    }
    
    
    @Override
    public Drawable getDrawable() { return drawableAnimated; }

    @Override
    public void run() {
        drawableAnimated.setImage("src/imagenes/" + path_img);
        if (path_img != null && path_img.endsWith(".gif")) {
            List<Image> gifImageFrames = gifImages.get(path_img);
            List<Integer> gifDelayFrames = gifDelay.get(path_img);
            int currentFrame = 0;
            for (Image frame : gifImageFrames) {
                    drawableAnimated.setImage(frame);
                    drawableAnimated.repaint();
                    try { sleep(gifDelayFrames.get(currentFrame++)); } catch (InterruptedException e) {  e.printStackTrace(); }
                    //try { sleep(100); } catch (InterruptedException e) {  e.printStackTrace(); }
                }
        }
        drawableAnimated.repaint();
        manager.notifyEndAnimation(this, path_img == null);
    }

    @Override
    public void startAnimation() {
        this.start();
    }
    
    /**
     * @author @SantinoDF - ChatGPT3 
     */
    public static List<Image> resizeGifImages(List<BufferedImage> frames, int targetSize) {
        // Find the largest frame
        BufferedImage largestFrame = frames.get(0);
        for (BufferedImage frame : frames)
            if (frame.getWidth() * frame.getHeight() > largestFrame.getWidth() * largestFrame.getHeight())
                largestFrame = frame;

        // Calculate the scaling factor based on the largest frame to maintain proportions
        double scaleFactor = (double) targetSize / Math.max(largestFrame.getWidth(), largestFrame.getHeight());

        // Resize all frames proportionally and center them with padding
        List<Image> resizedFrames = new LinkedList<Image>();
        for (BufferedImage current : frames) {
            int width = (int) (current.getWidth() * scaleFactor);
            int height = (int) (current.getHeight() * scaleFactor);

            BufferedImage resizedImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = resizedImage.createGraphics();

            // Calculate padding to center the image
            int xOffset = (targetSize - width) / 2;
            int yOffset = (targetSize - height) / 2;

            g2d.drawImage(current, xOffset, yOffset, width, height, null);
            g2d.dispose();

            resizedFrames.add(resizedImage);
            
        }

        return resizedFrames;
    }
    
    // GETS METADATA TO KNOW GIF FRAME DELAY.
    private static IIOMetadataNode getNode(IIOMetadataNode rootNode, String nodeName) {
        int nNodes = rootNode.getLength();
        for (int i = 0; i < nNodes; i++)
            if (rootNode.item(i).getNodeName().compareToIgnoreCase(nodeName)== 0)
                return((IIOMetadataNode) rootNode.item(i));
        IIOMetadataNode node = new IIOMetadataNode(nodeName);
        rootNode.appendChild(node);
        return(node);
  }
}