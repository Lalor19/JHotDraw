package org.jhotdraw.samples.svg.figures.bddtest;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class RectangleCornersTest extends ScenarioTest<GivenSharpRectangle, WhenCornerChanges, ThenRoundedCorners> {

    @Test
    public void sharp_rectangle_changes_to_rounded() {
        given().a_sharp_rectangle_exists(10, 20, 30, 40);
        when().the_corners_are_changed_to(10, 10);
        then().rectangle_is_stroked_with_rounded_corners();
    }
}
