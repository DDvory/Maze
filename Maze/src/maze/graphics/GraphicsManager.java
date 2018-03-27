package maze.graphics;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import maze.gameobjects.Map;
import maze.util.Location;
import maze.util.Position;
import maze.util.XYPair;

/**
 *
 * @author DDvory
 */
public class GraphicsManager {
    
    private static final double TILE_SIZE = 25;
    private static final float FOV = 120;
    
    private static Map map;
    private static int rotation = 0; // 0 - 0°,1-90°,2-
    
    private HashMap<Double,ArrayList<XYPair>> selectVisibleObjects(Location loc) {
        HashMap<Double,ArrayList<XYPair>> ret = new HashMap<>();
        List<XYPair> walls = map.getWalls();
        walls.forEach((wall) -> {
            Position pos = getPos(wall);
            if (canSee(loc,pos)) {
                double dist = Math.sqrt(Math.pow(pos.getX()-loc.getX(),2)+Math.pow(pos.getY()-loc.getY(),2));
                if (!ret.containsKey(dist)) {
                    ret.put(dist, new ArrayList<>());
                }
                ret.get(dist).add(wall);
            }
        });
        
        
        return ret;
    }
    
    private boolean canSee(Location loc,Position pos) {
        double dx = pos.getX()-loc.getX();
        double dy = pos.getY()-loc.getY();
        double angle = Math.atan(dy/dx);
        if (!inAngle(loc.getYaw(),angle)) {
            return false;
        }
        if (dx > dy) {
            double a = dx / TILE_SIZE;
            a*= 25;
            dx /= a;
            dy /= a;
        } else {
            double a = dy / TILE_SIZE;
            a*=25;
            dx /= a;
            dy /= a;
        }
        double x = loc.getX();
        double y = loc.getY();
        while (true) {
            x += dx;
            y += dy;
            XYPair p = getTile(x,y);
            
            if (Math.abs(x-pos.getX()) < 0.5 && Math.abs(y - pos.getY()) < 0.5) {
                return true;
            }
            
            if (map.getType(p) == 1) {
                return false;
            }
        }
        
    }
    
    private boolean inAngle(double yaw,double angle) {
        double min = (360 + yaw-(FOV/2))%360;
        double max = (yaw + (FOV/2)) % 360;
        if (min > max) {
            if (!(min >= angle && angle >= max)) {
                return true;
            }
        } else {
            if (min < angle && angle < max) {
                return true;
            }
        }
        return false;
    }
    
    private XYPair getTile(Location loc) {
        return getTile(loc.getX(),loc.getY());
    }
    private XYPair getTile(double x,double y) {
        return new XYPair((int)(x/TILE_SIZE),(int)(y/TILE_SIZE));
    }
    
    private Location getLoc(XYPair pair,float yaw) {
        return new Location(pair.getX()*TILE_SIZE,pair.getY()*TILE_SIZE,yaw);
    }
    
    private Position getPos(XYPair pair) {
        return new Position(pair.getX()*TILE_SIZE,pair.getY()*TILE_SIZE);
    }
    
}
