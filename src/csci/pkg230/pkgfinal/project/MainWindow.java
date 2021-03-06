package csci.pkg230.pkgfinal.project;

import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.Timer;

// MainWindow essentially doubles as the game engine
public class MainWindow extends JFrame implements ActionListener, KeyListener {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;

    private static final int TOP_OBSTACLE_START = 0;
    private static final int BOTTOM_OBSTACLE_START = 300; // This is also the distance between a set of top and bottom obstacles

    private static final int TIME_BETWEEN_OBSTACLES = 2000; // Frequency of obstacle appearance
    private static final int OBSTACLE_HEIGHT_VARIANCE = 400; // The deviance of the opening of the obstacles

    private static final String INSTRUCTIONS_HTML = "<html><h1 style=\"font-size: 1em;\">Press <span style=\"font-size: 1.1em; font-style: bold; color: rgb(15, 139, 153)\"><i>SPACE</i></span> to begin…</h1></html>";
    private static final String SCORE_HTML = "<html><h1 style=\"text-shadow: 2px 2px 5px gray;\">Score: %d</h1></html>";
    private static final String PAUSED_HTML = "<html><center><h1 style=\"font-size: 1.5em;\">PAUSED</h1><h4 style=\"font-size:0.9em;\">Press <span style=\"font-size: 1.1em; font-style: bold; color: rgb(15, 139, 153)\"><i>SPACE</i></span> to continue.</h4></center></html>";
    private static final String GAME_OVER_HTML = "<html><center><h1 style=\"font-size: 1.5em;\">GAME OVER</h1>"
                + "<h2 style=\"font-size:1.1em;\">Final Score: <span style=\"color:%s; text-shadow: 2px 2px 5px gray;\">%d</span>"
                + "</h2><h4style=\"font-size: 0.5em;\">Press <span style=\"font-size: 1em; font-style: bold; color: rgb(255, 84, 0)\"><i>R</i></span> to try again.</h4></center></html>";

    private boolean isRunning = false;
    private int timeSinceLastSpawn = 0;

    private long lastTick;
    private Timer timer;
    private Character player;
    private Entity ground;
    private ArrayList<Entity> obstacles = new ArrayList<>();
    private HashMap<State, OverlayPanel> overlays = new HashMap<>();

    private static final String TICK_COMMAND = "tick";

