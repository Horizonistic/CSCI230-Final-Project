package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

// todo: implement abstract interface for hitboxes?

// Responsible for a graphical asset and its associated hitbox.
public class Entity extends JPanel
{
    protected final int OBSTACLE_MOVEMENT_VELOCITY = 4;
    
    public class Dimensions
    {
        public static final int PLAYER_WIDTH = 100;
        public static final int PLAYER_HEIGHT = 100;
        
        public static final int OBSTACLE_WIDTH = 100;
        public static final int OBSTACLE_HEIGHT = 1000;
        
        public static final int GROUND_WIDTH = MainWindow.WINDOW_WIDTH;
        public static final int GROUND_HEIGHT = 50;
    }
    
    public enum Type {
        PLAYER,
        OBSTACLE,
        GROUND,
        BACKGROUND
    };
    
    protected Type type;
    protected Texture texture;
    protected Rectangle hitbox; // Rectangle for now for ease of use, probably forever
    public Point position; // Public to avoid getters/settings for hitbox
    
    // For this project we won't ever need a texture-less entity
    public Entity(Type type, Point position)
    {
        this.setLayout(null);
        
        this.position = position;
        this.type = type;
        
        this.loadTextureFromType(type);
        this.loadHitboxFromType(type);
        
        this.setBounds(this.hitbox);
    }
    
    // For such a small project let's just hard-code the image locations into the code
    private void loadTextureFromType(Type type)
    {
        // todo: add textures to load
        switch (type)
        {
            case PLAYER:
                this.texture = new Texture("images/player.png", new Rectangle(this.position.x, this.position.y, Dimensions.PLAYER_WIDTH, Dimensions.PLAYER_HEIGHT));
                this.add(this.texture);
                break;
            
            case OBSTACLE:
                this.texture = new Texture("images/obstacle.png", new Rectangle(this.position.x, this.position.y, Dimensions.OBSTACLE_WIDTH, Dimensions.OBSTACLE_HEIGHT));
                this.add(this.texture);
                break;
                
            case GROUND:
                this.texture = new Texture("images/ground_temp.png", new Rectangle(this.position.x, this.position.y, Dimensions.GROUND_WIDTH, Dimensions.GROUND_HEIGHT));
                this.add(this.texture);
                break;
            
            case BACKGROUND:
                break;
        }
    }
    
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
                break;
        }
    }
    
    public void update(int dTime)
    {
        this.revalidate(); // Tells AWT to redraw the JPanel
//        this.texture.redrawHitbox(this.hitbox); // Redraws the hitbox
        
        // todo: check type and update position accordingly
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
        }
    }
    
    public Texture getTexture()
    {
        return this.texture;
    }
}