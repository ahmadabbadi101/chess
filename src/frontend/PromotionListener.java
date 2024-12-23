package frontend;

import ChessCore.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PromotionListener implements ActionListener {
    private final PawnPromotion promotion;
    private final ChessBoardGUI board;
    private final PromotionGUI parent;

    private final Square source;

    private final Square dest;

    public PromotionListener(PromotionGUI parent, ChessBoardGUI board, PawnPromotion promotion, Square source, Square dest) {
        this.parent = parent;
        this.board = board;
        this.promotion = promotion;
        this.source = source;
        this.dest = dest;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        board.setPromoteTo(promotion);
        parent.setVisible(false);

        Player player = board.game.getWhoseTurn();

        int destRow = dest.getRank().getValue();
        int destCol = dest.getFile().getValue();

        if (board.isFlipped) {
            destRow = 7 - destRow;
            destCol = 7 - destCol;
        }

        BoardRank flippedRank = BoardRank.values()[destRow];
        BoardFile flippedFile = BoardFile.values()[destCol];

        Square flippedDest = new Square(flippedFile, flippedRank);

        Tile destTile = board.getTile(flippedDest);
        Button destBtn = (Button) destTile.getChild();

        BtnIcon icon;

        switch (promotion) {
            case Queen:
                if (player == Player.BLACK) {
                    icon = new BtnIcon("BlackQueen");
                    destBtn.setChild(icon);
                } else {
                    icon = new BtnIcon("WhiteQueen");
                    destBtn.setChild(icon);
                }
                break;
            case Knight:
                if (player == Player.BLACK) {
                    icon = new BtnIcon("BlackKnight");
                    destBtn.setChild(icon);
                } else {
                    icon = new BtnIcon("WhiteKnight");
                    destBtn.setChild(icon);
                }
                break;
            case Bishop:
                if (player == Player.BLACK) {
                    icon = new BtnIcon("BlackBishop");
                    destBtn.setChild(icon);
                } else {
                    icon = new BtnIcon("WhiteBishop");
                    destBtn.setChild(icon);
                }
                break;
            case Rook:
                if (player == Player.BLACK) {
                    icon = new BtnIcon("BlackRook");
                    destBtn.setChild(icon);
                } else {
                    icon = new BtnIcon("WhiteRook");
                    destBtn.setChild(icon);
                }
                break;
        }

        Move promotionMove = new Move(source, dest, promotion);
        board.game.makeMove(promotionMove);
    }
}
