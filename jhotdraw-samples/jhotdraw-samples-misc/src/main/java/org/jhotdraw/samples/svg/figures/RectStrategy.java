package org.jhotdraw.samples.svg.figures;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public interface RectStrategy {
    public Shape fillShape(RoundRectangle2D.Double roundRect);
    public Shape strokeShape(RoundRectangle2D.Double roundRect, double acv);
    public Shape transformShape(RoundRectangle2D.Double roundRect);
}
