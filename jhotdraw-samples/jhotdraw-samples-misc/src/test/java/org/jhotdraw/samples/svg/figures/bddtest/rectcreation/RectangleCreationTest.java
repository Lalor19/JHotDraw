package org.jhotdraw.samples.svg.figures.bddtest.rectcreation;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class RectangleCreationTest extends ScenarioTest<GivenSVG, WhenRectangleAction, ThenRectangleOut> {

    @Test
    public void create_rectangles(){
        given().an_svg_figure_factory_is_available();
        when().creating_a_rectangle_with_bounds(10, 20, 30, 40);
        then().a_rectangle_figure_exists()
                .and().matches_requested_bounds();
    }
}
