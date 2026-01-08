package org.jhotdraw.samples.svg.gui;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.draw.gui.JAttributeSlider;

public class GivenOpacitySlider extends Stage<GivenOpacitySlider> {

    @ProvidedScenarioState
    OpacitySliderState opacitySliderState = new OpacitySliderState();

    //GIVEN an opacity slider with range 0–100
    public GivenOpacitySlider the_opacity_slider() {
        opacitySliderState.opacitySlider = new JAttributeSlider(JAttributeSlider.VERTICAL, 0,100,100);
        return this;
    }

}