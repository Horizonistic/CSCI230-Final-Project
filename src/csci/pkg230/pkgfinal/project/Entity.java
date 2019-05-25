package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

/*
    Responsible for a sprite and its associated hitbox and render lcoation
*/
public class Entity extends JPanel
{
    protected final int OBSTACLE_MOVEMENT_VELOCITY = 6;
    
    public class Dimensions
    {
        public static final int PLAYER_WIDTH = 100;
        public static final int PLAYER_HEIGHT = 100;
        
        public static final int OBSTACLE_WIDTH = 100;
        public static final int OBSTACLE_HEIGHT = 1000;
        
        public static final int GROUND_WIDTH = MainWindow.WINDOW_WIDTH;
        public static final int GROUND_HEIGHT = 50;
        
        public static final int BACKGROUND_WIDTH = MainWindow.WINDOW_WIDTH;
        public static final int BACKGROUND_HEIGHT = MainWindow.WINDOW_HEIGHT;
    }
    
    // todo: remove background?  if we don't use an image for it
    public enum Type {
        PLAYER,
        OBSTACLE,
        GROUND,
        BACKGROUND
    };
    
    protected Type type;
    protected Sprite texture;
    protected Rectangle hitbox;
    public Point position; // Public to avoid getters/settings for hitbox
    
    // For this project we won't ever need a texture-less entity
    public Entity(Type type, Point position)
    {
        this.setLayout(null);
        
        this.position = position;
        this.type = type;
        
        this.loadTextureFromType(type);
        this.loadHitboxFromType(type);
        
        if (hitbox != null) {
            this.setBounds(this.hitbox);
        }
    }
    
    /*
        Loads a sprite based on the Entity type
    */
    private void loadTextureFromType(Type type)
    {
        // For such a small project let's just hard-code the image locations into the code
        switch (type)
        {
            case PLAYER:
                this.texture = new Sprite("images/player.png", new Rectangle(this.position.x, this.position.y, Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT));
                this.add(this.texture);
                break;
            
            case OBSTACLE:
                this.texture = new Sprite("images/obstacle.png", new Rectangle(this.position.x, this.position.y, Dimensions.OBSTACLE_WIDTH, Dimensions.OBSTACLE_HEIGHT));
                this.add(this.texture);
                break;
                
            case GROUND:
                this.texture = new Sprite("images/ground_temp.png", new Rectangle(this.position.x, this.position.y, Dimensions.GROUND_WIDTH, Dimensions.GROUND_HEIGHT));
                this.add(this.texture);
                break;
            
            case BACKGROUND:
                this.texture = new Sprite("images/background.png", new Rectangle(this.position.x, this.position.y, Dimensions.BACKGROUND_WIDTH, Dimensions.BACKGROUND_HEIGHT));
                this.add(this.texture);
                break;
        }
    }
    
    /*
        Creates a rectangle based on the Entity type to use as the hitbox
    */
    private void loadHitboxFromType(Type type)
    {
        switch (type)
        {
            case PLAYER:
                this.hitbox = new Rectangle(this.position.x, this.position.y, Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT);
                break;
            
            case OBSTACLE:
                this.hitbox = new Rectangle(this.position.x, this.position.y, Dimensions.OBSTACLE_WIDTH, Dimensions.OBSTACLE_HEIGHT);
                break;
                
            case GROUND:
                this.hitbox = new Rectangle(this.position.x, this.position.y, Dimensions.GROUND_WIDTH, Dimensions.GROUND_HEIGHT);
                break;
            
            case BACKGROUND:
                this.hitbox = new Rectangle(this.position.x, this.position.y, Dimensions.BACKGROUND_WIDTH, Dimensions.BACKGROUND_HEIGHT);
                break;
        }
    }
    
    /*
        Updates Entity location based on delta time and Entity type
    */
    public void update(int dTime)
    {
        this.revalidate(); // Tells AWT to redraw the JPanel
        
        switch (type)
        {
            case PLAYER:
                break;
            
            case OBSTACLE:
                this.position.x -= OBSTACLE_MOVEMENT_VELOCITY;
                this.setLocation(this.position);
                break;
                
            case GROUND:
                break;
            
            case BACKGROUND:
                break;
                
            default:
                break;
        }
    }
}