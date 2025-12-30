package org.jhotdraw.samples.svg.figures.bddtest.rectcreation;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.figures.SVGRectFigure;

import java.awt.geom.Rectangle2D;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ThenRectangleOut extends Stage<ThenRectangleOut> {
    @ExpectedScenarioState
    Figure createdFigure;

    @ExpectedScenarioState
    double x,  y, w, h;

    public ThenRectangleOut a_rectangle_figure_exists() {
        assertThat(createdFigure)
                .isNotNull()
                .isInstanceOf(SVGRectFigure.class);
        return this;
    }
    public ThenRectangleOut matches_requested_bounds() {
        SVGRectFigure rect = (SVGRectFigure) createdFigure;

        Rectangle2D.Double bounds = rect.getBounds();
        assertThat(bounds.getX()).isEqualTo(x);
        assertThat(bounds.getY()).isEqualTo(y);
        assertThat(bounds.getWidth()).isEqualTo(w);
        assertThat(bounds.getHeight()).isEqualTo(h);
        return this;
    }
}
