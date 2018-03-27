package maze.util;

/**
 *
 * @author DDvory
 */
public class XYPair {
    
    private final int x,y;

    public XYPair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    

    /**
     * Get the value of y
     *
     * @return the value of y
     */
    public int getY() {
        return y;
    }

    
    /**
     * Get the value of x
     *
     * @return the value of x
     */
    public int getX() {
        return x;
    }

}
