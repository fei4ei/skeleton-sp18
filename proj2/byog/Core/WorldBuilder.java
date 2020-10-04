package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Random;

public class WorldBuilder {
    TERenderer ter;
    TETile[][] Tiles;
    private static long SEED;
    private static Random RANDOM;
    private static final int WIDTH = 60;
    private static final int HEIGHT = 40;
    private static int width = 54; // width and heights are measurements of the inner bound.
    private static int height = 34;
    int[] playerPos = new int[2];

    public WorldBuilder(long seed) {
        SEED = seed;
        RANDOM = new Random(SEED);
        ter = new TERenderer();
//        ter.initialize(WIDTH, HEIGHT);

        Tiles = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                Tiles[i][j] = Tileset.NOTHING;
            }
        }

        int num = 20;
        int[] x = randomX(width, num);
        int[] y = randomY(height, num);

        for (int i = 1; i < num; i++) {
            int[] lastPos = {x[i - 1], y[i - 1]};
            int[] currPos = {x[i], y[i]};
            PointConnector(Tiles, lastPos, currPos);
            int sideH = RANDOM.nextInt(5)+1;
            int sideW = RANDOM.nextInt(5)+1;
            RoomBuilder(Tiles, sideH, sideW, currPos);
        }

        playerPos[0] = x[0];
        playerPos[1] = y[0];
        Tiles[x[0]][y[0]] = Tileset.PLAYER;

        WallBuilder(Tiles, WIDTH, HEIGHT);
//        rb.ter.renderFrame(rb.Tiles);

    }

    /** Note that outermost layer cannot have Tileset.FLOOR: the wallbuilder will not wrap around this layer
     *
     * @param tiles
     */
    public static void WallBuilder(TETile[][] tiles, int WIDTH, int HEIGHT) {
        for (int x = 1; x < WIDTH-1; x++) {
            for (int y = 1; y < HEIGHT-1; y++) {
                if (tiles[x][y] == Tileset.FLOOR) {
                    wallbuilder(tiles, x, y);
                }
            }
        }
    }

    private static void wallbuilder(TETile[][] tiles, int x, int y) {
        for (int i = x-1; i<= x+1; i++) {
            for (int j = y-1; j <= y+1; j++) {
                if (tiles[i][j] == Tileset.NOTHING) {
                    tiles[i][j] = Tileset.WALL;
                }
            }
        }
    }


    /** This method will build one room or hallway of rectangular shape
     *
     * @param tiles
     * @param sideWidth: length of room/hallway along x axis
     * @param sideHeight: length of room/hallway along y axis
     * @param pos: x,y coordinates of the left top corner (assuming x axis points to the right)
     *
     */
    public static void RoomBuilder(TETile[][] tiles, int sideWidth, int sideHeight, int[] pos) {
        // build the floor within the room and/or hallway
        for (int y = 0; y < sideHeight; y++) {
            for (int x = 0; x < sideWidth; x++) {
                tiles[(pos[0] + x)][(pos[1] + y)] = Tileset.FLOOR;
            }
        }
    }

    public static void PointConnector(TETile[][] tiles, int[] lastPos, int[] Pos) {
        if (lastPos.equals(Pos)) {
            return;
        }

        int minX = Math.min(lastPos[0], Pos[0]);
        int maxX = Math.max(lastPos[0], Pos[0]);
        int minY = Math.min(lastPos[1], Pos[1]);
        int maxY = Math.max(lastPos[1], Pos[1]);

        // draw a horizontal floor
        for (int x = 0; x < maxX - minX + 1; x++) {
            tiles[minX + x][Pos[1]] = Tileset.FLOOR;
        }

        //draw a vertical floor
        for (int y = 0; y < maxY - minY + 1; y++) {
            tiles[lastPos[0]][(minY + y)] = Tileset.FLOOR;
        }
    }

    /** This method generates an int array of sorted numbers from smallest to largest.
     * Each number is generated as a random number between 0 and width.
     * @param largest: each int is between [0, width].
     * @param size: how many int in the array.
     * @return
     */
    public int[] randomY(int largest, int size) {
        int[] results = new int[size];
        for (int i = 0; i < size; i++) {
            results[i] = RANDOM.nextInt(largest) + 1;
        }
        return results;
    }

    public int[] randomX(int largest, int size) {
        int[] results = randomY(largest,size);
        Arrays.sort(results);
        return results;
    }
}
