package frontend;

import javax.swing.ImageIcon;
import java.awt.*;

public enum PieceIcons {
    BlackBishop("src/Graphics/BLACKBISHOP.png"),
    BlackKing("src/Graphics/BLACKKING.png"),
    BlackKnight("src/Graphics/BLACKKNIGHT.png"),
    BlackPawn("src/Graphics/BLACKPAWN.png"),
    BlackQueen("src/Graphics/BLACKQUEEN.png"),
    BlackRook("src/Graphics/BLACKROOK.png"),
    WhiteBishop("src/Graphics/WHITEBISHOP.png"),
    WhiteKing("src/Graphics/WHITEKING.png"),
    WhiteKnight("src/Graphics/WHITEKNIGHT.png"),
    WhitePawn("src/Graphics/WHITEPAWN.png"),
    WhiteQueen("src/Graphics/WHITEQUEEN.png"),
    WhiteRook("src/Graphics/WHITEROOK.png");

    private ImageIcon icon;

    PieceIcons(String iconPath) {

        icon = new ImageIcon(iconPath);
        Image iconScaled = icon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        icon = new ImageIcon(iconScaled);
    }

    public ImageIcon getIcon() {
        return icon;
    }
}
