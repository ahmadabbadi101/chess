package ChessCore;

public final class ClassicChessGame extends ChessGame {

    public ClassicChessGame() {
        super(ClassicBoardInitializer.getInstance());
    }

    public ClassicChessGame(ClassicChessGame other) {
        super(other);
    }
    @Override
    protected boolean isValidMoveCore(Move move) {
        return !Utilities.willOwnKingBeAttacked(this.getWhoseTurn(), move, this.getBoard());
    }
}
