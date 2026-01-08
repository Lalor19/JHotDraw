package org.jhotdraw.action.edit;

import static org.testng.Assert.*;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import org.jhotdraw.api.app.View;
import org.testng.annotations.Test;

public class UndoActionTest {

    private static class RecordingAction extends AbstractAction {
        boolean invoked = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            invoked = true;
        }
    }

    @Test
    public void undoAction_delegatesToViewUndoAction() {
        FakeView view = new FakeView();
        RecordingAction realUndo = new RecordingAction();

        view.getActionMap().put(UndoAction.ID, realUndo);

        UndoAction undoAction = new UndoAction(null, view) {
            @Override
            public View getActiveView() {
                return view;
            }
        };

        undoAction.actionPerformed(new ActionEvent(this, 0, "undo"));

        assertTrue(realUndo.invoked);
    }
}