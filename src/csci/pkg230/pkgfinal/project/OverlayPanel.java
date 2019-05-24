package csci.pkg230.pkgfinal.project;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Rectangle;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
    Shows overlay with a JLabel
*/
public class OverlayPanel extends JPanel {

    private Rectangle bounds;
    
    private JLabel label;
    
    
    public OverlayPanel(String text, Font font, Rectangle bounds) {
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
        
        label.setText(text);
        validate();
    }
    
    public void toggleOverlay() {
        if (this.isVisible()) {
            this.setVisible(false);
        }
        else
        {
            this.setVisible(true);
        }
    }
    
}
