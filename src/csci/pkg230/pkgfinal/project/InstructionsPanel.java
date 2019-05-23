package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Shows game instructions.
public class InstructionsPanel extends JPanel {

    Graphics2D graphics;
    
    private String instructions = "Press SPACE to begin…";
    
    
    public InstructionsPanel() {}

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        graphics = (Graphics2D) g;
        
        drawInstructions();
    }
    
    private void drawInstructions() {
        graphics.drawString(TOOL_TIP_TEXT_KEY, WIDTH, WIDTH);
    }
    
}
