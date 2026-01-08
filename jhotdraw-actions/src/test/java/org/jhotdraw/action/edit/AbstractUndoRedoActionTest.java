package org.jhotdraw.action.edit;

import static org.testng.Assert.*;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;

import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.View;
import org.testng.annotations.Test;

public class AbstractUndoRedoActionTest {

    private static class RecordingAction extends AbstractAction {
        boolean invoked = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            invoked = true;
        }
    }

    private static class TestUndoRedoAction extends AbstractUndoRedoAction {
        private final View view;

        TestUndoRedoAction(Application app, View view) {
            super(app, view);
            this.view = view;
        }

        @Override
        protected String getActionID() {
            return "test.action";
        }

        @Override
        public View getActiveView() {
            return view;
        }
    }

    @Test
    public void actionPerformed_delegatesToRealAction() {
        Application app = null;
        FakeView view = new FakeView();
        RecordingAction realAction = new RecordingAction();

        view.getActionMap().put("test.action", realAction);

        AbstractUndoRedoAction action =
                new TestUndoRedoAction(app, view);

        action.actionPerformed(new ActionEvent(this, 0, "cmd"));

        assertTrue(realAction.invoked);
    }

    @Test
    public void actionPerformed_doesNothingWhenNoView() {
        AbstractUndoRedoAction action =
                new TestUndoRedoAction(null, null);

        action.actionPerformed(new ActionEvent(this, 0, "cmd"));
    }

    @Test
    public void updateEnabledState_reflectsRealActionEnabled() {
        FakeView view = new FakeView();

        Action enabledAction = new AbstractAction() {
            @Override public void actionPerformed(ActionEvent e) {}
        };
        enabledAction.setEnabled(true);

        view.getActionMap().put("test.action", enabledAction);

        TestUndoRedoAction action =
                new TestUndoRedoAction(null, view);

        action.updateEnabledState();

        assertTrue(action.isEnabled());
    }
}