package VisualPlayers;

import java.util.Map;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * IF INSTANCES WILL BE DELETED,
 * MUST CALL CLEANUP METHOD TO UNSUBSCRIBE FROM SUBJECT.
 */
public class ImageStorage implements Observer {

    private static Map<String, Icon> images = new HashMap<String, Icon>();

    public ImageStorage() { Resources.registerObserver(this); }
    
    public void add(String imagePath, int sizeIcon) {
        if (!images.containsKey(imagePath)) {
            final Image scaledImage = new ImageIcon(Resources.getImagesFolderPath() + imagePath).getImage().getScaledInstance(sizeIcon, sizeIcon, Image.SCALE_SMOOTH);
            images.put(imagePath, new ImageIcon(scaledImage));
        }
    }

    public Icon get(String imagePath) { return images.get(imagePath); }

    // MUST BE CALLED BEFORE DELETING INSTANCE.
    public void cleanup() { Resources.removeObserver(this); }

    @Override
    public void update(int newTheme) { images.clear(); }
    
}
