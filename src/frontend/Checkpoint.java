package frontend;

import ChessCore.ClassicChessGame;

import javax.swing.*;

public class Checkpoint {
    private final ClassicChessGame game;
    private final boolean isFlipped;
    private final BtnIcon[][] icons;

    public Checkpoint(ClassicChessGame game, boolean isFlipped, Tile[][] tiles) {
        this.game = new ClassicChessGame(game);
        this.isFlipped = isFlipped;

        this.icons = new BtnIcon[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.icons[i][j] = tiles[i][j].getChild().getChild();
            }
        }
    }

    public boolean getIsFlipped() {
        return isFlipped;
    }

    public ClassicChessGame getGame() {
        return game;
    }
    public BtnIcon[][] getIcons() {
        return icons;
    }
}
