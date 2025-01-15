package frontend;

import ChessCore.BoardRank;
import ChessCore.BoardFile;
import ChessCore.Square;
import ChessCore.Pieces.Piece;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TilesListener implements ActionListener {
    private ChessBoardGUI board;
    private int row;
    private int col;

    public TilesListener(ChessBoardGUI board, int row, int col) {
        this.board = board;
        this.row = row;
        this.col = col;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        int flippedRow, flippedCol;

        if (board.isFlipped) {
            flippedRow = 7 - row;
            flippedCol = 7 - col;
        } else {
            flippedRow = row;
            flippedCol = col;
        }

        BoardRank rank = BoardRank.values()[flippedRow];
        BoardFile file = BoardFile.values()[flippedCol];

        Square sq = new Square(file, rank);
        Piece piece = board.game.getPieceAtSquare(sq);

        boolean isPossibleMove = board.isPossibleMove(flippedRow, flippedCol);

        if ((piece != null && piece.getOwner() == board.game.getWhoseTurn()) || isPossibleMove) {
            if (isPossibleMove) {
                board.save();
                board.movePiece(row, col);
                board.resetBoard();
                board.flipBoard();
                board.checkForCheck();
                board.endGame();
            } else {
                board.resetBoard();
                board.checkForCheck();
                board.highlightPossibleMoves(row, col);
            }
        } else {
            board.resetBoard();
            board.checkForCheck();
        }

    }
}
