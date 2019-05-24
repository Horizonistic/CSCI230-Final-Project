package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

// MainWindow essentially doubles as the game engine
public class MainWindow extends JFrame implements ActionListener, KeyListener {

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
    private HashMap<State, OverlayPanel> overlays = new HashMap<>();

    private static final String TICK_COMMAND = "tick";

    private OverlayPanel overlayPanel;

    private Game game = new Game();

    // State
    public enum State {
        NONE,
        READY,
        IN_PROGRESS,
        PAUSED,
        GAME_OVER
    };

    private State state = State.NONE;

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

        setupScene();
        setupTimer();

        moveTo(State.READY);

        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.getContentPane().setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.pack();
        this.repaint();
    }
    
    private void setupTimer() {
        // ~60 game ticks per second
        this.timer = new Timer(18, this); // For 60 tick rate updates
        this.timer.setActionCommand(TICK_COMMAND);
    }

    // State
    public void moveTo(State newState) {
        if (newState == State.NONE || state == newState) {
            return;
        }

        if (state == State.NONE) {
            this.overlays.get(newState).toggleOverlay();
        } else {
            this.overlays.get(this.state).toggleOverlay();
            this.overlays.get(newState).toggleOverlay();
        }

        switch (newState) {
            case READY:
                break;
                
            case IN_PROGRESS:
                this.isRunning = true;

                break;

            case PAUSED:
                this.isRunning = false;

                break;

            case GAME_OVER:
                this.isRunning = false;

                break;

            default:
                break;
        }

        state = newState;
    }

    // Scene Setup
    private void setupScene() {
        this.setupBackground(); // Add background first and add everything else on top
        this.setupPlayer(); // Initialize player before starting the ticks
        this.setupGround();
        this.setupOverlays();
    }

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

        this.getContentPane().add(backdrop);
    }

    // Overlays
    private void setupOverlays() {
        this.overlays.put(State.READY, new OverlayPanel("Press SPACE to begin…", GameUIFonts.headline, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)));
        this.getContentPane().add(this.overlays.get(State.READY));

        this.overlays.put(State.IN_PROGRESS, new OverlayPanel("Score: -", GameUIFonts.headline, new Rectangle(0, 0, 100, 100)));
        this.getContentPane().add(this.overlays.get(State.IN_PROGRESS));

        this.overlays.put(State.PAUSED, new OverlayPanel("PAUSED", GameUIFonts.headline, new Rectangle(0, 0, 100, 100)));
        this.getContentPane().add(this.overlays.get(State.PAUSED));

        this.overlays.put(State.GAME_OVER, new OverlayPanel("GAME OVER\nPress SPACE to try again.", GameUIFonts.headline, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT)));
        this.getContentPane().add(this.overlays.get(State.GAME_OVER));
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
            switch (state) {
                case IN_PROGRESS:
                    this.player.jump();

                    break;
                case READY:
                    this.startGame();

                    break;

                case PAUSED:
                    this.startGame();
                    
                case GAME_OVER:
                    this.reset();

                default:
                    break;
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
        this.lastTick = System.nanoTime() / 1000000;
        this.moveTo(State.IN_PROGRESS);
    }
    
    private void reset() {
        this.removeAll();
        this.setupScene();
        this.timer.restart();
        this.moveTo(State.READY);
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

        // Collision detection
        if (this.player.getBounds().intersects(this.ground.getBounds())) {
            this.moveTo(State.GAME_OVER);
        }
        if (!this.obstacles.isEmpty()) {
            for (Entity obstacle : this.obstacles) {
                if (this.player.getBounds().intersects(obstacle.getBounds())) {
                    this.moveTo(State.GAME_OVER);
                }
            }
        }

        // Spawning and despawning obstacles         
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
//                this.scoreDisplay.setText(String.valueOf(this.game.getScore()));
                this.obstacles.removeAll(toRemove); // To avoid ConcurrentModificationException
            }
        }
    }
}
