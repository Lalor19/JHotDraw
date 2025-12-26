package org.jhotdraw.samples.svg.figures;

import java.awt.*;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;

public class RoundedRectStrategy implements RectStrategy {
    public RoundedRectStrategy() {
    }

    @Override
    public Shape fillShape(RoundRectangle2D.Double roundRect) {
        return roundRect;
    }

    @Override
    public Shape strokeShape(RoundRectangle2D.Double roundRect, double acv) {
        Path2D.Double p = new Path2D.Double();
        double aw = roundRect.arcwidth / 2d;
        double ah = roundRect.archeight / 2d;
        p.moveTo((roundRect.x + aw), (float) roundRect.y);
        p.lineTo((roundRect.x + roundRect.width - aw), (float) roundRect.y);
        p.curveTo((roundRect.x + roundRect.width - aw * acv), (float) roundRect.y,
                (roundRect.x + roundRect.width), (float) (roundRect.y + ah * acv),
                (roundRect.x + roundRect.width), (roundRect.y + ah));
        p.lineTo((roundRect.x + roundRect.width), (roundRect.y + roundRect.height - ah));
        p.curveTo(
                (roundRect.x + roundRect.width), (roundRect.y + roundRect.height - ah * acv),
                (roundRect.x + roundRect.width - aw * acv), (roundRect.y + roundRect.height),
                (roundRect.x + roundRect.width - aw), (roundRect.y + roundRect.height));
        p.lineTo((roundRect.x + aw), (roundRect.y + roundRect.height));
        p.curveTo((roundRect.x + aw * acv), (roundRect.y + roundRect.height),
                (roundRect.x), (roundRect.y + roundRect.height - ah * acv),
                (float) roundRect.x, (roundRect.y + roundRect.height - ah));
        p.lineTo((float) roundRect.x, (roundRect.y + ah));
        p.curveTo((roundRect.x), (roundRect.y + ah * acv),
                (roundRect.x + aw * acv), (float) (roundRect.y),
                (float) (roundRect.x + aw), (float) (roundRect.y));
        p.closePath();
        return p;
    }

    @Override
    public Shape transformShape(RoundRectangle2D.Double roundRect) {
        return (Shape) roundRect.clone();
    }
}
