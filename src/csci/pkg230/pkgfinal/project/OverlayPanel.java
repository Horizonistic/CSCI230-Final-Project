package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

// Shows overlay with a string.
public class OverlayPanel extends JPanel {

    Graphics2D graphics;
    
    private String text;
    
    
    public OverlayPanel(String text) {
        this.text = text;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics = (Graphics2D) g;
        
        drawText();
    }
    
    private void drawText() {
        graphics.drawString(text, (int)(getWidth() * 0.5), (int)(getHeight() * 0.5));
    }
    
}
