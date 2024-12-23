package ChessCore;

import ChessCore.Pieces.*;

public final class ChessBoard {
    private final Piece[][] board;

    public ChessBoard(Piece[][] board) {
        this.board = board;
    }

    // This is a copy constructor, used for cloning the ChessBoard.
    public ChessBoard(ChessBoard board) {
        this.board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                this.board[i][j] = board.board[i][j];
            }
        }
    }

    public Piece getPieceAtSquare(Square square) {
        return board[square.getRank().ordinal()][square.getFile().ordinal()];
    }

    public void setPieceAtSquare(Square square, Piece piece) {
        board[square.getRank().ordinal()][square.getFile().ordinal()] = piece;
    }

    public boolean isTherePieceInBetween(Move move) {
        Square from = move.getFromSquare();
        Square to = move.getToSquare();
        if (move.isVerticalMove()) {
            BoardFile file = from.getFile();
            boolean moveForward = move.getDeltaY() > 0;
            var currentRank = from.getRank();
            while (true) {
                currentRank = moveForward ? currentRank.getNext() : currentRank.getPrevious();

                if (currentRank == to.getRank()) {
                    break;
                }

                if (getPieceAtSquare(new Square(file, currentRank)) != null) {
                    return true;
                }
            }
        } else if (move.isHorizontalMove()) {
            BoardRank rank = from.getRank();
            boolean moveForward = move.getDeltaX() > 0;
            var currentFile = from.getFile();
            while (true) {
                currentFile = moveForward ? currentFile.getNext() : currentFile.getPrevious();

                if (currentFile == to.getFile()) {
                    break;
                }

                if (getPieceAtSquare(new Square(currentFile, rank)) != null) {
                    return true;
                }
            }
        } else if (move.isDiagonalMove()) {
            BoardRank currentRank = from.getRank();
            BoardFile currentFile = from.getFile();

            boolean moveForwardX = move.getDeltaX() > 0;
            boolean moveForwardY = move.getDeltaY() > 0;
            while (true) {
                currentRank = moveForwardY ? currentRank.getNext() : currentRank.getPrevious();
                currentFile = moveForwardX ? currentFile.getNext() : currentFile.getPrevious();

                if (currentRank == to.getRank() && currentFile == to.getFile()) {
                    break;
                }

                if (getPieceAtSquare(new Square(currentFile, currentRank)) != null) {
                    return true;
                }
            }
        }

        return false;
    }

    public void printChessBoard() {
        System.out.print("  ");
        for (int i = 0; i < board[0].length; i++) {
            System.out.print((char) ('A' + i) + " ");
        }
        System.out.println();

        for (int i = board.length - 1; i >= 0; i--) {
            System.out.print((i + 1) + " ");
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != null) {
                    if (board[i][j] instanceof Pawn)
                        System.out.print("P");
                    else if (board[i][j] instanceof King)
                        System.out.print("K");
                    else if (board[i][j] instanceof Knight)
                        System.out.print("N");
                    else if (board[i][j] instanceof Rook)
                        System.out.print("R");
                    else if (board[i][j] instanceof Bishop)
                        System.out.print("B");
                    else if (board[i][j] instanceof Queen)
                        System.out.print("Q");

                    System.out.print(" ");
                } else {
                    System.out.print("  ");
                }
            }
            System.out.println();
        }
        System.out.println("---------------------------------------");
    }
}
