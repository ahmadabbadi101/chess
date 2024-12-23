package frontend;

import ChessCore.PawnPromotion;
import ChessCore.Player;
import ChessCore.Square;

import javax.swing.*;
import java.awt.*;

public class PromotionGUI extends javax.swing.JDialog {
    private final JPanel grid;

    private Square source;
    private Square dest;

    private final ChessBoardGUI parent;
    public PromotionGUI(ChessBoardGUI parent, Player currentPlayer, Square source, Square dest) {
        this.parent = parent;
        this.source = source;
        this.dest = dest;

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(230, 230);
        setTitle("Choose a piece to promote to");

        grid = new JPanel(new GridLayout(2, 2));
        this.add(grid);

        JPanel queenPanel = new JPanel(new GridLayout());
        queenPanel.setOpaque(true);
        grid.add(queenPanel);

        JPanel knightPanel = new JPanel(new GridLayout());
        knightPanel.setOpaque(true);
        grid.add(knightPanel);

        JPanel bishopPanel = new JPanel(new GridLayout());
        bishopPanel.setOpaque(true);
        grid.add(bishopPanel);

        JPanel rookPanel = new JPanel(new GridLayout());
        rookPanel.setOpaque(true);
        grid.add(rookPanel);

        JButton queenBtn = new JButton();
        JButton knightBtn = new JButton();
        JButton bishopBtn = new JButton();
        JButton rookBtn = new JButton();

        if (currentPlayer == Player.WHITE) {
            queenBtn.setIcon(PieceIcons.WhiteQueen.getIcon());
            knightBtn.setIcon(PieceIcons.WhiteKnight.getIcon());
            bishopBtn.setIcon(PieceIcons.WhiteBishop.getIcon());
            rookBtn.setIcon(PieceIcons.WhiteRook.getIcon());
        } else {
            queenBtn.setIcon(PieceIcons.BlackQueen.getIcon());
            knightBtn.setIcon(PieceIcons.BlackKnight.getIcon());
            bishopBtn.setIcon(PieceIcons.BlackBishop.getIcon());
            rookBtn.setIcon(PieceIcons.BlackRook.getIcon());
        }

        queenBtn.setContentAreaFilled(false);
        queenBtn.setOpaque(false);
        queenBtn.setFocusPainted(false);
        queenBtn.setBorderPainted(false);
        queenBtn.setBorderPainted(false);
        queenBtn.addActionListener(new PromotionListener(this, parent, PawnPromotion.Queen, source, dest));
        queenPanel.add(queenBtn);

        knightBtn.setContentAreaFilled(false);
        knightBtn.setOpaque(false);
        knightBtn.setFocusPainted(false);
        knightBtn.setBorderPainted(false);
        knightBtn.setBorderPainted(false);
        knightBtn.addActionListener(new PromotionListener(this, parent, PawnPromotion.Knight, source, dest));
        knightPanel.add(knightBtn);

        bishopBtn.setContentAreaFilled(false);
        bishopBtn.setOpaque(false);
        bishopBtn.setFocusPainted(false);
        bishopBtn.setBorderPainted(false);
        bishopBtn.setBorderPainted(false);
        bishopBtn.addActionListener(new PromotionListener(this, parent, PawnPromotion.Bishop, source, dest));
        bishopPanel.add(bishopBtn);

        rookBtn.setContentAreaFilled(false);
        rookBtn.setOpaque(false);
        rookBtn.setFocusPainted(false);
        rookBtn.setBorderPainted(false);
        rookBtn.setBorderPainted(false);
        rookBtn.addActionListener(new PromotionListener(this, parent, PawnPromotion.Rook, source, dest));
        rookPanel.add(rookBtn);
    }
}
