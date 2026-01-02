package org.jhotdraw.samples.svg.gui;

import org.assertj.swing.core.MouseButton;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.tool.CreationTool;
import org.jhotdraw.draw.tool.SelectionTool;
import org.jhotdraw.samples.svg.figures.SVGAttributedFigure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DrawRectangleSwingTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;
    private DefaultDrawingView view;
    private DrawingEditor editor;
    private CreationTool rectangleTool;
    private SelectionTool selectionTool;

    @Override
    protected void onSetUp() throws Exception {

        JFrame frame = GuiActionRunner.execute(() -> {
            JFrame f = new JFrame("Rectangle Test");
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
            JButton createRectangleButton = new JButton("Rectangle");
            createRectangleButton.setName("createRectangle");
            createRectangleButton.addActionListener(ev -> {
                editor.setActiveView(view);
                editor.setTool(rectangleTool);
                view.getComponent().requestFocusInWindow();
            });

            JToolBar toolBar = new JToolBar();
            toolBar.add(createRectangleButton);

            f.add(toolBar, BorderLayout.NORTH);
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
    public void user_Draws_rectangle_using_rectangle_tool(){
        window.requireVisible();
        window.button("createRectangle").click();
        window.robot().waitForIdle();

        Component canvas = view.getComponent();

        Point from = new Point(100, 100);
        Point to = new Point(310, 350);

        window.robot().moveMouse(canvas, from);
        window.robot().pressMouse(MouseButton.LEFT_BUTTON);
        window.robot().waitForIdle();
        window.robot().moveMouse(canvas, to);
        window.robot().releaseMouse(MouseButton.LEFT_BUTTON);

        window.robot().waitForIdle();

        Drawing drawing = view.getDrawing();

        Optional<SVGRectFigure> rectFigureOptional = drawing.getChildren().stream()
                .map(this::findRectFigure)
                .flatMap(Optional::stream)
                .findFirst();

        assertThat(rectFigureOptional).as("Rectangle should be created").isPresent();


        SVGRectFigure rectFigure = rectFigureOptional.get();
        Rectangle2D.Double bounds = rectFigure.getBounds();

        double expectedW = to.getX() - from.getX();
        double expectedH = to.getY() - from.getY();

        assertThat(bounds.getX()).isEqualTo(from.getX());
        assertThat(bounds.getY()).isEqualTo(from.getY());
        assertThat(bounds.getWidth()).isEqualTo(expectedW);
        assertThat(bounds.getHeight()).isEqualTo(expectedH);
    }

    private Optional<SVGRectFigure> findRectFigure(Figure figure) {
        if (figure instanceof SVGRectFigure) return Optional.of((SVGRectFigure) figure);

        if (figure instanceof CompositeFigure) {
            CompositeFigure compositeFigure = (CompositeFigure) figure;
            for (Figure child : compositeFigure.getChildren()) {
                Optional<SVGRectFigure> rectFigure = findRectFigure(child);
                if (rectFigure.isPresent()) return rectFigure;
            }
        }
        return Optional.empty();
    }
}
