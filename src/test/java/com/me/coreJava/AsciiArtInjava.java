package com.me.coreJava;

import org.junit.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

public class AsciiArtInjava {
    @Test public void
    draw_artiship() {
        int width = 300;
        int height = 100;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.drawString("artiship", 12, 24);
    }
}
