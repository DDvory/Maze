package maze.graphics;

import maze.util.XYPair;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JFrame;
import maze.gameobjects.Map;
import maze.util.Location;

/**
 *
 * @author DDvory
 */
public class GraphicsManager {
    
    private static final int TILE_SIZE = 25;
    private static final float FOV = 120;
    
    private static Map map;
    private static int rotation = 0; // 0 - 0°,1-90°,2-
    
    private HashMap<Integer,ArrayList<XYPair>> selectVisibleObjects(Location loc) {
        double lastAngle = 0;
        HashMap<Integer,ArrayList<XYPair>> ret = new HashMap<>();
        int dist=0;
        while (true) {
            
            
            
            
            if (lastAngle > FOV) {
                break;
            }
        }
        return ret;
    }
    
    private boolean canSee(Location loc,Position pos) {
        
    }
    
    
    private int calculateWidth() {
        
    }
    
    
    private XYPair getTile(Location loc) {
        return new XYPair((int)(loc.getX()/TILE_SIZE),(int)(loc.getY()/TILE_SIZE));
    }
    
    private Location getLoc(XYPair pair,float yaw) {
        return new Location(pair.getX()*TILE_SIZE,pair.getY()*TILE_SIZE,yaw);
    }
    
}
