package org.jhotdraw.undo;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.undo.CannotRedoException;

public class UndoManagerRedoAction extends AbstractAction {

    private final UndoRedoManager manager;

    public UndoManagerRedoAction(UndoRedoManager manager) {
        this.manager = manager;
        UndoRedoManager.getLabels().configureAction(this, "edit.redo");
        setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            manager.redo();
        } catch (CannotRedoException ex) {
            System.err.println("Cannot redo: " + ex);
        }
    }
}
