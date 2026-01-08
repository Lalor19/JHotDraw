package org.jhotdraw.action.edit;

import static org.testng.Assert.*;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import org.jhotdraw.api.app.View;
import org.testng.annotations.Test;

public class RedoActionTest {

    private static class RecordingAction extends AbstractAction {
        boolean invoked = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            invoked = true;
        }
    }

    @Test
    public void redoAction_delegatesToViewRedoAction() {
        FakeView view = new FakeView();
        RecordingAction realRedo = new RecordingAction();

        view.getActionMap().put(RedoAction.ID, realRedo);

        RedoAction redoAction = new RedoAction(null, view) {
            @Override
            public View getActiveView() {
                return view;
            }
        };

        redoAction.actionPerformed(new ActionEvent(this, 0, "redo"));

        assertTrue(realRedo.invoked);
    }
}