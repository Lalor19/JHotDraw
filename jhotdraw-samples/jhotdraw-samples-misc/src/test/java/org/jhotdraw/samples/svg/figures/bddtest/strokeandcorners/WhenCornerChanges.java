package org.jhotdraw.samples.svg.figures.bddtest.strokeandcorners;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.samples.svg.figures.RoundedRectStrategy;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WhenCornerChanges extends Stage<WhenCornerChanges> {

    @ExpectedScenarioState
    RoundRectangle2D.Double rectangle;

    @ProvidedScenarioState
    Shape strokeRect;


    public WhenCornerChanges the_corners_are_changed_to(double archWidth, double archHeight) {
        rectangle.arcwidth = 0;
        rectangle.archeight = 0;

        rectangle.arcwidth = archWidth;
        rectangle.archeight = archHeight;

        strokeRect = RoundedRectStrategy.INSTANCE.strokeShape(rectangle, 1.0);
        return this;
    }
}
