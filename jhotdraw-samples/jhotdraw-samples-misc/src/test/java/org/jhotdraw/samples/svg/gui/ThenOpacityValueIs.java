package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;


import static org.junit.Assert.*;

public class ThenOpacityValueIs extends Stage<ThenOpacityValueIs> {

    @ExpectedScenarioState
    OpacitySliderState opacitySliderState;

    //THEN the opacity value should be 50
    public ThenOpacityValueIs the_opacity_value_should_be(int expectedValue) {
        assertEquals(expectedValue, opacitySliderState.opacitySlider.getValue());
        return this;
    }
}