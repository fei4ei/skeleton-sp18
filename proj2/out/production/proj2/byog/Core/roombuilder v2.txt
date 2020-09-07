package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoomBuilder {
    private static final int WIDTH = 50;
    private static final int HEIGHT = 50;
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static HashSet FloorsCoord = new HashSet();


    /** This method will build one room or hallway of rectangular shape
     *
     * @param tiles
     * @param sideWidth: length of room/hallway along x axis
     * @param sideHeight: length of room/hallway along y axis
     * @param pos: x,y coordinates of the left top corner (assuming x axis points to the right)
     *
     */
    private static void RoomBuilder(TETile[][] tiles, int sideWidth, int sideHeight, int[] pos) {
        // build the floor within the room and/or hallway
        for (int y = 1; y < sideHeight-1; y++) {
            for (int x = 1; x < sideWidth-1; x++) {
                if (pos[0] + x < 0 || pos[0] + x >= WIDTH || pos[1] + y < 0 || pos[1] + y >= HEIGHT) {
                    continue;
                }
                tiles[pos[0] + x][pos[0] + y] = Tileset.FLOOR;
                FloorsCoord.add(new int[]{pos[0] + x, pos[0] + y});
            }
        }


        // build the wall surrounding the room and/or hallway.
        // TODO: ensure x,y coordiates of walls are between 0-WIDTH for x and 0-HEIGHT for y
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[pos[0] + x][pos[1]] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + x][pos[1]] = Tileset.WALL;
        }
        for (int x = 0; x < sideWidth; x++) {
            if (tiles[pos[0] + x][pos[1] + sideHeight - 1] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + x][pos[1] + sideHeight - 1] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[pos[0]][pos[1] + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0]][pos[1] + y] = Tileset.WALL;
        }

        for (int y = 0; y < sideHeight; y++) {
            if (tiles[pos[0] + sideWidth - 1][pos[1] + y] == Tileset.FLOOR) {
                continue;
            }
            tiles[pos[0] + sideWidth - 1][pos[1] + y] = Tileset.WALL;
        }


    }

    public static void RandomRooms(TETile[][] tiles) {
        int sidewidth = RANDOM.nextInt(10);
        int sideheight = RANDOM.nextInt(10);
        int[] pos = RandomElement(FloorsCoord);
        RoomBuilder(tiles, sidewidth, sideheight, pos);
    }

    public static int[] RandomElement(HashSet<int[]> positions) {
        int size = positions.size();
        int item = new Random().nextInt(size); // In real life, the Random object should be rather more shared than this
        int i = 0;
        for(int[] pos : positions) {
            if (i == item) {
                return pos;
            }
            i++;
        } return null;
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

        FloorsCoord.add(new int[]{25,25});
        for (int i = 0; i < 2; i++) {
            RandomRooms(Tiles);
        }

        ter.renderFrame(Tiles);
    }
}