    private Game game = new Game();

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
        this.pack();
        this.repaint();
    }

    /*
        Initializes timer
     */
    private void setupTimer() {
        // ~60 game ticks per second
        this.timer = new Timer(18, this); // For 60 tick rate updates
        this.timer.setActionCommand(TICK_COMMAND);
    }

    // States
    /*
        Switches game state and updates UI accordingly
     */
    public void moveTo(State newState) {
        if (newState == State.NONE || state == newState) {
            return;
        }

        if (state == State.NONE) {
            this.overlays.get(newState).toggleOverlay();
        } else if (state == State.GAME_OVER && newState == State.PAUSED) {
            return;
            
        } else {
            this.overlays.get(this.state).toggleOverlay();
            this.overlays.get(newState).toggleOverlay();
        }

        switch (newState) {
            case READY:
                this.isRunning = false;
                break;

            case IN_PROGRESS:
                this.isRunning = true;

                break;

            case PAUSED:
                this.isRunning = false;

                break;

            case GAME_OVER:
                this.isRunning = false;
                this.overlays.get(State.GAME_OVER).updateText(gameOverText(this.game.getScore()));

                break;

            default:
                break;
        }

        state = newState;
    }

    private String gameOverText(int score) {
        String color = "rgb(66, 244, 113)";

        return String.format(GAME_OVER_HTML, color, score);
    }

    /*
        Initializes all entities and UI elements
     */
    private void setupScene() {
        this.setupPlayer(); // Initialize player before starting the ticks
        this.setupGround();
        this.setupOverlays();
        this.setupBackground(); // Add background last so it's z-order is as high as possible
    }

    /*
        Initializes the player entity
     */
    private void setupPlayer() {
        Point startPoint = new Point(100, 0);

        this.player = new Character(startPoint);
        this.getContentPane().add(this.player);
    }

    /*
        Initializes the ground entity
     */
    private void setupGround() {
        this.ground = new Entity(Entity.Type.GROUND, new Point(0, WINDOW_HEIGHT - 50));
        this.getContentPane().add(this.ground);
        this.getContentPane().setComponentZOrder(this.ground, 0);
    }

    /*
        Initializes the background
     */
    private void setupBackground() {
        Entity backdrop = new Entity(Entity.Type.BACKGROUND, new Point(0, 0));

        this.getContentPane().add(backdrop);
        this.getContentPane().setComponentZOrder(backdrop, this.getContentPane().getComponentCount() - 1);
    }

    // Overlays
    /*
        Initializes the UI overlays and stores them in a HashMap
     */
    private void setupOverlays() {
        OverlayPanel overlay = new OverlayPanel(INSTRUCTIONS_HTML, GameUIFonts.headline, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
        this.overlays.put(State.READY, overlay);
        this.getContentPane().add(overlay);
        this.getContentPane().setComponentZOrder(overlay, 2);

        overlay = new OverlayPanel(String.format(SCORE_HTML, game.getScore()), GameUIFonts.headline, new Rectangle(0, 0, 300, 100));
        this.overlays.put(State.IN_PROGRESS, overlay);
        this.getContentPane().add(overlay);
        this.getContentPane().setComponentZOrder(overlay, 2);

        overlay = new OverlayPanel(PAUSED_HTML, GameUIFonts.headline, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
        this.overlays.put(State.PAUSED, overlay);
        this.getContentPane().add(overlay);
        this.getContentPane().setComponentZOrder(overlay, 2);

        overlay = new OverlayPanel("", GameUIFonts.headline, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
        this.overlays.put(State.GAME_OVER, overlay);
        this.getContentPane().add(overlay);
        this.getContentPane().setComponentZOrder(overlay, 2);

        if (this.state != State.NONE) {
            this.overlays.get(this.state).toggleOverlay();
        }
    }

    // Event handlers
    /*
        Timer events
     */
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
                    break;

                default:
                    break;
            }
        } else if (keyCode == KeyEvent.VK_P) { // Pause
            this.moveTo(State.PAUSED);

        } else if (keyCode == KeyEvent.VK_Q) { // Quit
            quitGame();

        } else if (keyCode == KeyEvent.VK_R) { // Reset
            if (state == State.GAME_OVER) {
                this.reset();
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
        if (!this.timer.isRunning()) {
            this.timer.start();
        }
        this.lastTick = System.nanoTime() / 1000000;
        this.moveTo(State.IN_PROGRESS);
    }

    private void quitGame() {
        System.exit(0);
    }

    /*
        Removes all obstacles and resets player back to the starting position
     */
    private void reset() {
        this.game.resetScore();
        this.obstacles.clear();
        this.getContentPane().removeAll();
        this.setupScene();
        this.moveTo(State.READY);
    }

    /*
        Processes game physics and collision detection, as well as obstacle spawning and despawning
     */
    private void update() {
        // Delta time updates            
        long currentTime = System.nanoTime() / 1000000;
        int dTime = (int) (currentTime - this.lastTick); // Divide by 1,000,000 to convert to miliseconds
        this.lastTick = currentTime;

        // Update positions
        this.player.update(dTime);
        for (Entity obstacle : this.obstacles) {
            obstacle.update(dTime);
        }

        // Collision detection
        if (this.player.getBounds().intersects(this.ground.getBounds())) {
            this.moveTo(State.GAME_OVER);

            return; // No longer need to check obstacles
        }
        if (!this.obstacles.isEmpty()) {
            for (Entity obstacle : this.obstacles) {
                if (this.player.getBounds().intersects(obstacle.getBounds())) {
                    this.moveTo(State.GAME_OVER);
                    return; // No longer need to check obstacles
                }
            }
        }

        // Spawning and despawning obstacles
        this.timeSinceLastSpawn += dTime;

        if (this.timeSinceLastSpawn > TIME_BETWEEN_OBSTACLES) {
            // Spawn a new top and bottom obstacle after a set amount of time
            Random random = new Random();

            // So we can keep the Y distance between the obstacles the same
            int randomHeight = random.nextInt(OBSTACLE_HEIGHT_VARIANCE);

            // Bottom obstacle
            Point bottomPosition = new Point(WINDOW_WIDTH - 1, randomHeight + BOTTOM_OBSTACLE_START);
            Entity bottomObstacle = new Entity(Entity.Type.OBSTACLE, bottomPosition);
            this.obstacles.add(bottomObstacle);
            this.getContentPane().add(bottomObstacle);
            this.getContentPane().setComponentZOrder(bottomObstacle, this.getContentPane().getComponentCount() - 2);

            // Top obstacle
            Point topPosition = new Point(WINDOW_WIDTH - 1, randomHeight + TOP_OBSTACLE_START - Entity.Dimensions.OBSTACLE_HEIGHT);
            Entity topObstacle = new Entity(Entity.Type.OBSTACLE, topPosition);
            this.obstacles.add(topObstacle);
            this.getContentPane().add(topObstacle);
            this.getContentPane().setComponentZOrder(topObstacle, this.getContentPane().getComponentCount() - 2);

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
            // If there is an obstacle to remove then remove it and update the score
            if (!toRemove.isEmpty()) {
                this.game.addPoint();
                this.overlays.get(State.IN_PROGRESS).updateText(String.format(SCORE_HTML, game.getScore()));
                this.obstacles.removeAll(toRemove); // To avoid ConcurrentModificationException
            }
        }
    }
}
