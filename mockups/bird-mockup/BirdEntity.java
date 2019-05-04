package final_project_mockup_cam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;

public class BirdEntity extends JComponent {

    class Colors {

        Color bodyColor;
        Color accentColor;

        Colors(Color bodyColor, Color accentColor) {
            this.bodyColor = bodyColor;
            this.accentColor = accentColor;
        }
    }
    
    Graphics2D g;
    
    final Colors DEFAULT_COLORS = new Colors(Color.red, Color.yellow);
    
    Colors colors = DEFAULT_COLORS;

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        this.g = (Graphics2D)g;

        drawBird(100, 50);
    }

    private void drawBird(float x, float y) {
        // Body
        drawBody(x, y);

        // Beak
        int beakX = (int) x + 280;
        int beakY = (int) y + 100;
        
        drawBeak(beakX, beakY);
        
        // Eye
        int eyeX = (int) x + 160;
        int eyeY = (int) y + 40;
        
        drawEye(eyeX, eyeY);

        // Wing
        int wingX = (int) x + 30;
        int wingY = (int) y + 160;

        drawWing(wingX, wingY);
    }
    
    private void drawBody(float x, float y) {
        if (g.getColor() != colors.bodyColor)
            g.setColor(colors.bodyColor);

        Ellipse2D shape = new Ellipse2D.Double(x, y, 300, 300);

        g.fill(shape);
    }

    private void drawBeak(int x, int y) {
        if (g.getColor() != colors.accentColor)
            g.setColor(colors.accentColor);
        
        int beakXPoints[] = {x, x + 100, x};
        int beakYPoints[] = {y, y + 50, y + 100};

        g.fillPolygon(beakXPoints, beakYPoints, 3);
    }
    
    private void drawEye(int eyeX, int eyeY) {
        if (g.getColor() != colors.accentColor)
            g.setColor(colors.accentColor);
        
        g.fillOval(eyeX, eyeY, 50, 50);
    }

    private void drawWing(int wingX, int wingY) {
        if (g.getColor() != colors.accentColor)
            g.setColor(colors.accentColor);
        
        int wingXPoints[] = {wingX, wingX + 120, wingX - 100};
        int wingYPoints[] = {wingY, wingY + 80, wingY + 100};

        g.fillPolygon(wingXPoints, wingYPoints, 3);
    }

}
