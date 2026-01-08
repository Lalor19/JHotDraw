package org.jhotdraw.action.edit;

import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URI;
import javax.swing.*;
import org.jhotdraw.api.app.*;
import org.jhotdraw.api.gui.URIChooser;

public class FakeView implements View {
    private final ActionMap map = new ActionMap();

    @Override public ActionMap getActionMap() { return map; }
    @Override public Application getApplication() { return null; }
    @Override public void setApplication(Application v) {}
    @Override public JComponent getComponent() { return null; }
    @Override public boolean isEnabled() { return false; }
    @Override public void setEnabled(boolean v) {}
    @Override public void clear() {}
    @Override public boolean isEmpty() { return false; }
    @Override public boolean hasUnsavedChanges() { return false; }
    @Override public void markChangesAsSaved() {}
    @Override public void execute(Runnable r) {}
    @Override public void init() {}
    @Override public void start() {}
    @Override public void activate() {}
    @Override public void deactivate() {}
    @Override public void stop() {}
    @Override public void dispose() {}
    @Override public void setActionMap(ActionMap m) {}
    @Override public void addPropertyChangeListener(PropertyChangeListener l) {}
    @Override public void removePropertyChangeListener(PropertyChangeListener l) {}
    @Override public void setMultipleOpenId(int v) {}
    @Override public int getMultipleOpenId() { return 0; }
    @Override public boolean isShowing() { return false; }
    @Override public void setShowing(boolean v) {}
    @Override public void setTitle(String v) {}
    @Override public String getTitle() { return ""; }
    @Override public void addDisposable(Disposable d) {}
    @Override public void removeDisposable(Disposable d) {}
    @Override public URI getURI() { return null; }
    @Override public void setURI(URI v) {}
    @Override public boolean canSaveTo(URI uri) { return false; }
    @Override public void write(URI uri, URIChooser c) throws IOException {}
    @Override public void read(URI uri, URIChooser c) throws IOException {}
}