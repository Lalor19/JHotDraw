package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;

public class OpacitySlidersValueScenarioTest extends ScenarioTest<GivenOpacitySlider, WhenOpacityValueIsSet, ThenOpacityValueIs> {

    //Given an opacity slider with range 0–100
    //When I set the value to 50
    //Then the opacity value should be 50
    @Test
    public void opacity_slider_returns_value() {
        given().the_opacity_slider();
        when().the_opacity_is_at(50);
        then().the_opacity_value_should_be(50);
    }
}