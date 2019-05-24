package csci.pkg230.pkgfinal.project;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
    A basic class to display an image
*/
public class Sprite extends JPanel
{
    private JLabel label = new JLabel();
    private Image image;
    private Rectangle shape;

    public Sprite(String imageURI, Shape shape)
    {
        this.setLayout(null);
        this.shape = new Rectangle(0, 0, ((Rectangle) shape).width, ((Rectangle) shape).height);
        try {
            this.image = ImageIO.read(getClass().getResource(imageURI)); // For local files

            ImageIcon imageIcon = new ImageIcon(image);
            this.label.setIcon(imageIcon);
            
            this.add(label);
            
            this.label.setBounds(this.shape);
            this.setBounds(this.shape);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}