package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.JPanel;

// todo: implement abstract interface for hitboxes?

// Responsible for a graphical asset and its associated hitbox.
public class Entity extends JPanel
{
    protected final float MAX_X_VELOCITY = 10.0f;
    
    // Subject to change
    public enum Type {
        PLAYER,
        PIPE,
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
        this.loadTextureFromType(type);
        this.loadHitboxFromType(type);
        
        this.position = position;
        this.type = type;
    }
    
    // For such a small project let's just hard-code the image locations into the code
    private void loadTextureFromType(Type type)
    {
        // todo: add textures to load
        switch (type)
        {
            case PLAYER:
                this.texture = new Texture("images/horse.png", new Rectangle(0, 0, 200, 200));
                this.add(this.texture);
                break;
            
            case PIPE:
                break;
                
            case GROUND:
                break;
            
            case BACKGROUND:
                break;
        }
    }
    
    // Let's also do the same for the hitboxes
    private void loadHitboxFromType(Type type)
    {
        // todo: add appropriate shape for each type's htibox
        switch (type)
        {
            case PLAYER:
                this.hitbox = new Rectangle(0, 0, 200, 200);
                break;
            
            case PIPE:
                break;
                
            case GROUND:
                break;
            
            case BACKGROUND:
                break;
        }
    }
    
    public void update(int dTime)
    {
        this.texture.revalidate(); // Tells AWT to redraw the JPanel
        this.texture.redrawHitbox(this.hitbox); // Redraws the hitbox
        
        // todo: check type and update position accordingly
    }
    
    public Texture getTexture()
    {
        return this.texture;
    }
}