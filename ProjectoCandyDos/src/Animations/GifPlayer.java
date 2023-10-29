package Animations;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataNode;

import GUI.Drawable;

public class GifPlayer {
    private static Map<String, List<Image>> gifImages = new HashMap<String, List<Image>>();
    private static Map<String, List<Integer>> gifDelay = new HashMap<String, List<Integer>>();

    private String path;
    GifPlayer(String basePath) { path = basePath; }

    public void play(String animationPath, Drawable drawableAnimated) {
        List<Image> gifImageFrames = gifImages.get(animationPath);
        List<Integer> gifDelayFrames = gifDelay.get(animationPath);
        int currentFrame = 0;
        for (Image frame : gifImageFrames) {
                drawableAnimated.setImage(frame);
                drawableAnimated.repaint();
                try { Thread.sleep(gifDelayFrames.get(currentFrame++)); } catch (InterruptedException e) {  e.printStackTrace(); }
        }
    }
    
    public void add(String animationPath, int gifSize) {
        if (!gifImages.containsKey(animationPath)) {
            List<BufferedImage> gifFrameImages = new LinkedList<BufferedImage>();
            List<Integer> gifDelayFrames = new LinkedList<Integer>();
            try {
                File gifFile = new File(path + animationPath);
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
            gifImages.put(animationPath, resizeGifImages(gifFrameImages, gifSize));
        }
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
