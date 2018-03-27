package maze.gameobjects;

import java.awt.Dimension;

/**
 *
 * @author DDvory
 */
public interface Map {
    public int getType(int x,int y); //0 - chodba, 1 - stena
    public Dimension getSize();
    
}
