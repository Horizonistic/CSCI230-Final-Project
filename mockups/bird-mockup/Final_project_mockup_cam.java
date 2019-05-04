/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package final_project_mockup_cam;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author camgrigoriadis
 */
public class Final_project_mockup_cam {
    
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        
        frame.setSize(640, 480);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JComponent birdComponent = new BirdEntity();
                
        frame.add(birdComponent);
        
        frame.setVisible(true);
    }
    
}
