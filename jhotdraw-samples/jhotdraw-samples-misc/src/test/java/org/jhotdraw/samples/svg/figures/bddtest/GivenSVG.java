package org.jhotdraw.samples.svg.figures.bddtest;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ProvidedScenarioState;
import org.jhotdraw.samples.svg.io.DefaultSVGFigureFactory;
import org.jhotdraw.samples.svg.io.SVGFigureFactory;

public class GivenSVG extends Stage<GivenSVG> {

    @ProvidedScenarioState
    SVGFigureFactory factory;

    public GivenSVG an_svg_figure_factory_is_available(){
        factory = new DefaultSVGFigureFactory();
        return this;
    }
}
