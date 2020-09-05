package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class test {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static final int initialX = 1;
    public static final int intialY = 1;
    public static int currentX = 25;
    public static int currentY = 25;

    /** This method will build one room or hallway of rectangular shape
     *
     * @param tiles
     * @param sideWidth: length of room/hallway along x axis
     * @param sideHeight: length of room/hallway along y axis
     * @param cornerX: x value of the left top corner (assuming x axis points to the right)
     * @param cornerY: y value of the left top corner (assuming y axis points downwards)
     */
    private static void RoomBuilder(TETile[][] tiles, int sideWidth, int sideHeight,
                                    int cornerX, int cornerY) {
        // build the floor within the room and/or hallway
        for (int y = 1; y < sideHeight-1; y++) {
            for (int x = 1; x < sideWidth-1; x++) {
                if (cornerX + x < 0 || cornerX + x >= WIDTH || cornerY + y < 0 ||cornerY + y >= HEIGHT) {
                    continue;
                }
                tiles[cornerX + x][cornerY + y] = Tileset.FLOOR;
            }
        }

        // build the wall surrounding the room and/or hallway
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[cornerX + x][cornerY] == Tileset.FLOOR) {
                continue;
            }
            tiles[cornerX + x][cornerY] = Tileset.WALL;
        }
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[cornerX + x][cornerY + sideHeight - 1] == Tileset.FLOOR) {
                continue;
            }
            tiles[cornerX + x][cornerY + sideHeight - 1] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[cornerX][cornerY + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[cornerX][cornerY + y] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[cornerX + sideWidth - 1][cornerY + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[cornerX + sideWidth - 1][cornerY + y] = Tileset.WALL;
        }

    }

    public static void RandomRooms(TETile[][] tiles) {
        int sidewidth = RANDOM.nextInt(10);
        int sideheight = RANDOM.nextInt(10);
        int cornerx = currentX;
        int cornery = currentY;
        RoomBuilder(tiles, sidewidth, sideheight, cornerx, cornery);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        TETile[][] Tiles = new TETile[WIDTH][HEIGHT];

        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                Tiles[x][y] = Tileset.NOTHING;
            }
        }

        for (int i = 0; i < 10; i++) {
            RandomRooms(Tiles);
        }

        ter.renderFrame(Tiles);
    }
}
