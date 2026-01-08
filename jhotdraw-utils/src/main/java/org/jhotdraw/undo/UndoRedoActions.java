package org.jhotdraw.undo;

import javax.swing.Action;

public class UndoRedoActions {

    private final Action undoAction;
    private final Action redoAction;

    public UndoRedoActions(Action undoAction, Action redoAction) {
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    public Action getUndoAction() {
        return undoAction;
    }

    public Action getRedoAction() {
        return redoAction;
    }
}