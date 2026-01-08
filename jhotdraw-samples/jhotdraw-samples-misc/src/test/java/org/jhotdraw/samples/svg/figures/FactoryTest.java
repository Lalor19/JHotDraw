package org.jhotdraw.samples.svg.figures;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class FactoryTest {

    @Test
    public void testCreateRectFactory(){
        SVGAttributedFigure figure = SVGAttributedFigure.createRectFactory();

        assertNotNull(figure);
        assertTrue(figure instanceof SVGRectFigure);
    }
}
