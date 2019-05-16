package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

// MainWindow essentially doubles as the game engine

/**
 *
 * @author Infernous
 */
public class MainWindow extends JFrame implements ActionListener, KeyListener
{
    private long lastTick;
    private Timer timer;
    private Character player;
    
    public MainWindow()
    {
        // todo: load textures
        // todo: load UI
        
        this.addKeyListener(this);
        
        // Initialize player before starting the ticks
        this.player = new Character(new Point(0, 0));
        this.add(this.player);
        
        // ~60 game ticks per second
        Timer timer = new Timer(18, this); // For 60 tick rate updates
        timer.setActionCommand("tick");
        timer.start();
        
        this.lastTick = System.nanoTime() / 1000000;
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1200, 800);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        
        if (command.equals("tick"))
        {
            long currentTime = System.nanoTime() / 1000000;
            int dTime = (int) (currentTime - this.lastTick); // Divide by 1,000,000 to convert to miliseconds
            this.lastTick = currentTime;
            
            this.player.update(18); // todo: change to dTime that doesn't have a large amount of time passed since program start? (find best place to set lastTick
            
//            this.player.revalidate();
            // todo: keep obstacles stored in ArrayList (that way oldest is always first in array and when removed everything automatically shifts))
            // todo: generate/remove moving obstacles based on position
            // todo: check collision detection
            
            // Update for next tick
            // Doing it like this for now because physics starts from the very second the program is opened using System.nanoTime()
        }
    }
    
    // Must implement all key event methods even if we only use one.
    @Override
    public void keyPressed(KeyEvent event)
    {
        int keyCode = event.getKeyCode();
        
        if (keyCode == KeyEvent.VK_SPACE)
        {
            this.player.jump();
        System.out.println("Jump");
        }
    }

    @Override
    public void keyTyped(KeyEvent event)
    {
        return;
    }

    @Override
    public void keyReleased(KeyEvent event)
    {
        return;
    }
}
