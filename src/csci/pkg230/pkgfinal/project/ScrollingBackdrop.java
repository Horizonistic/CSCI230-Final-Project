package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class ScrollingBackdrop extends JPanel {

    // The graphics context.
    private Graphics2D graphics;
    
    // Colors
    
    Color backgroundColor;
    
    Color foregroundColor;
    

    // Width, in pixels.
    int width;

    // Height, in pixels.
    int height;
    

    // The horizontal scroll position, expressed as a double min 0 and max 1.
    double position = 0;

    // Constructor
    ScrollingBackdrop(int width, int height, Color backgroundColor, Color foregroundColor) {
        System.out.println("Creating backdrop of width:" + width + " and height:" + height + ".");

        this.width = width;
        this.height = height;
        
        this.backgroundColor = backgroundColor;
        this.foregroundColor = foregroundColor;
    }
    
    @Override
    public void setBackground(Color color) {
        this.backgroundColor = color;
        
        repaint();
    }
    
    @Override
    public void setForeground(Color color) {
        this.foregroundColor = color;
        
        repaint();
    }

    // Draw
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("Drawing backdrop.");
        
        graphics = (Graphics2D) g;

        drawBackground();
    }

    private void drawBackground() {
        graphics.setColor(backgroundColor);
        
        graphics.fillRect(0, 0, width, height);
        
        
        graphics.setColor(foregroundColor);
        
        
        int buildingWidth = Math.min(100, (int)(width * 0.3));
        int buildingHeight = (int)(height * 0.3);
        
        int[] xPoints = {
            0,
            buildingWidth,
            buildingWidth,
            0
        };
        int[] yPoints = {
            0,
            buildingHeight,
            buildingHeight,
            0
        };
        
        int pointCount = xPoints.length;
        
        graphics.fillPolygon(xPoints, yPoints, pointCount);
    }

    
    // Position getter/setter
    public double getPosition() {
        return this.position;
    }

    public void setPosition(double position) {
        this.position = position;
    }

}
