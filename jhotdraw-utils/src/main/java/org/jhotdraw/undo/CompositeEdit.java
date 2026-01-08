package org.jhotdraw.undo;

import javax.swing.undo.*;

public class CompositeEdit extends CompoundEdit {

    private static final long serialVersionUID = 1L;
    private String presentationName;
    private boolean isSignificant;
    private boolean isVerbose;

    public void setVerbose(boolean b) {
        isVerbose = b;
    }

    public CompositeEdit() {
        isSignificant = true;
    }

    public CompositeEdit(boolean isSignificant) {
        this.isSignificant = isSignificant;
    }

    public CompositeEdit(String presentationName) {
        this.presentationName = presentationName;
        isSignificant = true;
    }

    public CompositeEdit(String presentationName, boolean isSignificant) {
        this.presentationName = presentationName;
        this.isSignificant = isSignificant;
    }

    @Override
    public String getPresentationName() {
        return (presentationName != null) ? presentationName : super.getPresentationName();
    }

    @Override
    public String getUndoPresentationName() {
        return ((presentationName != null) ? UndoRedoManager.getLabels().getString("edit.undo.text") + " " + presentationName : super.getUndoPresentationName());
    }

    @Override
    public String getRedoPresentationName() {
        return ((presentationName != null) ? UndoRedoManager.getLabels().getString("edit.redo.text") + " " + presentationName : super.getRedoPresentationName());
    }

    @Override
    public boolean addEdit(UndoableEdit anEdit) {
        if (anEdit == this) {
            end();
            return true;
        } else if (isInProgress() && (anEdit instanceof CompositeEdit)) {
            return true;
        } else {
            return super.addEdit(anEdit);
        }
    }

    @Override
    public boolean isSignificant() {
        return (isSignificant) ? super.isSignificant() : false;
    }

    public void setSignificant(boolean newValue) {
        isSignificant = newValue;
    }
}