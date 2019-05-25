package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

public class Backdrop extends JPanel {

    // The graphics context.
    private Graphics2D graphics;

    // Colors
    Color backgroundColor1;
    
    Color backgroundColor2;

    Color foregroundColor1;

    Color foregroundColor2;

    Color shadowColor = new Color(72, 81, 81);

    // Width, in pixels.
    int width;

    // Height, in pixels.
    int height;

    // The horizontal scroll position, expressed as a double min 0 and max 1.
    double position = 0;

    // Constructor
    Backdrop(int x, int y, int width, int height, Color backgroundColor1, Color backgroundColor2, Color foregroundColor1, Color foregroundColor2) {
        System.out.println("Creating backdrop of width:" + width + " and height:" + height + ".");

        this.width = width;
        this.height = height;

        this.backgroundColor1 = backgroundColor1;
        this.backgroundColor2 = backgroundColor2;
        this.foregroundColor1 = foregroundColor1;
        this.foregroundColor2 = foregroundColor2;

        this.setBounds(new Rectangle(x, y, width, height));
    }

    // Draw
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Drawing backdrop.");

        graphics = (Graphics2D) g;

        drawBackground();
        drawForeground();
    }

    private void drawBackground() {
        graphics.setColor(backgroundColor1);

        graphics.fillRect(0, 0, width, height);

        graphics.setColor(backgroundColor2);

        for (int i = 0; i < 25; i++) {
            graphics.fillRect(50 + (i * 100), -(int) (height * 0.1), (int) (height * 0.05), (int) (height * 1.5));
        }
    }

    private void drawForeground() {
        int xDistance = Math.min(300, (int) (width * 0.3));

        int[] yPoints = {
            height,
            (int) (height * 0.8),
            height
        };

        int pointCount = yPoints.length;

        graphics.setColor(shadowColor);

        for (int i = 0; i < 5; i++) {
            graphics.fillPolygon(xValues(xDistance - (int) (xDistance * 0.05), xDistance * i - (int) (xDistance * 0.1)), yPoints, pointCount);
        }

        graphics.setColor(foregroundColor1);

        for (int i = 0; i < 5; i++) {
            graphics.fillPolygon(xValues(xDistance, xDistance * i - (int) (xDistance * 0.1)), yPoints, pointCount);
        }

        graphics.setColor(shadowColor);

        for (int i = 0; i < 5; i++) {
            graphics.fillPolygon(xValues(xDistance + (int) (xDistance * 0.4) - (int) (xDistance * 0.05), xDistance * i), yPoints, pointCount);
        }

        graphics.setColor(foregroundColor2);

        for (int i = 0; i < 5; i++) {
            graphics.fillPolygon(xValues(xDistance + (int) (xDistance * 0.4), xDistance * i), yPoints, pointCount);
        }
    }

    private int[] xValues(int xDistance, int offset) {
        return new int[]{
            0 + offset,
            (int) (xDistance * 0.5) + offset,
            xDistance + offset
        };
    }

    // Position getter/setter
    public double getPosition() {
        return this.position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

}
