package csci.pkg230.pkgfinal.project;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

// MainWindow essentially doubles as the game engine
public class MainWindow extends JFrame implements ActionListener, KeyListener
{
    public static int WINDOW_WIDTH = 1280;
    public static int WINDOW_HEIGHT = 720;
    
    private boolean isRunning = false;
    private int timeSinceLastSpawn = 0;
    
    private long lastTick;
    private Timer timer;
    private Character player;
    private Entity ground;
    private ArrayList<Entity> obstacles = new ArrayList<>();
    
    public MainWindow()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Must set layout to null for absolute positioning
        // When using null layouts you MUST use setBounds for it to show up
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/none.html
        this.setLayout(null);
        
        // todo: load UI
        this.setupInstructions();
        
        this.addKeyListener(this);
        
        // Initialize player before starting the ticks
        this.setupPlayer();
        
        this.ground = new Entity(Entity.Type.GROUND, new Point(0, WINDOW_HEIGHT - 50));
        this.getContentPane().add(this.ground);
        System.out.println(this.ground.getLocation());
        
        // ~60 game ticks per second
        this.timer = new Timer(18, this); // For 60 tick rate updates
        this.timer.setActionCommand("tick");
        
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.pack();
        this.setVisible(true);
        this.setResizable(false);
    }
    
    private void setupPlayer() {
        this.player = new Character(new Point(100, 0));
        this.getContentPane().add(this.player);
    }
    
    private void setupInstructions() {
//        JLabel label = new JLabel("Press SPACE to begin…");
//        
//        Font font = new Font("Menlo", 120);
//        
//        label.setFont(font);
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (!isRunning)
        {
            this.timer.stop();
            return;
        }
        String command = event.getActionCommand();
        
        if (command.equals("tick"))
        {
            // Delta time updates
            long currentTime = System.nanoTime() / 1000000;
            int dTime = (int) (currentTime - this.lastTick); // Divide by 1,000,000 to convert to miliseconds
            this.lastTick = currentTime;
            
            this.player.update(dTime);
            for (Entity obstacle : this.obstacles)
            {
                obstacle.update(dTime);
            }
//            this.ground.update(dTime);
            
            // Collision detection
            if (this.player.getBounds().intersects(this.ground.getBounds()))
            {
                this.isRunning = false;
            }
            if (!this.obstacles.isEmpty())
            {
                for (Entity obstacle : this.obstacles)
                {
                    if (this.player.getBounds().intersects(obstacle.getBounds()))
                    {
                        this.isRunning = false;
                    }
                }
            }
            
            //
            // Spawning and despawning obstacles            
            //
            this.timeSinceLastSpawn += dTime;
            if (this.timeSinceLastSpawn > 2000)
            {
                // Spawn a new obstacle after a set amount of time
                Random random = new Random();
                Point position = new Point(WINDOW_WIDTH - 1, random.nextInt(100) + 300);
                Obstacle obstacle = new Obstacle(Entity.Type.OBSTACLE, position);
                obstacle.revalidate();
                this.obstacles.add(obstacle);
                this.getContentPane().add(obstacle);
                this.timeSinceLastSpawn = 0;
            }
            
            if (!this.obstacles.isEmpty())
            {
                // Detect if any obstacles are no longer visible and remove them if they are
                ArrayList<Entity> toRemove = new ArrayList<>();
                for (Entity obstacle : this.obstacles)
                {
                    if (!obstacle.getBounds().intersects(this.getContentPane().getBounds()))
                    {
                        toRemove.add(obstacle);
                    }
                }
                this.obstacles.removeAll(toRemove); // To avoid ConcurrentModificationException
            }
            
            
        }
    }
    
    private void startGame()
    {
        this.timer.start();
        this.isRunning = true;
        this.lastTick = System.nanoTime() / 1000000;
    }
    
    // Must implement all key event methods even if we only use one.
    @Override
    public void keyPressed(KeyEvent event)
    {
        int keyCode = event.getKeyCode();
        
        if (keyCode == KeyEvent.VK_SPACE && this.isRunning)
        {
            this.player.jump();
        }
        else
        {
            this.startGame();
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
