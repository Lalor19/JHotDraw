package org.jhotdraw.samples.svg.figures.bddtest.curverectangles;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;

import java.awt.geom.RoundRectangle2D;

public class GivenSharpRectangle extends Stage<GivenSharpRectangle> {

    @ProvidedScenarioState
    RoundRectangle2D.Double rectangle;

    public GivenSharpRectangle a_sharp_rectangle_exists(double x, double y, double w, double h) {
        rectangle = new RoundRectangle2D.Double(x,y,w,h,0,0);
        return this;
    }
}
