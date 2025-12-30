package org.jhotdraw.samples.svg.gui;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.assertj.swing.junit.testcase.AssertJSwingJUnitTestCase;
import org.jhotdraw.draw.DefaultDrawingView;
import org.jhotdraw.samples.svg.Main;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;

public class DrawRectangleSwingTest extends AssertJSwingJUnitTestCase {

    private FrameFixture window;

    @Override
    protected void onSetUp() throws Exception {
        JFrame frame = GuiActionRunner.execute(() -> {
            Main.main(new String[0]);

            for (Frame frames : Frame.getFrames()) {
                if (frames instanceof JFrame && frames.isVisible()) {
                    return (JFrame) frames;
                }
            }
            throw new IllegalStateException("No frames were found");
        });

        window = new FrameFixture(robot(), frame);
        window.show();
    }

    @Test
    public void user_Draws_rectangle_using_rectangle_tool(){
        window.requireVisible();

        window.button("createRectangle").click();

        window.component("SVGDrawingPanel").drag(new Point(10, 10)).drop(new Point(110, 60));
    }
}
