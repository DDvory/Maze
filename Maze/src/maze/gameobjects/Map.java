package maze.gameobjects;

import java.awt.Dimension;
import java.util.List;
import maze.util.XYPair;

/**
 *
 * @author DDvory
 */
public interface Map {
    public int getType(int x,int y); //0 - chodba, 1 - stena
    public int getType(XYPair pair); // ak je za hranicou vrat stenu
    public List<XYPair> getWalls();
    public Dimension getSize();
    
}
