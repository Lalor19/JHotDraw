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

public class RectangleSwingTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private DefaultDrawingView view;
    private DrawingEditor editor;

    private CreationTool rectangleTool;
    private SelectionTool selectionTool;

    @Override
    protected void onSetUp() throws Exception {
        JFrame frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Rectangle Swing Test");
            f.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            f.setLayout(new BorderLayout());

            view = new DefaultDrawingView();
            view.setName("drawingView");
            view.setDrawing(new DefaultDrawing());
            view.setFocusable(true);

            editor = new DefaultDrawingEditor();
            editor.add(view);
            editor.setActiveView(view);

            rectangleTool = new CreationTool(SVGAttributedFigure.createRectFactory());
            selectionTool = new SelectionTool();
            editor.setTool(selectionTool);

            JButton rectangleButton = new JButton("Rectangle");
            rectangleButton.setName("createRectangleButton");
            rectangleButton.addActionListener(action -> {
                editor.setActiveView(view);
                editor.setTool(rectangleTool);
                view.getComponent().requestFocusInWindow();
            });

            JButton selectionButton = new JButton("Select");
            selectionButton.setName("selectionButton");
            selectionButton.addActionListener(action -> {
                editor.setActiveView(view);
                editor.setTool(selectionTool);
                view.getComponent().requestFocusInWindow();
            });

            JToolBar toolBar = new JToolBar();
            toolBar.add(rectangleButton);
            toolBar.add(selectionButton);

            f.add(toolBar, BorderLayout.NORTH);
            f.add(view.getComponent(), BorderLayout.CENTER);

            f.setSize(600, 600);
            f.setLocationRelativeTo(null);
            f.setVisible(true);
            return f;
        });

        window = new FrameFixture(robot(), frame);
        window.show(new Dimension(600, 600));
    }

    @Test
    public void user_draws_rectangle_test(){
        window.requireVisible();
        window.button("createRectangleButton").click();
        window.robot().waitForIdle();

        Component canvas = view.getComponent();

        Point from = new Point(100, 100);
        Point to = new Point(300, 350);
        drag(canvas, from, to);

        SVGRectFigure rectFigure = findFirstRect(view.getDrawing())
                .orElseThrow(() -> new AssertionError("no rectangle figure"));

        Rectangle2D.Double bounds = rectFigure.getBounds();

        double expectedW = to.getX() - from.getX();
        double expectedH = to.getY() - from.getY();

        assertThat(bounds.getX()).isEqualTo(from.getX());
        assertThat(bounds.getY()).isEqualTo(from.getY());
        assertThat(bounds.getWidth()).isEqualTo(expectedW);
        assertThat(bounds.getHeight()).isEqualTo(expectedH);
    }

    @Test
    public void user_curves_rectangle_test(){
        window.button("createRectangleButton").click();
        window.robot().waitForIdle();

        Component canvas = view.getComponent();

        Point from = new Point(120, 120);
        Point to = new Point(350, 400);
        drag(canvas, from, to);

        SVGRectFigure rectFigure = findFirstRect(view.getDrawing())
                .orElseThrow(() -> new AssertionError("no rectangle figure"));

        // Asserting that the rectangle is sharp at first
        assertThat(rectFigure.getArcWidth()).isEqualTo(0d);
        assertThat(rectFigure.getArcHeight()).isEqualTo(0d);

        window.button("selectionButton").click();
        window.robot().waitForIdle();

        clickInside(rectFigure);

        GuiActionRunner.execute(() -> view.setHandleDetailLevel(0));
        window.robot().waitForIdle();

        Rectangle2D.Double bounds = rectFigure.getBounds();
        Point handleStart = view.drawingToView(new Point2D.Double(
                bounds.x + rectFigure.getArcWidth(),
                bounds.y + rectFigure.getArcHeight()
        ));

        Point probe = new Point(handleStart.x + 1, handleStart.y + 1);

        Handle handle = GuiActionRunner.execute(() -> view.findHandle(probe));
        assertThat(handle).isNotNull();
        assertThat(handle).isInstanceOf(SVGRectRadiusHandle.class);

        // Curving the rectangle
        Point handleTarget = view.drawingToView(new Point2D.Double(bounds.x + 60, bounds.y + 60));
        drag(canvas, probe, handleTarget);

        // Rendering the curing rectangle
        GuiActionRunner.execute(() -> {
            view.revalidate();
            view.repaint();
        });
        window.robot().waitForIdle();

        // Asserting the rounded rectangle
        assertThat(rectFigure.getArcWidth()).isGreaterThanOrEqualTo(0d);
        assertThat(rectFigure.getArcHeight()).isGreaterThanOrEqualTo(0d);
        assertThat(rectFigure.getArcWidth()).isEqualTo(60);
        assertThat(rectFigure.getArcHeight()).isEqualTo(60);
    }

    private void drag(Component canvas, Point from, Point to) {
        window.robot().moveMouse(canvas, from);
        window.robot().pressMouse(MouseButton.LEFT_BUTTON);
        window.robot().moveMouse(canvas, to);
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
                Optional<SVGRectFigure> result = findRectFigure(child);
                if (result.isPresent()) return result;
            }
        }
        return Optional.empty();
    }

    private void clickInside(SVGRectFigure figure) {
        Rectangle rectangle = view.drawingToView(figure.getBounds());
        Point inside = new Point(rectangle.x + rectangle.width / 2, rectangle.y + rectangle.height / 2);
        window.robot().click(view.getComponent(), inside, MouseButton.LEFT_BUTTON, 1);
        window.robot().waitForIdle();
    }
}
