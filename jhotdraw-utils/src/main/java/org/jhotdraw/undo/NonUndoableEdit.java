package org.jhotdraw.undo;

import javax.swing.undo.*;

public class NonUndoableEdit extends AbstractUndoableEdit {

    private static final long serialVersionUID = 1L;

    public NonUndoableEdit() {
    }

    @Override
    public boolean canUndo() {
        return false;
    }

    @Override
    public boolean canRedo() {
        return false;
    }
}
