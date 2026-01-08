package org.jhotdraw.undo;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;

public class UndoManagerUndoAction extends AbstractAction {

    private final UndoRedoManager manager;

    public UndoManagerUndoAction(UndoRedoManager manager) {
        this.manager = manager;
        UndoRedoManager.getLabels().configureAction(this, "edit.undo");
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            manager.undo();
        } catch (CannotUndoException ex) {
            System.err.println("Cannot undo: " + ex);
        }
    }
}
