package org.jhotdraw.samples.svg.figures.bddtest.rectcreation;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.AttributeKey;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.samples.svg.io.SVGFigureFactory;

import java.util.Collections;
import java.util.Map;

public class WhenRectangleAction extends Stage<WhenRectangleAction> {

    @ExpectedScenarioState
    SVGFigureFactory factory;

    @ProvidedScenarioState
    Figure createdFigure;

    @ProvidedScenarioState
    double x, y, w, h;

    public WhenRectangleAction creating_a_rectangle_with_bounds(double x, double y, double w, double h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;

        Map<AttributeKey<?>, Object> attributes = Collections.emptyMap();
        createdFigure = factory.createRect(x, y, w, h, attributes);
        return this;
    }
}
