package csci.pkg230.pkgfinal.project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Shows overlay with a string.
public class OverlayPanel extends JPanel {

    // Graphics2D graphics;
    
    private String text;
    
    private Rectangle bounds;
    
    private JLabel label;
    
    
    public OverlayPanel(String text, Font font, Rectangle bounds) {
        this.text = text;
        this.bounds = bounds;
        
        this.setBounds(bounds);
        setOpaque(false);
        
        this.setLayout(new BorderLayout());
        
        label = new JLabel();
        
        label.setText(text);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(font);
        
        this.add(label, BorderLayout.CENTER);
        validate();
        
        this.setVisible(false);
    }
    
    public void updateText(String text) {
        this.text = text;
        
        label.setText(text);
        validate();
    }
    
    public void toggleOverlay() {
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
    
}
