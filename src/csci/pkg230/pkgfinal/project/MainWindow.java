package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

// MainWindow essentially doubles as the game engine
public class MainWindow extends JFrame implements ActionListener, KeyListener
{
    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    
    private static final int TOP_OBSTACLE_START = 0;
    private static final int BOTTOM_OBSTACLE_START = 300;
    
    private boolean isRunning = false;
    private int timeSinceLastSpawn = 0;

    private long lastTick;
    private Timer timer;
    private Character player;
    private Entity ground;
    private ArrayList<Entity> obstacles = new ArrayList<>();
    private JLabel scoreDisplay;

    private static final String TICK_COMMAND = "tick";
    
    
    private Game game = new Game();

    // State
    public enum State {
        READY,
        IN_PROGRESS,
        PAUSED,
        GAME_OVER
    };

    private State state = State.READY;
    

    public MainWindow() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Setup JFrame before adding entities so we can access things like getHeight() for the background
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setVisible(true);
        this.setResizable(false);
        
        // Must set layout to null for absolute positioning
        // When using null layouts you MUST use setBounds for it to show up
        // https://docs.oracle.com/javase/tutorial/uiswing/layout/none.html
        this.setLayout(null);

        this.addKeyListener(this);

        // Setup scene
        this.setupBackground(); // Add background first and add everything else on top
        this.setupPlayer(); // Initialize player before starting the ticks
        this.setupGround();
        this.setupInstructions(); // Overlay instructions
        this.setupScore();
        
        // Overlay instructions
        this.setupInstructions();
        
        // ~60 game ticks per second
        this.timer = new Timer(18, this); // For 60 tick rate updates
        this.timer.setActionCommand(TICK_COMMAND);

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.pack();
        this.repaint();
    }

    // State
    public void moveTo(State newState) {
        switch (newState) {
            case READY:
                break;
                
            case IN_PROGRESS:
                break;
                
            case PAUSED:
                break;

            case GAME_OVER:
                break;

            default:
                break;
        }
    }

    // Scene Setup
    private void setupPlayer() {
        Point startPoint = new Point(100, 0);
        
        this.player = new Character(startPoint);
        this.getContentPane().add(this.player);
    }

    private void setupGround() {
        this.ground = new Entity(Entity.Type.GROUND, new Point(0, WINDOW_HEIGHT - 50));
        this.getContentPane().add(this.ground);
    }

    private void setupBackground() {
        ScrollingBackdrop backdrop = new ScrollingBackdrop(this.getWidth(), this.getHeight(), new Color(242, 255, 253), new Color(125, 198, 224));

        add(backdrop);
    }

    private void setupInstructions() {
//        JLabel label = new JLabel("Press SPACE to begin…");
//        
//        Font font = new Font("Menlo", 120);
//        
//        label.setFont(font);
    }

    private void setupScore()
    {
        this.scoreDisplay = new JLabel("0", SwingConstants.RIGHT);
        this.scoreDisplay.setFont(new Font("Helvetica", Font.BOLD, 64));
        this.scoreDisplay.setBounds(WINDOW_WIDTH - 125, 0, 100, 100);
        this.getContentPane().add(this.scoreDisplay);
    }

    // Event Handlers
    // Timer Events
    @Override
    public void actionPerformed(ActionEvent event) {
        if (!isRunning) {
            this.timer.stop();
            return;
        }
        String command = event.getActionCommand();

        if (command.equals(TICK_COMMAND)) {
            update();
        }
    }

    // Key Events
    // Must implement all key event methods even if we only use one.
    @Override
    public void keyPressed(KeyEvent event) {
        int keyCode = event.getKeyCode();

        if (keyCode == KeyEvent.VK_SPACE) {
            if (this.isRunning) {
                this.player.jump();

            } else {
                this.startGame();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
        return;
    }

    @Override
    public void keyReleased(KeyEvent event) {
        return;
    }

    // Game State
    private void startGame() {
        this.timer.start();
        this.isRunning = true;
        this.lastTick = System.nanoTime() / 1000000;
    }

    private void update() {
        // Delta time updates            
        long currentTime = System.nanoTime() / 1000000;
        int dTime = (int) (currentTime - this.lastTick); // Divide by 1,000,000 to convert to miliseconds
        this.lastTick = currentTime;

        this.player.update(dTime);
        for (Entity obstacle : this.obstacles) {
            obstacle.update(dTime);
        }
//            this.ground.update(dTime);

        // Collision detection
        if (this.player.getBounds().intersects(this.ground.getBounds())) {
            this.isRunning = false;
        }
        if (!this.obstacles.isEmpty()) {
            for (Entity obstacle : this.obstacles) {
                if (this.player.getBounds().intersects(obstacle.getBounds())) {
                    this.isRunning = false;
                }
            }
        }

        //
        // Spawning and despawning obstacles            
        //
        this.timeSinceLastSpawn += dTime;
        
        if (this.timeSinceLastSpawn > 2000) {
            // Spawn a new top and bottom obstacle after a set amount of time
            Random random = new Random();

            // So we can keep the Y distance between the obstacles the same
            int randomHeight = random.nextInt(400);

            // Bottom obstacle
            Point bottomPosition = new Point(WINDOW_WIDTH - 1, randomHeight + BOTTOM_OBSTACLE_START);
            Entity bottomObstacle = new Entity(Entity.Type.OBSTACLE, bottomPosition);
            this.obstacles.add(bottomObstacle);
            this.getContentPane().add(bottomObstacle);

            // Top obstacle
            Point topPosition = new Point(WINDOW_WIDTH - 1, randomHeight + TOP_OBSTACLE_START - Entity.Dimensions.OBSTACLE_HEIGHT);
            Entity topObstacle = new Entity(Entity.Type.OBSTACLE, topPosition);
            this.obstacles.add(topObstacle);
            this.getContentPane().add(topObstacle);

            this.timeSinceLastSpawn = 0;
        }

        if (!this.obstacles.isEmpty()) {
            // Detect if any obstacles are no longer visible and remove them if they are
            ArrayList<Entity> toRemove = new ArrayList<>();
            for (Entity obstacle : this.obstacles) {
                if (!obstacle.getBounds().intersects(this.getContentPane().getBounds())) {
                    toRemove.add(obstacle);
                }
            }
            if (!toRemove.isEmpty()) {
                this.game.addPoint();
                this.scoreDisplay.setText(String.valueOf(this.game.getScore()));
                this.obstacles.removeAll(toRemove); // To avoid ConcurrentModificationException
            }
        }
    }
}
