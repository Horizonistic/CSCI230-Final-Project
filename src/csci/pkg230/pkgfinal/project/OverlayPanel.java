package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JPanel;

// Shows overlay with a string.
public class OverlayPanel extends JPanel {

    Graphics2D graphics;
    
    private String text;
    
    private Rectangle bounds;
    
    
    public OverlayPanel(String text, Rectangle bounds) {
        this.text = text;
        this.bounds = bounds;
        this.setBounds(bounds);
        setOpaque(false);
        
        this.setVisible(false);
    }
    
    public void updateText(String text) {
        this.text = text;
    }
    
    public void toggleOverlay()
    {
        if (this.isVisible()) {
            System.out.println("Hiding");
            this.setVisible(false);
        }
        else
        {
            System.out.println("Showing");
            this.setVisible(true);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics = (Graphics2D) g;
        
        drawText();
    }
    
    private void drawText() {
//        graphics.drawString(text, this.bounds.x, this.bounds.y);
        graphics.drawString(text, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
    }
    
}
