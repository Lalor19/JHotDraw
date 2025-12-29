package org.jhotdraw.samples.svg.figures;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class StrategySelectionTest {

    private static Shape invokeGetTransformedShape(SVGRectFigure figure) throws Exception {
        Method method = SVGRectFigure.class.getDeclaredMethod("getTransformedShape");
        method.setAccessible(true);
        return (Shape) method.invoke(figure);
    }

    @Test
    public void testSelectRectStrategy_Sharp() throws Exception {
        SVGRectFigure figure = new SVGRectFigure(10, 20, 30, 40, 0, 0);

        Shape shape = invokeGetTransformedShape(figure);

        assertTrue(shape instanceof Rectangle2D);
        assertEquals(figure.getBounds(), shape.getBounds());
    }

    @Test
    public void testSelectRectStrategy_Rounded() throws Exception {
        SVGRectFigure figure = new SVGRectFigure(10, 20, 30, 40, 10, 10);

        Shape shape = invokeGetTransformedShape(figure);

        assertTrue(shape instanceof RoundRectangle2D);
        assertEquals(figure.getBounds(), shape.getBounds());
    }
}
