package org.jhotdraw.samples.svg.figures.bddtest.curverectangles;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

import java.awt.*;
import java.awt.geom.Path2D;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ThenRoundedCorners extends Stage<ThenRoundedCorners> {

    @ExpectedScenarioState
    Shape strokeRect;

    public ThenRoundedCorners rectangle_is_stroked_with_rounded_corners() {
        assertThat(strokeRect).isInstanceOf(Path2D.class);
        return this;
    }
}
