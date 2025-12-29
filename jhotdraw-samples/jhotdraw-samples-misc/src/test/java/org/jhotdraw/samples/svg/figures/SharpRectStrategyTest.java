package org.jhotdraw.samples.svg.figures;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import static org.junit.Assert.*;

public class SharpRectStrategyTest {

    @Test
    public void testFillShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 20, 30, 40, 0, 0);

        Shape shape = SharpRectStrategy.INSTANCE.fillShape(roundRect);

        assertTrue(shape instanceof Rectangle2D);
        assertEquals(roundRect.getBounds2D(), shape.getBounds());
    }

    @Test
    public void testStrokeShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 10, 30, 40, 0, 0);

        Shape shape1 = SharpRectStrategy.INSTANCE.strokeShape(roundRect, 0.0);
        Shape shape2 = SharpRectStrategy.INSTANCE.strokeShape(roundRect, 1.0);

        assertEquals(roundRect.getBounds2D(), shape1.getBounds());
        assertEquals(roundRect.getBounds2D(), shape2.getBounds());
    }

    @Test
    public void testTransformShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 10, 30, 40, 0, 0);

        Shape shape = SharpRectStrategy.INSTANCE.transformShape(roundRect);

        assertTrue(shape instanceof Rectangle2D);
        assertEquals(roundRect.getBounds2D(), shape.getBounds());
    }
}