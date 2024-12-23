package frontend;

import javax.swing.ImageIcon;
import java.awt.Image;

public class IconFactory {
    public static ImageIcon createIcon(String path, int width, int height) {
        ImageIcon icon;

        icon = new ImageIcon(path);
        Image iconScaled = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        icon = new ImageIcon(iconScaled);

        return icon;
    }
}
