package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

// todo: implement abstract interface for hitboxes?

// Responsible for a graphical asset and its associated hitbox.
public class Entity extends JPanel
{
    protected final float MAX_X_VELOCITY = 10.0f;
    protected final int OBSTACLE_MOVEMENT_VELOCITY = 4;
    
    // Subject to change
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
                this.texture = new Texture("images/player.png", new Rectangle(this.position.x, this.position.y, 200, 200));
                this.add(this.texture);
                break;
            
            case OBSTACLE:
                this.texture = new Texture("images/obstacle.png", new Rectangle(this.position.x, this.position.y, 100, Math.abs(this.position.y - MainWindow.WINDOW_HEIGHT)));
                this.add(this.texture);
                break;
                
            case GROUND:
                this.texture = new Texture("images/ground_temp.png", new Rectangle(this.position.x, this.position.y, MainWindow.WINDOW_WIDTH, 50));
                this.add(this.texture);
                break;
            
            case BACKGROUND:
                break;
        }
    }
    
    // Let's also do the same for the hitboxes
    // Values are hard-coded for sake of laziness
    private void loadHitboxFromType(Type type)
    {
        // todo: add appropriate shape for each type's htibox
        switch (type)
        {
            case PLAYER:
                this.hitbox = new Rectangle(this.position.x, this.position.y, 200, 200);
                break;
            
            case OBSTACLE:
                this.hitbox = new Rectangle(this.position.x, this.position.y, 100, Math.abs(this.position.y - MainWindow.WINDOW_HEIGHT));
                break;
                
            case GROUND:
                this.hitbox = new Rectangle(this.position.x, this.position.y, MainWindow.WINDOW_WIDTH, 50);
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