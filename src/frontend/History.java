package frontend;

import java.util.Stack;

public class History {
    private final ChessBoardGUI game;
    private final Stack<Checkpoint> undoStack;
    private final Stack<Checkpoint> redoStack;

    public History(ChessBoardGUI game) {
        this.game = game;
        undoStack = new Stack<Checkpoint>();
        redoStack = new Stack<Checkpoint>();
    }

    public void saveCheckpoint(Checkpoint checkpoint) {
        undoStack.push(checkpoint);

        while (!redoStack.isEmpty()) {
            redoStack.pop();
        }
    }

    public void undo() {
        Checkpoint lastState = undoStack.pop();
        redoStack.push(game.getCurrentState());
        game.setState(lastState);
    }

    public void redo() {
        Checkpoint state = redoStack.pop();
        undoStack.push(game.getCurrentState());
        game.setState(state);
    }

    public boolean isUndoEmpty() {
        return undoStack.isEmpty();
    }

    public boolean isRedoEmpty() {
        return redoStack.isEmpty();
    }

}
