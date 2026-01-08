package org.jhotdraw.action.edit;

import org.jhotdraw.api.app.Application;
import org.jhotdraw.api.app.View;
import org.jhotdraw.util.*;

public class UndoAction extends AbstractUndoRedoAction {

    public static final String ID = "edit.undo";

    public UndoAction(Application app, View view) {
        super(app, view);
        ResourceBundleUtil.getBundle("org.jhotdraw.action.Labels")
                .configureAction(this, ID);
    }

    @Override
    protected String getActionID() {
        return ID;
    }
}