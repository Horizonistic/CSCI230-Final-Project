package csci.pkg230.pkgfinal.project;

import java.awt.Polygon;
import java.awt.Shape;

/**
 * Responsible for a graphical asset and its associated hitbox.
 * 
 * @author Jacob
 */
public class Entity
{
    protected Object texture;
    protected Shape hitbox;
    
    public Entity()
    {
        this.texture = new Object();
        this.hitbox = new Polygon();
    }
    
    public Entity(String texture, Shape hitbox)
    {
        // todo: figure out assets; pictures?  made of Graphic2D.draw's?  If made of shapes load from file?
        // todo: load from file for hitbox shape?
    }
    
}
