package csci.pkg230.pkgfinal.project;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 * 
 * @author Jacob
 */
public class MainWindow extends JFrame implements ActionListener
{
    private Character player;
    
    public MainWindow()
    {
        // todo: load data (textures, hitboxes) from files
        // todo: load UI
        
        // Ideally graphical updates and game ticks would be independent but it's more work
        Timer timer = new Timer(18, this); // For 60 tick rate updates
        timer.setActionCommand("tick");
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        
        if (command.equals("tick"))
        {
            // todo: process physics
            // todo: check collision detection
        }
    }
}
