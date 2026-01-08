package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ExpectedScenarioState;

public class WhenOpacityValueIsSet extends Stage<WhenOpacityValueIsSet> {

    @ExpectedScenarioState
    OpacitySliderState opacitySliderState;

    //WHEN I set the value to 50
    public WhenOpacityValueIsSet the_opacity_is_at(int value) {
        opacitySliderState.opacitySlider.setValue(value);
        return this;
    }

}