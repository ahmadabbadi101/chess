package frontend;

import ChessCore.*;
import ChessCore.Pieces.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ChessBoardGUI extends javax.swing.JFrame {
    ClassicChessGame game;
    private Tile[][] tiles;

    private static final int BOARD_WIDTH = 520;
    private static final int BOARD_HEIGHT = 520;
    private static final int TOOLBAR_HEIGHT = 75;

    private ArrayList<Square> possibleMoves;
    private Square source;
    private PawnPromotion promoteTo;
    boolean isFlipped;

    private final History history;

    public ChessBoardGUI() {
        isFlipped = false;

        promoteTo = PawnPromotion.None;

        history = new History(this);

        game = new ClassicChessGame();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setSize(BOARD_WIDTH, BOARD_HEIGHT + TOOLBAR_HEIGHT);
        setTitle("Chess Game");

        JToolBar toolbar = new JToolBar();
        toolbar.setFloatable(false);

        Container pane = this.getContentPane();
        pane.add(toolbar, BorderLayout.NORTH);

        JButton undoBtn = new JButton("Undo");
        ImageIcon undoIcon = IconFactory.createIcon("src/Graphics/UNDO.png", 15, 15);
        undoBtn.setIcon(undoIcon);
        toolbar.add(undoBtn);

        undoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!history.isUndoEmpty()) {
                    history.undo();
                }
            }
        });

        JButton redoBtn = new JButton("Redo");
        ImageIcon redoIcon = IconFactory.createIcon("src/Graphics/REDO.png", 15, 15);
        redoBtn.setIcon(redoIcon);
        toolbar.add(redoBtn);

        redoBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!history.isRedoEmpty()) {
                    history.redo();
                }
            }
        });

        JPanel board = new JPanel(new GridLayout(8, 8));
        this.add(board);

        AbstractAction undoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!history.isUndoEmpty())
                    history.undo();
            }
        };

        KeyStroke undoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(undoStroke, "ctrlZ");
        board.getActionMap().put("ctrlZ", undoAction);
        AbstractAction redoAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!history.isRedoEmpty())
                    history.redo();
            }
        };

        KeyStroke redoStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
        board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(redoStroke, "ctrlY");
        board.getActionMap().put("ctrlY", redoAction);


        tiles = new Tile[8][8];

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                tiles[7 - i][j] = new Tile(this, i, j);

                JPanel tile = tiles[7 - i][j].getSelf();

                Button btn = new Button(this, i, j);

                tiles[7 - i][j].setChild(btn);

                board.add(tile);
            }
        }
    }

    void highlightPossibleMoves(int row, int col) {
        int flippedRow;
        int flippedCol;

        if (isFlipped) {
            flippedRow = 7 - row;
            flippedCol = 7 - col;
        } else {
            flippedRow = row;
            flippedCol = col;
        }

        Button srcBtn = tiles[row][col].getChild();
        srcBtn.setOpaque(true);
        srcBtn.setContentAreaFilled(true);

        BoardRank rank = BoardRank.values()[flippedRow];
        BoardFile file = BoardFile.values()[flippedCol];

        source = new Square(file, rank);
        possibleMoves = game.getAllValidMovesFromSquare(source);

        for (Square move : possibleMoves) {
            int i = move.getRank().getValue();
            int j = move.getFile().getValue();

            if (isFlipped) {
                i = 7 - i;
                j = 7 - j;
            }

            Button possibleBtn = tiles[i][j].getChild();
            possibleBtn.setOpaque(true);
            possibleBtn.setContentAreaFilled(true);
        }
    }

    void movePiece(int flippedDestRow, int flippedDestCol) {
        if (source != null) {
            int srcRow = source.getRank().getValue();
            int srcCol = source.getFile().getValue();

            int flippedSrcRow, flippedSrcCol, destRow, destCol;

            if (isFlipped) {
                flippedSrcRow = 7 - srcRow;
                flippedSrcCol = 7 - srcCol;

                destRow = 7 - flippedDestRow;
                destCol = 7 - flippedDestCol;
            } else {
                flippedSrcRow = srcRow;
                flippedSrcCol = srcCol;

                destRow = flippedDestRow;
                destCol = flippedDestCol;
            }


            BoardRank rank = BoardRank.values()[destRow];
            BoardFile file = BoardFile.values()[destCol];
            Square dest = new Square(file, rank);

            Button srcPiece = tiles[flippedSrcRow][flippedSrcCol].getChild();
            Button destPiece =  tiles[flippedDestRow][flippedDestCol].getChild();

            BtnIcon srcIcon = srcPiece.getChild();

            // Promotion
            if (game.getPieceAtSquare(source) instanceof Pawn && (destRow == 7 || destRow == 0)) {
                PromotionGUI promotionWindow = new PromotionGUI(this, game.getWhoseTurn(), source, dest);
                promotionWindow.setVisible(true);
            } else {
                // Castling
                if (game.getPieceAtSquare(source) instanceof King && Math.abs(srcCol - destCol) == 2) {
                    int rookSrcCol = (flippedSrcCol - flippedDestCol) > 0 ? 0 : 7;
                    Button rook = tiles[flippedSrcRow][rookSrcCol].getChild();

                    int rookDestCol;

                    if (game.getWhoseTurn() == Player.WHITE) {
                        if (rookSrcCol == 0) {
                            rookDestCol = 3;
                        } else {
                            rookDestCol = 5;
                        }
                    } else {
                        if (rookSrcCol == 0) {
                            rookDestCol = 2;
                        } else {
                            rookDestCol = 4;
                        }
                    }

                    Button rookDest = tiles[flippedSrcRow][rookDestCol].getChild();

                    rookDest.setChild(rook.getChild());
                    rook.setChild(null);
                }

                // EnPassant
                if (game.getPieceAtSquare(source) instanceof Pawn && Math.abs(srcCol - destCol) == 1 && game.getPieceAtSquare(dest) == null) {
                    Button enPassantPiece = tiles[flippedSrcRow][flippedDestCol].getChild();
                    enPassantPiece.setChild(null);
                }

                destPiece.setChild(srcIcon);

                Move move = new Move(source, dest);
                game.makeMove(move);
            }
            srcPiece.setChild(null);
        }
    }

    void checkForCheck() {
        if (Utilities.isInCheck(game.getWhoseTurn(), game.getBoard())) {
            Square king = Utilities.getKingSquare(game.getWhoseTurn(), game.getBoard());

            int kingRow = king.getRank().getValue();
            int kingCol = king.getFile().getValue();

            if (isFlipped) {
                kingRow = 7 - kingRow;
                kingCol = 7 - kingCol;
            }

            Button kingBtn = tiles[kingRow][kingCol].getChild();
            kingBtn.setBackground(Color.red);
            kingBtn.setOpaque(true);
            kingBtn.setContentAreaFilled(true);
        }
    }

    boolean isPossibleMove(int row, int col) {
        if (possibleMoves == null)
            return false;

        BoardRank rank = BoardRank.values()[row];
        BoardFile file = BoardFile.values()[col];

        var sq = new Square(file, rank);
        for (Square move : possibleMoves) {
            if (sq.getFile() == move.getFile() && sq.getRank() == move.getRank()) {
                return true;
            }
        }

        return false;
    }

    void resetBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button btn = tiles[i][j].getChild();

                btn.setOpaque(false);
                btn.setContentAreaFilled(false);
                btn.setBackground(Button.GREEN);
            }
        }

        if (possibleMoves != null)
            possibleMoves.clear();
        source = null;
    }

    void flipBoard() {
        for (int i = 0; i < 8 / 2; i++) {
            for (int j = 0; j < 8 / 2; j++) {
                Button btn1 = tiles[i][j].getChild();
                Button btn2 = tiles[i][7 - j].getChild();

                Button btn3 = tiles[7 - i][j].getChild();
                Button btn4 = tiles[7 - i][7 - j].getChild();

                BtnIcon icon1 = btn1.getChild();
                BtnIcon icon2 = btn2.getChild();
                BtnIcon icon3 = btn3.getChild();
                BtnIcon icon4 = btn4.getChild();

                btn1.setChild(icon4);
                btn2.setChild(icon3);
                btn3.setChild(icon2);
                btn4.setChild(icon1);
            }
        }

        isFlipped = !isFlipped;
    }

    public void setPromoteTo(PawnPromotion promoteTo) {
        this.promoteTo = promoteTo;
    }

    public Tile getTile(Square sq) {
        int row = sq.getRank().getValue();
        int col = sq.getFile().getValue();

        return tiles[row][col];
    }

    public void save() {
        Checkpoint checkpoint = getCurrentState();
        history.saveCheckpoint(checkpoint);
    }

    public Checkpoint getCurrentState() {
        return new Checkpoint(game, isFlipped, tiles);
    }

    public void setState (Checkpoint checkpoint) {
        this.isFlipped = checkpoint.getIsFlipped();
        this.game = checkpoint.getGame();

        BtnIcon[][] checkpointIcons = checkpoint.getIcons();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Button btn = this.tiles[i][j].getChild();

                btn.setChild(checkpointIcons[i][j]);
            }
        }

    }
    private void showGameOverPopup(String message) {
            JOptionPane.showMessageDialog(null, message, "Game Over", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }

        public void endGame() {
            if(game.isGameEnded())
            {
                switch (game.getGameStatus()) {
                    case WHITE_WON ->
                            showGameOverPopup("Checkmate! White wins!");
                    case BLACK_WON ->
                        showGameOverPopup("Checkmate! Black wins!");
                    case STALEMATE ->
                            showGameOverPopup("Stalemate! The game is a draw.");
                    case INSUFFICIENT_MATERIAL ->
                            showGameOverPopup("Insufficient material. The game is a draw.");
                }
            }
        }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChessBoardGUI().setVisible(true);
            }
        });
    }
}
