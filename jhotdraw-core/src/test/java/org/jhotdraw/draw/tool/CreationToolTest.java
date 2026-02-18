package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.junit.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.*;
import javax.swing.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class CreationToolTest {

    static class TestableCreationTool extends CreationTool {
        boolean updateCalled = false;
        boolean repaintCalled = false;
        boolean toolDoneFired = false;
        boolean creationFinishedCalled = false;

        DrawingView view;
        Drawing drawing;

        TestableCreationTool(Figure prototype, DrawingView view, Drawing drawing) {
            super(prototype);
            this.view = view;
            this.drawing = drawing;
        }

        @Override protected void updateCreatedFigureDuringDrag(MouseEvent evt) {
            updateCalled = true;
            super.updateCreatedFigureDuringDrag(evt);
        }

        @Override protected void repaintView() {
            repaintCalled = true;
            super.repaintView();
        }

        @Override protected DrawingView getView() {
            return view;
        }

        @Override public Drawing getDrawing() {
            return drawing;
        }

        @Override protected void fireToolDone() {
            toolDoneFired = true;
        }

        @Override protected void creationFinished(Figure createdFigure) {
            creationFinishedCalled = true;
        }
    }

    private MouseEvent evt(int x, int y) {
        return new MouseEvent(new JButton(), MouseEvent.MOUSE_MOVED, System.currentTimeMillis(),
                0, x, y, 1, false);
    }

    @Test
    public void mouseDragged_withCreatedFigure_callsUpdateAndRepaint() {
        DrawingView view = mock(DrawingView.class);
        JComponent comp = mock(JComponent.class);
        when(view.getComponent()).thenReturn(comp);

        Drawing drawing = mock(Drawing.class);

        Figure prototype = mock(Figure.class);
        TestableCreationTool tool = new TestableCreationTool(prototype, view, drawing);

        Figure created = mock(Figure.class);
        tool.createdFigure = created;

        tool.anchor = new Point(10, 10);

        tool.mouseDragged(evt(50, 60));

        assertTrue(tool.updateCalled);
        assertTrue(tool.repaintCalled);
        verify(comp, times(1)).repaint();
    }

    @Test
    public void mouseDragged_withoutCreatedFigure_doesNothing() {
        DrawingView view = mock(DrawingView.class);
        JComponent comp = mock(JComponent.class);
        when(view.getComponent()).thenReturn(comp);
        Drawing drawing = mock(Drawing.class);

        TestableCreationTool tool = new TestableCreationTool(mock(Figure.class), view, drawing);
        tool.createdFigure = null;

        tool.mouseDragged(evt(50, 60));

        assertFalse(tool.updateCalled);
        assertFalse(tool.repaintCalled);
        verify(comp, never()).repaint();
    }

    @Test
    public void mouseReleased_withoutCreatedFigure_firesToolDoneWhenConfigured() {
        DrawingView view = mock(DrawingView.class);
        when(view.getComponent()).thenReturn(mock(JComponent.class));
        Drawing drawing = mock(Drawing.class);

        TestableCreationTool tool = spy(new TestableCreationTool(mock(Figure.class), view, drawing));
        tool.createdFigure = null;

        doReturn(true).when(tool).isToolDoneAfterCreation();

        tool.mouseReleased(evt(0, 0));
        assertTrue(tool.toolDoneFired);
    }

    @Test
    public void mouseReleased_withoutCreatedFigure_doesNotFireToolDoneWhenNotConfigured() {
        DrawingView view = mock(DrawingView.class);
        when(view.getComponent()).thenReturn(mock(JComponent.class));
        Drawing drawing = mock(Drawing.class);

        TestableCreationTool tool = spy(new TestableCreationTool(mock(Figure.class), view, drawing));
        tool.createdFigure = null;

        doReturn(false).when(tool).isToolDoneAfterCreation();

        tool.mouseReleased(evt(0, 0));
        assertFalse(tool.toolDoneFired);
    }

    @Test
    public void mouseReleased_zeroSized_removesCreatedFigure() {
        DrawingView view = mock(DrawingView.class);
        when(view.getComponent()).thenReturn(mock(JComponent.class));
        Drawing drawing = mock(Drawing.class);

        TestableCreationTool tool = spy(new TestableCreationTool(mock(Figure.class), view, drawing));
        Figure created = mock(Figure.class);
        tool.createdFigure = created;

        when(created.getBounds()).thenReturn(new Rectangle2D.Double(0, 0, 0, 0));
        doReturn(false).when(tool).isToolDoneAfterCreation();

        tool.mouseReleased(evt(0, 0));

        verify(drawing, times(1)).remove(created);
        assertFalse(tool.creationFinishedCalled);
    }
    @Test
    public void mouseReleased_nonZero_finishesCreation_andClearsCreatedFigure() {
        DrawingView view = mock(DrawingView.class);
        when(view.getComponent()).thenReturn(mock(JComponent.class));
        Drawing drawing = mock(Drawing.class);

        TestableCreationTool tool = spy(new TestableCreationTool(mock(Figure.class), view, drawing));
        Figure created = mock(Figure.class);
        tool.createdFigure = created;

        when(created.getBounds()).thenReturn(new Rectangle2D.Double(0, 0, 10, 10));

        tool.mouseReleased(evt(100, 100));
        assertNull(tool.createdFigure);

        tool.mouseReleased(evt(100, 100));

        assertTrue(tool.creationFinishedCalled);
        assertNull(tool.createdFigure);
    }
}