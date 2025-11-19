package org.jhotdraw.samples.utility;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.function.Consumer;
import java.util.function.Supplier;


public final class RenderSupport {

    private RenderSupport() {
    }

    public static void draw(Graphics2D g, double opacity, Supplier<Rectangle2D.Double> drawingArea, Consumer<Graphics2D> painter) {

        double newOpacity = Math.min(Math.max(0d, opacity), 1d);
        if (newOpacity == 0d) return;

        if (newOpacity < 1d) {
            Rectangle2D.Double area = (Rectangle2D.Double) drawingArea.get().clone();
            Rectangle2D clipBounds = g.getClipBounds();
            if (clipBounds != null) {
                Rectangle2D.intersect(area, clipBounds, area);
            }
            if (!area.isEmpty()) {
                final AffineTransform at = g.getTransform();
                final double sx = at.getScaleX();
                final double sy = at.getScaleY();
                final int width = Math.max(1, (int) ((2 + area.width) * sx));
                final int height = Math.max(1, (int) ((2 + area.height) * sy ));

                BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
                Graphics2D gr = buf.createGraphics();
                gr.scale(sx, sy);
                gr.translate((int) -area.x, (int) -area.y);
                gr.setRenderingHints(g.getRenderingHints());
                painter.accept(gr);
                gr.dispose();
                Composite savedComposite = g.getComposite();
                g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) newOpacity));
                g.drawImage(buf, (int) area.x, (int) area.y,
                        2 + (int) area.width, 2 + (int) area.height, null);
                g.setComposite(savedComposite);
            }
        } else {
            painter.accept(g);
        }

    }
}


