package maze.graphics;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import maze.gameobjects.Map;
import maze.util.Location;
import maze.util.Position;
import maze.util.XYPair;

/**
 *
 * @author DDvory
 */
public class GraphicsManager {

    private static final int TILE_SIZE = 25;
    private static final float FOV = 90;

    private static Map map;

    private static class TestMap implements Map {

        List<XYPair> walls = new ArrayList<>();
        int types[][] = new int[20][20];
        Dimension size = new Dimension(20, 20);

        public TestMap() {
            types[0] =  new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
            types[1] =  new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[2] =  new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[3] =  new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[4] =  new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[5] =  new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[6] =  new int[]{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1};
            types[7] =  new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[8] =  new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[9] =  new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[10] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[11] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[12] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[13] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[14] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[15] = new int[]{1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1};
            types[16] = new int[]{1, 0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1};
            types[17] = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[18] = new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1};
            types[19] = new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};

            for (int i = 0; i < 20; i++) {
                for (int j = 0; j < 20; j++) {
                    if (types[j][i] == 1) {
                        walls.add(new XYPair(i, j));
                    }
                }
            }
        }

        @Override
        public int getType(int x, int y) {
            if (x < 0 || x >= size.width || y < 0 || y >= size.height) {
                return 1;
            }
            return types[y][x];
        }

        @Override
        public int getType(XYPair pair) {
            return getType(pair.getX(), pair.getY());
        }

        @Override
        public List<XYPair> getWalls() {
            return walls;
        }

        @Override
        public Dimension getSize() {
            return size;
        }

    }
    private static HashMap<Double, ArrayList<XYPair>> objs;
    private static int direction = 0;
    private static Graphics g;
    private static int blocked = 0;

    public static void main(String[] args) {
        map = new TestMap();
        Location pos = new Location(TILE_SIZE, TILE_SIZE, 225.0);

        JFrame f = new JFrame("Test");
        Canvas c = new Canvas();
        c.setBounds(0, 0, TILE_SIZE * map.getSize().width, TILE_SIZE * map.getSize().height);
        f.add(c);
        f.pack();
        f.setVisible(true);
        c.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                // System.out.println(e.getKeyCode());
                switch (e.getKeyCode()) {
                    case 38:
                    case 39:
                    case 40:
                    case 37:
                        direction = e.getKeyCode();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (direction == e.getKeyCode()) {
                    direction = 0;
                }
            }
        });

        c.createBufferStrategy(3);
        long lastFPS = System.currentTimeMillis();
        int fps = 0;
        while (true) {

            double x, y;
            switch (direction) {
                case 38:
                    y = Math.sin(Math.toRadians(pos.getYaw() % 90));
                    x = Math.cos(Math.toRadians(pos.getYaw() % 90));
                    if (pos.getYaw() >= 90 && pos.getYaw() < 180) {
                        x *= -1;

                    } else if (pos.getYaw() >= 180 && pos.getYaw() < 270) {
                        y = y + x;
                        x = y - x;
                        y = y - x;

                    } else if (pos.getYaw() >= 270) {
                        y *= -1;
                    } else if (pos.getYaw() < 90) {
                        y = y + x;
                        x = y - x;
                        y = y - x;
                        x *= -1;
                        y *= -1;
                    }

                    pos.setX(pos.getX() + x);
                    pos.setY(pos.getY() + y);
                    break;
                case 40:
                    y = Math.sin(Math.toRadians(pos.getYaw() % 90)) * -1;
                    x = Math.cos(Math.toRadians(pos.getYaw() % 90)) * -1;
                    if (pos.getYaw() >= 90 && pos.getYaw() < 180) {
                        x *= -1;

                    } else if (pos.getYaw() >= 180 && pos.getYaw() < 270) {
                        y = y + x;
                        x = y - x;
                        y = y - x;

                    } else if (pos.getYaw() >= 270) {
                        y *= -1;
                    } else if (pos.getYaw() < 90) {
                        y = y + x;
                        x = y - x;
                        y = y - x;
                        x *= -1;
                        y *= -1;
                    }
                    pos.setX(pos.getX() + x);
                    pos.setY(pos.getY() + y);
                    break;
                case 37:
                    pos.setYaw((pos.getYaw() + 0.5) % 360);
                    break;
                case 39:
                    pos.setYaw((360 + pos.getYaw() - 0.5) % 360);
            }

            BufferStrategy buff = c.getBufferStrategy();
            g = buff.getDrawGraphics();
            Dimension size = map.getSize();
            g.setColor(Color.white);
            g.fillRect(0, 0, size.width * TILE_SIZE, size.height * TILE_SIZE);

            objs = selectVisibleObjects(pos);
            g.setColor(Color.BLACK);
            for (int i = 0; i < size.width; i++) {
                for (int j = 0; j < size.height; j++) {
                    int type = map.getType(i, j);
                    if (type == 1) {
                        g.fillRect(i * TILE_SIZE, j * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }

                }

            }

            List<Double> arrayList = new ArrayList<Double>(objs.keySet());
            Collections.sort(arrayList, new Comparator<Double>() {
                @Override
                public int compare(Double o1, Double o2) {
                    return o1.compareTo(o2) * -1;
                }
            });
            g.setColor(Color.red);
            for (Double d : arrayList) {
                ArrayList<XYPair> get = objs.get(d);
                for (XYPair xYPair : get) {
                    g.fillRect(xYPair.getX() * TILE_SIZE, xYPair.getY() * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }

            g.setColor(Color.blue);
            g.fillRect((int) pos.getX() - TILE_SIZE / 2, (int) pos.getY() - TILE_SIZE / 2, TILE_SIZE, TILE_SIZE);
            buff.show();
            fps++;
            if (lastFPS + 1000 < System.currentTimeMillis()) {
                lastFPS = System.currentTimeMillis();
                System.out.println("FPS: " + fps + " BLOCKED: " + (blocked/fps));
                fps = blocked = 0;
            }

            try {
                Thread.sleep(10L);
            } catch (InterruptedException ex) {
                Logger.getLogger(GraphicsManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private static HashMap<Double, ArrayList<XYPair>> selectVisibleObjects(Location loc) {
        HashMap<Double, ArrayList<XYPair>> ret = new HashMap<>();
        ArrayList<XYPair> pairs = new ArrayList<>();
        List<XYPair> walls = map.getWalls();
        walls.forEach((wall) -> {
            Position pos = getPos(wall);
            Position pos2 = new Position(pos.getX() + TILE_SIZE, pos.getY());
            Position pos3 = new Position(pos.getX(), pos.getY() + TILE_SIZE);
            Position pos4 = new Position(pos.getX() + TILE_SIZE, pos.getY() + TILE_SIZE);
            if (canSee(loc, pos, ret,pairs)) {
                double dist = Math.sqrt(Math.pow(pos.getX() - loc.getX(), 2) + Math.pow(pos.getY() - loc.getY(), 2));
                if (!pairs.contains(wall)) {
                    if (!ret.containsKey(dist)) {
                        ret.put(dist, new ArrayList<>());
                    }
                    ret.get(dist).add(wall);
                    pairs.add(wall);
                } else {
                    blocked++;
                }

            } else if (canSee(loc, pos2, ret,pairs)) {
                double dist = Math.sqrt(Math.pow(pos2.getX() - loc.getX(), 2) + Math.pow(pos2.getY() - loc.getY(), 2));
                if (!pairs.contains(wall)) {
                    if (!ret.containsKey(dist)) {
                        ret.put(dist, new ArrayList<>());
                    }
                    ret.get(dist).add(wall);
                    pairs.add(wall);
                } else {
                    blocked++;
                }
            } else if (canSee(loc, pos3, ret,pairs)) {
                double dist = Math.sqrt(Math.pow(pos3.getX() - loc.getX(), 2) + Math.pow(pos3.getY() - loc.getY(), 2));
                if (!pairs.contains(wall)) {
                    if (!ret.containsKey(dist)) {
                        ret.put(dist, new ArrayList<>());
                    }
                    ret.get(dist).add(wall);
                    pairs.add(wall);
                } else {
                    blocked++;
                }
            } else if (canSee(loc, pos4, ret,pairs)) {
                double dist = Math.sqrt(Math.pow(pos4.getX() - loc.getX(), 2) + Math.pow(pos4.getY() - loc.getY(), 2));
                if (!pairs.contains(wall)) {
                    if (!ret.containsKey(dist)) {
                        ret.put(dist, new ArrayList<>());
                    }
                    ret.get(dist).add(wall);
                    pairs.add(wall);
                } else {
                    blocked++;
                }
            }
        });

        return ret;
    }

    private static boolean canSee(Location loc, Position pos, HashMap<Double, ArrayList<XYPair>> ret,ArrayList<XYPair> pairs) {
        double dx = pos.getX() - loc.getX();
        double dy = pos.getY() - loc.getY();
        double angle = Math.toDegrees(Math.atan(Math.abs(dy / dx)));
        if (dx < 0 && dy >= 0) {
            angle += 90;
        } else if (dx >= 0 && dy >= 0) {
            angle = 180 + (90 - angle);
        } else if (dx >= 0 && dy < 0) {
            angle += 270;
        } else {
            angle = 90 - angle;
        }

        g.setColor(Color.ORANGE);
        /* int anglee = (int) (angle * 100);
        g.drawString("" + (anglee / 100.0), (int) pos.getX() + 50, (int) pos.getY());*/
        if (!inAngle(loc.getYaw(), angle)) {
            return false;
        }
        if (Math.abs(dx) > Math.abs(dy)) {
            double a = Math.abs(dx / TILE_SIZE);
            a *= 25;
            dx /= a;
            dy /= a;
        } else {
            double a = Math.abs(dy / TILE_SIZE);
            a *= 25;
            dx /= a;
            dy /= a;
        }
        double x = loc.getX();
        double y = loc.getY();
        g.setColor(Color.gray);
        while (true) {
            x += dx;
            y += dy;
            XYPair p = getTile(x, y);
            g.drawRect((int) x, (int) y, 1, 1);
            if (Math.abs(x - pos.getX()) < 1 && Math.abs(y - pos.getY()) < 1) {
                return true;
            }

            if (map.getType(p) == 1) {
                double dist = Math.sqrt(Math.pow(x - loc.getX(), 2) + Math.pow(y - loc.getY(), 2));
                if (!pairs.contains(p)) {
                    if (!ret.containsKey(dist)) {
                        ret.put(dist, new ArrayList<>());
                    }
                    ret.get(dist).add(p);
                    pairs.add(p);
                } else {
                    blocked++;
                }
                return false;
            }
        }

    }

    private static boolean inAngle(double yaw, double angle) {

        double min = (360 + yaw - (FOV / 2)) % 360;
        double max = (yaw + (FOV / 2)) % 360;
        if (min > max) {
            if (!(min >= angle && angle >= max)) {
                //System.out.println("Orig2: " + yaw + "  To: " + angle);
                return true;
            }
        } else {
            if (min < angle && angle < max) {
                // System.out.println("Orig: " + yaw + "  To: " + angle);
                return true;
            }
        }
        return false;
    }

    private static XYPair getTile(Location loc) {
        return getTile(loc.getX(), loc.getY());
    }

    private static XYPair getTile(double x, double y) {
        return new XYPair((int) (x / TILE_SIZE), (int) (y / TILE_SIZE));
    }

    private static Location getLoc(XYPair pair, float yaw) {
        return new Location(pair.getX() * TILE_SIZE, pair.getY() * TILE_SIZE, yaw);
    }

    private static Position getPos(XYPair pair) {
        return new Position(pair.getX() * TILE_SIZE, pair.getY() * TILE_SIZE);
    }

}
