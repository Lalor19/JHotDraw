package org.jhotdraw.samples.svg.figures;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public enum SharpRectStrategy implements RectStrategy {
    INSTANCE;

    @Override
    public Shape fillShape(RoundRectangle2D.Double roundRect) {
        return roundRect.getBounds2D();
    }

    @Override
    public Shape strokeShape(RoundRectangle2D.Double roundRect, double acv) {
        return roundRect.getBounds2D();
    }

    @Override
    public Shape transformShape(RoundRectangle2D.Double roundRect) {
        return roundRect.getBounds2D();
    }
}
