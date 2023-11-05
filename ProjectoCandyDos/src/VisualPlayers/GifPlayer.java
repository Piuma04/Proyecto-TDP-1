package VisualPlayers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import GUI.Drawable;

/**
 * IF INSTANCES WILL BE DELETED,
 * MUST CALL CLEANUP METHOD TO UNSUBSCRIBE FROM SUBJECT.
 */
public class GifPlayer implements Observer {

    private static Map<String, List<Icon>> gifImages = new ConcurrentHashMap<String, List<Icon>>();
    private static Map<String, List<Integer>> gifDelay = new ConcurrentHashMap<String, List<Integer>>();

    public GifPlayer() { Resources.registerObserver(this); }

    public void play(String animationPath, Drawable drawableAnimated) {
        final List<Icon> gifImageFrames = gifImages.get(animationPath);
        final List<Integer> gifDelayFrames = gifDelay.get(animationPath);
        int currentFrame = 0;
        for (Icon frame : gifImageFrames) {
                drawableAnimated.setIcon(frame);
                drawableAnimated.repaint();
                try { Thread.sleep(gifDelayFrames.get(currentFrame++)); } catch (InterruptedException e) {  e.printStackTrace(); }
        }
    }
    
    public void add(String animationPath, int gifSize) {
        if (!gifImages.containsKey(animationPath)) {
            List<BufferedImage> gifFrameImages = new LinkedList<BufferedImage>();
            List<Integer> gifDelayFrames = new LinkedList<Integer>();
            try {
                final File gifFile = new File(Resources.getImagesFolderPath() + animationPath);
                ImageReader reader = ImageIO.getImageReadersBySuffix("gif").next();
                reader.setInput(ImageIO.createImageInputStream(gifFile));
                int i = reader.getMinIndex();
                final int gifAmountFrames = reader.getNumImages(true);

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
            gifImages.put(animationPath, resizeGifImages(gifFrameImages, gifSize));
        }
    }
    /**
     * @author @SantinoDF - ChatGPT3 
     */
    public static List<Icon> resizeGifImages(List<BufferedImage> frames, int targetSize) {
        // Find the largest frame
        BufferedImage largestFrame = frames.get(0);
        for (BufferedImage frame : frames)
            if (frame.getWidth() * frame.getHeight() > largestFrame.getWidth() * largestFrame.getHeight())
                largestFrame = frame;

        // Calculate the scaling factor based on the largest frame to maintain proportions
        final double scaleFactor = (double) targetSize / Math.max(largestFrame.getWidth(), largestFrame.getHeight());

        // Resize all frames proportionally and center them with padding
        List<Icon> resizedFrames = new LinkedList<Icon>();
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

            resizedFrames.add( new ImageIcon(resizedImage) );
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

    // MUST BE CALLED BEFORE DELETING INSTANCE.
    public void cleanup() { Resources.removeObserver(this); }

    @Override
    public void update(int newTheme) {
        gifImages.clear();
        gifDelay.clear();
     }
}
