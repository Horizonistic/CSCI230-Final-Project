package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.awt.geom.Point2D;

public class Character extends Entity
{
    protected final float MAX_Y_VELOCITY = 5.0f;
    protected final float GRAVITY = 0.002f; // Accelerate downwards at 0.1 pixels/tick (6 pixels/second)
    protected final float JUMP_STRENGTH = 0.7f;
    
    // Point is used as a 2D vector so I don't have to recreate one
    protected Point2D.Float velocity;
    
    public Character(Point position)
    {
        super(Entity.Type.PLAYER, position);
        
        this.velocity = new Point2D.Float(0.0f, 0.0f);
    }
    
    @Override
    public void update(int dTime)
    {
        // Apply gravity to velocity
        if (this.velocity.y < MAX_Y_VELOCITY && this.velocity.y > -MAX_Y_VELOCITY)
        {
            this.velocity.y += GRAVITY * dTime;
        }
        else if (this.velocity.y > MAX_Y_VELOCITY)
        {
            this.velocity.y = MAX_Y_VELOCITY;
        }
        else if (this.velocity.y < -MAX_Y_VELOCITY)
        {
            this.velocity.y = -MAX_Y_VELOCITY;
        }
        
        // Only need to worry about the Y axis, player stays static on the X axis
        this.position.y += (int) (this.velocity.y * dTime);
        
        this.setLocation(this.position);
        
        this.hitbox.setLocation(this.position);
    }
    
    // Add instant upward velocity next game tick
    public void jump()
    {
        // Negative for up
        this.velocity.y = -JUMP_STRENGTH;
    }
}
