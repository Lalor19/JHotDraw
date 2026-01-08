package org.jhotdraw.action.edit;

import java.awt.event.ActionEvent;
import javax.swing.Action;
import org.jhotdraw.action.AbstractViewAction;
import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.View;

public abstract class AbstractUndoRedoAction extends AbstractViewAction {

    protected AbstractUndoRedoAction(Application app, View view) {
        super(app, view);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Action realAction = getRealAction();
        if (realAction != null && realAction != this) {
            realAction.actionPerformed(e);
        }
    }

    protected abstract String getActionID();

    protected Action getRealAction() {
        return (getActiveView() == null)
                ? null
                : getActiveView().getActionMap().get(getActionID());
    }

    protected void updateEnabledState() {
        Action realAction = getRealAction();
        setEnabled(realAction != null && realAction.isEnabled());
    }
}