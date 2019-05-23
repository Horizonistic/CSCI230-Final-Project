package csci.pkg230.pkgfinal.project;

import java.awt.Point;
import java.util.Random;
import javax.swing.text.Position;

public class Obstacle extends Entity
{
    public Obstacle(Type type, Point position)
    {
        super(Entity.Type.OBSTACLE, position);
    }
    
}
