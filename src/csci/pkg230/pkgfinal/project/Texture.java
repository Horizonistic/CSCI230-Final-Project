/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg230.pkgfinal.project;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Shape;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Texture extends JPanel
    {
        private JLabel label = new JLabel();
        private Image image;
        private Shape shape;
    
        public Texture(String imageURI, Shape shape)
        {
            this.shape = shape;
            try {
//                this.image = ImageIO.read(new URL(imageURI)); // For getting from URLs
                this.image = ImageIO.read(getClass().getResource(imageURI)); // For local files
                
                ImageIcon imageIcon = new ImageIcon(image);
                this.label.setIcon(imageIcon);
                this.add(label);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        /*
        *   Width and height are passed as percetages;
        *   width = 150 means the axis is scaled 150%
        */
        public void resizeImage(float width, float height)
        {
            // Get current dimensions
            float x = this.label.getIcon().getIconWidth();
            float y = this.label.getIcon().getIconHeight();
            
            // Scale to new dimensions
            int newWidth = (int) (x * (width / 100.0));
            int newHeight = (int) (y * (height / 100.0));
            
            // Make new scaled imageIcon from current
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
            label.setIcon(imageIcon);
        }
        
        /*
        * Scale to a specified height keeping axes locked
        */
        public void resizeImage(int height)
        {
            // Get current dimensions
            float x = this.label.getIcon().getIconWidth();
            float y = this.label.getIcon().getIconHeight();
            
            float scale = height / y;
            float aspectRatio = x / y;
            
            // Calculate new dimensions
            int newWidth = (int) (x * aspectRatio * scale);
            int newHeight = (int) (y * scale);
            
            // Make new scaled imageIcon from current
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            label.setIcon(imageIcon);
        }
        
        // Temporary hack to see hitboxes
        public void redrawHitbox(Shape shape)
        {
            this.shape = shape;
            this.repaint();
        }
        
        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);
            
            Graphics2D g2 = (Graphics2D) g;
            g2.draw(this.shape);
        }
        //
    }