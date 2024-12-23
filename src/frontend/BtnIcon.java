package frontend;

import javax.swing.ImageIcon;
import java.awt.*;

public class BtnIcon implements Leaf<ImageIcon> {
    private final ImageIcon self;

    public BtnIcon(String iconName) {
        iconName = iconName.toUpperCase();

        String iconPath = "src/Graphics/" + iconName + ".png";

        this.self = IconFactory.createIcon(iconPath, 60, 60);
    }

    public ImageIcon getSelf() {
        return self;
    }
}
