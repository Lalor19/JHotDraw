package org.jhotdraw.samples.svg.gui;

import org.jhotdraw.draw.DrawingEditor;
import org.jhotdraw.draw.gui.JAttributeSlider;
import org.jhotdraw.gui.JPopupButton;
import org.junit.*;
import org.mockito.Mockito;

import javax.swing.*;

import java.awt.*;

import static org.junit.Assert.*;


public class CanvasToolBarTest {
    private CanvasToolBar canvasToolBar;
    private JFrame jFrame;


    //Due to workflow
    @BeforeClass
    public static void skipInCI() {
        Assume.assumeTrue("Skipping Swing tests in CI (no display)",
                System.getenv("CI") == null);
    }

    @Before
    public void setUp() {
        canvasToolBar = new CanvasToolBar();
        jFrame = new JFrame();
        jFrame.add(canvasToolBar);

        DrawingEditor editor = Mockito.mock(DrawingEditor.class);
        canvasToolBar.setEditor(editor);
    }

    @Test
    public void canvasToolBarCreatedTest() {
        assertNotNull(canvasToolBar);
    }

    @Test
    public void opacitySliderCreatedTest_State1() {
        JComponent component = canvasToolBar.createDisclosedComponent(1);
        assertNotNull(component);
        JAttributeSlider slider = helperFindOpacitySlider(component);
        assertNotNull(slider);
    }

    @Test
    public void opacitySliderCreatedTest_State2() {
        JComponent component = canvasToolBar.createDisclosedComponent(2);
        assertNotNull(component);
        JAttributeSlider slider = helperFindOpacitySlider(component);
        assertNotNull(slider);
    }

    @Test
    public void opacitySliderValueTest() {
        JComponent component = canvasToolBar.createDisclosedComponent(1);
        JAttributeSlider slider = helperFindOpacitySlider(component);

        assertEquals(0, slider.getMinimum());
        assertEquals(100, slider.getMaximum());

        slider.setValue(50);
        assertEquals(50, slider.getValue());
    }

    @After
    public void tearDown() {
        canvasToolBar.removeAll();
        jFrame.dispose();
    }

    public JAttributeSlider helperFindOpacitySlider (Container container) {
        if (container == null) { return null; }

        for (Component component : container.getComponents()) {
            if (component instanceof JAttributeSlider) {
                return (JAttributeSlider) component;
            }

            if (component instanceof JPopupButton) {
                JPopupMenu popupMenu = ((JPopupButton) component).getPopupMenu();
                JAttributeSlider slider = helperFindOpacitySlider(popupMenu);
                if (slider != null) {
                    return slider;
                }
            }

            if (component instanceof Container) {
                JAttributeSlider slider = helperFindOpacitySlider((Container) component);
                if (slider != null) {
                    return slider;
                }
            }
        }
        return null;
    }
}