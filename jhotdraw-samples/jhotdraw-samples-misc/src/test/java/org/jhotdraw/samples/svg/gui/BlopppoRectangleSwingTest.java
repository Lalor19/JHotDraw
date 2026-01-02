package org.jhotdraw.samples.svg.gui;

import org.assertj.swing.core.MouseButton;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.handle.Handle;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.draw.tool.SelectionTool;
import org.jhotdraw.samples.svg.figures.SVGAttributedFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.jhotdraw.samples.svg.figures.SVGRectRadiusHandle;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class BlopppoRectangleSwingTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private DefaultDrawingView view;
    private DrawingEditor editor;

    private CreationTool rectTool;
    private SelectionTool selectionTool;

    @Override
    protected void onSetUp() {
        JFrame frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Rectangle Swing Tests");
            f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            f.setLayout(new BorderLayout());

            view = new DefaultDrawingView();
            view.setName("drawingView");
            view.setDrawing(new DefaultDrawing());
            view.setFocusable(true);

            editor = new DefaultDrawingEditor();
            editor.add(view);
            editor.setActiveView(view);

            rectTool = new CreationTool(SVGAttributedFigure.createRectFactory());
            selectionTool = new SelectionTool();
            editor.setTool(selectionTool);

            JButton rectBtn = new JButton("Rectangle");
            rectBtn.setName("createRectangle");
            rectBtn.addActionListener(e -> {
                editor.setActiveView(view);
                editor.setTool(rectTool);
                view.getComponent().requestFocusInWindow();
            });

            JButton selectBtn = new JButton("Select");
            selectBtn.setName("selectTool");
            selectBtn.addActionListener(e -> {
                editor.setActiveView(view);
                editor.setTool(selectionTool);
                view.getComponent().requestFocusInWindow();
            });

            JToolBar tb = new JToolBar();
            tb.add(rectBtn);
            tb.add(selectBtn);

            f.add(tb, BorderLayout.NORTH);
            f.add(view.getComponent(), BorderLayout.CENTER);

            f.setSize(800, 600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            return f;
        });

        window = new FrameFixture(robot(), frame);
        window.show(new Dimension(800, 600));
    }

    @Test
    public void user_draws_rectangle_using_rectangle_tool() {
        window.requireVisible();
        window.button("createRectangle").click();
        window.robot().waitForIdle();

        Component canvas = view.getComponent();
        Point from = new Point(100, 100);
        Point to = new Point(310, 350);

        drag(canvas, from, to);

        SVGRectFigure rect = findFirstRect(view.getDrawing())
                .orElseThrow(() -> new AssertionError("Rectangle should be created"));

        Rectangle2D.Double bounds = rect.getBounds();

        double expectedW = to.getX() - from.getX();
        double expectedH = to.getY() - from.getY();

        assertThat(bounds.getX()).isEqualTo(from.getX());
        assertThat(bounds.getY()).isEqualTo(from.getY());
        assertThat(bounds.getWidth()).isEqualTo(expectedW);
        assertThat(bounds.getHeight()).isEqualTo(expectedH);
    }

    @Test
    public void user_curves_rectangle_by_dragging_the_radius_handle() {
        // 1) Draw a sharp rectangle
        window.button("createRectangle").click();
        window.robot().waitForIdle();

        Component canvas = view.getComponent();
        Point from = new Point(120, 120);
        Point to = new Point(360, 280);
        drag(canvas, from, to);

        SVGRectFigure rect = findFirstRect(view.getDrawing())
                .orElseThrow(() -> new AssertionError("Expected a rectangle to exist"));

        assertThat(rect.getArcWidth()).isEqualTo(0d);
        assertThat(rect.getArcHeight()).isEqualTo(0d);

        // 2) Select it (so handles are created)
        window.button("selectTool").click();
        window.robot().waitForIdle();

        clickInside(rect);

        // ensure SVGRectRadiusHandle is present
        GuiActionRunner.execute(() -> view.setHandleDetailLevel(0));
        window.robot().waitForIdle();

        // 3) Find the radius handle
        Rectangle2D.Double b = rect.getBounds();
        Point handleStart = view.drawingToView(new Point2D.Double(
                b.x + rect.getArcWidth(),
                b.y + rect.getArcHeight()
        ));

        Point probe = new Point(handleStart.x + 1, handleStart.y + 1);

        Handle h = GuiActionRunner.execute(() -> view.findHandle(probe));
        assertThat(h).as("Handle at probe point").isNotNull();
        assertThat(h).as("Expected SVGRectRadiusHandle").isInstanceOf(SVGRectRadiusHandle.class);

        // drag to increase rounding
        Point handleTarget = view.drawingToView(new Point2D.Double(b.x + 60, b.y + 60));
        drag(canvas, probe, handleTarget);

        GuiActionRunner.execute(() -> {
            view.revalidate();
            view.repaint();
        });
        window.robot().waitForIdle();

        assertThat(rect.getArcWidth()).as("arcWidth").isGreaterThan(0d);
        assertThat(rect.getArcHeight()).as("arcHeight").isGreaterThan(0d);
        assertThat(rect.getArcWidth()).isEqualTo(60);
        assertThat(rect.getArcHeight()).isEqualTo(60);
    }

    // ---- helpers ----

    private void clickInside(SVGRectFigure rect) {
        Rectangle vr = view.drawingToView(rect.getBounds());
        Point inside = new Point(vr.x + vr.width / 2, vr.y + vr.height / 2);
        window.robot().click(view.getComponent(), inside, MouseButton.LEFT_BUTTON, 1);
        window.robot().waitForIdle();
    }

    private void drag(Component canvas, Point start, Point end) {
        window.robot().moveMouse(canvas, start);
        window.robot().pressMouse(MouseButton.LEFT_BUTTON);
        window.robot().moveMouse(canvas, end);
        window.robot().releaseMouse(MouseButton.LEFT_BUTTON);
        window.robot().waitForIdle();
    }

    private Optional<SVGRectFigure> findFirstRect(Drawing drawing) {
        return drawing.getChildren().stream()
                .map(this::findRectFigure)
                .flatMap(Optional::stream)
                .findFirst();
    }

    private Optional<SVGRectFigure> findRectFigure(Figure figure) {
        if (figure instanceof SVGRectFigure) return Optional.of((SVGRectFigure) figure);

        if (figure instanceof CompositeFigure) {
            for (Figure child : ((CompositeFigure) figure).getChildren()) {
                Optional<SVGRectFigure> rect = findRectFigure(child);
                if (rect.isPresent()) return rect;
            }
        }
        return Optional.empty();
    }
}
