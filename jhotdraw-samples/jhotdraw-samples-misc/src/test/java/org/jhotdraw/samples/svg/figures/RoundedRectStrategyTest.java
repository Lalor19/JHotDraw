package org.jhotdraw.samples.svg.figures;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

import static org.junit.Assert.*;

public class RoundedRectStrategyTest {

    @Test
    public void testFillShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 20, 30, 40, 10, 10);

        Shape shape = RoundedRectStrategy.INSTANCE.fillShape(roundRect);

        assertSame(roundRect, shape);
    }

    @Test
    public void testStrokeShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 20, 30, 40, 10, 10);
        double acv = 0.55;

        Shape shape = RoundedRectStrategy.INSTANCE.strokeShape(roundRect, acv);

        assertTrue(shape instanceof Path2D);
        assertEquals(roundRect.getBounds2D(), shape.getBounds2D());
    }

    @Test
    public void testStrokeShape_differentACV() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 20, 30, 40, 10, 10);
        double acv0 = 0.0;
        double acv1 = 1.0;

        Shape shape0 = RoundedRectStrategy.INSTANCE.strokeShape(roundRect, acv0);
        Shape shape1 = RoundedRectStrategy.INSTANCE.strokeShape(roundRect, acv1);

        assertEquals(roundRect.getBounds2D(), shape0.getBounds2D());
        assertEquals(roundRect.getBounds2D(), shape1.getBounds2D());

    }

    @Test
    public void testTransformShape() {
        RoundRectangle2D.Double roundRect = new RoundRectangle2D.Double(10, 20, 30, 40, 10, 10);

        Shape shape = RoundedRectStrategy.INSTANCE.transformShape(roundRect);

        assertTrue(shape instanceof RoundRectangle2D);
        assertNotSame(roundRect, shape);
        assertEquals(roundRect.getBounds2D(), shape.getBounds2D());
    }
}