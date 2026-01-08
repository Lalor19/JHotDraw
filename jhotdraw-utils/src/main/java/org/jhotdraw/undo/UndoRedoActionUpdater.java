package org.jhotdraw.undo;

import javax.swing.Action;

public class UndoRedoActionUpdater {

    private final UndoRedoManager manager;
    private final Action undoAction;
    private final Action redoAction;

    public UndoRedoActionUpdater(
            UndoRedoManager manager,
            Action undoAction,
            Action redoAction) {
        this.manager = manager;
        this.undoAction = undoAction;
        this.redoAction = redoAction;
    }

    public void update() {
        String label;

        if (manager.canUndo()) {
            undoAction.setEnabled(true);
            label = manager.getUndoPresentationName();
        } else {
            undoAction.setEnabled(false);
            label = UndoRedoManager.getLabels().getString("edit.undo.text");
        }
        undoAction.putValue(Action.NAME, label);
        undoAction.putValue(Action.SHORT_DESCRIPTION, label);

        if (manager.canRedo()) {
            redoAction.setEnabled(true);
            label = manager.getRedoPresentationName();
        } else {
            redoAction.setEnabled(false);
            label = UndoRedoManager.getLabels().getString("edit.redo.text");
        }
        redoAction.putValue(Action.NAME, label);
        redoAction.putValue(Action.SHORT_DESCRIPTION, label);
    }
}