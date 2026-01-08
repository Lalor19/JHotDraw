package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.gui.JAttributeSlider;

public class WhenOpacityValueIsSet extends Stage<WhenOpacityValueIsSet> {

    @ProvidedScenarioState
    OpacitySliderState opacitySliderState;

    //WHEN I set the value to 50
    public WhenOpacityValueIsSet the_opacity_is_at(int value) {
        opacitySliderState.opacitySlider.setValue(value);
        return this;
    }

}