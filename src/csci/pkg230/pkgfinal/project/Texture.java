/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csci.pkg230.pkgfinal.project;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
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
        private Rectangle shape;
    
        public Texture(String imageURI, Shape shape)
        {
            this.setLayout(null);
            this.shape = new Rectangle(0, 0, ((Rectangle) shape).width, ((Rectangle) shape).height);
            try {
//                this.image = ImageIO.read(new URL(imageURI)); // For getting from URLs
                this.image = ImageIO.read(getClass().getResource(imageURI)); // For local files
                
                ImageIcon imageIcon = new ImageIcon(image);
                this.label.setIcon(imageIcon);
                this.add(label);
                this.resizeImage(this.shape.width, this.shape.height);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
        /*
        *   Width and height are passed as percentages;
        *   width = 150 means the axis is scaled 150%
        */
        public void resizeImage(float width, float height)
        {
            // Get current dimensions
            float x = this.label.getIcon().getIconWidth();
            float y = this.label.getIcon().getIconHeight();
            
            // Scale to new dimensions
            int newWidth = (int) (x * (width / 100.0f));
            int newHeight = (int) (y * (height / 100.0f));
            
            // Scale rectangle for bounds setting
            this.shape.width = newWidth;
            this.shape.height = newHeight;
            
            // Make new scaled imageIcon from current
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_DEFAULT));
            this.label.setIcon(imageIcon);
            this.label.setBounds(shape);
            this.label.setBounds(this.shape);
            this.setBounds(this.shape);
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
            
            System.out.println(this.shape);
            // Scale rectangle for bounds setting
            this.shape.width = newWidth;
            this.shape.height = newHeight;
            System.out.println(this.shape);
            
            // Make new scaled imageIcon from current
            ImageIcon imageIcon = new ImageIcon(image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH));
            this.label.setIcon(imageIcon);
            this.label.setBounds(shape);
        }
    }